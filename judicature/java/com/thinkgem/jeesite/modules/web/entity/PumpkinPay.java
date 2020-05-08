package com.thinkgem.jeesite.modules.web.entity;

import java.sql.Timestamp;

public class PumpkinPay {

	private int id;
	private String userId;
	private String courseId;
	private int pumpkinCount;
	private Timestamp payDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public int getPumpkinCount() {
		return pumpkinCount;
	}
	public void setPumpkinCount(int pumpkinCount) {
		this.pumpkinCount = pumpkinCount;
	}
	public Timestamp getPayDate() {
		return payDate;
	}
	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}
	
}
