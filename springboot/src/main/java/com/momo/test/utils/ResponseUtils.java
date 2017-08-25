package com.momo.test.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
/**
 * 响应json数据工具
 * @author MoMo
 *
 */
public class ResponseUtils {

	private static Log log = LogFactory.getLog(ResponseUtils.class);
	
	public static void sendMessage(HttpServletResponse response,Boolean isSuccess,Object message){
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", isSuccess);
		map.put("message", message);
		String jsonString = JSON.toJSONString(map);
		log.info("返回的json:"+jsonString);
		try {
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
