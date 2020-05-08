package com.thinkgem.jeesite.modules.teacher.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;

/**
 * 试卷模板对应的试题
 */
public class ExamdetailQuestion extends DataEntity<ExamdetailQuestion> {

	private static final long serialVersionUID = 1L;
    
	private VersionQuestion question;
	private Examdetail  examdetail;
	private Integer sort;
	
	private String  quesType; //试题类型 （单选题、多选题等11中题型）
	
	private Integer count; //统计出过本题的次数
	
	
	public ExamdetailQuestion(){
		
	}
	public ExamdetailQuestion(VersionQuestion question){
		this.question=question;
	}
	
	
	public VersionQuestion getQuestion() {
		return question;
	}
	public void setQuestion(VersionQuestion question) {
		this.question = question;
	}
	public Examdetail getExamdetail() {
		return examdetail;
	}
	public void setExamdetail(Examdetail examdetail) {
		this.examdetail = examdetail;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getQuesType() {
		return quesType;
	}
	
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	
	
	
}
