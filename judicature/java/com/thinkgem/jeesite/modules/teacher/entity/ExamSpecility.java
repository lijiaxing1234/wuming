package com.thinkgem.jeesite.modules.teacher.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;

/**
 * 试卷考试所适用专业
 * 
 */
public class ExamSpecility extends DataEntity<ExamSpecility> {
	private static final long serialVersionUID = 1L;
	
	private Examination  exam; //组卷类
	private Specialty    specialty; //专业类
	
	public ExamSpecility(){
		
	}
	
	public ExamSpecility(String id){
		this.id=id;
	}
	
	
	public Examination getExam() {
		return exam;
	}
	public void setExam(Examination exam) {
		this.exam = exam;
	}
	public Specialty getSpecialty() {
		return specialty;
	}
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}
	
	

}
