package com.ss501.myplaybff.playground.service.impl;

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

import com.ss501.myplaybff.playground.dto.PlaygroundDto;
import com.ss501.myplaybff.playground.service.PlaygroundService;
import com.ss501.myplaybff.reservation.domain.dto.Reservation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaygroundServiceImpl implements PlaygroundService{

	private RestTemplate restTemplate;
	private static final Logger log = LoggerFactory.getLogger(PlaygroundServiceImpl.class);
	
	@Value("${api.playground.url}")
	private String apiGatewayUrl;
	
	
	@Override
	public PlaygroundDto registerPlayground(PlaygroundDto playgroundDto) {
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		PlaygroundDto playground = new PlaygroundDto();
		
		playground = playgroundDto;
		playground.setPlaygroundLevelType("STANDARD");
		
		HttpEntity<PlaygroundDto> request = new HttpEntity<>(playground, headers);
		
	  	log.info(String.format("%s%s", apiGatewayUrl, "/playground/api/v1/register") + " call");
	  	log.info(request.getBody().toString());
		
	  	restTemplate = new RestTemplate();
        ResponseEntity<PlaygroundDto> result = restTemplate.postForEntity(String.format("%s%s", apiGatewayUrl, "/playground/api/v1/register"), request, PlaygroundDto.class);
        
        log.info(result.getBody().toString());
        playground.setId(result.getBody().getId());
        return playgroundDto;
		
	}

	@Override
	public List<PlaygroundDto> getAllPlayground() {
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8"))); 
		
	  	log.info(String.format("%s%s", apiGatewayUrl, "/playground/api/v1/playgroundList") + " call");
	  	restTemplate = new RestTemplate();
        ResponseEntity<PlaygroundDto[]> result = restTemplate.getForEntity(String.format("%s%s", apiGatewayUrl, "/playground/api/v1/playgroundList/"), PlaygroundDto[].class);
        
        log.info(result.getBody().toString());
        
        return Arrays.asList(result.getBody());
	}
	
	@Override
	public PlaygroundDto getPlaygroundByPlaygroundId(String id) {
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8"))); 
		
		String url = String.format("%s%s%s", apiGatewayUrl, "/playground/api/v1/playground/{playgroundId}", id);
	  	log.info(url + " get call");
	  	
	  	restTemplate = new RestTemplate();
        ResponseEntity<PlaygroundDto> result = restTemplate.getForEntity(String.format("%s%s%s", apiGatewayUrl, "/playground/api/v1/playground/", id), PlaygroundDto.class);
        
        log.info(result.getBody().toString());
        
        return result.getBody();
	}

	@Override
	public List<PlaygroundDto> getAllPlaygroundByCorporateId(String id) {
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8"))); 
		
	  	log.info(String.format("%s%s", apiGatewayUrl, "/playground/api/v1/playgroundList/{corporateId}") + " call");
	  	restTemplate = new RestTemplate();
        ResponseEntity<PlaygroundDto[]> result = restTemplate.getForEntity(String.format("%s%s%s", apiGatewayUrl, "/playground/api/v1/playgroundList/", id), PlaygroundDto[].class);
        
        log.info(result.getBody().toString());
        
        return Arrays.asList(result.getBody());
		
	}

}
