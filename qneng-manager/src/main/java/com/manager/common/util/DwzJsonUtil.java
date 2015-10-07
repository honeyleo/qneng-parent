package com.manager.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;

public class DwzJsonUtil {
	public static Map<String, String> getOkStatusMsg(String msg) {
		Map<String, String> res = new HashMap<String, String>();
		res.put("statusCode", "200");
		if (null != msg && msg.length() > 0) {
			res.put("message", msg);
		}
		return res;
	}
		
	public static Map<String, String> getOkStatusMsgWithClose(String msg) {
		Map<String, String> res = new HashMap<String, String>();
		res.put("statusCode", "200");
		if (null != msg && msg.length() > 0) {
			res.put("message", msg);
		}
		res.put("callbackType", "closeCurrent");
		return res;
	}

	public static Map<String, String> getRedictStatusMsg(String msg, String url) {
		Map<String, String> res = new HashMap<String, String>();
		res.put("statusCode", "100");
		if (null != msg && msg.length() > 0) {
			res.put("message", msg);
		}
		res.put("url", url);
		return res;
	}

	public static Map<String, String> getErrorStatusMsg(String msg) {
		Map<String, String> res = new HashMap<String, String>();
		res.put("statusCode", "300");
		res.put("message", msg);
		return res;
	}

	public static String getSessionTimeOutMsg() {
		return "{\"statusCode\":\"301\", \"message\":\"会话超时，请重新登录!\"}";
	}

	public static String getJSON(Object obj){
		String jsonStr = JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
		return jsonStr;
	}
	
	public static void main(String[] args) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("date", new Date());
		map.put("name", "廖鹏");
		String str = getJSON(map);
		System.out.println(str);
	}
}
