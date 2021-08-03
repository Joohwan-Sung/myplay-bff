package com.ss501.myplaybff.reservation.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ss501.myplaybff.reservation.domain.dto.Reservation;
import com.ss501.myplaybff.reservation.domain.dto.ReservationDto;
import com.ss501.myplaybff.reservation.service.ReservationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ReservationRestController {
	private ReservationService reservationService;
	private static final Logger log = LoggerFactory.getLogger(ReservationRestController.class);
	
	/**
	 * 예약 신규 생성 후 신청자 ID의 예약 정보 조회 view로 이동
	 * @param reservationDto
	 * @param model
	 * @return
	 */
	@PostMapping("/reservations")
	public ModelAndView create(@ModelAttribute ReservationDto reservationDto, final Model model) {
		ResponseEntity<ReservationDto> response = null;
		ModelAndView mav = new ModelAndView();
	    
		log.info("method : POST, URI : /reservations");
		log.info("Json : " + reservationDto.toString());
		
		try {
			response = new ResponseEntity<ReservationDto>(reservationService.reserve(reservationDto), HttpStatus.OK);
			mav.setViewName("redirect:/reservations?applicantId="+response.getBody().getApplicantId());
			mav.addObject("reservation", response.getBody());
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			mav.setViewName("redirect:/error");
		}
		
		return mav;
	}
	

	/**
	 * 예약 정보 변경 후 개인/법인 번호 ID의 예약 정보 조회 view로 이동<P>
	 * Note : HTML에서 Form sumit으로 PUT method 호출이 안되어 ajax로 호출함
	 * @param reservationDto
	 * @param reservationId
	 * @return
	 */
	@PutMapping("/reservations/{reservationId}")
	public String update(HttpSession session, @RequestBody ReservationDto reservationDto, @PathVariable("reservationId") String reservationId) {
		String memberType = Objects.toString(session.getAttribute("type"), "");
		String forwardUrl = "/reservations";
		ResponseEntity<Reservation> response = null;
		
		log.info("method : PUT, URI : /reservations/{reservationId}");
		log.info("Json : " + reservationDto.toString());
		
		try {
			response = new ResponseEntity<Reservation>(reservationService.update(reservationId, reservationDto), HttpStatus.OK);
			
			if (response == null || response.getStatusCode() != HttpStatus.OK) {
				log.error("reservation with id " + reservationId + " not found");
				throw new Exception("reservation with id " + reservationId + " not found");
			}
			
			if ("corporate".equals(memberType)) {
				forwardUrl += "?corporateId="+response.getBody().getPlayground().getCorporateMember().getCorporateId();
			} else {
				forwardUrl += "?applicantId="+response.getBody().getApplicant().getPersonalId();
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return "/error";
		}
		
		return forwardUrl;
	}
}
