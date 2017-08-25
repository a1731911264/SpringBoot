package com.momo.test.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping("/test/{name}")
	public String test(@PathVariable String name){
		return new StringBuilder("<strong> Hello ").append(name+"</strong>").toString();
	}
	
}
