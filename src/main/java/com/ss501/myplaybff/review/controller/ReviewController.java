package com.ss501.myplaybff.review.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss501.myplaybff.playground.service.PlaygroundService;
import com.ss501.myplaybff.review.dto.Questioner;
import com.ss501.myplaybff.review.dto.PersonalMember;
import com.ss501.myplaybff.review.dto.PlaygroundDto;
import com.ss501.myplaybff.review.dto.Qna;
import com.ss501.myplaybff.review.dto.Review;
import com.ss501.myplaybff.review.dto.ReviewDto;
import com.ss501.myplaybff.review.service.ReviewService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

// URL-화면 매핑용 컨트롤러
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ReviewController {

	private final PlaygroundService playgroundService;
	private final ReviewService reviewService;
	private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
	
	@GetMapping("/review/registration/{playgroundId}")
	public String getReview(HttpServletRequest request, HttpSession session, @PathVariable("playgroundId") String playgroundId, Model model) {
		/*
		 * if (session.getAttribute("id") == null) { log.info("Login Id : " +
		 * session.getAttribute("id").toString()); return "redirect:/login"; }
		 */
		String returnUrl = "";

		String userId = null;
		String userName = null;
		
		if (session.getAttribute("id") == null || session.getAttribute("name") == null) {
			userId = "abc@mail.com";
			userName = "notLogonUser";
			// return "redirect:/login";
		} else {
			log.info("Login Id : " + session.getAttribute("id").toString());
			userId = session.getAttribute("id").toString();
			userName = session.getAttribute("name").toString();
			//userName = "testUser";
		}
		
		try {
			com.ss501.myplaybff.playground.dto.PlaygroundDto registeredPlaygroundDto = playgroundService.getPlaygroundByPlaygroundId(playgroundId);
			
			String logMsg = "playgroundList : ";
			if(registeredPlaygroundDto != null)
				logMsg += registeredPlaygroundDto.toString();
			log.info(logMsg);
			
			PlaygroundDto playgroundDto = new PlaygroundDto();
			
			//playgroundDto.setPlaygroundId(registeredPlaygroundDto.getPlaygroundId());
			playgroundDto.setId(Long.parseLong(playgroundId));
			playgroundDto.setName(registeredPlaygroundDto.getName());
//			playgroundDto.setPlaygroundId((long) 13);
//			playgroundDto.setName("ipark-footsal");
			
			PersonalMember personalMember = new PersonalMember();
			personalMember.setPersonalMemberId(userId);
			personalMember.setPersonalMemberName(userName);
	
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
			Calendar time = Calendar.getInstance();
			String now = format1.format(time.getTime());
			log.info("now : " + now);
			
			model.addAttribute("playgroundInfo", playgroundDto);
			model.addAttribute("personalMember", personalMember);
			model.addAttribute("time", now);
			
			returnUrl = "review/registerReview";
		} catch (Exception e) {
			returnUrl = "404";
		}
		return returnUrl;
	}
	

	
	@GetMapping("/qna")
	public String getQna(HttpSession session, Model model) {
		log.info("ReviewController >>  getQna() Called : " + "~/qna");
		System.out.println("ReviewController >>  getQna() Called : " + "~/qna");
		/*
		 * if (session.getAttribute("id") == null) { log.info("Login Id : " +
		 * session.getAttribute("id").toString()); return "redirect:/login"; }
		 */
		String returnUrl = "";
		
		String userId = null;
		String userName = null;
		
		if (session.getAttribute("id") == null || session.getAttribute("name") == null) {
			userId = "abc@mail.com";
			userName = "notLogonUser";
			// return "redirect:/login";
		} else {
			log.info("Login Id : " + session.getAttribute("id").toString());
			userId = session.getAttribute("id").toString();
			userName = session.getAttribute("name").toString();
			//userName = "testUser";
		}
		
		model.addAttribute("loginUserId", userId);
		model.addAttribute("loginUserName", userName);
		
		try {
			List<com.ss501.myplaybff.playground.dto.PlaygroundDto> playgroundList = playgroundService.getAllPlayground();
			
			String logMsg = "";
			if(playgroundList != null && playgroundList.size() > 0)	
				logMsg += playgroundList.get(0).toString();
			else	
				logMsg += "playgroundList.size() = 0";
			log.info(logMsg);
			
			model.addAttribute("playgroundList", playgroundList);
			returnUrl = "review/registerQna";
		} catch(Exception e) {
			returnUrl = "error";
		}
		return "review/registerQna";
	}
	
	@GetMapping("/review/reviews/{playgroundId}")
	public String findReviewsByPlaygroundId(@PathVariable Long playgroundId, Model model) {
		String returnUrl = "";
		System.out.println("===== ReviewRestController : findReviewsByPlaygroundId(Long playgroundId) =====/ playgroundId : " + playgroundId.toString());

		ResponseEntity<List<ReviewDto>> response = null;
		try {
			List<Review> reviewList = reviewService.findReviewsByPlaygroundId(playgroundId);
			model.addAttribute("reviewList", reviewList);

			String logMsg = "Response : ";
			if(reviewList != null && reviewList.size() > 0)
				logMsg = logMsg + reviewList.get(0).toString();
			else 
				logMsg = logMsg + "reviewList.size() == 0";
			log.info(logMsg);
			
			
			returnUrl = "review/reviewList";
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			returnUrl = "error";
		}	  

		return returnUrl;
	}
	
	@GetMapping("/qna/qnaList")
	public String getQnaList(HttpSession session, Model model) {
		log.info("ReviewController >>  getQnaList() Called : " + "~/qna/qnaList");
		System.out.println("ReviewController >>  getQnaList() Called : " + "~/qna/qnaList");
		/*
		 * if (session.getAttribute("id") == null) { log.info("Login Id : " +
		 * session.getAttribute("id").toString()); return "redirect:/login"; }
		 */
		
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
		
		String returnUrl = null;
		String memberType = null;
		memberType = Objects.toString( session.getAttribute("type")== null ? "corporate" : session.getAttribute("type") , "" );
		//memberType = "P"; // 법인회원(c) 라고 가정
				
		try {
			List<Qna> qnaList = new ArrayList<>();
			
			if(memberType.equals("corporate")) { // 법인회원인 경우
				List<com.ss501.myplaybff.playground.dto.PlaygroundDto> playgroundList = playgroundService.getAllPlaygroundByCorporateId(userId);
				
				String logMsg = "playgroundList : ";
				if(playgroundList != null && playgroundList.size() > 0)	logMsg += playgroundList.get(0).toString();
				else	logMsg += "playgroundList.size() = 0";
				log.info(logMsg);
				
				List<Long> playgroundIdList = new ArrayList<>();
				
				for(int i=0; i < playgroundList.size(); i++) {
					playgroundIdList.add(playgroundList.get(i).getId());
				}
					
				qnaList = reviewService.findQnaByPlaygroundIds(playgroundIdList);
				
				String logMsg2 = "qnaList : ";
				if(qnaList != null && qnaList.size() > 0)	logMsg2 += qnaList.get(0).toString();
				else	logMsg2 += "qnaList.toString()";
				log.info(logMsg2);
				
				returnUrl = "review/qnaListCorp";
			} else { // if(memberType.equals("personal")) // 개인회원인 경우
				qnaList = reviewService.findQnaByQuestionerId(userId);
				
				returnUrl = "review/qnaList";
			}
			model.addAttribute("qnaList", qnaList);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return "error";
		}
		
		return returnUrl;
	}
	
//	@GetMapping("/qna/qnaListCorp")
//	public String getQnaListCorp(HttpSession session, Model model) {
//		/*
//		 * if (session.getAttribute("id") == null) { log.info("Login Id : " +
//		 * session.getAttribute("id").toString()); return "redirect:/login"; }
//		 */
//		
//		//String memberType = Objects.toString(session.getAttribute("type"), "");
//		
//		String userId = null;
//		String userName = null;
//
//		userId = "abc@mail.com";
//		userName = "testUser";
//		String corporateId = userId;
//		
//		try {
//			List<Long> playgroundIdList = new ArrayList<Long>();
//			playgroundIdList.add((long)1);
//			playgroundIdList.add((long)2);
//			
//			List<com.ss501.myplaybff.playground.dto.PlaygroundDto> playgroundList = playgroundService.getAllPlayground();
//			log.info("playgroundList : " + playgroundList.get(0).toString());
//			
//			List<Qna> qnaList = reviewService.findQnaByCorpdId(userId);
//			log.info("qnaList : " + qnaList.toString());
//			model.addAttribute("qnaList", qnaList);
//
//		} catch (Exception e) {
//			log.debug(e.getMessage(), e);
//			return "/404";
//		}
//		
//		return "/review/qnaListCorp";
//	}
	
	



}
