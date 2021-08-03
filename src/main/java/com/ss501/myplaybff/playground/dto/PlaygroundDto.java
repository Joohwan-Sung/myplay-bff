package com.ss501.myplaybff.playground.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaygroundDto {
	private Long id;
	//시설물 이름
	private String name;
	
	// 법인회원 정보(CoporateMember VO)
	//private String corporateId;
    //private String corporateName;
	
    
    private AddressDto address;
    
    private CorporateMemberDto corporateMember;
    
    
    // 주소 정보(Address VO)
	private String zipCode;
	private String baseAddress;
	private String detailAddress;
	
	//시설물 GPS정보
	private String gpsCoordinates;
	
	//수용인원
	private Integer capacity;
	
	//시설물 시작시간
	private String startTime;
	
	//시설물 종료시간
	private String finishTime;
	
	//시설물 시간당 비용 
	private Long priceHour;
	
	//시설물 타입
	private String playgroundType;
	
	//시설물 등급
	private String playgroundLevelType;
}
