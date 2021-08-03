package com.ss501.myplaybff.payment.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

	private Long id;
	
	private CreditCard creditCard;
	
	private Long reservationId;
	
	private String paymentStatus;
	
	private Integer price;

	private Date approvalDate;
	
	
}
