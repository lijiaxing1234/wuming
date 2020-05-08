package com.thinkgem.jeesite.modules.student.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 测验使用的模板Entity
 * 
 * @author .36
 *
 */
public class StudentExamDetail extends DataEntity<StudentExamDetail> {

	private static final long serialVersionUID = 1L;
	private String id; // 主键
	private SExam exam; // 考试
	private String printTemplate; // 模板
	private String type; // A：A卷 B:B卷 其他:0

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SExam getExam() {
		return exam;
	}

	public void setExam(SExam exam) {
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

}
