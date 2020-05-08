package com.thinkgem.jeesite.modules.student.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.student.dao.SExamDao;
import com.thinkgem.jeesite.modules.student.entity.SExam;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;

@Service
@Transactional(readOnly = true) 
public class SExamService {
	@Autowired
	private SExamDao dao;

	/**
	 * 获取学生应该参加但是还未参加的测试
	 * @param examType
	 * @return
	 */
	public List<SExam> getStudentNotExam(String examType) {
		String studentId = StudentUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("examType", examType);
		paraMap.put("studentId", studentId);
		return dao.getStudentNotExam(paraMap);
	}
	
	/**
	 * 根据测试id 获取测试详情id
	 * @param examId
	 * @return
	 */
	public List<String> getExamDetailIdByExamId(String examId) {
		return dao.getExamDetailIdByExamId(examId);
	}

	/**
	 * 根据测试id查询测试实体
	 * @param examId
	 * @return
	 */
	public SExam getSExamEntityById(String examId) {
		return dao.getSExamEntityById(examId);
	}

	public List<CourseVesion> getCourseVersionByStudentId() {
		String studentId = StudentUserUtils.getUser().getId();
		return dao.getCourseVersionByStudentId(studentId);
	}

	/**
	 * 根据学生id 版本id 测试类型  查询测试
	 * @param versionId
	 * @param string
	 * @return
	 */
	public List<SExam> getNotExamByStudentIdVersionIdExamType(String versionId, String examType) {
		String StudentId = StudentUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", StudentId);
		paraMap.put("versionId", versionId);
		paraMap.put("examType", examType);
		return dao.getNotExamByStudentIdVersionIdExamType(paraMap);
	}

	public boolean isSubmit(String studentId, String examId, String examDetailId) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("examId", examId);
		List<StudentTask> list = dao.selectStudentTask(paraMap);
		if(null != list && list.size() > 0){
			return true;
		}
		return false;
	}

	public Long getLatelyExampleQuestionsCount(String versionId) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", StudentUserUtils.getUser().getId());
		paraMap.put("versionId", versionId);
		return dao.getLatelyExampleQuestionsCount(paraMap);
	}

}
