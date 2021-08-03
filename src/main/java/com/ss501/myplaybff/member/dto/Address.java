package com.ss501.myplaybff.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	
	private String zipCode;		
	private String baseAddress;		
	private String detailAddress;
	
}
