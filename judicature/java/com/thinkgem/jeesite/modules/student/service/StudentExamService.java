package com.thinkgem.jeesite.modules.student.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.student.dao.StudentExamDao;
import com.thinkgem.jeesite.modules.student.entity.SExam;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;

@Service
@Transactional(readOnly = true)
public class StudentExamService extends CrudService<StudentExamDao, SExam> {
	
	@Autowired
	private StudentExamDao dao;

	public Page<VersionQuestion> findMyQuestionLib(Page<VersionQuestion> page, VersionQuestion question) {
		question.setPage(page);
		page.setList(dao.findMyQuestionLib(question));
		return page;
	}

	public StudentAnswer getStudentAnswer(String questionId, String examId) {
		StudentAnswer studentAnswer = new StudentAnswer();
		studentAnswer.setStudentId(StudentUserUtils.getUser().getId());
		studentAnswer.setExamId(examId);
		studentAnswer.setQuestionId(questionId);
		StudentAnswer studentAnswer2 = dao.getStudentAnswer(studentAnswer);
		if(null != studentAnswer2){
			if(studentAnswer2.getQuestionType().equals("11")){
				if(studentAnswer2.getAnswer0().equals("1")){
					studentAnswer2.setAnswer0("正确");
				}
				if(studentAnswer2.getAnswer0().equals("2")){
					studentAnswer2.setAnswer0("错误");
				}
			}
			String answer0 = studentAnswer2.getAnswer0(); 
			if(StringUtils.contains(answer0, "&lt;p&gt;") && StringUtils.contains(answer0, "&lt;/p&gt;")){
				answer0 = answer0.replace("&lt;p&gt;", "");
				answer0 = answer0.replace("&lt;/p&gt;", "");
				studentAnswer2.setAnswer0(answer0);
			}
		}
		return studentAnswer2;
	}

	public List<StudentAnswer> getStudentAnswerByQuestionIdAndStudentId(String questionId) {
		StudentAnswer studentAnswer = new StudentAnswer();
		studentAnswer.setStudentId(StudentUserUtils.getUser().getId());
		studentAnswer.setQuestionId(questionId);
		return dao.getStudentAnswerByQuestionIdAndStudentId(studentAnswer);
	}

	public List<String> getJoinAndSubmitExamIdList() {
		return dao.getJoinAndSubmitExamIdList(StudentUserUtils.getUser().getId());
	}

	public List<String> getNotJoinNotOverTimeExamIdList() {
		return dao.getNotJoinNotOverTimeExamIdList(StudentUserUtils.getUser().getId());
	}

	public List<String> getNotJoinOverTimeExamIdList() {
		return dao.getNotJoinOverTimeExamIdList(StudentUserUtils.getUser().getId());
	}

	public Page<VersionQuestion> findMyWrongQuestionLib(Page<VersionQuestion> page, VersionQuestion question) {
	    Map<String, String> sqlMap = question.getSqlMap();
		sqlMap.put("studentId", StudentUserUtils.getUser().getId());
		sqlMap.put("versionId", (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId"));
		question.setPage(page);
		page.setList(dao.findMyWrongQuestionLib(question));
		return page;
	}

	public List<SExam> getStudentJoinedExam(SExam exam) {
		return dao.getStudentJoinedExam(exam);
	}

	public List<CourseVesion> getCourseVersionListByStudentId(String studentId) {
		return dao.getCourseVersionListByStudentId(studentId);
	}

	public List<VersionQuestion> getCongenericQuestions(String questionId, int i) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("questionId", questionId);
		paraMap.put("count", i);
		return dao.getCongenericQuestions(paraMap);
	}

	public String getResourceFilePath(String resourceId) {
		String filePath = dao.getResourceFilePath(resourceId);
		return filePath;
	}

	

}
