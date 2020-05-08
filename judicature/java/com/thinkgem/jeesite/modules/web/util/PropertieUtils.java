package com.thinkgem.jeesite.modules.web.util;

import com.thinkgem.jeesite.common.utils.PropertiesLoader;

public class PropertieUtils {
	
	private static PropertiesLoader properties = new PropertiesLoader(
			"uploadpath.properties");
	
	public static String getProperty(String key){	
		return properties.getProperty(key);
	}
	
	/**
	 * 读取服务器访问路径，会自动在后面补上/
	 * @param key
	 * @return
	 */
	public static String getServer(String key){	
		String uri = properties.getProperty(key);
		if (uri.lastIndexOf("/") != uri.length() - 1) {
			uri += "/";
		}
		return uri;
	}
}
