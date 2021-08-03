package com.ss501.myplaybff.review.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ss501.myplaybff.playground.service.PlaygroundService;
import com.ss501.myplaybff.review.dto.PersonalMember;
import com.ss501.myplaybff.review.dto.PlaygroundDto;
import com.ss501.myplaybff.review.dto.Qna;
import com.ss501.myplaybff.review.dto.QnaDto;
import com.ss501.myplaybff.review.dto.Review;
import com.ss501.myplaybff.review.dto.ReviewDto;
import com.ss501.myplaybff.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

// 데이터 조회용 컨트롤러 (RestController)
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ReviewRestController {

	private final ReviewService reviewService;
	private final PlaygroundService playgroundService;
	private static final Logger log = LoggerFactory.getLogger(ReviewRestController.class);





	@PostMapping("/review")
	public ModelAndView saveReview(@ModelAttribute ReviewDto reviewDto, final Model model) {
		ResponseEntity<ReviewDto> response = null;
		ModelAndView mav = new ModelAndView();

		log.info("reviewDto : " + reviewDto.toString());

		try {
			response = new ResponseEntity<ReviewDto>(reviewService.saveReview(reviewDto), HttpStatus.OK);
			mav.setViewName("redirect:playground/playground/"+response.getBody().getPlaygroundId().toString());
			mav.addObject("reviewList", response.getBody());
			return mav;
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			mav.setViewName("redirect:error");
		}

		return mav;
	}

	@PostMapping("/qna")
	public ModelAndView saveQuestion(@ModelAttribute QnaDto qnaDto, final Model model) {
		ResponseEntity<QnaDto> response = null;
		ModelAndView mav = new ModelAndView();

		// log.info("Json : " + reviewDto.toString());

		try {
			log.info("qnaDto : " + qnaDto.toString());
			response = new ResponseEntity<QnaDto>(reviewService.saveQuestion(qnaDto), HttpStatus.OK);
			mav.setViewName("redirect:/qna/qnaList/");
			mav.addObject("qnaList", response.getBody());
			return mav;
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			mav.setViewName("redirect:/error");
		}
		return mav;
	}

	@PutMapping("/answer/{qnaId}")
	public String saveAnswer(HttpSession session, @RequestBody QnaDto qnaDto, @PathVariable("qnaId") String qnaId) {

		String userId = null;
		String userName = null;
		
		if (session.getAttribute("id") == null || session.getAttribute("name") == null) {
			userId = "99999999999";
			userName = "notLogonCorpUser";
			// return "redirect:/login";
		} else {
			log.info("Login Id : " + session.getAttribute("id").toString());
			userId = session.getAttribute("id").toString();
			userName = session.getAttribute("name").toString();
			//userName = "testCorpUser";
		}
		
		String forwardUrl = "/qna/qnaList";

		qnaDto.setAnswererId(userId);
		qnaDto.setAnswererName(userName);	

		ResponseEntity<QnaDto> response = null;

		log.info("qnaDto : " + qnaDto.toString());

		try {
			response = new ResponseEntity<QnaDto>(reviewService.saveAnswer(qnaId, qnaDto), HttpStatus.OK);
			//mav.setViewName("redirect:/qna/qnaListCorp/");
			//mav.addObject("qnaList", response.getBody());
			// return mav;
			if (response == null || response.getStatusCode() != HttpStatus.OK) {
				log.error("answer with id " + qnaId + " not found");
				throw new Exception("answer with id " + qnaId + " not found");
			}
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			return "error";
		}
		return forwardUrl;
	}
}

