package com.ss501.myplaybff.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
	private String creditCardOwner;
	private String creditCardNo;
}
