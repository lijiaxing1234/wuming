/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 学生Entity
 */
public class Student extends DataEntity<Student> {

	private static final long serialVersionUID = 1L;
	@ExcelField(title = "学生姓名")
	private String name; // 学生姓名
	@ExcelField(title = "专业名称")
	private String specialtyTitle;
	private SchoolClass schoolClass; // 班级
	@ExcelField(title = "班级名称")
	private String classCode; // 班号
	@ExcelField(title = "登录名（学号）")
	private String stdCode; // 学号
	@ExcelField(title = "性别")
	private String stdSex; // 性别
	@ExcelField(title = "年龄")
	private String stdAge; // 年龄
	@ExcelField(title = "手机号码")
	private String stdPhone; // 电话号
	@ExcelField(title = "邮箱")
	private String stdEmail; // 邮箱
	@ExcelField(title = "登录密码")
	private String stdPassword; // 密码

	// 导入学生需要字段 机构编码
	@ExcelField(title = "机构编码")
	private String officeCode;

	private String status;// 标记该学生的试卷是否已判完

	private Date endDate;
	private Date startDate;
	private String schoolId;
	
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSpecialtyTitle() {
		return specialtyTitle;
	}

	public void setSpecialtyTitle(String specialtyTitle) {
		this.specialtyTitle = specialtyTitle;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public Student() {
		super();
	}

	public Student(String id) {
		super(id);
	}

	@Length(min = 0, max = 20, message = "学生姓名长度必须介于 0 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}

	@Length(min = 0, max = 64, message = "学号长度必须介于 0 和 64 之间")
	public String getStdCode() {
		return stdCode;
	}

	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
	}

	@Length(min = 0, max = 2, message = "性别长度必须介于 0 和 2 之间")
	public String getStdSex() {
		return stdSex;
	}

	public void setStdSex(String stdSex) {
		this.stdSex = stdSex;
	}

	@Length(min = 0, max = 3, message = "年龄长度必须介于 0 和 3 之间")
	public String getStdAge() {
		return stdAge;
	}

	public void setStdAge(String stdAge) {
		this.stdAge = stdAge;
	}

	@Length(min = 0, max = 11, message = "电话号长度必须介于 0 和 11 之间")
	public String getStdPhone() {
		return stdPhone;
	}

	public void setStdPhone(String stdPhone) {
		this.stdPhone = stdPhone;
	}

	@Length(min = 0, max = 100, message = "邮箱长度必须介于 0 和 100 之间")
	public String getStdEmail() {
		return stdEmail;
	}

	public void setStdEmail(String stdEmail) {
		this.stdEmail = stdEmail;
	}

	@Length(min = 0, max = 100, message = "密码长度必须介于 0 和 100 之间")
	public String getStdPassword() {
		return stdPassword;
	}

	public void setStdPassword(String stdPassword) {
		this.stdPassword = stdPassword;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Student)) {
			return false;
		}
		Student student = (Student) obj;
		return this.getStdCode().equals(student.getStdCode());
	}

	@Override
	public int hashCode() {
		return this.stdCode.hashCode();
	}

}