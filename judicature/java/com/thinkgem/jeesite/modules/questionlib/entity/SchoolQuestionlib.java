/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 学校数据库Entity
 * 
 * @author ryz
 * @version 2016-09-14
 */
public class SchoolQuestionlib extends DataEntity<SchoolQuestionlib> {

	private static final long serialVersionUID = 1L;
	private String schoolId; // 学校id
	private String questionlibId; // 题库id
	private Office school; // 学校
	@ExcelField(title = "学校名称", align = 2)
	private String schoolName; //
	@ExcelField(title = "学校负责人",align = 2)
	private String master;// 学校负责人姓名
	private CourseQuestionlib courseQuestionlib; // 题库
	@ExcelField(title = "题库名称", align = 2)
	private String questionLibName;
	@ExcelField(title = "授权开始时间",align = 2)
	private Date validStartDate; // 有效期开始时间
	@ExcelField(title = "授权结束时间",align = 2)
	private Date validEndDate; // 有效期结束时间
	@ExcelField(title = "状态",align = 2)
	private String state; // 使用状态 1.试用 2.购买 3.过期

	private Specialty specialty;
	private Course course;
	private CourseVesion courseVesion;
	
	private String ownerType;		// 所属类型
	
	
	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	private String questionlibOwner; // 题库所有者
	
	public String getQuestionlibOwner() {
		return questionlibOwner;
	}

	public void setQuestionlibOwner(String questionlibOwner) {
		this.questionlibOwner = questionlibOwner;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getQuestionLibName() {
		return questionLibName;
	}

	public void setQuestionLibName(String questionLibName) {
		this.questionLibName = questionLibName;
	}

	public SchoolQuestionlib() {
		super();
	}

	public SchoolQuestionlib(String id) {
		super(id);
	}

	@Length(min = 0, max = 32, message = "学校长度必须介于 0 和 32 之间")
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	@Length(min = 0, max = 32, message = "题库长度必须介于 0 和 32 之间")
	public String getQuestionlibId() {
		return questionlibId;
	}

	public void setQuestionlibId(String questionlibId) {
		this.questionlibId = questionlibId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	@Length(min = 0, max = 32, message = "使用状态长度必须介于 0 和 32 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Office getSchool() {
		return school;
	}

	public void setSchool(Office school) {
		this.school = school;
	}

	public CourseQuestionlib getCourseQuestionlib() {
		return courseQuestionlib;
	}

	public void setCourseQuestionlib(CourseQuestionlib courseQuestionlib) {
		this.courseQuestionlib = courseQuestionlib;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CourseVesion getCourseVesion() {
		return courseVesion;
	}

	public void setCourseVesion(CourseVesion courseVesion) {
		this.courseVesion = courseVesion;
	}

}