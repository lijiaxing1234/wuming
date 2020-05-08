/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.questionlib.common.QuestionlibTld;

/**
 * 课程Entity
 * @author webcat
 * @version 2016-08-15
 */
public class Course extends DataEntity<Course> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 名称
	private String specialtyId;		// 专业
	private String level;		//  1、课程 ；2：真题
	private String phase;		// 学段
	private String couseSystem;		// 课程体系
	private Specialty specialty;
	private String courseCode;	//课程代码
	private Integer sort; 	// 排序
	
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Course() {
		super();
	}

	public Course(String id){
		super(id);
	}
    
	@SuppressWarnings("static-access")
	@ExcelField(title="专业名称", align=2, sort=1)
	public String getSpecialtyTitle(){
		QuestionlibTld qlt=new QuestionlibTld();
		if(qlt.getSpecialtyByID(specialtyId) !=null){
			return qlt.getSpecialtyByID(specialtyId).getTitle();
		}
		return null;
	}
	
	@ExcelField(title="课程名称", align=2, sort=2)
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=32, message="专业长度必须介于 0 和 32 之间")
	public String getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}
	
	@Length(min=0, max=10, message="难度长度必须介于 0 和 10 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=10, message="学段长度必须介于 0 和 10 之间")
	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	@Length(min=0, max=10, message="课程体系长度必须介于 0 和 10 之间")
	public String getCouseSystem() {
		return couseSystem;
	}

	public void setCouseSystem(String couseSystem) {
		this.couseSystem = couseSystem;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
}