package com.ss501.myplaybff.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss501.myplaybff.sample.service.SampleService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("")
public class SampleController {
	private final SampleService sampleService;
	
	//view
	@GetMapping("/sample")
	public String goSampleTables() {
		return "sample_tables";
	}
	
	//model(data) and view
	@GetMapping("/samplewithdata")
	public String findAllSample(final Model model) {
		model.addAttribute("sampleList", sampleService.findAllSample());
		
		return "sample_tables";
	}
}
