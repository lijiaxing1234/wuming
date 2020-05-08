package com.thinkgem.jeesite.modules.web.entity;

public class UserSign {

	private int id;
	private String userId;
	private String signDate;
	private int pumpkinCount;
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
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public int getPumpkinCount() {
		return pumpkinCount;
	}
	public void setPumpkinCount(int pumpkinCount) {
		this.pumpkinCount = pumpkinCount;
	}
	
}
