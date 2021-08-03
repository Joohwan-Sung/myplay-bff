package com.ss501.myplaybff.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalMemberDto{
	
	private Long id ;	
	private String name;	
	private String email;		
	private String mobile;
	private String loginPassword;
	
	private String zipCode;		
	private String baseAddress;		
	private String detailAddress;
	
	private String statusType;		
	private String levelType;		
	private String favoriteType;		
	private Long point;	
	private Long mileage;

}
