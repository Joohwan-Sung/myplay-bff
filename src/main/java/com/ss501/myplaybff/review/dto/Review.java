package com.ss501.myplaybff.review.dto;

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
public class Review {

	private Long id;
	private PersonalMember writer;
	private Long playgroundId;
	private String contents;
	private String createDate;
	private Integer mark;
	private String reviewStatus;
}
