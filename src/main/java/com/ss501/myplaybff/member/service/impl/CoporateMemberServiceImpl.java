package com.ss501.myplaybff.member.service.impl;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ss501.myplaybff.member.dto.Address;
import com.ss501.myplaybff.member.dto.ConfirmInfo;
import com.ss501.myplaybff.member.dto.CoporateMember;
import com.ss501.myplaybff.member.dto.CoporateMemberDto;
import com.ss501.myplaybff.member.dto.PersonalMember;
import com.ss501.myplaybff.member.dto.PersonalMemberDto;
import com.ss501.myplaybff.member.service.CoporateMemberService;

@Service
public class CoporateMemberServiceImpl implements CoporateMemberService{
	
	RestTemplate restTemplate;
	private static final Logger log = LoggerFactory.getLogger(CoporateMemberServiceImpl.class);
	
	@Value("${api.member.url}")
	private String apiMemberUrl;

	@Override
	public CoporateMember findCoporateMember(Long Id) {
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		log.info("findCoporateMember(API URL) : " + apiMemberUrl);
		log.info("findCoporateMember(URL) : " + String.format("%s%s", apiMemberUrl, "/playground/members/coporateMember/{id}") + " request");
				
		restTemplate = new RestTemplate();
		
		ResponseEntity<CoporateMember[]> response = restTemplate.getForEntity(String.format("%s%s%s", apiMemberUrl, 
				"/playground/members/coporateMember/", Id), CoporateMember[].class);
		
		System.out.println(response.getBody().toString());
		
		return Arrays.asList(response.getBody()).get(0);
	}

	@Override
	public List<CoporateMember> findAllCoporateMember() {
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		log.info("findAllCoporateMember(API URL) : " + apiMemberUrl);
		log.info("findAllCoporateMember(URL) : " + String.format("%s%s", apiMemberUrl, "/playground/members/coporateMemberList") + " request");
		
		restTemplate = new RestTemplate();
		
		ResponseEntity<CoporateMember[]> response = restTemplate.getForEntity(String.format("%s%s", apiMemberUrl, 
				"/playground/members/coporateMemberList"), CoporateMember[].class);
		
		log.info("findAllCoporateMember(response) : " + response.getBody().toString());
		
		return Arrays.asList(response.getBody());
	}
	
	@Override
	public CoporateMember registerCoporateMember(CoporateMemberDto coporateMemberDto) {
	
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		CoporateMember coporateMember = new CoporateMember();
		Address address = new Address();
		ConfirmInfo confirmInfo = new ConfirmInfo();
		
		address.setBaseAddress(coporateMemberDto.getBaseAddress());
		address.setDetailAddress(coporateMemberDto.getDetailAddress());
		address.setZipCode(coporateMemberDto.getZipCode());		
		
		log.info("registerCoporateMember(Address) : " + address.toString());
		
		confirmInfo.setConfirmDate(coporateMemberDto.getConfirmDate());
		confirmInfo.setFilePath(coporateMemberDto.getFilePath());
		
		log.info("registerCoporateMember(ConfirmInfo) : " + confirmInfo.toString());
		
		coporateMember.setId(coporateMemberDto.getId());	
		coporateMember.setLoginPassword(coporateMemberDto.getLoginPassword());
		coporateMember.setName(coporateMemberDto.getName());
		coporateMember.setEmail(coporateMemberDto.getEmail());	
		coporateMember.setTel(coporateMemberDto.getTel());	
		coporateMember.setCoporateNo(coporateMemberDto.getCoporateNo());	
		coporateMember.setPgNo(coporateMemberDto.getPgNo());
		
		coporateMember.setStatusType("JUDGEMENT");
				
		coporateMember.setAddress(address);	
		coporateMember.setConfirmInfo(confirmInfo);
		
		HttpEntity<CoporateMember> request = new HttpEntity<>(coporateMember, headers);
		
		log.info("registerCoporateMember(API URL) : " + apiMemberUrl);
		log.info("registerCoporateMember(URL) : " + String.format("%s%s", apiMemberUrl, "/playground/members/coporatememberRegister") + " call");
	  	log.info("registerCoporateMember(request) : " + request.getBody().toString());
	  	
	  	restTemplate = new RestTemplate();
        ResponseEntity<CoporateMemberDto> result = restTemplate.postForEntity(String.format("%s%s", apiMemberUrl, 
        		"/playground/members/coporatememberRegister"), 
        		request, CoporateMemberDto.class);
		
		log.info("registerCoporateMember(response) : " + result.getBody().toString());
        
        coporateMember.setId(result.getBody().getId());
		return coporateMember;
	}
	
	@Override
	public CoporateMember modifyCoporateMember(CoporateMemberDto coporateMemberDto) {
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		CoporateMember coporateMember = new CoporateMember();
		Address address = new Address();
		ConfirmInfo confirmInfo = new ConfirmInfo();
		
		address.setBaseAddress(coporateMemberDto.getBaseAddress());
		address.setDetailAddress(coporateMemberDto.getDetailAddress());
		address.setZipCode(coporateMemberDto.getZipCode());		
		
		log.info("modifyCoporateMember(Address) : " + address.toString());
		
		confirmInfo.setConfirmDate(coporateMemberDto.getConfirmDate());
		confirmInfo.setFilePath(coporateMemberDto.getFilePath());
		
		log.info("modifyCoporateMember(ConfirmInfo) : " + confirmInfo.toString());
		
		coporateMember.setId(coporateMemberDto.getId());	
		coporateMember.setLoginPassword(coporateMemberDto.getLoginPassword());
		coporateMember.setName(coporateMemberDto.getName());
		coporateMember.setEmail(coporateMemberDto.getEmail());	
		coporateMember.setTel(coporateMemberDto.getTel());	
		coporateMember.setCoporateNo(coporateMemberDto.getCoporateNo());	
		coporateMember.setPgNo(coporateMemberDto.getPgNo());
				
		coporateMember.setStatusType(coporateMemberDto.getStatusType());
				
		coporateMember.setAddress(address);	
		coporateMember.setConfirmInfo(confirmInfo);
		
		HttpEntity<CoporateMember> request = new HttpEntity<>(coporateMember, headers);
		
		log.info("modifyCoporateMember(API URL) : " + apiMemberUrl);
		log.info("modifyCoporateMember(URL) : " + String.format("%s%s", apiMemberUrl, "/playground/members/coporatememberRegister") + " call");
	  	log.info("modifyCoporateMember(request) : " + request.getBody().toString());
	  	
	  	restTemplate = new RestTemplate();
        ResponseEntity<CoporateMemberDto> result = restTemplate.postForEntity(String.format("%s%s", apiMemberUrl, 
        		"/playground/members/coporatememberRegister"), 
        		request, CoporateMemberDto.class);
		
		log.info("registerCoporateMember(response) : " + result.getBody().toString());
        
        coporateMember.setId(result.getBody().getId());
		return coporateMember;
	}	
}
