package com.ss501.myplaybff.reservation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaygroundOwner {
	private Long corporateId;
	
	private String name;
}
