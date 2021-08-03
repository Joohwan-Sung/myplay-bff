package com.ss501.myplaybff.reservation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailablePlaygroundDto {
	private String corporateId;
    private String corporateName;
    private String playgroundId;
    private String playgroundType;
}
