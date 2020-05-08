package com.thinkgem.jeesite.modules.web.entity;

import java.util.UUID;

import com.thinkgem.jeesite.modules.sys.entity.Office;

public class Questions implements Comparable<Questions>{

	
	
	private String id;
	private String examCode; // 考点
	private String quesType; // 题型
	private String quesLevel; // 难度
	private String quesPoint; // 分值
	private String quesRealPoint; 
	private String title; // 题目
	private String questionCode; // 试题编码
	private String choice0; // 选项A
	private String choice1; // 选项B
	private String choice2; // 选项C
	private String choice3; // 选项D
	private String choice4; // 选项E
	private String choice5; // 选项F
	private String choice6; // 选项G
	private String choice7; // 选项H
	private String choice8; // 选项I
	private String choice9; // 选项J

	private String answer0; // 答案 单选题、多选题、填空题、计算题、简答题答案
	private String answer1;
	private String answer2;
	private String answer3;
	private String count; // 选择题选项或填空题答案的数目
	private String description; // 讲解
	private String writer; // 命题人
	private String checker; // 审题人
	private Office office; // 单位
	private String tearchId; // 所属老师
	private String versionId; // 课程id
	private String trueVersionId;
	private String questionlibId; // 题库
	private String studentId;
	private int isCorrect;
	private String material;
	private String answerId = UUID.randomUUID().toString().trim().replaceAll("-", "");
	
	
	public String getTrueVersionId() {
		return trueVersionId;
	}
	public void setTrueVersionId(String trueVersionId) {
		this.trueVersionId = trueVersionId;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getAnswer1() {
		return answer1;
	}
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	public String getAnswer2() {
		return answer2;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public String getAnswer3() {
		return answer3;
	}
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnserId(String answerId) {
		this.answerId = answerId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public int getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(int isCorrect) {
		this.isCorrect = isCorrect;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExamCode() {
		return examCode;
	}
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}
	public String getQuesType() {
		return quesType;
	}
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	public String getQuesLevel() {
		return quesLevel;
	}
	public void setQuesLevel(String quesLevel) {
		this.quesLevel = quesLevel;
	}
	public String getQuesPoint() {
		return quesPoint;
	}
	public void setQuesPoint(String quesPoint) {
		this.quesPoint = quesPoint;
	}
	public String getQuesRealPoint() {
		return quesRealPoint;
	}
	public void setQuesRealPoint(String quesRealPoint) {
		this.quesRealPoint = quesRealPoint;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQuestionCode() {
		return questionCode;
	}
	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}
	public String getChoice0() {
		return choice0;
	}
	public void setChoice0(String choice0) {
		this.choice0 = choice0;
	}
	public String getChoice1() {
		return choice1;
	}
	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}
	public String getChoice2() {
		return choice2;
	}
	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}
	public String getChoice3() {
		return choice3;
	}
	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}
	public String getChoice4() {
		return choice4;
	}
	public void setChoice4(String choice4) {
		this.choice4 = choice4;
	}
	public String getChoice5() {
		return choice5;
	}
	public void setChoice5(String choice5) {
		this.choice5 = choice5;
	}
	public String getChoice6() {
		return choice6;
	}
	public void setChoice6(String choice6) {
		this.choice6 = choice6;
	}
	public String getChoice7() {
		return choice7;
	}
	public void setChoice7(String choice7) {
		this.choice7 = choice7;
	}
	public String getChoice8() {
		return choice8;
	}
	public void setChoice8(String choice8) {
		this.choice8 = choice8;
	}
	public String getChoice9() {
		return choice9;
	}
	public void setChoice9(String choice9) {
		this.choice9 = choice9;
	}
	public String getAnswer0() {
		return answer0;
	}
	public void setAnswer0(String answer0) {
		this.answer0 = answer0;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public Office getOffice() {
		return office;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
	public String getTearchId() {
		return tearchId;
	}
	public void setTearchId(String tearchId) {
		this.tearchId = tearchId;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getQuestionlibId() {
		return questionlibId;
	}
	public void setQuestionlibId(String questionlibId) {
		this.questionlibId = questionlibId;
	}
	@Override
	public int compareTo(Questions o) {
		int quesType2 = Integer.parseInt(this.getQuesType());
		int quesType3 = Integer.parseInt(o.getQuesType());
		int i = quesType2 - quesType3;
		return i;
	}
	
}
