/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.teacher.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.VersionQuestionService;
import com.thinkgem.jeesite.modules.teacher.dao.ExamClassDao;
import com.thinkgem.jeesite.modules.teacher.dto.ExamStudentDTO;
import com.thinkgem.jeesite.modules.teacher.entity.ExamClass;

/**
 * 作业Service
 */
@Service
@Transactional(readOnly = true)
public class ExamClassService extends CrudService<ExamClassDao, ExamStudentDTO>{
	@Autowired
	ExamClassDao examClassDao;
	@Autowired
	VersionQuestionService versionQuestionService;
	
	public ExamStudentDTO get(String id) {
		return super.get(id);
	}
	
	public List<ExamStudentDTO> query(ExamStudentDTO examDTO){
		return super.findList(examDTO);
	}
	/**
	 * 试卷适用的班级内学生的列表
	 */
	public Page<ExamStudentDTO> findExamStudent(Page<ExamStudentDTO> page,ExamStudentDTO examStudent){
		examStudent.setPage(page);
		List<ExamStudentDTO> list = examClassDao.findExamStudent(examStudent);
		String id=examStudent.getExam().getId();
		//填充完整字段
		for (ExamStudentDTO examStudentDTO : list) {
			if(id!=null && !(("").equals(id)) && examStudentDTO!=null && examStudentDTO.getStudent()!=null && examStudentDTO.getStudent().getId()!=null && !(("").equals(examStudentDTO.getStudent().getId()))){
				//查询某个学生对应某个测试的提交时间
				String studentId=examStudentDTO.getStudent().getId();
				Date time = examClassDao.findStudnetSubmitTime(studentId,id);
				//查询某个学生在某个测试时需回答的总题数
				Integer right = examClassDao.findStudentRight(studentId,id);
				//查询某个学生对应某个测试各个题目的正确数
				Integer totalTitle = examClassDao.findStudentTotalTitle(studentId,id);
				examStudentDTO.setSubmitTime(time);			//学生提交时间
				if(time!=null && !(("").equals(time))){
					examStudentDTO.setTotalTitle(right);	//题目总数
					examStudentDTO.setTotalRight(totalTitle);		//学生答对数
					examStudentDTO.setTotalError(right-totalTitle);//答错数
					double i=0.00;
					if(right==0){
						 i=0;
					}else{
						i=(double)totalTitle/(double)right;
					}
					 NumberFormat nt = NumberFormat.getPercentInstance();
					  nt.setMinimumFractionDigits(2);
					  String format = nt.format(i);
					   //最后格式化并输出
					examStudentDTO.setRightPercent(format);//正确率
				}
			}
		}
		
		page.setList(list);
		return page;
	}
	@Transactional(readOnly = false)
	public void insertQuestion(String questionId,String examDetailId){
		String[] questionIds=questionId.split(",");
		 //1先删除知识点
		examClassDao.deleteExamClass(examDetailId);
		 //2后添加知识点
	
		int i=0;
		for (String qId : questionIds) {
			VersionQuestion versionQuestion = versionQuestionService.get(qId);
			String questionType=null;
			if(versionQuestion!=null){
				questionType=versionQuestion.getQuesType();
			}
			 examClassDao.insertQuestion(qId,examDetailId,i,questionType);
			 i++;
		}
		 /*Parameter cParam=new Parameter(new Object[][]{
				 {"examDetailId",examDetailId},
				 {"questionIds",questionIds}
		 });
		 if(questionIds.length>0){
			 //1先删除知识点
			 //2后添加知识点
			 examClassDao.insertQuestion(cParam);
		 }*/
		
	}
	@Transactional(readOnly = false)
	public void insertKnowId(String examId,String knowId){
		 String[] knowIds=knowId.split(",");
		 
		Set<String> set = Sets.newHashSet();
		for (int i = 0; i < knowIds.length; i++) {
			set.add(knowIds[i]);
		} 
	    String[] arrayResult = (String[]) set.toArray(new String[set.size()]);  
		 
		 Parameter cParam=new Parameter(new Object[][]{
				 {"examId",examId},
				 {"knowIds",arrayResult}
		 });
		 if(knowIds.length>0){
			 //1先删除知识点
			 //2后添加知识点
			 examClassDao.insertKnowId(cParam);
		 }
	}
	public String queryIdByExamId(String examId){
		return examClassDao.queryIdByExamId(examId);
	}
}

