package com.ss501.myplaybff.member.dto;

import java.util.List;

import com.ss501.myplaybff.reservation.domain.dto.Applicant;
import com.ss501.myplaybff.reservation.domain.dto.Participant;
import com.ss501.myplaybff.reservation.domain.dto.Playground;
import com.ss501.myplaybff.reservation.domain.dto.Reservation;
import com.ss501.myplaybff.reservation.domain.dto.ReservationDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalMember {
	private Long id ;	
	private String name;	
	private String email;		
	private String mobile;
	private String loginPassword;
	
	private Address address;
	
	private String statusType;		
	private String levelType;		
	private String favoriteType;		
	private Long point;	
	private Long mileage;
}
