package com.ss501.myplaybff.reservation.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ss501.myplaybff.playground.service.PlaygroundService;
import com.ss501.myplaybff.reservation.domain.dto.AvailablePlaygroundDto;
import com.ss501.myplaybff.reservation.domain.dto.Participant;
import com.ss501.myplaybff.reservation.domain.dto.Reservation;
import com.ss501.myplaybff.reservation.domain.dto.ReservationDto;
import com.ss501.myplaybff.reservation.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ReservationController {
	private final ReservationService reservationService;
	private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
	private final PlaygroundService playgroundService;
	/**
	 * reservePlayground.html view
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/reservations/create")
	public String reservePlayground(HttpSession session, @RequestParam String playgroundId, Model model) {
		if (session.getAttribute("id") == null) {
			return "redirect:/login";
		}
		
		AvailablePlaygroundDto playground = new AvailablePlaygroundDto();
		/*playground.setCorporateId("1234");
		playground.setCorporateName("Olympus");
		if (playgroundId.isPresent()) {
			playground.setPlaygroundId(playgroundId.get());
		}
		playground.setPlaygroundType("FOOTBALL");
		*/
		
	    com.ss501.myplaybff.playground.dto.PlaygroundDto registeredPlaygroundDto = playgroundService.getPlaygroundByPlaygroundId(playgroundId);
	      playground.setCorporateId(registeredPlaygroundDto.getCorporateMember().getCorporateId());
	      playground.setCorporateName(registeredPlaygroundDto.getCorporateMember().getCorporateName());
	      
	         playground.setPlaygroundId(registeredPlaygroundDto.getId().toString());
	      
	      playground.setPlaygroundType(registeredPlaygroundDto.getPlaygroundType());
		
		model.addAttribute("playground", playground);
		
		// 객체안의 객체를 JSON으로 만들기 위해서 추가하고 싶은 array만큼 추가
		ReservationDto reservationForm = new ReservationDto();
		reservationForm.setApplicantId(Long.valueOf(session.getAttribute("id").toString()));
		SimpleDateFormat defaultSimpleDateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
        String currentTimestamp = defaultSimpleDateFormat.format(new Date());
        reservationForm.setDate(currentTimestamp.substring(0, 8));
        reservationForm.setStartTime(currentTimestamp.substring(8, 14));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 1);
        String finishTimestamp = defaultSimpleDateFormat.format(cal.getTime());
        reservationForm.setFinishTime(finishTimestamp.substring(8, 14));
        
		reservationForm.addParicipant(new Participant());
		reservationForm.addParicipant(new Participant());
		model.addAttribute("form", reservationForm);
        
		return "reservation/playgroundReservation";
	}
	
	/**
	 * myReservationList.html view
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/reservations")
	public String getAllMyReservation(HttpSession session, @RequestParam Optional<String> applicantId, @RequestParam Optional<String> corporateId, Model model) {
		if (session.getAttribute("id") == null) {
			return "redirect:/login";
		}
		
		log.info("method : GET, URI : /reservations");
		
		try {
			List<Reservation> myReservation = null;
			if (applicantId.isPresent()) {
				myReservation = reservationService.getAllReservationByApplicantId(applicantId.get());
			} else if (corporateId.isPresent()) {
				myReservation = reservationService.getAllReservationByCorporateId(corporateId.get());
			} else {
        		throw new Exception();
        	}
			
			model.addAttribute("reservationList", myReservation);
			
			if (myReservation != null && myReservation.isEmpty() == false) {
				log.info("Response : " + myReservation.get(0).toString());
			}
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
			return "404";
		}
		return "reservation/reservationList";
	}
	
	/**
	 * reservationDetail.html view
	 * @param reservationId
	 * @param model
	 * @return
	 */
	@GetMapping("/reservations/{reservationId}")
	public String getReservationByReservationId(HttpSession session, @PathVariable("reservationId")String reservationId, final Model model) {
		if (session.getAttribute("id") == null) {
			return "redirect:/login";
		}
		
		log.info("method : GET, URI : /reservations/{reservationId}");
		
		try {
			Reservation reservation = reservationService.getReservationByReservationId(reservationId);
			model.addAttribute("reservation", reservation);
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
			return "404";
		}
		return "reservation/reservationDetail";
	}
}
