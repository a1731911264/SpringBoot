package com.momo.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping(value = { "/portals", "/" })
	public String portals() {
		return "portals";
	}
}
