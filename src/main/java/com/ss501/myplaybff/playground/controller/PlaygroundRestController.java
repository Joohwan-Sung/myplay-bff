package com.ss501.myplaybff.playground.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss501.myplaybff.playground.dto.PlaygroundDto;
import com.ss501.myplaybff.playground.service.PlaygroundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/playground")
@RequiredArgsConstructor
public class PlaygroundRestController {
	private final PlaygroundService playgroundService;
	private static final Logger log = LoggerFactory.getLogger(PlaygroundRestController.class);
/*	
	@PostMapping("/save111")
	public String callReservation(HttpSession session, @RequestBody PlaygroundDto playgroundDto, Model model) {
		ResponseEntity<PlaygroundDto> response = null;
		log.info("Json1111111111 : " + playgroundDto.toString());
		try {
			response = new ResponseEntity<PlaygroundDto>(playgroundService.registerPlayground(playgroundDto), HttpStatus.OK);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		
		return "redirect:/playground/playgrounds/";
		//return "redirect:/myReservationList/"+response.getBody().getApplicantId();
	}*/
}
