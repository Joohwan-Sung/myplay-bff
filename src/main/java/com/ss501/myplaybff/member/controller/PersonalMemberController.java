package com.ss501.myplaybff.member.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ss501.myplaybff.member.dto.Address;
import com.ss501.myplaybff.member.dto.PersonalMember;
import com.ss501.myplaybff.member.dto.PersonalMemberDto;
import com.ss501.myplaybff.member.service.PersonalMemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class PersonalMemberController {

	private PersonalMemberService personalMemberService;
	private static final Logger log = LoggerFactory.getLogger(PersonalMemberService.class);
	
	/*model(data) and view */
	@GetMapping("member/PersonalMemberView")
	public String findAllPersonalMember(final Model model) {
		
		List<PersonalMember> response = null;
		response = personalMemberService.findAllPersonalMember();
		log.info("findAllPersonalMember(response) : " + response.toString());
		model.addAttribute("personalMemberList", response);
		log.info("findAllPersonalMember(Model) : " + model.toString());
		
		return "member/PersonalMemberView";
	}
	
	/* view and model(data) */
	@GetMapping("member/PersonalMemberSave")
	public String SavePersonalMember(final Model model) {
		
		return "member/PersonalMemberSave";
	}
	
	@GetMapping("member/PersonalMemberUpdate")
	public String UpdatePersonalMember(@RequestParam Optional<Long> Id, final Model model) {
		
		PersonalMember personalMember = null;	
		PersonalMemberDto personalMemberDto = new PersonalMemberDto();		
		
		if (Id.isPresent()) {
			log.info("UpdatePersonalMember(Id) : " + Id.get());
		}
		
		try {
			personalMember = personalMemberService.findPersonalMember(Id.get());
			
			personalMemberDto.setId(personalMember.getId());
			personalMemberDto.setName(personalMember.getName());
			personalMemberDto.setEmail(personalMember.getEmail());
			personalMemberDto.setMobile(personalMember.getMobile());
			personalMemberDto.setZipCode(personalMember.getAddress().getZipCode());
			personalMemberDto.setBaseAddress(personalMember.getAddress().getBaseAddress());
			personalMemberDto.setDetailAddress(personalMember.getAddress().getDetailAddress());
			personalMemberDto.setPoint(personalMember.getPoint());
			personalMemberDto.setMileage(personalMember.getMileage());
			
			model.addAttribute("personalMember", personalMemberDto);
			log.info("UpdatePersonalMember(Model) : " + model.toString());
		}catch (Exception e) {
			log.info("UpdatePersonalMember(Exception) : " + e.getMessage());
		}
		
		return "member/PersonalMemberUpdate";
	}
	
	//view
	@GetMapping("member/test")
	public String testMember() {
		return "member/test";
	}
		
	@PostMapping("/member/PersonalMember_save")
	public String registerPersonalMember(@ModelAttribute PersonalMemberDto form, final Model model) {
		
		ResponseEntity<PersonalMember> response = null;
		
		log.info("registerPersonalMember(Form) : " + form.toString());
		log.info("registerPersonalMember(Model) : " + model.toString());
		
		try {
			response = new ResponseEntity<PersonalMember>(personalMemberService.registerPersonalMember(form), HttpStatus.OK);
			log.info("registerPersonalMember(Response) : " + response.toString());
		} catch (Exception e) {
			log.info("registerPersonalMember(Exception) : " + e.getMessage());
		}
		
		return "redirect:/login";
	}	
	
	@PostMapping("/member/PersonalMember_update")
	public String modifyPersonalMember(@ModelAttribute PersonalMemberDto form, final Model model) {
		
		ResponseEntity<PersonalMember> response = null;
		
		log.info("modifyPersonalMember(Form) : " + form.toString());
		log.info("modifyPersonalMember(Model) : " + model.toString());
		
		try {
			response = new ResponseEntity<PersonalMember>(personalMemberService.modifyPersonalMember(form), HttpStatus.OK);
			log.info("modifyPersonalMember(Response) : " + response.toString());
		} catch (Exception e) {
			log.info("modifyPersonalMember(Exception) : " + e.getMessage());
		}
		
		return "redirect:PersonalMemberView";
	}	
}
