package com.ss501.myplaybff.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoporateMemberDto {
	
	private Long id;	
	private String loginPassword;	
	private String name;	
	private String email;	
	private String  tel;	
	private String coporateNo;	
	private String pgNo;
	
	private String zipCode;		
	private String baseAddress;		
	private String detailAddress;	
	
	private String statusType;	

	private String confirmDate;	
	private String filePath;

}
