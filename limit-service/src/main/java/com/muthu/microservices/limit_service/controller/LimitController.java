package com.muthu.microservices.limit_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muthu.microservices.limit_service.bean.Limits;
import com.muthu.microservices.limit_service.configuration.Configuration;

@RestController
public class LimitController {

	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public Limits getLimits() {
		return new Limits(configuration.getMinimum(), configuration.getMaximum());
	}
}
