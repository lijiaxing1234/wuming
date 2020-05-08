package com.thinkgem.jeesite.modules.questionlib.dao;


import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestion;

/**
 * 提问DAO接口
 */
@MyBatisDao
public interface UQuestionDao extends CrudDao<UQuestion> {

	int countAllList(UQuestion uQuestion);
	
	/**
	 * 查询携带回答数量的问题列表 
	 * 
	 * @param uQuestion
	 * @return 查询携带回答数量的问题列表 
	 */
	List<UQuestion> findListWithAnswerCount(UQuestion uQuestion);
   
	
	UQuestion getQuestion(UQuestion question);

	List<UQuestion> get2LatestQuestionAndAnswers(Map<String, String> paraMap);

	int getTMsgCount(String shcoolId);

	List<Map<String, Object>> getTMsgText(String schoolId);

	List<Map<String, Object>> getTeacherChartsData(Map<String, Object> paraMap);

	Map<String, Object> getTeacherMain1(Map<String, String> paraMap);

	Map<String, Object> getTeacherMain3(Map<String, String> paraMap);

	Map<String, Object> getTeacherMain2(Map<String, String> paraMap);

	Map<String, Object> teacherCourseSchedule(Map<String, String> paraMap);

	List<Map<String, Object>> getTeacherChartsData2(Map<String, Object> paraMap);

}