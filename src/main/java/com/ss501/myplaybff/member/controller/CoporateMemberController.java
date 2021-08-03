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

import com.ss501.myplaybff.member.dto.CoporateMember;
import com.ss501.myplaybff.member.dto.CoporateMemberDto;
import com.ss501.myplaybff.member.dto.PersonalMember;
import com.ss501.myplaybff.member.dto.PersonalMemberDto;
import com.ss501.myplaybff.member.service.CoporateMemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class CoporateMemberController {
	
	private CoporateMemberService coporateMemberService;
	private static final Logger log = LoggerFactory.getLogger(CoporateMemberController.class);
	
	/*model(data) and view */
	@GetMapping("member/CoporateMemberView")
	public String findAllCoporateMemeber(final Model model) {
	
		List<CoporateMember> response = null;
		response = coporateMemberService.findAllCoporateMember();
		log.info("findAllCoporateMemeber(response) : " + response.toString());
		model.addAttribute("coporateMemberList", response);
		log.info("findAllCoporateMemeber(Model) : " + model.toString());
		
		return "member/CoporateMemberView";
	}
	
	/* view and model(data) */
	@GetMapping("member/CoporateMemberSave")
	public String SaveCoporateMember(final Model model) {
		
		try {
			CoporateMemberDto coporateMember = new CoporateMemberDto();
			model.addAttribute("coporateMember", coporateMember);
			log.info("SaveCoporateMember(Model) : " + model.toString());
		}catch (Exception e) {
			log.info("SaveCoporateMember(Exception) : " + e.getMessage());
		}
		
		return "member/CoporateMemberSave";
	}
	
	@GetMapping("member/CoporateMemberUpdate")
	public String UpdateCoporateMember(@RequestParam Optional<Long> Id, final Model model) {
		
		CoporateMember coporateMember = null;	
		CoporateMemberDto coporateMemberDto = new CoporateMemberDto();		
		
		if (Id.isPresent()) {
			log.info("UpdateCoporateMember(Id) : " + Id.get());
		}
		
		try {
			coporateMember = coporateMemberService.findCoporateMember(Id.get());
			
			coporateMemberDto.setId(coporateMember.getId());
			coporateMemberDto.setName(coporateMember.getName());
			coporateMemberDto.setEmail(coporateMember.getEmail());
			coporateMemberDto.setTel(coporateMember.getTel());
			coporateMemberDto.setCoporateNo(coporateMember.getCoporateNo());
			coporateMemberDto.setPgNo(coporateMember.getPgNo());
			coporateMemberDto.setZipCode(coporateMember.getAddress().getZipCode());
			coporateMemberDto.setBaseAddress(coporateMember.getAddress().getBaseAddress());
			coporateMemberDto.setDetailAddress(coporateMember.getAddress().getDetailAddress());
			coporateMemberDto.setConfirmDate(coporateMember.getConfirmInfo().getConfirmDate());
			coporateMemberDto.setFilePath(coporateMember.getConfirmInfo().getFilePath());
			
			model.addAttribute("coporateMember", coporateMemberDto);
			log.info("UpdateCoporateMember(Model) : " + model.toString());
		}catch (Exception e) {
			log.info("UpdateCoporateMember(Exception) : " + e.getMessage());
		}
		
		return "member/CoporateMemberUpdate";
	}
	
	@PostMapping("/member/CoporateMember_save")
	public String registerCoporateMember(@ModelAttribute CoporateMemberDto form, final Model model) {
		
		ResponseEntity<CoporateMember> response = null;
		
		log.info("RegisterCoporateMember(Form) : " + form.toString());
		log.info("RegisterCoporateMember(Model) : " + model.toString());
		
		try {
			response = new ResponseEntity<CoporateMember>(coporateMemberService.registerCoporateMember(form), HttpStatus.OK);
			log.info("RegisterCoporateMember(Response) : " + response.toString());
		} catch (Exception e) {
			log.info("RegisterCoporateMember(Exception) : " + e.getMessage());
		}
		
		return "redirect:/login";
	}
	
	@PostMapping("/member/CoporateMember_update")
	public String modifyCoporateMember(@ModelAttribute CoporateMemberDto form, final Model model) {
		
		ResponseEntity<CoporateMember> response = null;
		
		log.info("modifyCoporateMember(Form) : " + form.toString());
		log.info("modifyCoporateMember(Model) : " + model.toString());
		
		try {
			response = new ResponseEntity<CoporateMember>(coporateMemberService.modifyCoporateMember(form), HttpStatus.OK);
			log.info("modifyPersonalMember(Response) : " + response.toString());
		} catch (Exception e) {
			log.info("modifyPersonalMember(Exception) : " + e.getMessage());
		}
		
		return "redirect:CoporateMemberView";
	}	

}
