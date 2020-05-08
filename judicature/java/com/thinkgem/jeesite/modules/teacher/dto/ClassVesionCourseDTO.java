package com.thinkgem.jeesite.modules.teacher.dto;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.TableClass;

public class ClassVesionCourseDTO extends DataEntity<ClassVesionCourseDTO>{
	private TableClass tableClass;		//班级
	private CourseVesion courseVesion;	//版本
	private Course course;				//课程
	
	
	
	public TableClass getTableClass() {
		return tableClass;
	}
	public void setTableClass(TableClass tableClass) {
		this.tableClass = tableClass;
	}
	public CourseVesion getCourseVesion() {
		return courseVesion;
	}
	public void setCourseVesion(CourseVesion courseVesion) {
		this.courseVesion = courseVesion;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public ClassVesionCourseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClassVesionCourseDTO(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public ClassVesionCourseDTO(TableClass tableClass, CourseVesion courseVesion, Course course) {
		super();
		this.tableClass = tableClass;
		this.courseVesion = courseVesion;
		this.course = course;
	}
	@Override
	public String toString() {
		return "ClassVesionCourseDTO [tableClass=" + tableClass + ", courseVesion=" + courseVesion + ", course="
				+ course + "]";
	}
	
	
	
}
