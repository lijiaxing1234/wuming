package com.thinkgem.jeesite.modules.teacher.entity;

/**
 * 试题的记录
 * 记录试题的出题次数和练习题的练习次数
 * @author flychao
 * @version v.0.1
 */
public class QuestionRecord extends QuestionslibSplit{

	private static final long serialVersionUID = 1L;
	

	private Long   practiceCount;  //练习次数
	private Long   exampaperCount; //出卷次数
	
	
	public Long getPracticeCount() {
		return practiceCount;
	}
	public void setPracticeCount(Long practiceCount) {
		this.practiceCount = practiceCount;
	}
	public Long getExampaperCount() {
		return exampaperCount;
	}
	public void setExampaperCount(Long exampaperCount) {
		this.exampaperCount = exampaperCount;
	}
	
	
	

}
