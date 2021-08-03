package com.ss501.myplaybff.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
	
	private Long paymentId;
	private String creditCardOwner;
	private String creditCardNo;
	private Long reservationId;
	private String paymentStatus;
	private Integer price;
	private String approvalDate;
	
	public PaymentDto(Payment payment) {
		this.paymentId = payment.getId();
		this.creditCardOwner = payment.getCreditCard().getCreditCardOwner();
		this.creditCardNo = payment.getCreditCard().getCreditCardNo();
		this.reservationId = payment.getReservationId();
		this.paymentStatus = payment.getPaymentStatus();
		this.price = payment.getPrice();
		this.approvalDate = payment.getApprovalDate().toString();
	}

}
