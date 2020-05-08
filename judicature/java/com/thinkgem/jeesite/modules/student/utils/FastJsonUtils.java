package com.thinkgem.jeesite.modules.student.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FastJsonUtils {
	
	/**
	 * 数据验证返回提示信息debugMessage
	 * @param status
	 * @param debugMessage
	 * @return
	 */
	public static <T> String addSD(String debugMessage){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", "3");
		jsonObject.put("message", "");
		jsonObject.put("debugMessage", debugMessage);
		List<T> list = new ArrayList<T>();
		JSONArray array = (JSONArray) JSON.toJSON(list);
		jsonObject.put("data", array);
		return jsonObject.toString();
	}
	
	/**
	 * @param status
	 * @param message
	 * @param list
	 * @return
	 */
	public static <T> String addSMA(Integer status,String message,List<T> list){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", status+"");
		jsonObject.put("message", message);
		JSONArray array = null;
		if(null == list){
			List<T> arrayList = new ArrayList<T>();
			array = (JSONArray) JSON.toJSON(arrayList);
		}else{
			array = (JSONArray) JSON.toJSON(list);
		}
		jsonObject.put("data", array);
		return jsonObject.toString();
	}
	
}
