package com.ss501.myplaybff.payment.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

import com.ss501.myplaybff.payment.dto.Payment;
import com.ss501.myplaybff.payment.dto.PaymentDto;


//@FeignClient(name = "payment", url = "${api.payment.url}")
public interface PaymentService {

//	@RequestMapping(method = RequestMethod.GET, path = "/payments")
	public List<Payment> findAllPayment();

	public PaymentDto makePay(PaymentDto payment);

//	@RequestMapping(method = RequestMethod.POST, path = "/payments")
//	public void makePay(@RequestBody Payment payment);

}
