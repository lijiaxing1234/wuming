package com.thinkgem.jeesite.modules.teacher.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;

/**
 * 试卷适用的班级
 */
public class ExamClass extends DataEntity<ExamClass> {

	private static final long serialVersionUID = 1L;

	private  Examination exam;  //试卷
	private  SchoolClass schoolClass; //学校班级
	private String status;//试卷对应班级状态
	
	public Examination getExam() {
		return exam;
	}
	public void setExam(Examination exam) {
		this.exam = exam;
	}
	public SchoolClass getSchoolClass() {
		return schoolClass;
	}
	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ExamClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExamClass(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public ExamClass(Examination exam, SchoolClass schoolClass, String status) {
		super();
		this.exam = exam;
		this.schoolClass = schoolClass;
		this.status = status;
	}

}
