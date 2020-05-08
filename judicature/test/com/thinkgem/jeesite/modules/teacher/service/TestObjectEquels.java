package com.thinkgem.jeesite.modules.teacher.service;

import com.thinkgem.jeesite.modules.questionlib.entity.Teacher;

public class TestObjectEquels {
	public static void main(String[] args) {
		Teacher t1 = new Teacher();
		t1.setLoginName("aa");
		t1.setName("zs");
		t1.setNo("11");
		Teacher t2 = new Teacher();
		t2.setLoginName("aa");
		t2.setName("zs");
		t2.setNo("11");
		System.out.println(t1.equals(t2));
		
		
		
	}
}
