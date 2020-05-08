package com.thinkgem.jeesite.modules.teacher.dto;


public class ScoreDTO {
	private String scoreRange;
	private String number;
	public String getScoreRange() {
		return scoreRange;
	}
	public void setScoreRange(String scoreRange) {
		this.scoreRange = scoreRange;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public ScoreDTO() {
		super();
	}
	public ScoreDTO(String scoreRange, String number) {
		super();
		this.scoreRange = scoreRange;
		this.number = number;
	}
	@Override
	public String toString() {
		return "ScoreDTO [scoreRange=" + scoreRange + ", number=" + number + "]";
	}
	
}
