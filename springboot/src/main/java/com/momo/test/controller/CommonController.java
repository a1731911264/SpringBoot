package com.momo.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {

	
	// 跳转home
	@RequestMapping("/home")
	public String home(){
		return "index";
	}
	
	@RequestMapping("/about")
	public String about(){
		return "about";
	}
	@RequestMapping("/blog")
	public String blog(){
		return "blog";
	}
	@RequestMapping("/contact")
	public String contact(){
		return "contact";
	}
	@RequestMapping("/portfolio")
	public String portfolio(){
		return "portfolio";
	}
}
