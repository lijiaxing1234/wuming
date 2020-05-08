package com.thinkgem.jeesite.modules.student.mobile.domain;

/**
 * 题目（包含 题干，答案，我的答案）
 * 
 * @author .36
 *
 */
public class QuestionVo {

	private String id;
	private String examDetailId;
	private String examCode; // 考点
	private String quesType; // 题型
	private String strQuesType; // 试题类型 文本
	private String quesLevel; // 难度
	private String quesPoint; // 分值
	private String description; // 讲解
	private String writer; // 命题人
	private String checker; // 审题人

	private String title; // 题目
	private String strTitle;
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
	private String[] choiceImgArr;

	private String answer0; // 答案
							// 单选题（只有一个正确答案）、多选题（ABC、ADE）、填空题（依次往后存储）、计算题、简答题答案
	private String answer1; // 答案
	private String answer2; // 答案
	private String answer3; // 答案
	private String answer4; // 答案
	private String answer5; // 答案
	private String answer6; // 答案
	private String answer7; // 答案
	private String answer8; // 答案
	private String answer9; // 答案
	private String answerImg;
	private String[] tiankongCount;
	private String isCorrent;
	private String count;
	
	

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getIsCorrent() {
		return isCorrent;
	}

	public void setIsCorrent(String isCorrent) {
		this.isCorrent = isCorrent;
	}

	public String getStrTitle() {
		return strTitle;
	}

	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	public String[] getTiankongCount() {
		return tiankongCount;
	}

	public void setTiankongCount(String[] tiankongCount) {
		this.tiankongCount = tiankongCount;
	}

	private String myAnswer; // 我的答案
	private String[] answerArr;

	public String getAnswerImg() {
		return answerImg;
	}

	public void setAnswerImg(String answerImg) {
		this.answerImg = answerImg;
	}

	public String[] getAnswerArr() {
		return answerArr;
	}

	public void setAnswerArr(String[] answerArr) {
		this.answerArr = answerArr;
	}

	public String[] getChoiceImgArr() {
		return choiceImgArr;
	}

	public void setChoiceImgArr(String[] choiceImgArr) {
		this.choiceImgArr = choiceImgArr;
	}

	public String getStrQuesType() {
		return strQuesType;
	}

	public void setStrQuesType(String strQuesType) {
		this.strQuesType = strQuesType;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getAnswer5() {
		return answer5;
	}

	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}

	public String getAnswer6() {
		return answer6;
	}

	public void setAnswer6(String answer6) {
		this.answer6 = answer6;
	}

	public String getAnswer7() {
		return answer7;
	}

	public void setAnswer7(String answer7) {
		this.answer7 = answer7;
	}

	public String getAnswer8() {
		return answer8;
	}

	public void setAnswer8(String answer8) {
		this.answer8 = answer8;
	}

	public String getAnswer9() {
		return answer9;
	}

	public void setAnswer9(String answer9) {
		this.answer9 = answer9;
	}

	public String getMyAnswer() {
		return myAnswer;
	}

	public void setMyAnswer(String myAnswer) {
		this.myAnswer = myAnswer;
	}

	public String getExamDetailId() {
		return examDetailId;
	}

	public void setExamDetailId(String examDetailId) {
		this.examDetailId = examDetailId;
	}

}
