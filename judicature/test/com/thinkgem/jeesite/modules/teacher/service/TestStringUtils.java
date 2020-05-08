package com.thinkgem.jeesite.modules.teacher.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.junit.Test;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;

public class TestStringUtils {
//	public static void main(String[] args) {
//		String str = "ABCD";
//		byte[] bytes = str.getBytes();
//		for (byte b : bytes) {
//			System.out.println(String.valueOf((char)b));
//		}
//		
//		
//	}
	
	
	
	
	
	public static void main(String[] args) {
		StringBuffer failureMsg = new StringBuffer();
		failureMsg.append("asfdsafds");
		System.out.println(StringUtils.isBlank(failureMsg));
		System.out.println(String.valueOf(failureMsg));
		
	}
	
	
	
}
