/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 班级Entity
 * @author webcat
 * @version 2016-09-09
 */
public class TableClass extends DataEntity<TableClass> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 班级名称
	private String schoolId;		// 所属学校
	private String specialtyId;		// 所属专业
	
	public TableClass() {
		super();
	}

	public TableClass(String id){
		super(id);
	}

	@Length(min=0, max=100, message="班级名称长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=32, message="所属学校长度必须介于 0 和 32 之间")
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	@Length(min=0, max=32, message="所属专业长度必须介于 0 和 32 之间")
	public String getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}
	
}