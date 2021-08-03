package com.ss501.myplaybff.reservation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Applicant {
	private Long personalId;
	
	private String email;
	
	private String loginPassword;
	
	private String name;
	
	private String  mobile;
	
	private Address memberAddress;
	
	private String memberStatusType;
	
	private String memberLevelType;
	
	private String memberFavoriteType;
	
	private Long point;
	
	private Long mileage;
}
