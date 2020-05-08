package com.thinkgem.jeesite.modules.teacher.service;

import java.util.Date;

public class DateTest {
	public static void main(String[] args) {
		Date date1 = new Date();
		Date date2 = new Date();
		
		int compareTo = date1.compareTo(date2);
		System.out.println(compareTo);
		
	}
}
