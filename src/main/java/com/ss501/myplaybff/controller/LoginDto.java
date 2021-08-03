package com.ss501.myplaybff.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
	private String memberType;
	
	private Long id ;
		
	private String email;
		
	private String loginPassword;
		
	private String name;
		
	private String  mobile;
		
	private String zipCode;
		
	private String baseAddress;
		
	private String detailAddress;
		
	private String statusType;
		
	private String levelType;
		
	private String favoriteType;
		
	private String point;
	
}
