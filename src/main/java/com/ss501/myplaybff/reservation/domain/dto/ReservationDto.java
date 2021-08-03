package com.ss501.myplaybff.reservation.domain.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
	private Long reservationId;
	private Long applicantId;
	private String applicantMobile;
	private String applicantName;
	private Long corporateId;
    private String corporateName;
    private Long playgroundId;
    private String playgroundType;
    private String date;
    private String startTime;
	private String finishTime;
    private List<Participant> participants = new ArrayList<>();
    private String statusType;
    
    public void addParicipant(Participant participant) {
    	this.participants.add(participant);
    }
}
