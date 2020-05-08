package com.thinkgem.jeesite.modules.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.web.entity.UserRecord;
import com.thinkgem.jeesite.modules.web.entity.UserRecordAnswer;

@MyBatisDao
public interface UserAnswerMapper {

	
	/*************用户记录基础表**************/
	
	
	/**
	 * 查询用户记录基础表
	 */
	public List<UserRecord> selectUserRecord(UserRecord userRecord);
	
	/**
	 * 插入
	 * 用户记录基础表
	 */
	public int insertUserRecordSelective(UserRecord userRecord);
	
	 /**
	  * 修改
	  * 用户记录基础表
	 
	public int updateUserRecorByPrimaryKeySelective(UserRecord userRecord); */
	
	
	
	
	
	/**************用户答题记录表*********************/
	
	/**
	 * 查询用户答题记录
	 * @param userRecordAnswer
	 * @return
	 */
	public List<UserRecordAnswer> selectUserRecordAnswer(UserRecordAnswer userRecordAnswer);
	
	/**
	 * 插入
	 * 用户答题记录表
	 */
	public int insertUserRecordAnswerSelective(UserRecordAnswer userRecordAnswer);
	
	 /**
	  * 修改
	  * 用户答题记录表*/
	  
	public int updateUserRecordAnswerByPrimaryKeySelective(UserRecordAnswer userRecordAnswer);
	
	
	
	
	
	/*************统计用户答题情况*****************/
	
	/**
	 * 统计用户 已经做了多少题，做错多少题，做对多少题
	 * 
	 **/
	public List<Map<String,Object>>  statisUserAnswer(UserRecordAnswer userRecordAnswer);
	
	/**
	 * 查询课程下有多少试题
	 * @param courseId  课程Id
	 * @param  knowId  知识点Id （可为null）
	 * @return 课程下拥有多少试题
	 */
	Integer countQuestionBycourseIdKnowId(@Param("courseId")String courseId,@Param("knowId")String knowId);
	
	
}
