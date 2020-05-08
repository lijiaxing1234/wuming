package com.thinkgem.jeesite.modules.questionlib.dto;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;

public class SchoolStaticsDTO  extends DataEntity<SchoolStaticsDTO> {

	private String id;
	private String schoolName;
	private int teacherCount;
	private int studentCount;
	private int questionlibCount;
	private int examCount;
	
	private int start;
	private int pageSize;
	
	
	
	private String specialtyId;
	private String specialtyName;
	
	private String courseId;
	private String courseName;
	
	private String versionId;
	private String versionName;
	
	
	
	public String getSpecialtyId() {
		return specialtyId;
	}
	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ExcelField(title="学校名称", align=2, sort=1)
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	@ExcelField(title="教师数量", align=2, sort=2)
	public int getTeacherCount() {
		return teacherCount;
	}
	public void setTeacherCount(int teacherCount) {
		this.teacherCount = teacherCount;
	}
	@ExcelField(title="学生数量", align=2, sort=3)
	public int getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}
	@ExcelField(title="题库数量", align=2, sort=4)
	public int getQuestionlibCount() {
		return questionlibCount;
	}
	public void setQuestionlibCount(int questionlibCount) {
		this.questionlibCount = questionlibCount;
	}
	@ExcelField(title="试卷数量", align=2, sort=5)
	public int getExamCount() {
		return examCount;
	}
	public void setExamCount(int examCount) {
		this.examCount = examCount;
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
	
	

}
