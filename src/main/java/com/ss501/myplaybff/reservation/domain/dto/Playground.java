package com.ss501.myplaybff.reservation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playground {
	private Long playgroundId;
	
	private PlaygroundOwner corporateMember;
	
	private String playgroundName;
	
	private Address playgroundAddress;
	
	private String gpsCoordinates;
	
	private Integer capacity;
	
	private String openTime;
	
	private String closeTime;
	
	private Long priceHour;
	
	private String playgroundType;
	
	private String playgroundLevelType;
}
