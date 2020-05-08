package com.thinkgem.jeesite.modules.web.entity;

import java.sql.Timestamp;
import java.util.List;

public class Course {

	private String id;
	private String title;
	private String specialty_id;
	private String level;
	private String phase;
	private String course_code;
	private String couse_system;
	private String create_by;
	private Timestamp create_date;
	private String update_by;
	private Timestamp update_date;
	
	
	private Integer totalQuestion;
	private Integer hasDoneQuestion;
	private Integer rightQuestion;
	private Integer wrongQuestion;
	private Double accuracy;
	
	
	private List<Knowledge> knowledges;
	
	public Double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
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
	public List<Knowledge> getKnowledges() {
		return knowledges;
	}
	public void setKnowledges(List<Knowledge> knowledges) {
		this.knowledges = knowledges;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSpecialty_id() {
		return specialty_id;
	}
	public void setSpecialty_id(String specialty_id) {
		this.specialty_id = specialty_id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getCourse_code() {
		return course_code;
	}
	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}
	public String getCouse_system() {
		return couse_system;
	}
	public void setCouse_system(String couse_system) {
		this.couse_system = couse_system;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Timestamp getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public Timestamp getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}
	
	public Integer getHasDoneQuestion() {
		return hasDoneQuestion;
	}
	public void setHasDoneQuestion(Integer hasDoneQuestion) {
		this.hasDoneQuestion = hasDoneQuestion;
	}
}
