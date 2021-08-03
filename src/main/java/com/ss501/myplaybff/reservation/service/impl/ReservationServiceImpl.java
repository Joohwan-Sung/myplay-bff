package com.ss501.myplaybff.reservation.service.impl;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ss501.myplaybff.reservation.domain.dto.Applicant;
import com.ss501.myplaybff.reservation.domain.dto.Playground;
import com.ss501.myplaybff.reservation.domain.dto.Reservation;
import com.ss501.myplaybff.reservation.domain.dto.ReservationDateTime;
import com.ss501.myplaybff.reservation.domain.dto.ReservationDto;
import com.ss501.myplaybff.reservation.service.ReservationService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ReservationServiceImpl implements ReservationService {
	private RestTemplate restTemplate;
	private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
	@Value("${api.reservation.url}")
	private String apiReservationUrl;
	
	@Override
	public ReservationDto reserve(ReservationDto reservationDto) {
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		Reservation reservation = new Reservation();
		Applicant applicant = new Applicant();
		applicant.setPersonalId(reservationDto.getApplicantId());
		//applicant.setMobile(reservationDto.getApplicantMobile());
		//applicant.setName(reservationDto.getApplicantName());
		reservation.setApplicant(applicant);
		
		Playground playground = new Playground();
		playground.setPlaygroundId(reservationDto.getPlaygroundId());
		//playground.setPlaygroundType(reservationDto.getPlaygroundType());
		reservation.setPlayground(playground);
		
		ReservationDateTime reservationDateTime = new ReservationDateTime();
		reservationDateTime.setDate(reservationDto.getDate());
		reservationDateTime.setStartTime(reservationDto.getStartTime());
		reservationDateTime.setFinishTime(reservationDto.getFinishTime());
		reservation.setReservationDateTime(reservationDateTime);
		
		reservation.setParticipants(reservationDto.getParticipants());
		reservation.setStatusType("RESERVED");
		
	  	HttpEntity<Reservation> request = new HttpEntity<>(reservation, headers);
		
	  	String url = String.format("%s%s", apiReservationUrl, "/reservation/api/v1/reservations");
	  	log.info(url + " post call");
	  	log.info(request.getBody().toString());
	  	
	  	restTemplate = new RestTemplate();
        ResponseEntity<Reservation> result = restTemplate.postForEntity(String.format("%s%s", apiReservationUrl, "/reservation/api/v1/reservations"), request, Reservation.class);
        
        log.info(result.getBody().toString());
        reservationDto.setReservationId(result.getBody().getId());
        return reservationDto;
	}

	/**
	 * 신청자 ID로 조회하므로 parameter 형식으로 구성하여 reservations GET API 호출
	 */
	@Override
	public List<Reservation> getAllReservationByApplicantId(String id) {
		String url = String.format("%s%s%s", apiReservationUrl, "/reservation/api/v1/reservations?applicantId=", id);
	  	log.info(url + " get call");
	  	
	  	restTemplate = new RestTemplate();
        ResponseEntity<Reservation[]> result = restTemplate.getForEntity(String.format("%s%s%s", apiReservationUrl, "/reservation/api/v1/reservations?applicantId=", id), Reservation[].class);
        
        log.info(result.getBody().toString());
        
        return Arrays.asList(result.getBody());
	}

	
	@Override
	public Reservation getReservationByReservationId(String id) {
		String url = String.format("%s%s%s", apiReservationUrl, "/reservation/api/v1/reservations/", id);
	  	log.info(url + " get call");
	  	
	  	restTemplate = new RestTemplate();
        ResponseEntity<Reservation> result = restTemplate.getForEntity(String.format("%s%s%s", apiReservationUrl, "/reservation/api/v1/reservations/", id), Reservation.class);
        
        log.info(result.getBody().toString());
        
        return result.getBody();
	}

	/**
	 * 법인 ID로 조회하므로 parameter 형식으로 구성하여 reservations GET API 호출
	 */
	@Override
	public List<Reservation> getAllReservationByCorporateId(String id) {
		String url = String.format("%s%s%s", apiReservationUrl, "/reservation/api/v1/reservations?corporateId=", id);
	  	log.info(url + " get call");
	  	
	  	restTemplate = new RestTemplate();
        ResponseEntity<Reservation[]> result = restTemplate.getForEntity(String.format("%s%s%s", apiReservationUrl, "/reservation/api/v1/reservations?corporateId=", id), Reservation[].class);
        
        log.info(result.getBody().toString());
        
        return Arrays.asList(result.getBody());
	}

	/**
	 * reservations PUT method를 호출하기 위해서 RestTemplate의 exchange 메소드 사용함
	 */
	@Override
	public Reservation update(String id, ReservationDto reservationDto) {
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		HttpEntity<ReservationDto> request = new HttpEntity<>(reservationDto, headers);
		String url = String.format("%s%s%s", apiReservationUrl, "/reservation/api/v1/reservations/", id);
	  	log.info(url + " put call");
	  	log.info(request.getBody().toString());
	  	
	  	restTemplate = new RestTemplate();
	  	ResponseEntity<Reservation> result = restTemplate.exchange(url, HttpMethod.PUT, request, Reservation.class);
        
        log.info("Response : " + result.toString());
        return result.getBody();
	}
}
