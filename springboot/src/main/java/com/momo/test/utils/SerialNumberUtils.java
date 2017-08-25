package com.momo.test.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class SerialNumberUtils {

	private static Map<String,String> map = new HashMap<String,String>();
	
	
	public static synchronized String getSerialNumber(Long currentTimeMillis){
		
		String serialNumber = map.get(String.valueOf(currentTimeMillis));
		StringBuilder builder = new StringBuilder(String.valueOf(currentTimeMillis));
		if (StringUtils.isBlank(serialNumber)) {
			// 清空上一次操作的生成的id
			clear();
			// 非并发操作 生成一个id
			builder.append("01");
		} else {
			// 不是空 则说明是并发操作
			Integer i = Integer.valueOf(serialNumber.substring(serialNumber.length()-2));
			if (i<9) {
				builder.append("0").append(String.valueOf(++i));
			} else {
				builder.append(String.valueOf(++i));
			}
		}
		map.put(String.valueOf(currentTimeMillis), builder.toString());
		return builder.toString();
	}
	
	private static void clear(){
		map.clear();
	}
	/*public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					long currentTimeMillis = System.currentTimeMillis();
					String serialNumber = getSerialNumber(currentTimeMillis);
					System.out.println(serialNumber);
					
				}
			}).start();
		}
	}*/
}
