package com.thinkgem.jeesite.modules.web.entity;

import java.sql.Timestamp;
import java.util.List;

public class Knowledge {

	private String id;
	private String knowledgeCode;
	private String title;
	private String creditHours;
	private String versionId;
	private String level;
	private String parentId;
	private String createBy;
	private Timestamp createDate;
	private String updateBy;
	private Timestamp updateDate;
	private String delFlag;
	private Integer sort;
	
	private Knowledge parent;//父级对象
	
	
	private Integer totalQuestion;
	private Integer hasDoneQuestion;
	private Integer rightQuestion;
	private Integer wrongQuestion;
	private Double accuracy;
	private List<Knowledge> sonKnowledges;
	
	private String userId;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getHasDoneQuestion() {
		return hasDoneQuestion;
	}
	public void setHasDoneQuestion(Integer hasDoneQuestion) {
		this.hasDoneQuestion = hasDoneQuestion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKnowledgeCode() {
		return knowledgeCode;
	}
	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreditHours() {
		return creditHours;
	}
	public void setCreditHours(String creditHours) {
		this.creditHours = creditHours;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getTotalQuestion() {
		return totalQuestion;
	}
	public void setTotalQuestion(Integer totalQuestion) {
		this.totalQuestion = totalQuestion;
	}
	public Integer getRightQuestion() {
		return rightQuestion;
	}
	public void setRightQuestion(Integer rightQuestion) {
		this.rightQuestion = rightQuestion;
	}
	public Integer getWrongQuestion() {
		return wrongQuestion;
	}
	public void setWrongQuestion(Integer wrongQuestion) {
		this.wrongQuestion = wrongQuestion;
	}
	public Double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}
	public List<Knowledge> getSonKnowledges() {
		return sonKnowledges;
	}
	public void setSonKnowledges(List<Knowledge> sonKnowledges) {
		this.sonKnowledges = sonKnowledges;
	}
	
	public Knowledge getParent() {
		return parent;
	}
	public void setParent(Knowledge parent) {
		this.parent = parent;
	}
	
	
	
	
	
	
	
}
