/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 教学资源Entity
 * 
 * @author yfl
 * @version 2016-09-14
 */
public class Edresources extends DataEntity<Edresources> {

	private static final long serialVersionUID = 1L;
	private String resName; // 资源名称
	private String resFile; // 教学资源文件路径
	private String userId; // 用户id
	private String userName;
	private String officeId;//学校id
	private String knowledgeId; //
	private String knowledgeName;
	private String versionId;// 版本id
	private String versionName;// 版本名称
	private CourseKnowledge courseKnowledge;
	private String courseId;
	private String courseName;// 课程名称
	private String studentId;
	private String uploadUserName;
	

	public String getUploadUserName() {
		return uploadUserName;
	}

	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getKnowledgeName() {
		return knowledgeName;
	}

	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	// 课程知识点
	public CourseKnowledge getCourseKnowledge() {
		return courseKnowledge;
	}

	public void setCourseKnowledge(CourseKnowledge courseKnowledge) {
		this.courseKnowledge = courseKnowledge;
	}

	public Edresources() {
		super();
	}

	// 教学资源id
	public Edresources(String id) {
		super(id);
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResFile() {
		return resFile;
	}

	public void setResFile(String resFile) {
		this.resFile = resFile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

}