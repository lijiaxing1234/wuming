/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.questionlib.common.QuestionlibTld;

/**
 * 课程版本Entity
 * 
 * @author webcat
 * @version 2016-08-16
 */
public class CourseVesion extends DataEntity<CourseVesion> {

	private static final long serialVersionUID = 1L;
	
	
	static QuestionlibTld qlt=new QuestionlibTld();
	
	private String title; // 名称
	private String courseId; // 课程
	
	private Course course;
	private Specialty specialty;
	
	
	private String versionCode;	//版本代码
	private String specialtyId; // 专业
	public String getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}

	public CourseVesion() {
		super();
	}

	public CourseVesion(String id) {
		super(id);
	}
   
	
	/**
	 * 	${questionlib:getSpecialtyByID(questionlib:getCourseByID(courseVesion.courseId).specialtyId).title}
		${questionlib:getCourseByID(courseVesion.courseId).title}
	 */
	
	
	@SuppressWarnings("static-access")
	@ExcelField(title="专业名称", align=2, sort=1)
	@Length(min = 0, max = 100, message = "名称长度必须介于 0 和 100 之间")
	public String getSpecialtyTitle() {
		
		if(qlt.getCourseByID(courseId)!=null){
			String spId=qlt.getCourseByID(courseId).getSpecialtyId();
			if(qlt.getSpecialtyByID(spId) !=null){
				return qlt.getSpecialtyByID(spId).getTitle();
			}
		}
		return null;
	}
	@SuppressWarnings("static-access")
	@ExcelField(title="课程名称", align=2, sort=2)
	@Length(min = 0, max = 100, message = "名称长度必须介于 0 和 100 之间")
	public String getCourseTitle() {
		if(qlt.getCourseByID(courseId)!=null){
			return qlt.getCourseByID(courseId).getTitle();
		}
		return null;
	}
	
	@ExcelField(title="版本名称", align=2, sort=3)
	@Length(min = 0, max = 100, message = "名称长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min = 0, max = 32, message = "课程长度必须介于 0 和 32 之间")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

}