package com.ss501.myplaybff.payment.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ss501.myplaybff.payment.dto.CreditCard;
import com.ss501.myplaybff.payment.dto.Payment;
import com.ss501.myplaybff.payment.dto.PaymentDto;
import com.ss501.myplaybff.payment.service.PaymentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class PaymentRestController {
		
	private PaymentService paymentService;
	private static final Logger log = LoggerFactory.getLogger(PaymentRestController.class);

	/**
	 * 1. payment post method
	 * 
	 * @param paymentDto
	 * @param model
	 * @return
	 */
	@PostMapping("/payments")
	public ModelAndView makePay(@ModelAttribute PaymentDto paymentDto, final Model model) {
		ResponseEntity<PaymentDto> response = null;
		ModelAndView mav = new ModelAndView();

		log.info("Json : " + paymentDto.toString());
		
		response = new ResponseEntity<PaymentDto>(paymentService.makePay(paymentDto), HttpStatus.OK);
		mav.setViewName("redirect:/payments");
		mav.addObject("payment", response.getBody());

		return mav;
	}

	
	/**
	 * 2. makePay FeignClient
	 * 
	 * @param paymentDto
	 * @param model
	 * @return
	
	@PostMapping("/payments")
	public ModelAndView makePay(@ModelAttribute PaymentDto paymentDto, final Model model) {
		ResponseEntity<PaymentDto> response = null;
		ModelAndView mav = new ModelAndView();

		log.info("makepay feign client");
		// ----- paymentDto to payment VO
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
		} catch (Exception e) {
			payment.setApprovalDate(new Date());
			e.printStackTrace();
		}
		// ----- paymentDto to payment VO end
		log.info("payment Json : " + payment.toString());
		
//		response = new ResponseEntity<PaymentDto>(paymentService.makePay(payment), HttpStatus.OK);

        try {
            paymentService.makePay(payment);
        }catch(Exception e) {
            throw new RuntimeException("결제서비스 호출 실패입니다.");
        }
        
		mav.setViewName("redirect:/payments");
//		mav.addObject("payment", response.getBody());
		mav.addObject(payment);
        
		return mav;
	}
*/
}
