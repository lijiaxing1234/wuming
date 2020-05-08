/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 课程版本Entity
 * @author webcat
 * @version 2016-09-16
 */
public class SCourseVersion extends DataEntity<SCourseVersion> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 名称
	private Course course;		// 课程
	private String schoolVersionId;
	
	
	public String getSchoolVersionId() {
		return schoolVersionId;
	}

	public void setSchoolVersionId(String schoolVersionId) {
		this.schoolVersionId = schoolVersionId;
	}

	public SCourseVersion() {
		super();
	}

	public SCourseVersion(String id){
		super(id);
	}

	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
}