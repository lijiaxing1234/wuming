/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.utils.CacheUtils;

 
/**
 * email工具类
 * @author cq
 * @version 2016-11-28
 */
public class EmailUtils {

 
	public static final String EMAIL_CACHE = "emailCache";
	public static final String EMAIL_CACHE_ID_ = "Verification_Code_";
 
	
	/**
	 * 根据ID获取用户验证码
	 * @param id
	 * @return 取不到返回空字符
	 */
	public static String get(String id){
		
		String key = "";
		try {
			 key = (String)CacheUtils.get(EMAIL_CACHE, EMAIL_CACHE_ID_ + id);
		} catch (Exception e) {
		 
		}
	 
		return key;
	}
	
public static boolean put(String id ,String key){
		
	try {
		CacheUtils.put(EMAIL_CACHE, EMAIL_CACHE_ID_ + id,  key);
		 
		return  true;
	} catch (Exception e) {
		// TODO: handle exception
	}
	 return  false;
	}

public static boolean remove(String id){
	
	try {
		CacheUtils.remove(EMAIL_CACHE, EMAIL_CACHE_ID_ + id);
		 
		return  true;
	} catch (Exception e) {
		// TODO: handle exception
	}
	 return  false;
	}
	
	
	
	
	 
}
