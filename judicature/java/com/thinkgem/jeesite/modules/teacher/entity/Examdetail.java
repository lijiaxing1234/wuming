package com.thinkgem.jeesite.modules.teacher.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 试卷打印的模板和调用的模板
 */
public class Examdetail  extends DataEntity<Examdetail>{

	private static final long serialVersionUID = 1L;
	
	private  Examination exam;
	private String printTemplate;
	
	private  String type; //A：A卷 B:B卷 其他:0
	
	private  Double scores; //当前试卷已出试题的总分数
	
    private String mainTitle;
    private String subTitle;
	
	public Examdetail(){
		
	}
	public Examdetail(String id){
		this.id=id;
	}
	
	
	
	public Examination getExam() {
		return exam;
	}
	public void setExam(Examination exam) {
		this.exam = exam;
	}
	public String getPrintTemplate() {
		return printTemplate;
	}
	public void setPrintTemplate(String printTemplate) {
		this.printTemplate = printTemplate;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Double getScores() {
		return scores;
	}
	public void setScores(Double scores) {
		this.scores = scores;
	}
	public String getMainTitle() {
		return mainTitle;
	}
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
}
