/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.teacher.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.teacher.dao.ExamDao;
import com.thinkgem.jeesite.modules.teacher.dao.TestPaperDao;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;

/**
 * 作业Service
 * @author webcat
 * @version 2016-08-15
 */
@Service
@Transactional(readOnly = true)
public class TestPaperService extends CrudService<TestPaperDao, Exam> {
	@Autowired
	public TestPaperDao testPaperDao;
	@Autowired
	public ExamDao examDao;
	
	public Exam get(String id) {
		return super.get(id);
	}
	
	public List<Exam> query(Exam examDTO){
		return super.findList(examDTO);
	}
	
	public Page<Exam> findPage(Page<Exam> page, Exam exam) {
		exam.setPage(page);
		List<Exam> list=testPaperDao.findList(exam);
		for (Exam exam2 : list) {
			//1随堂测 2考试 3作业 4例题  5在线考试
			if(exam2.getExamType()!=null&&!(("").equals(exam2.getExamType()))){
				if(exam2.getExamType().equals("1")){
					exam2.setExamType("随堂测试");
				}else{
					if(exam2.getExamType().equals("2")){
						exam2.setExamType("组卷考试");
					}else{
						if(exam2.getExamType().equals("3")){
							exam2.setExamType("课后作业");
						}else{
							if(exam2.getExamType().equals("4")){
								exam2.setExamType("课堂例题");
							}else{
								if(exam2.getExamType().equals("5")){
									exam2.setExamType("在线考试");
								}
							}
						}
					}
				}
			}
		}
		page.setList(list);
		return page;
	}
	//获取班级列表
	public Page<Exam> getExamClass(Page<Exam> page, Exam exam){
		exam.setPage(page);
		page.setList(testPaperDao.getExamClass());
		return page;
	}
	//试卷管理的删除功能
	@Transactional(readOnly = false)
	public void deleteExam(String examId,String examClassId){
		testPaperDao.deleteExam(examId,examClassId);
	}
	//保存试卷
	@Transactional(readOnly = false)
	public void save(String examId,String examClassId){
		testPaperDao.save(examId,examClassId);
	}
	//试卷管理的发布考试
	@Transactional(readOnly = false)
	public void publishExam(String examId,String examClassId){
		testPaperDao.publishExam(examId,examClassId);
	}
	//试卷管理的结束考试(随堂、课后作业、考试)
	@Transactional(readOnly = false)
	public void submitExam(String examId,String examClassId){
		testPaperDao.submitExam(examId,examClassId);
	}
	//课堂例题的发布
	@Transactional(readOnly = false)
	public void submitExample(String examId,String examClassId){
		testPaperDao.submitExam2(examId,examClassId);
	}
	//试卷管理的保存为模板功能
	@Transactional(readOnly = false)
	public void saveExam(String examId){
		testPaperDao.saveExample(examId);
	}
	//获取教师所任课程的所有测试成绩信息
	public Page<Exam> getExamByTeacherIdAndVersionId(Page<Exam> page,Exam exam,String userId,String versionId){
		exam.setPage(page);
		Map<String,String> map=exam.getSqlMap();
	    map.put("userId", userId);
	    map.put("versionId",versionId);
		page.setList(testPaperDao.getExamTeacherIdAndVersionId(exam));
		return page;
	}
	//获取教师所任课程的所有组卷信息
		public Page<Exam> findExamList(Page<Exam> page,Exam exam,String userId,String versionId){
			exam.setPage(page);
			Map<String,String> map=exam.getSqlMap();
		    map.put("userId", userId);
		    map.put("versionId",versionId);
			page.setList(testPaperDao.findExamList(exam));
			return page;
		}
	//获取教师所任课程的所有试卷信息
		public Page<Exam> getExamOnLine(Page<Exam> page,Exam exam,String userId,String versionId){
			exam.setPage(page);
			Map<String,String> map=exam.getSqlMap();
		    map.put("userId", userId);
		    map.put("versionId",versionId);
		    List<Exam> exam2 = testPaperDao.getExamOnLine(exam);
		    for (Exam exam3 : exam2) {
		    	if(exam3!=null&&exam3.getExamClass()!=null){
		    		
					String examClassId=exam3.getExamClass().getId();
					List<String> studentIds= examDao.getStudentByClassId(examClassId);
					Integer unSubmitCount=0;
					for (String studentId : studentIds) {
						String submitOrNot=examDao.findUnSubmit(studentId,exam3.getId());
						if((" ").equals(submitOrNot)||submitOrNot==null){
							unSubmitCount++;
						}
					}
					exam3.setUnSubmitPerson(unSubmitCount);//未答题人数
					String status=examDao.getExamClassMark(exam3.getId(), examClassId);
					exam3.setIsTeacher(status);
				}
			}
			page.setList(exam2);
			return page;
		}
		//未完成
		public Page<Exam> getExamOnLine2(Page<Exam> page,Exam exam,String userId,String versionId){
			exam.setPage(page);
			Map<String,String> map=exam.getSqlMap();
		    map.put("userId", userId);
		    map.put("versionId",versionId);
		    List<Exam> exam2 = testPaperDao.getExamOnline2(exam);
			page.setList(exam2);
			return page;
		}
		//线下已完成
		public Page<Exam> getExam(Page<Exam> page,Exam exam,String userId,String versionId){
			exam.setPage(page);
			Map<String,String> map=exam.getSqlMap();
		    map.put("userId", userId);
		    map.put("versionId",versionId);
		    List<Exam> exam2 = testPaperDao.getExam(exam);
		    for (Exam exam3 : exam2) {
		    	if(exam3!=null&&exam3.getExamClass()!=null){
		    		
					String examClassId=exam3.getExamClass().getId();
					List<String> studentIds= examDao.getStudentByClassId(examClassId);
					Integer unSubmitCount=0;
					for (String studentId : studentIds) {
						String submitOrNot=examDao.findUnSubmit(studentId,exam3.getId());
						if((" ").equals(submitOrNot)||submitOrNot==null){
							unSubmitCount++;
						}
					}
					exam3.setUnSubmitPerson(unSubmitCount);//未答题人数
					String status=examDao.getExamClassMark(exam3.getId(), examClassId);
					exam3.setIsTeacher(status);
				}
			}
			page.setList(exam2);
			return page;
		}
		//线下未完成
		public Page<Exam> getExam2(Page<Exam> page,Exam exam,String userId,String versionId){
			exam.setPage(page);
			Map<String,String> map=exam.getSqlMap();
		    map.put("userId", userId);
		    map.put("versionId",versionId);
		    List<Exam> exam2 = testPaperDao.getExam3(exam);
			page.setList(exam2);
			return page;
		}
		//获取教师所任课程的所有在线考试试卷信息
		public Page<Exam> getOnLineExam(Page<Exam> page,Exam exam,String userId,String versionId){
			exam.setPage(page);
			Map<String,String> map=exam.getSqlMap();
		    map.put("userId", userId);
		    map.put("versionId",versionId);
			page.setList(testPaperDao.getOnLineExam(exam));
			return page;
		}
		public List<Exam> getExam2(Exam exam,String userId,String versionId){
			
			List<Exam> exam2 = testPaperDao.getExam2(exam,userId,versionId);
			return exam2;
		}
		
