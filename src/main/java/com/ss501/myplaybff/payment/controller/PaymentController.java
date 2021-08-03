package com.ss501.myplaybff.payment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss501.myplaybff.payment.dto.Payment;
import com.ss501.myplaybff.payment.dto.PaymentDto;
import com.ss501.myplaybff.payment.service.PaymentService;
import com.ss501.myplaybff.reservation.controller.ReservationController;
import com.ss501.myplaybff.reservation.domain.dto.Reservation;
import com.ss501.myplaybff.reservation.service.ReservationService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class PaymentController {

	private final PaymentService paymentService;
	private final ReservationService reservationService;
	private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

	@Autowired
	CircuitBreakerFactory circuitBreakerFactory;

	// payment table 화면 로드
	@GetMapping("payment")
	public String goPayment(HttpSession session, Model model) {
		PaymentDto paymentForm = new PaymentDto();
		model.addAttribute("paymentForm", paymentForm);

		// return empty table page
		return "payment/payment_tables";
	}

	/**
	 * 1. payments 전체 조회
	 * 
	 * @param model
	 * @return
	 */

	@GetMapping("payments")
	public String findAllPayment(final Model model) {

		// circuit breaker 적용 전
//		List<Payment> pmList = paymentService.findAllPayment();

		// circuit breaker
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		List<Payment> pmList = circuitBreaker.run(() -> paymentService.findAllPayment(),
				throwable -> new ArrayList<>());

		model.addAttribute("paymentList", pmList);
		return "payment/payment_tables";
	}

	/**
	 * 2. reservation Id 기반 payment 조회
	 * 
	 * @param reservationId
	 * @param model
	 * @return
	 */

	/**
	 * 3. reservation detail 에서 pay 연결
	 * 
	 * @param reservationId
	 * @param model
	 * @return
	 */
	@GetMapping("/payments/pay/{reservationId}")
	public String goPaymentByReservationId(HttpSession session, @PathVariable("reservationId") String reservationId,
			final Model model) {
//		if (session.getAttribute("id") == null) {
//			return "redirect:/login";
//		}

		try {
			Reservation reservation = reservationService.getReservationByReservationId(reservationId);
			model.addAttribute("reservation", reservation);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "404";
		}
		// reservation 객체 model - pay 화면으로 전달
		return "payment/pay";
	}

}
