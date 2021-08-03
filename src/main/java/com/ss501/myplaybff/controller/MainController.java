package com.ss501.myplaybff.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss501.myplaybff.member.dto.CoporateMember;
import com.ss501.myplaybff.member.dto.PersonalMember;
import com.ss501.myplaybff.member.service.CoporateMemberService;
import com.ss501.myplaybff.member.service.PersonalMemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class MainController {
	private PersonalMemberService personalMemberService;
	private CoporateMemberService corporateMemberService;
	
	@GetMapping("/")
	public String goHome() {
		return "login";
	}
	
	@GetMapping("/index")
	public String goIndex() {
		return "index";
	}
	
	@GetMapping("index.do")
	public String goIndex2() {
		return "login";
	}
	@GetMapping("index2.do")
	public String goIndex3() {
		return "login";
	}
	
	@GetMapping("404")
	public String goNotFound() {
		return "404";
	}
	
	@GetMapping("login")
	public String login() {
		return "login";
	}
	
	@GetMapping("utilities_other")
	public String goutilities_other() {
		return "utilities-other";
	}	
	
	@PostMapping("login")
	public String login(HttpSession session, @ModelAttribute LoginDto loginDto, final Model model) {
		session.setAttribute("id", loginDto.getId());
		session.setAttribute("loginId", loginDto.getEmail());
		session.setAttribute("type", loginDto.getMemberType());
		session.setAttribute("name", "Unknown");
		
		// Mermber Service 호출
		PersonalMember personalMember = null;
		CoporateMember corporateMember = null;
		
		if ("personal".equals(loginDto.getMemberType())) {
			personalMember = personalMemberService.findPersonalMember(loginDto.getId());
			if (personalMember != null) {
				session.setAttribute("name", personalMember.getName());
			}
		} else if ("corporate".equals(loginDto.getMemberType())) {
			corporateMember = corporateMemberService.findCoporateMember(loginDto.getId());
			if (corporateMember != null) {
				session.setAttribute("name", corporateMember.getName());
			}
		}
		
		return "index";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.setAttribute("id", null);
		session.setAttribute("loginId", null);
		session.setAttribute("name", null);
		
		return "login";
	}
}
