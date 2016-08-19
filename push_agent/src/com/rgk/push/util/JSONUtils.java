package com.rgk.push.util;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.rgk.push.test.RegisterData;
import com.rgk.push.test.RegisterDetails;

public class JSONUtils {
	private static final Logger logger = Logger.getLogger(JSONUtils.class);
	private JSONUtils() {}
	
	@SuppressWarnings("unchecked")
	public static <T> T toBean(JSONObject detailJSON, Class<T> tCla) {
		T t = null;
		try {
			t = ((T) JSONObject.toBean(detailJSON, tCla));
		} catch(Exception e) {
			logger.error(e.toString(), e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String jsonStr, Class<T> tCla) {
		T t = null;
		try {
			t = ((T) JSONObject.toBean(JSONObject.fromObject(jsonStr), tCla));
		} catch(Exception e) {
			logger.error(e.toString(), e);
		}
		return t;
	}
	
	public static enum JSON_TYPE {
		JSON_TYPE_OBJECT,JSON_TYPE_ARRAY,JSON_TYPE_ERROR
	}
	
	public static JSON_TYPE getJSONType(String str) {
		if(str==null || "".equals(str.trim())) {
			return JSON_TYPE.JSON_TYPE_ERROR;
		}
		final char firstChar = str.charAt(0);
		switch(firstChar) {
		case '{':
			return JSON_TYPE.JSON_TYPE_OBJECT;
		case '[':
			return JSON_TYPE.JSON_TYPE_ARRAY;
		default:
			return JSON_TYPE.JSON_TYPE_ARRAY;
		}
	}
}
