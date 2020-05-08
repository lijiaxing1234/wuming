package com.thinkgem.jeesite.modules.web.entity;

import java.util.UUID;


public class UserInfo {

	private String id = UUID.randomUUID().toString().trim().replaceAll("-", "");
	private String name;
	private String sex;
	private String phone;
	private String password;
	private String createDate;
	private String icon;
	private String region;
	private long studyTime;
	private int liveCount;
	private double queNum;
	private Long videoTime;
	private int pumpkinCount;
	private String examDate;
	int longDay;
	
	public int getLongDay() {
		return longDay;
	}
	public void setLongDay(int longDay) {
		this.longDay = longDay;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public int getPumpkinCount() {
		return pumpkinCount;
	}
	public void setPumpkinCount(int pumpkinCount) {
		this.pumpkinCount = pumpkinCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public long getStudyTime() {
		return studyTime;
	}
	public void setStudyTime(long studyTime) {
		this.studyTime = studyTime;
	}
	public int getLiveCount() {
		return liveCount;
	}
	public void setLiveCount(int liveCount) {
		this.liveCount = liveCount;
	}
	
	public double getQueNum() {
		return queNum;
	}
	public void setQueNum(double queNum) {
		this.queNum = queNum;
	}
	public Long getVideoTime() {
		return videoTime;
	}
	public void setVideoTime(Long videoTime) {
		this.videoTime = videoTime;
	}
	
}
