package com.ss501.myplaybff.payment.service.impl;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ss501.myplaybff.payment.dto.CreditCard;
import com.ss501.myplaybff.payment.dto.Payment;
import com.ss501.myplaybff.payment.dto.PaymentDto;
import com.ss501.myplaybff.payment.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	private RestTemplate restTemplate;
	private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
	
	@Value("${api.payment.url}")
	private String apiPaymentUrl;

//	@Override
	public List<Payment> findAllPayment() {
		// serivce impl 에서 payment controller 호출
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		log.info(String.format("%s%s", apiPaymentUrl, "/payments") + " call get method");
		restTemplate = new RestTemplate();
		ResponseEntity<Payment[]> result = restTemplate.getForEntity(String.format("%s%s", apiPaymentUrl, "/payments"),
				Payment[].class);
		
		log.info(result.getBody().toString());

		return Arrays.asList(result.getBody());
	}

	@Override
	public PaymentDto makePay(PaymentDto paymentDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		// payment controller 호출 - payment 등록
		CreditCard creditCard = new CreditCard();
		creditCard.setCreditCardOwner(paymentDto.getCreditCardOwner());
		creditCard.setCreditCardNo(paymentDto.getCreditCardNo());

		Payment payment = new Payment();
		payment.setCreditCard(creditCard);
		payment.setReservationId(paymentDto.getReservationId());
		payment.setPrice(paymentDto.getPrice());
		payment.setPaymentStatus("APPROVED");

		SimpleDateFormat fDate = new SimpleDateFormat("yyyy-MM-dd");
		try {
			payment.setApprovalDate(fDate.parse(paymentDto.getApprovalDate()));
		} catch (ParseException e) {
			payment.setApprovalDate(null);
			e.printStackTrace();
		}
		log.info("make pay service impl >>" + payment.toString());
		HttpEntity<Payment> request = new HttpEntity<>(payment, headers);
		restTemplate = new RestTemplate();
		ResponseEntity<Payment> result = restTemplate.postForEntity(String.format("%s%s", apiPaymentUrl, "/payments"),
				request, Payment.class);

//        log.info("make pay response >>" + result.getBody().toString());
//        paymentDto.setPaymentId(result.getBody().getId());

		return paymentDto;
	}

}
