package com.ss501.myplaybff.playground.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
	private Integer zipCode;
	
	private String baseAddress;
	
	private String detailAddress;
}
