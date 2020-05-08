package com.thinkgem.jeesite.modules.teacher;

/**
 * 静态常量
 */
public class Constants {

	/**
	 **************************
	 *       试卷实体常量
	 ***************************
	 */
	
	/**
	 * 试卷类型 ~随堂练习（需要学生作答，每次只有一道题）
	 */
	public static  final String  EXAMINATION_EXAMTYPE_TEST="1"; 
	/**
	 * 试卷类型 ~组卷考试	线下考试 (纸质试卷)
	 */
	public static  final String  EXAMINATION_EXAMTYPE_EXAM="2";
	/**
	 * 试卷类型 ~在线考试
	 */
	public static  final String  EXAMINATION_EXAMTYPE_ONLINEEXAM="5";
	/**
	 * 试卷类型 ~课后作业	相当于在线考试
	 */
	public static  final String  EXAMINATION_EXAMTYPE_OPERATION="3"; 
	/**
	 * 试卷类型 ~课堂例题	（不用学生作答，直接有讲解，每次只有一道题）
	 */
	public static  final String  EXAMINATION_EXAMTYPE_EXAMPLE="4"; 
	
	/**
	 * 试卷类型 ~是AB卷
	 */
	public static final String  EXAMINATION_ISAB_YES="1";
	/**
	 * 试卷类型 ~不是AB卷
	 */
	public static final String  EXAMINATION_ISAB_NO="0";
	/**
	 * 试卷类型 ~手动组卷
	 */
	public static final String  EXAMINATION_EXAMMODE_YES="1";
	/**
	 * 试卷类型 ~自动组卷
	 */
	public static final String  EXAMINATION_EXAMMODE_NO="0";
	
	
	/************
	 * 单选题、多选题、简答题
	 * 同类题型分值是相同的
	 * 
	 * ****************/
	/**
	 * 单选题、
	 * 
	 */
	public static final  double  QUESTION_RADIO_SIMPLE_SCORE = 1d;
	public static final  double  QUESTION_RADIO_GENERAL_SCORE = 1d;
	public static final  double  QUESTION_RADIO_DIFFICULT_SCORE = 1d;
	/**
	 * 多选题
	 */
	public static final  double  QUESTION_MULTIPLE_SIMPLE_SCORE = 2f;
	public static final  double  QUESTION_MULTIPLE_GENERAL_SCORE = 2f;
	public static final  double  QUESTION_MULTIPLE_DIFFICULT_SCORE = 2f;
	/**
	 * 简答题
	 */
	public static final  double  QUESTION_SHORT_ANSWER_SIMPLE_SCORE = 5f;
	public static final  double  QUESTION_SHORT_ANSWER_GENERAL_SCORE = 5f;
	public static final  double  QUESTION_SHORT_ANSWER_DIFFICULT_SCORE = 5f;
	
/*	
	public static final  double  question_blank_score = 2f;
	public static final  double  question_compute_score = 2f;*/
	
	
}
