package com.ss501.myplaybff.review.service.impl;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ss501.myplaybff.playground.service.PlaygroundService;
import com.ss501.myplaybff.review.dto.Answerer;
import com.ss501.myplaybff.review.dto.PersonalMember;
import com.ss501.myplaybff.review.dto.Playground;
import com.ss501.myplaybff.review.dto.Qna;
import com.ss501.myplaybff.review.dto.QnaDto;
import com.ss501.myplaybff.review.dto.Questioner;
import com.ss501.myplaybff.review.dto.Review;
import com.ss501.myplaybff.review.dto.ReviewDto;
import com.ss501.myplaybff.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final PlaygroundService playgroundService;
	private RestTemplate restTemplate;
	private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

	@Value("${api.review.url}")
	private String apiGatewayUrl;

	@Override
	public List<ReviewDto> findAllReviews() {
		List<ReviewDto> listReviewDto = new ArrayList<ReviewDto>();
		// TODO Auto-generated method stub
		//
		return listReviewDto;
	}

	@Override
	public List<Review> findReviewsByPlaygroundId(Long playgroundId) {
		// TODO Auto-generated method stub
		log.info("ReviewServiceImpl > findReviewsByPlaygroundId(playgroundId) : " + playgroundId);
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	  	log.info(String.format("%s%s", apiGatewayUrl, "/review/api/v1/reviews/{playgroundId}") + " call");
	  	restTemplate = new RestTemplate();
        ResponseEntity<Review[]> result = restTemplate.getForEntity(String.format("%s%s%s", apiGatewayUrl, "/review/api/v1/reviews/", playgroundId), Review[].class);
        
        log.info("result : " + result.getBody().toString());
        
        return Arrays.asList(result.getBody());
	}

	@Override
	public ReviewDto findReviewByReviewId(Long reviewId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReviewDto saveReview(ReviewDto reviewDto) {
		// TODO Auto-generated method stub
		log.info("ReviewServiceImpl > saveReview() : ");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		PersonalMember writer = new PersonalMember();
		writer.setPersonalMemberId(reviewDto.getWriterId());
		writer.setPersonalMemberName(reviewDto.getWriterName());
		
		Review review = new Review();
		review.setWriter(writer);
		review.setPlaygroundId(reviewDto.getPlaygroundId());
		review.setContents(reviewDto.getContents());
		review.setCreateDate(reviewDto.getCreateDate());
		review.setMark(reviewDto.getMark());
		review.setReviewStatus("REGISTERED");

		HttpEntity<Review> request = new HttpEntity<>(review, headers);
		log.info(String.format("%s%s", apiGatewayUrl, "/review/api/v1/review") + " call");

		restTemplate = new RestTemplate();
        ResponseEntity<Review> result = restTemplate.postForEntity(String.format("%s%s", apiGatewayUrl, "/review/api/v1/review"), request, Review.class);	
        log.info("result.getBody().toString() : " + result.getBody().toString());
        reviewDto.setId(result.getBody().getId());
        return reviewDto;
	}

	@Override
	public QnaDto saveQuestion(QnaDto qnaDto) {
		// TODO Auto-generated method stub
		log.info("ReviewServiceImpl > saveQna() : ");
		
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String today = format1.format(time.getTime());
		
		Questioner questioner = new Questioner();
		questioner.setQuestionerId(qnaDto.getQuestionerId());
		questioner.setQuestionerName(qnaDto.getQuestionerName());
		
		Playground playground = new Playground();
		playground.setPlaygroundId(Long.parseLong(qnaDto.getPlaygroundInfo().split("/")[0]));
		playground.setPlaygroundName(qnaDto.getPlaygroundInfo().split("/")[1]);

		Qna qna = new Qna();
		
		qna.setCreateDateQ(today);
		
//		qna.setQuestionerId(qnaDto.getQuestionerId());
//		qna.setQuestionerName(qnaDto.getQuestionerName());
		qna.setQuestioner(questioner); /////
		qna.setQuestion(qnaDto.getQuestion());
		qna.setPlayground(playground);
//		qna.setPlaygroundId(Long.parseLong(qnaDto.getPlaygroundInfo().split("/")[0]));
//		qna.setPlaygroundName(qnaDto.getPlaygroundInfo().split("/")[1]);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		HttpEntity<Qna> request = new HttpEntity<>(qna, headers);
		log.info(String.format("%s%s", apiGatewayUrl, "/review/api/v1/qna") + " call");
		log.info("qnaDto : " + qnaDto.toString());
		restTemplate = new RestTemplate();
        ResponseEntity<Qna> result = restTemplate.postForEntity(String.format("%s%s", apiGatewayUrl, "/review/api/v1/qna"), request, Qna.class);	
        log.info("result.getBody().toString() : " + result.getBody().toString());
        qnaDto.setId(result.getBody().getId());
        return qnaDto;
	}

	@Override
	public List<Qna> findQnaByPlaygroundIds(List<Long> playgroundIdList) {
		// TODO Auto-generated method stub
		log.info("ReviewServiceImpl > findQnaByCorpdId(List<Long> playgroundIdList) : " + playgroundIdList.toString());

		playgroundIdList.add((long)1);
		playgroundIdList.add((long)2);
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity<List<Long>> request = new HttpEntity<List<Long>>(playgroundIdList, headers);
		
		String listOfIds = playgroundIdList.stream().map(Object::toString).collect(Collectors.joining(","));
		
	  	log.info(String.format("%s%s", apiGatewayUrl, "/review/api/v1/qna/corp/{playgroundId}") + " call");
	  	restTemplate = new RestTemplate();
        ResponseEntity<Qna[]> result = restTemplate.getForEntity(String.format("%s%s%s", apiGatewayUrl, "/review/api/v1/qna/corp/", listOfIds), Qna[].class);
        
        log.info(result.getBody().toString());
        
        return Arrays.asList(result.getBody());
	}

	@Override
	public List<Qna> findQnaByQuestionerId(String questionerId) {
		// TODO Auto-generated method stub
		log.info("ReviewServiceImpl > findQnaByQuestionerId(questionerId) : " + questionerId);
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	  	log.info(String.format("%s%s", apiGatewayUrl, "/review/api/v1/qna/{questionerId}") + " call");
	  	restTemplate = new RestTemplate();
        ResponseEntity<Qna[]> result = restTemplate.getForEntity(String.format("%s%s%s", apiGatewayUrl, "/review/api/v1/qna/", questionerId), Qna[].class);

        List<Qna> qnaList = Arrays.asList(result.getBody());
        log.info(qnaList.toString());
        
        return Arrays.asList(result.getBody());
	}

//	@Override
//	public List<Qna> findQnaByCorporateId(String corporateId) {
		// TODO Auto-generated method stub
//		log.info("ReviewServiceImpl > findQnaByCorporateId(corporateId) : " + corporateId);
//		
//		HttpHeaders headers = new HttpHeaders(); 
//		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		
//	  	log.info(String.format("%s%s", apiGatewayUrl, "/review/api/v1/qna/coprp/{corporateId}") + " call");
//	  	restTemplate = new RestTemplate();
//        ResponseEntity<Qna[]> result = restTemplate.getForEntity(String.format("%s%s%s", apiGatewayUrl, "/review/api/v1/qna/corp/", corporateId), Qna[].class);
//
//        List<Qna> qnaList = Arrays.asList(result.getBody());
//        log.info(qnaList.toString());
//        
//        return Arrays.asList(result.getBody());
//		
//		return null;
//	}

	@Override
	public QnaDto saveAnswer(String id, QnaDto qnaDto) {
		// TODO Auto-generated method stub
		
		log.info("ReviewServiceImpl > saveAnswer() : ");
		
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String today = format1.format(time.getTime());
		
		Answerer answerer = new Answerer();
		answerer.setAnswererId(qnaDto.getAnswererId());
		answerer.setAnswererName(qnaDto.getAnswererName());

		Qna qna = new Qna();
		qna.setCreateDateA(today);
		qna.setAnswer(qnaDto.getAnswer());
		qna.setAnswerer(answerer);
		qna.setId(Long.parseLong(id));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		HttpEntity<Qna> request = new HttpEntity<>(qna, headers);
		String url = String.format("%s%s%s", apiGatewayUrl, "/review/api/v1/qna/", id);
		//String url = String.format("%s%s", apiGatewayUrl, "/review/api/v1/qna/", qnaDto.getId());
		
		log.info(url + "put call");
		log.info("qnaDto : " + qnaDto.toString());
		
		restTemplate = new RestTemplate();
		ResponseEntity<Qna> result = restTemplate.exchange(url, HttpMethod.PUT, request, Qna.class);
        //ResponseEntity<Qna> result = restTemplate.postForEntity(String.format("%s%s", apiGatewayUrl, "/review/api/v1/qna/", qnaDto.getId()), request, Qna.class);	
        log.info("result.getBody().toString() : " + result.getBody().toString());
        qnaDto.setId(result.getBody().getId());
        return qnaDto;
	}
}
