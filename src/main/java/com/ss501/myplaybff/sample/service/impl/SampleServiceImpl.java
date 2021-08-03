package com.ss501.myplaybff.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ss501.myplaybff.sample.dto.SampleDto;
import com.ss501.myplaybff.sample.service.SampleService;

@Service
public class SampleServiceImpl implements SampleService {

	@Override
	public List<SampleDto> findAllSample() {
		SampleDto sample1 = new SampleDto("Emliy", "Supporter");
		SampleDto sample2 = new SampleDto("Jay", "Crew");
		
		List<SampleDto> sampleList = new ArrayList<>();
		sampleList.add(sample1);
		sampleList.add(sample2);
		
		return sampleList;
	}

}
