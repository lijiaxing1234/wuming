package com.thinkgem.jeesite.modules.web.util;

import java.io.File;

/**
 * 获取项目信息
 */
public class SystemUtils {

	
	public static String getSysPath() {
		String path = getClassPath();
		return path.substring(0, path.indexOf(File.separator+"WEB-INF"+File.separator+"classes"));
	}

	public static String getClassPath() {
		String classPath = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		File file =new File(classPath);
		return file.getPath();
	}

	public static String getSystempPath() {
		return System.getProperty("java.io.tmpdir");
	}

	public static String getSeparator() {
		return System.getProperty("file.separator");
	}
	
	
	/**
	 * 获取当前操作系统名称. 
	 * @return 操作系统名称 例如:windows,Linux,Unix等.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}
	
	

	public static void main(String[] args) {
		System.out.println(getOSName());
		
		System.out.println(getSysPath());
		System.out.println(System.getProperty("java.io.tmpdir"));
		System.out.println(getSeparator());
		System.out.println(getClassPath());
	}
}
