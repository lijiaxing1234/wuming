package com.thinkgem.jeesite.modules.questionlib.dto;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class TeacherDTO extends DataEntity<TeacherDTO>{

	private static final long serialVersionUID = 1L;
	private String teacherId;
	private String teacherName;
	
	private int classCount;
	private int courseCount;
	private int classTestCount;
	private int examCount;
	private int homeworkCount;
	private int exampleCount;
	private int onlineExamCount;
	
	private int start;
	private int pageSize;
	
	private String schoolId;
	private String schoolName;
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public int getClassCount() {
		return classCount;
	}
	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}
	public int getCourseCount() {
		return courseCount;
	}
	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}
	public int getClassTestCount() {
		return classTestCount;
	}
	public void setClassTestCount(int classTestCount) {
		this.classTestCount = classTestCount;
	}
	public int getExamCount() {
		return examCount;
	}
	public void setExamCount(int examCount) {
		this.examCount = examCount;
	}
	public int getHomeworkCount() {
		return homeworkCount;
	}
	public void setHomeworkCount(int homeworkCount) {
		this.homeworkCount = homeworkCount;
	}
	public int getExampleCount() {
		return exampleCount;
	}
	public void setExampleCount(int exampleCount) {
		this.exampleCount = exampleCount;
	}
	public int getOnlineExamCount() {
		return onlineExamCount;
	}
	public void setOnlineExamCount(int onlineExamCount) {
		this.onlineExamCount = onlineExamCount;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	

}
