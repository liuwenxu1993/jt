package com.jt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static ObjectMapper mapper =new ObjectMapper();
	
	public static String toJson(Object target) {
		String result = null;
		try {
			result = mapper.writeValueAsString(target);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static <T>T toObject(String json,Class<T> targetClass){
		T object = null;
		try {
			object = mapper.readValue(json, targetClass);
			return object;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
