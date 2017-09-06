package com.momo.test.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringConvter implements  Converter<String, String>{

	@Override
	public String convert(String source) {
		if(source ==null || source.trim().equals("")){
			return null;
		}
		return source.trim();
	}

}
