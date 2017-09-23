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
//	@Bean(name = "multipartResolver")
//    public MultipartResolver multipartResolver(){
//     CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//     resolver.setDefaultEncoding("UTF-8");
//     resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
//     resolver.setMaxInMemorySize(40960);
//     resolver.setMaxUploadSize(10*1024*1024);//上传文件大小 50M 50*1024*1024
//     return resolver;
// }   
	public static void main(String[] args) {
		SpringApplication.run(Console.class, args);
	}
	
}
