package com.thinkgem.jeesite.modules.teacher.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.sys.entity.User;


/**
 * 试卷实体
 */
public class Examination extends DataEntity<Examination> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否模板标记（0：不是；1：是；）
	 */
	public static final String EXAM_TEMPLATE_NO = "0";
	public static final String EXAM_TEMPLATE_YES = "1";
	/**
	 * 试卷类型 ~手动组卷
	 */
	public static final String  EXAMINATION_EXAMMODE_YES="1";
	/**
	 * 试卷类型 ~自动组卷
	 */
	public static final String  EXAMINATION_EXAMMODE_NO="0";
	
	
	private String title;    //名称
	private String examType; //类型：1随堂测 2组卷考试 3作业 4例题 5在线考试
	private User   teacher; //老师
	private CourseVesion version ; //课程版本
	private String  examPlace; //考点
	private Date   beginTime; //开始时间
	private Date   endTime;   //结束时间
	private Date publishAnswerTime;//公布答案时间
	private String  isAb;    //是否是Ab卷 ，1是0否
	private String  totalScore; //总分数
	
	private String  isNew;  //是否是新建 1:新建0:调用模板  2:保存为模板
	private String  examMode; //组卷方式1:手动组卷0:自动组卷
	
	
	private Double  questionRadio;  //单选题分数
	private Double  questionMultiple; //多选题分数
	private Double  questionBlank;  //填空题分数
	private Double  questionShortAnswer; //简答题分数
	private Double  questionCompute;  //计算题分数
    
	private String  difficult; //难度倾向~困难 
	private String  simple;  //难度倾向~简单
	private String  general;//难度倾向~一般
	
	private String examHours; //考点学时

	private String endHours;//作业用时
	private String answerHours;//公布答案时间
	
	private String status; //测试状态
	
	private String istemplate; //0:不是模板；1：是模板；默认为0
	private String useTemplatId;//调用模板的id
	
	private String timeDifference;//考试共需多长时间
	
	
	public  Examination (){
		this.isNew="1";
		this.isAb="0";
		this.istemplate="0";
		this.examMode="0";
	} 
	
	public Examination(String id){
		this();
		this.id=id;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public User getTeacher() {
		return teacher;
	}
	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public CourseVesion getVersion() {
		return version;
	}
	public void setVersion(CourseVesion version) {
		this.version = version;
	}
	
	
	public String getExamPlace() {
		return examPlace;
	}
	public void setExamPlace(String examPlace) {
		this.examPlace = examPlace;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getIsAb() {
		return isAb;
	}
	public void setIsAb(String isAb) {
		this.isAb = isAb;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	
    public String getExamMode() {
		return examMode;
	}
    public void setExamMode(String examMode) {
		this.examMode = examMode;
	}

    
	public Double getQuestionRadio() {
		return questionRadio;
	}

	public void setQuestionRadio(Double questionRadio) {
		this.questionRadio = questionRadio;
	}

	public Double getQuestionMultiple() {
		return questionMultiple;
	}

	public void setQuestionMultiple(Double questionMultiple) {
		this.questionMultiple = questionMultiple;
	}

	public Double getQuestionBlank() {
		return questionBlank;
	}

	public void setQuestionBlank(Double questionBlank) {
		this.questionBlank = questionBlank;
	}

	public Double getQuestionShortAnswer() {
		return questionShortAnswer;
	}

	public void setQuestionShortAnswer(Double questionShortAnswer) {
		this.questionShortAnswer = questionShortAnswer;
	}

	public Double getQuestionCompute() {
		return questionCompute;
	}

	public void setQuestionCompute(Double questionCompute) {
		this.questionCompute = questionCompute;
	}

	public String getDifficult() {
		return difficult;
	}

	public void setDifficult(String difficult) {
		this.difficult = difficult;
	}

	public String getSimple() {
		return simple;
	}

	public void setSimple(String simple) {
		this.simple = simple;
	}

	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}
    
	public String getExamHours() {
		return examHours;
	}
	public void setExamHours(String examHours) {
		this.examHours = examHours;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    public String getIstemplate() {
		return istemplate;
	}
    public void setIstemplate(String istemplate) {
		this.istemplate = istemplate;
	}

	public String getUseTemplatId() {
		return useTemplatId;
	}

	public void setUseTemplatId(String useTemplatId) {
		this.useTemplatId = useTemplatId;
	}
	
	public Date getPublishAnswerTime() {
		return publishAnswerTime;
	}

	public void setPublishAnswerTime(Date publishAnswerTime) {
		this.publishAnswerTime = publishAnswerTime;
	}
	
	public String getTimeDifference() {
		return timeDifference;
	}

	public void setTimeDifference(String timeDifference) {
		this.timeDifference = timeDifference;
	}

	public List<String> getExamHoursList(){
		List<String> list=null;
		if(StringUtils.isNotBlank(examHours)){
	       list=Arrays.asList(examHours.split(","));
		}
		return list;
	}

	public String getEndHours() {
		return endHours;
	}

	public void setEndHours(String endHours) {
		this.endHours = endHours;
	}

	public String getAnswerHours() {
		return answerHours;
	}

	public void setAnswerHours(String answerHours) {
		this.answerHours = answerHours;
	}
}
