package com.thinkgem.jeesite.modules.student.utils;

import java.util.Map;

public class PageMobileUtils {
	
	public static Map<String, Object> getPageMap(String page,String pageSize,Map<String,Object> paraMap){
		int i = 0;
		int j = 10;
		if(null == pageSize || "".equals(pageSize)){
			paraMap.put("j", j);
		}else{
			j = Integer.parseInt(pageSize);
			paraMap.put("j", j);
		}
		if(null == page || "".equals(page)){
			paraMap.put("i", 0);
		}else{
			paraMap.put("i", (Integer.parseInt(page)-1)*j);
		}
		return paraMap;
	}
	
}
