package com.thinkgem.jeesite.modules.student.mobile.domain;

import java.util.Date;

public class HomeworkVo {
	private String id;
	private String name;
	private String tearcherId;
	private String tearcherName;
	private Date startDate;
	private Date endDate;
	private String state;
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
	public String getTearcherId() {
		return tearcherId;
	}
	public void setTearcherId(String tearcherId) {
		this.tearcherId = tearcherId;
	}
	public String getTearcherName() {
		return tearcherName;
	}
	public void setTearcherName(String tearcherName) {
		this.tearcherName = tearcherName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