		@Transactional(readOnly = false)
		public void submitExamClassId(String examId, String examClassId) {
			testPaperDao.submitExamClassId(examId,examClassId);
		}
		
		@Transactional(readOnly = false)
		public void submitTestPaper(String examId,String examClassId) {
			testPaperDao.submitTestPaper(examId,examClassId);
		}
		/*
		 * 在线成绩列表
		 */
		public Page<Exam> getOnLineExamByTeacherIdAndVersionId(Page<Exam> page, Exam exam, String userId,
				String versionId) {
			exam.setPage(page);
			Map<String,String> map=exam.getSqlMap();
		    map.put("userId", userId);
		    map.put("versionId",versionId);
			page.setList(testPaperDao.getOnLineExamByTeacherIdAndVersionId(exam));
			return page;
		}

		//获取所有的考试列表（除了线下考试）
		public Page<Exam> getAllExam(Page<Exam> page, Exam exam, String userId, String versionId) {
			exam.setPage(page);
			Map<String, String> sqlMap = exam.getSqlMap();
			sqlMap.put("userId",userId);
			sqlMap.put("versionId",versionId);
			page.setList(testPaperDao.getAllExam(exam));
			return page;
		}
		//删除模板
		@Transactional(readOnly = false)
		public void deleteExam2(String examId) {
			testPaperDao.deleteExam2(examId);
		}
		//查询所有未进行判卷的不需手动判卷的试卷
		public List<Exam> getAllUnSubmitExam(String userId, String versionId) {
			return testPaperDao.getAllUnSubmitExam(userId,versionId);
		}
		public List<Exam> getAllUnSubmitExam2(String userId, String versionId) {
			return testPaperDao.getAllUnSubmitExam2(userId,versionId);
		}
		//修改试卷与班级的状态
		@Transactional(readOnly = false)
		public void updateExamClassStatus(Exam exam) {
			testPaperDao.updateExamClassStatus(exam);
			testPaperDao.updateExamClassStatus2(exam);
		}
}

