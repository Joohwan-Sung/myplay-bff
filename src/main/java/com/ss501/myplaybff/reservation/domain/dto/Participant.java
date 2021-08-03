package com.ss501.myplaybff.reservation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
	private Long id;
	
	private String name;
	
	private String mobile;
	
}
