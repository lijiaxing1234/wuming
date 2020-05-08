/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 申请入平台Entity
 * @author flychao
 * @version 2016-12-06
 */
public class TableQuestionlibImportApply extends DataEntity<TableQuestionlibImportApply>{
	
	private static final long serialVersionUID = 1L;
	private String schoolId;		// school_id
	String  schoolName;
	
	private String queslibImportId;	// queslib_import_id
	private Integer status;		// -1:未通过;1:申请中;2:已通过;
	
	
	
	
	QuestionlibImport queslibImport;
	
	public TableQuestionlibImportApply() {
		super();
	}

	public TableQuestionlibImportApply(String id){
		super(id);
	}

	@Length(min=1, max=32, message="school_id长度必须介于 1 和 32 之间")
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	@Length(min=1, max=32, message="queslib_import_id长度必须介于 1 和 32 之间")
	public String getQueslibImportId() {
		return queslibImportId;
	}

	public void setQueslibImportId(String queslibImportId) {
		this.queslibImportId = queslibImportId;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public QuestionlibImport getQueslibImport() {
		return queslibImport;
	}
	public void setQueslibImport(QuestionlibImport queslibImport) {
		this.queslibImport = queslibImport;
	}
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
}