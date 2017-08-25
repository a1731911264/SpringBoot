package com.momo.test.utils;

import java.io.UnsupportedEncodingException;

import org.springframework.util.DigestUtils;

public class MD5Utils {

	public static String getMD5Str(String str){
		try {
			return DigestUtils.md5DigestAsHex(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
