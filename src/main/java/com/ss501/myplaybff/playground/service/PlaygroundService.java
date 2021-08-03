package com.ss501.myplaybff.playground.service;

import java.util.List;

import com.ss501.myplaybff.playground.dto.PlaygroundDto;
import com.ss501.myplaybff.reservation.domain.dto.Playground;

public interface PlaygroundService {
	// 1.시설물 등록
	PlaygroundDto registerPlayground(PlaygroundDto playgroundDto);
	
	// 2.시설물 전체 조회
	List<PlaygroundDto> getAllPlayground();
	PlaygroundDto getPlaygroundByPlaygroundId(String id);
	List<PlaygroundDto> getAllPlaygroundByCorporateId(String id);
}
