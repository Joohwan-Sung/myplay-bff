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
import com.ss501.myplaybff.member.dto.PersonalMember;
import com.ss501.myplaybff.member.dto.PersonalMemberDto;
import com.ss501.myplaybff.member.service.PersonalMemberService;

@Service
public class PersonalMemberServiceImpl implements PersonalMemberService{
	
	RestTemplate restTemplate;
	private static final Logger log = LoggerFactory.getLogger(PersonalMemberServiceImpl.class);
	
	@Value("${api.member.url}")
	private String apiMemberUrl;
	
	@Override
	public PersonalMember findPersonalMember(Long Id) {
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		log.info("findPersonalMember(API URL) : " + apiMemberUrl);
		log.info("findPersonalMember(URL) : " + String.format("%s%s", apiMemberUrl, "/playground/members/personalMember/{id}") + " request");
		
		restTemplate = new RestTemplate();		
		
		ResponseEntity<PersonalMember[]> response = restTemplate.getForEntity(String.format("%s%s%s", apiMemberUrl, 
				"/playground/members/personalMember/", Id), PersonalMember[].class);
		
		log.info("findPersonalMember(response) : " + response.getBody().toString());
		
		return Arrays.asList(response.getBody()).get(0);
	}

	@Override
	public List<PersonalMember> findAllPersonalMember() {
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		log.info("findAllPersonalMember(API URL) : " + apiMemberUrl);
		log.info("findAllPersonalMember(URL) : " + String.format("%s%s", apiMemberUrl, "/playground/members/personalMemberList") + " request");
		
		restTemplate = new RestTemplate();
		
		ResponseEntity<PersonalMember[]> response = restTemplate.getForEntity(String.format("%s%s", apiMemberUrl, 
				"/playground/members/personalMemberList"), PersonalMember[].class);		
		
		log.info("findAllPersonalMember(response) : " +response.getBody().toString());
		
		return Arrays.asList(response.getBody());
	}
	
	@Override
	public PersonalMember registerPersonalMember(PersonalMemberDto personalMemberDto) {
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		PersonalMember personalMember = new PersonalMember(); 
		Address address = new Address(); 		
		
		address.setBaseAddress(personalMemberDto.getBaseAddress());
		address.setDetailAddress(personalMemberDto.getDetailAddress());
		address.setZipCode(personalMemberDto.getZipCode());
		
		log.info("registerPersonalMember(Address) : " + address.toString());
		
		personalMember.setId(personalMemberDto.getId());
		personalMember.setName(personalMemberDto.getName());
		personalMember.setEmail(personalMemberDto.getEmail());
		personalMember.setMobile(personalMemberDto.getMobile());
		personalMember.setLoginPassword(personalMemberDto.getLoginPassword());
		
		personalMember.setAddress(address);
		
		personalMember.setStatusType("JOIN");
		personalMember.setLevelType(personalMemberDto.getLevelType());
		personalMember.setFavoriteType(personalMemberDto.getFavoriteType());
		personalMember.setPoint(0L);
		personalMember.setMileage(0L);
				
		HttpEntity<PersonalMember> request = new HttpEntity<>(personalMember, headers);
		
	  	log.info("registerPersonalMember(API URL) : " + apiMemberUrl);
		log.info("registerPersonalMember(URL) : " + String.format("%s%s", apiMemberUrl, "/playground/members/personalmemberRegister") + " call");
	  	log.info("registerPersonalMember(request) : " + request.getBody().toString());
	  	
	  	restTemplate = new RestTemplate();
        ResponseEntity<PersonalMemberDto> result = restTemplate.postForEntity(String.format("%s%s", apiMemberUrl, 
        		"/playground/members/personalmemberRegister"), 
        		request, PersonalMemberDto.class);
		
        log.info("registerPersonalMember(response) : " + result.getBody().toString());
        
        personalMember.setId(result.getBody().getId());
		return personalMember;
	}	
	
	@Override
	public PersonalMember modifyPersonalMember(PersonalMemberDto personalMemberDto) {
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		PersonalMember personalMember = new PersonalMember(); 
		Address address = new Address(); 		
		
		address.setBaseAddress(personalMemberDto.getBaseAddress());
		address.setDetailAddress(personalMemberDto.getDetailAddress());
		address.setZipCode(personalMemberDto.getZipCode());
		
		log.info("modifyPersonalMember(Address) : " + address.toString());
		
		personalMember.setId(personalMemberDto.getId());
		personalMember.setName(personalMemberDto.getName());
		personalMember.setEmail(personalMemberDto.getEmail());
		personalMember.setMobile(personalMemberDto.getMobile());
		personalMember.setLoginPassword(personalMemberDto.getLoginPassword());
		
		personalMember.setAddress(address);
		
		personalMember.setStatusType(personalMemberDto.getStatusType());
		personalMember.setLevelType(personalMemberDto.getLevelType());
		personalMember.setFavoriteType(personalMemberDto.getFavoriteType());
		personalMember.setPoint(personalMemberDto.getPoint());
		personalMember.setMileage(personalMemberDto.getMileage());
				
		HttpEntity<PersonalMember> request = new HttpEntity<>(personalMember, headers);
		
	  	log.info("modifyPersonalMember(API URL) : " + apiMemberUrl);
		log.info("modifyPersonalMember(URL) : " + String.format("%s%s", apiMemberUrl, "/playground/members/personalmemberRegister") + " call");
	  	log.info("modifyPersonalMember(request) : " + request.getBody().toString());
	  	
	  	restTemplate = new RestTemplate();
        ResponseEntity<PersonalMemberDto> result = restTemplate.postForEntity(String.format("%s%s", apiMemberUrl, 
        		"/playground/members/personalmemberRegister"), 
        		request, PersonalMemberDto.class);
		
        log.info("modifyPersonalMember(response) : " + result.getBody().toString());
        
        personalMember.setId(result.getBody().getId());
		return personalMember;
	}	

}
