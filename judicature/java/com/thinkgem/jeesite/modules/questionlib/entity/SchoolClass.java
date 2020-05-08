/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 班级Entity
 * 
 * @author webcat
 * @version 2016-09-14
 */
public class SchoolClass extends DataEntity<SchoolClass> {

	private static final long serialVersionUID = 1L;
	@ExcelField(title = "班级名称")
	private String title; // 班级名称
	private String schoolId;
	private Office school; // 所属学校
	private String classNumber; // 班级编号（学校内部班级编号，供学校导入学生使用）
	private Date startDate;
	private Date endDate;
	@ExcelField(title = "专业名称")
	private String specialtyTitle;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	@Length(min = 0, max = 100, message = "专业名称长度必须介于 0 和 100 之间")
	public String getSpecialtyTitle() {
		return specialtyTitle;
	}

	public void setSpecialtyTitle(String specialtyTitle) {
		this.specialtyTitle = specialtyTitle;
	}

	// 导入班级需要字段 班级的有效时间
	@ExcelField(title = "班级创建时间")
	private String strStartDate;// 班级创建时间
	@ExcelField(title = "班级毕业时间")
	private String strEndDate;// 班级结束时间

	public String getStrStartDate() {
		return strStartDate;
	}

	public void setStrStartDate(String strStartDate) {
		this.strStartDate = strStartDate;
	}

	public String getStrEndDate() {
		return strEndDate;
	}

	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
	}

	public SchoolClass() {
		super();
	}

	public String getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public SchoolClass(String id) {
		super(id);
	}

	@Length(min = 0, max = 100, message = "班级名称长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Office getSchool() {
		return school;
	}

	public void setSchool(Office school) {
		this.school = school;
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SchoolClass)) {
			return false;
		}
		SchoolClass schoolClass = (SchoolClass) obj;
		return this.title.equals(schoolClass.getTitle());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	
	
	
	
	
	

}