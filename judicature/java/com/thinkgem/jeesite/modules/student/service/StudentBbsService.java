package com.thinkgem.jeesite.modules.student.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.questionlib.dao.UAnswerDao;
import com.thinkgem.jeesite.modules.questionlib.dao.UQuestionDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.UAnswer;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestion;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestionAnswer;
import com.thinkgem.jeesite.modules.student.dao.StudentBbsDao;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;

@Service
@Transactional(readOnly = true)
public class StudentBbsService {

	@Autowired
	private UQuestionDao dao;
	@Autowired
	private StudentBbsDao studentBbsDao;
	@Autowired
	UAnswerDao  answerDao;
	@Autowired
	UQuestionDao questionDao;

	/**
	 * 审核通过的问题列表
	 * @param page	分页对象
	 * @param uQuestion	问题实体
	 * @return	审核通过的问题分页列表
	 */
	public Page<UQuestion> findQuestionPage(Page<UQuestion> page, UQuestion question) {
		Student student = StudentUserUtils.getUser();
		Map<String, String> sqlMap = question.getSqlMap();
		sqlMap.put("officeId", getOfficeIdByStudentId(student.getId()));
		question.setPage(page);
		question.setDelFlag(UQuestion.DEL_FLAG_AUDIT);
		page.setList(dao.findListWithAnswerCount(question));
		return page;
	}
	
	public List<UQuestion> get2LatestQuestionAndAnswers() {
		Student student = StudentUserUtils.getUser();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("officeId", getOfficeIdByStudentId(student.getId()));
		return dao.get2LatestQuestionAndAnswers(paraMap);
	}

	private String getOfficeIdByStudentId(String studentId) {
		return studentBbsDao.getOfficeIdByStudentId(studentId);
	}
	
	@Transactional(readOnly = false)
	public int insertQuestion(UQuestion uQuestion) {
		Student student = StudentUserUtils.getUser();
		//根据学生id查询学生所在学校id
		String officeId = studentBbsDao.getSchoolIdByStudentId(student.getId());
		Map<String,String> sqlMap=uQuestion.getSqlMap();
		sqlMap.put("officeId",officeId);
		uQuestion.setStudent(student);
		uQuestion.preInsert();
		return dao.insert(uQuestion);
	}

	/**
	 * 根据问答id获取问答的最新的十条回答
	 * @param uQuestionId
	 * @return
	 */
	public List<UQuestionAnswer> getUQuestionDetail(String uQuestionId) {
		return studentBbsDao.getUQuestionDetail(uQuestionId);
	}

	public Page<UAnswer> findAnswerPage(Page<UAnswer> page, UAnswer uAnswer) {
		uAnswer.setDelFlag(UAnswer.DEL_FLAG_AUDIT);
		uAnswer.setPage(page);
		page.setList(answerDao.findList(uAnswer));
		return page;
	}

	public UQuestion getQuestionbyId(String quesId) {
		UQuestion question =new UQuestion(quesId);
		return questionDao.getQuestion(question);
	}

	@Transactional(readOnly = false)
	public int insertAnswer(UAnswer uAnswer) {
		Student student = StudentUserUtils.getUser();
		String studentId = student.getId();
		String officeId = getOfficeIdByStudentId(studentId);
		Map<String,String> sqlMap=uAnswer.getSqlMap();
		sqlMap.put("officeId",officeId);
		uAnswer.setStudent(student);
		uAnswer.preInsert();
		return studentBbsDao.insert(uAnswer);
	}

}
