package com.thinkgem.jeesite.modules.teacher.dto;

public class LoginDTO{
	private String onLineCount;//在线考试未判卷统计
	private String testCount;//随堂练习未判卷统计
	private String homeWorkCount;//课后作业未判卷统计
	private String unPublishHomeWork;//未发布的课后作业统计
	private String unFinishTest;//未收卷的随堂统计
	public String getOnLineCount() {
		return onLineCount;
	}
	public void setOnLineCount(String onLineCount) {
		this.onLineCount = onLineCount;
	}
	public String getTestCount() {
		return testCount;
	}
	public void setTestCount(String testCount) {
		this.testCount = testCount;
	}
	public String getHomeWorkCount() {
		return homeWorkCount;
	}
	public void setHomeWorkCount(String homeWorkCount) {
		this.homeWorkCount = homeWorkCount;
	}
	public String getUnPublishHomeWork() {
		return unPublishHomeWork;
	}
	public void setUnPublishHomeWork(String unPublishHomeWork) {
		this.unPublishHomeWork = unPublishHomeWork;
	}
	public String getUnFinishTest() {
		return unFinishTest;
	}
	public void setUnFinishTest(String unFinishTest) {
		this.unFinishTest = unFinishTest;
	}
	public LoginDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginDTO(String onLineCount, String testCount, String homeWorkCount, String unPublishHomeWork,
			String unFinishTest) {
		super();
		this.onLineCount = onLineCount;
		this.testCount = testCount;
		this.homeWorkCount = homeWorkCount;
		this.unPublishHomeWork = unPublishHomeWork;
		this.unFinishTest = unFinishTest;
	}
	@Override
	public String toString() {
		return "LoginDTO [onLineCount=" + onLineCount + ", testCount=" + testCount + ", homeWorkCount=" + homeWorkCount
				+ ", unPublishHomeWork=" + unPublishHomeWork + ", unFinishTest=" + unFinishTest + "]";
	}
	
	
}
