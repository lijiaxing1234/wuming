package com.thinkgem.jeesite.modules.teacher.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;

/**
 * 试卷考的知识点
 *
 */
public class Examknowledge extends DataEntity<Examknowledge> {

	private static final long serialVersionUID = 1L;

	private  Examination  exam;  //试卷
	private CourseKnowledge  courseKnowledge;  //考的知识点
	
	
	public Examination getExam() {
		return exam;
	}
	public void setExam(Examination exam) {
		this.exam = exam;
	}
	
    public CourseKnowledge getCourseKnowledge() {
		return courseKnowledge;
	}
    public void setCourseKnowledge(CourseKnowledge courseKnowledge) {
		this.courseKnowledge = courseKnowledge;
	}
    
}
