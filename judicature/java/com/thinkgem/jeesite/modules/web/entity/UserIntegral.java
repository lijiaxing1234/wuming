package com.thinkgem.jeesite.modules.web.entity;

import java.util.Date;

public class UserIntegral {

	private int id;
	private String userId;
	private Date createDate;
	private int videoTime;
	private int questionNum;
	private int liveTime;
	private int videoIntegral;
	private int questionIntegral;
	private int liveIntegral;
	private int total;
	private int signIntegral;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getVideoTime() {
		return videoTime;
	}
	public void setVideoTime(int videoTime) {
		this.videoTime = videoTime;
	}
	public int getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}
	public int getLiveTime() {
		return liveTime;
	}
	public void setLiveTime(int liveTime) {
		this.liveTime = liveTime;
	}
	public int getVideoIntegral() {
		return videoIntegral;
	}
	public void setVideoIntegral(int videoIntegral) {
		this.videoIntegral = videoIntegral;
	}
	public int getQuestionIntegral() {
		return questionIntegral;
	}
	public void setQuestionIntegral(int questionIntegral) {
		this.questionIntegral = questionIntegral;
	}
	public int getLiveIntegral() {
		return liveIntegral;
	}
	public void setLiveIntegral(int liveIntegral) {
		this.liveIntegral = liveIntegral;
	}
	public int getSignIntegral() {
		return signIntegral;
	}
	public void setSignIntegral(int signIntegral) {
		this.signIntegral = signIntegral;
	}
	
	
}
