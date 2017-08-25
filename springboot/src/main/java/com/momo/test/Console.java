package com.momo.test;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.momo.test.config.CheckImgServlet;
@SpringBootApplication(scanBasePackages={"com.momo.test.controller","com.momo.test.service","com.momo.test.dao","com.momo.test.config"})
@ServletComponentScan
public class Console {
	@SuppressWarnings("unchecked")
	@Bean
	public ServletRegistrationBean<CheckImgServlet> servletRegistrationBean() {
		return new ServletRegistrationBean<CheckImgServlet>(new CheckImgServlet(), "/code/registerCode");// ServletName默认值为首字母小写，即myServlet
	}
	public static void main(String[] args) {
		SpringApplication.run(Console.class, args);
	}
	
}
