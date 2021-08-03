package com.ss501.myplaybff.playground.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss501.myplaybff.playground.dto.AddressDto;
import com.ss501.myplaybff.playground.dto.CorporateMemberDto;
import com.ss501.myplaybff.playground.dto.PlaygroundDto;
import com.ss501.myplaybff.playground.service.PlaygroundService;
import com.ss501.myplaybff.review.dto.Review;
import com.ss501.myplaybff.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class PlaygroundController {
	private final PlaygroundService playgroundService;
	private final ReviewService reviewService;

	private static final Logger log = LoggerFactory.getLogger(PlaygroundController.class);
	
	@PostMapping("/playground/save")
	public String savePlayground(HttpSession session, @ModelAttribute PlaygroundDto form, final Model model) {
		ResponseEntity<PlaygroundDto> response = null;
		
		AddressDto ad = new AddressDto();
		CorporateMemberDto cm = new CorporateMemberDto();
		
		ad.setBaseAddress(form.getBaseAddress());
		ad.setDetailAddress(form.getDetailAddress());
		ad.setZipCode(Integer.parseInt(form.getZipCode().toString()));
		log.info("쎄션 id= " + session.getAttribute("id").toString());
		log.info("쎄션 name= " + session.getAttribute("name").toString());
		cm.setCorporateId(session.getAttribute("id").toString());
		cm.setCorporateName(session.getAttribute("name").toString());
		
		//form.setCorporateId(session.getAttribute("id").toString());
		//form.setCorporateName(session.getAttribute("name").toString());
		
		form.setAddress(ad);
		form.setCorporateMember(cm);
		
		try {
			response = new ResponseEntity<PlaygroundDto>(playgroundService.registerPlayground(form), HttpStatus.OK);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return "redirect:/playground/playgrounds/";
	}
	
		//1. 시설물 등록
		@GetMapping("/playground/playground")
		public String registerPlayground(HttpSession session, Model model) {
			if (session.getAttribute("id") == null) {
				return "redirect:/login";
			}
			return "playground/registerPlayground";		
		}
		
		//2. 시설물 전체 조회
		@GetMapping("/playground/playgrounds")
		public String getPlaygrounds(HttpSession session, Model model) {
			System.out.println("시설물 전체 조회");	
			List<PlaygroundDto> playgroundList = playgroundService.getAllPlayground();		
			model.addAttribute("playgroundList", playgroundList);		
			
			return "playground/playgroundView";		
		}
		
		//3. 시설물 상세 조회
		@GetMapping("/playground/playground/{playgroundId}")
		public String getPlaygroundById(HttpSession session, @PathVariable("playgroundId") String playgroundId, Model model) {
			System.out.println("시설물 단건 조회");	
			
			try {
				PlaygroundDto myPlayground = playgroundService.getPlaygroundByPlaygroundId(playgroundId);
				model.addAttribute("playground", myPlayground);
				log.info("Response : " + myPlayground.toString());
				
				// 시설물에 등록된 후기 조회 ------------------------------
				List<Review> reviewList = reviewService.findReviewsByPlaygroundId(Long.parseLong(playgroundId));
				model.addAttribute("reviewList", reviewList);
				String logMsg = "Response : ";
				if(reviewList.size() >= 1)
					logMsg = logMsg + reviewList.get(0).toString();
				else 
					logMsg = logMsg + "reviewList.size() == 0";
				log.info(logMsg);
				// --------------------------------------------------
			}catch(Exception e)
			{
				log.error(e.getMessage(), e);
				return "404";
			}
			
			return "playground/playgroundDetail";		
		}
		
		//3. 시설물 단건 조회 --시설물 개인회원 조회, 법인회원 조회로 변경 필
		@GetMapping("/playground/playgrounds/{corporateId}")
		public String getPlaygroundBCorporateId(HttpSession session, @PathVariable("corporateId") String corporateId, Model model) {
			System.out.println("시설물 법인회원 아이디 넘겨서 조회");	
			
			if (session.getAttribute("id") == null) {
				return "redirect:/login";
			}
			
			try {
				List<PlaygroundDto> myPlayground = playgroundService.getAllPlaygroundByCorporateId(corporateId);
				model.addAttribute("playgroundList", myPlayground);
				//log.info("Response : " + myPlayground.get(0).toString());
			}catch(Exception e)
			{
				log.error(e.getMessage(), e);
				return "404";
			}
			
			return "playground/playgroundView";		
		}
		
		
		@GetMapping("/hpa")
		public String forHPA() {
			double x = 0.0001;
			String hostname ="";
			for (int i =0;i <=1000000;i++) {
				x += Math.sqrt(x);
			}
			try {
				hostname = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return "playground/playgroundView";
		}
}
