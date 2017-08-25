package com.momo.test.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class IntercepetorConfig extends WebMvcConfigurerAdapter{
	@Override 
	public void addInterceptors(InterceptorRegistry registry) { 
		InterceptorRegistration addInterceptor = registry.addInterceptor(new LoginInterceptor());
		addInterceptor.addPathPatterns("/**");
		addInterceptor.excludePathPatterns("/user/toLogin","/user/toRegister","/static/*","/user/register","/code/registerCode","/user/login","/user/checkUsername","/user/checkValidateCode");
	}

}
