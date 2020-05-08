package com.thinkgem.jeesite.modules.teacher.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.UAnswerDao;
import com.thinkgem.jeesite.modules.questionlib.dao.UQuestionDao;
import com.thinkgem.jeesite.modules.questionlib.entity.UAnswer;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestion;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

@Service
public class BBSService {

	@Autowired
	UQuestionDao questionDao;
	@Autowired
	UAnswerDao  answerDao;
	
	/**
	 * 审核通过的问题列表
	 * @param page  分页对象
	 * @param question 问题实题
	 * @return 审核通过的问题分页列表
	 */
	public Page<UQuestion>  findQuestionPage(Page<UQuestion> page,UQuestion question){
		
		User user=TearcherUserUtils.getUser();
		Map<String,String> sqlMap=question.getSqlMap();
		sqlMap.put("officeId",user.getCompany().getId());
		question.setPage(page);
		question.setDelFlag(UQuestion.DEL_FLAG_AUDIT);
		page.setList(questionDao.findListWithAnswerCount(question));
		return page;
	}
	
	public UQuestion getQuestionbyId(String quesId){
		/*User user=TearcherUserUtils.getUser();*/
		UQuestion question =new UQuestion(quesId);
/*		Map<String,String> sqlMap=question.getSqlMap();
		sqlMap.put("officeId",user.getCompany().getId());*/
	    return questionDao.getQuestion(question);	
	}
	
	
	
	/**
	 * 回答列表
	 * @param page
	 * @param uAnswer
	 * @return 审核通过的回答分页列表
	 */
	public Page<UAnswer>  findAnswerPage(Page<UAnswer> page,UAnswer uAnswer){
		User user=TearcherUserUtils.getUser();
		Map<String,String> sqlMap=uAnswer.getSqlMap();
		sqlMap.put("officeId",user.getCompany().getId());
		uAnswer.setDelFlag(UAnswer.DEL_FLAG_AUDIT);
		uAnswer.setPage(page);
		page.setList(answerDao.findList(uAnswer));
		return page;
	}
	
	/**
	 * 创建问题
	 * @return 受影响行数
	 */
	public int  insertQuestion(UQuestion uQuestion){
		User user=TearcherUserUtils.getUser();
		Map<String,String> sqlMap=uQuestion.getSqlMap();
		sqlMap.put("officeId",user.getCompany().getId());
		uQuestion.setTeacher(user);
		uQuestion.preInsert();
		return questionDao.insert(uQuestion);
	}
	
	/**
	 * 创建问题
	 * @return 受影响行数
	 */
	public int  insertAnswer(UAnswer uAnswer){
		User user=TearcherUserUtils.getUser();
		Map<String,String> sqlMap=uAnswer.getSqlMap();
		sqlMap.put("officeId",user.getCompany().getId());
		uAnswer.setTeacher(user);
		uAnswer.preInsert();
		return answerDao.insert(uAnswer);
	}

	public int getTMsgCount() {
		if(TearcherUserUtils.getUser() == null){
			return 0;
		}
		String shcoolId = TearcherUserUtils.getUser().getCompany().getId();
		int i = questionDao.getTMsgCount(shcoolId);
		return i;
	}

	public List<Map<String, Object>> getTMsgText() {
		String schoolId = TearcherUserUtils.getUser().getCompany().getId();
		return questionDao.getTMsgText(schoolId);
	}

	public List<Map<String, Object>> getTeacherChartsData(String duration) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String teacherId = TearcherUserUtils.getUser().getId();
		paraMap.put("teacherId", teacherId);
		if(StringUtils.isBlank(duration)){
			paraMap.put("duration", null);
		}else{
			paraMap.put("duration", Integer.parseInt(duration));
		}
		return questionDao.getTeacherChartsData(paraMap);
	}
	
	public List<Map<String, Object>> getTeacherChartsData(String duration, String examType) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String teacherId = TearcherUserUtils.getUser().getId();
		paraMap.put("teacherId", teacherId);
		if(StringUtils.isBlank(duration)){
			paraMap.put("duration", null);
		}else{
			paraMap.put("duration", Integer.parseInt(duration));
		}
		paraMap.put("examType", examType);
		return questionDao.getTeacherChartsData2(paraMap);
	}
	

	public Map<String, Object> getTeacherMain1() {
		Map<String, String> paraMap = new HashMap<String, String>();
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		String teacherId = TearcherUserUtils.getUser().getId();
		paraMap.put("teacherId", teacherId);
		paraMap.put("versionId", versionId);
		return questionDao.getTeacherMain1(paraMap);
	}

	public Map<String, Object> getTeacherMain3() {
		Map<String, String> paraMap = new HashMap<String, String>();
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		String teacherId = TearcherUserUtils.getUser().getId();
		paraMap.put("teacherId", teacherId);
		paraMap.put("versionId", versionId);
		return questionDao.getTeacherMain3(paraMap);
	}

	public Map<String, Object> getTeacherMain2() {
		Map<String, String> paraMap = new HashMap<String, String>();
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		String teacherId = TearcherUserUtils.getUser().getId();
		paraMap.put("teacherId", teacherId);
		paraMap.put("versionId", versionId);
		return questionDao.getTeacherMain2(paraMap);
	}

	public Map<String, Object> teacherCourseSchedule(String versionId) {
		String teacherId = TearcherUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("teacherId", teacherId);
		paraMap.put("versionId", versionId);
		return questionDao.teacherCourseSchedule(paraMap);
	}

}
