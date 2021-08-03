package com.ss501.myplaybff.reservation.domain.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
	private Long id;
	
	private Applicant applicant;
	
	private Playground playground;
	
	private ReservationDateTime reservationDateTime;
	
	private List<Participant> participants = new ArrayList<>();
	
	private String statusType;
}
