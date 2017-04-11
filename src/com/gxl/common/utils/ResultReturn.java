package com.gxl.common.utils;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResultReturn {

	public static Map<String, Object> setMap(Map<String, Object> map,int errorCode,String msg,List datas) {
		map.put("error", errorCode);
		map.put("msg", msg);
		if(datas!=null)
			map.put("datas", datas);
		return map;
	}
	
	public static Map<String, Object> setJson(Map<String, Object> map,int errorCode,String msg,Map<String, Object> data) {
		map.put("error", errorCode);
		map.put("msg", msg);
		if(data!=null)
			map.put("data", data);
		return map;
	}
}
