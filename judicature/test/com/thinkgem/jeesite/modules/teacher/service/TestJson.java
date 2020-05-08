package com.thinkgem.jeesite.modules.teacher.service;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.StringUtils;

public class TestJson {
	public static void main(String[] args) {
		
		
	/*	String studentAnswer = "{\"student_id\":\"1qasdfgh\",\"exam_id\":\"rsedsfghj\",\"exam_detail_id\":\"rsedsfghj\",\"startTime\":\"2016-09-09 12:12:00\",\"data\":[{\"question_id\":\"jhhjk\",\"answer\":\"jjhjk\",\"quesType\":\"1\"},{\"question_id\":\"jhhjk\",\"answer\":\"jjhjk\",\"quesType\":\"1\"}]}";
		String substringBetween = "[" + StringUtils.substringBetween(studentAnswer, "[", "]") + "]";
		JSONArray jsonArray = JSONArray.parseArray(substringBetween);
		for(int i=0;i<jsonArray.size();i++){
		     JSONObject jobj =  (JSONObject) jsonArray.get(i);
		     String object = (String)jobj.get("answer");
		     System.out.println(object);
		}*/
		
		
		try {
			String trade_no =new String("JmJ6Lcj+0uccprjmPSBGr4P8T9oZbHsyQ+jOkWFwOFocWFSmkgNUcg81Bf7Fwh7VgtFrFNp+ftxWntSvYOgKCBFYPffe9ZAEA3oupbLXnA4REUFYpDxrAeD+UmIMKi0w+6nRIxspYlPH+i7TASmUS2fK9f/kq2rT2WNCgleSrD9HKpr76vEkP47GI8Pmc8Hw+ZFlJOvkzR+Lx+ds4hoJUOHQ1KPQAbCmba/NDWL88fVpThohbglEhBk8CB/VFRvT2UaKo/n3d2PEwxaNMr0VcFy1954AqEEH8Oh8CcL/jUZbF0OAhiMmZEyEuwPGmgcuXrfRoFZ9Obt5lR47+4cnaQ==".getBytes("ISO-8859-1"),"UTF-8");
		
	       byte[] arr=com.alipay.api.internal.util.codec.Base64.decodeBase64(trade_no.getBytes());
		
		 System.out.println(new String(arr));
		}  catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
