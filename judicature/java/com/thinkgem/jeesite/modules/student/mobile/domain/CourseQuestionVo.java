package com.thinkgem.jeesite.modules.student.mobile.domain;

import java.util.Date;

public class CourseQuestionVo {
	private String questionId;
	private String questionTitle;
	private String tearcherId;
	private String tearcherName;
	private Date startDate;
	private String courseId;
	private String courseName;
	private String versionId;
	private String versionName;
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
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
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	
}
