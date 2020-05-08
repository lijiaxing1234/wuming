/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.teacher.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.student.service.StudentAnswerService;
import com.thinkgem.jeesite.modules.teacher.dao.ExamClassDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamDao;
import com.thinkgem.jeesite.modules.teacher.dto.Exam2;
import com.thinkgem.jeesite.modules.teacher.dto.ExamScoreDTO;
import com.thinkgem.jeesite.modules.teacher.dto.ExamStudentDTO;
import com.thinkgem.jeesite.modules.teacher.dto.StudentQuestionDTO;
import com.thinkgem.jeesite.modules.teacher.dto.VersionQuestionAnswerServiceDTO;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.ExamClass;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;

/**
 * 作业Service
 */
@Service
@Transactional(readOnly = true)
public class ExamService extends CrudService<ExamDao, Exam> {
	@Autowired
	ExamDao examDao;
	@Autowired
	ExamClassDao examClassDao;
	@Autowired
	private StudentAnswerService studentAnswerService;
	
	public Exam get(String id) {
		Exam exam = super.get(id);
		if(exam!=null && (exam.getStatus()!=null) && !(("").equals(exam.getStatus()))){
			String status=exam.getStatus();
		}
		return exam;
	}
	public List<Exam> query(Exam examDTO){
		return super.findList(examDTO);
	}
	
	//根据教师id及版本id获取作业列表
	public Page<Exam> getExam(Page<Exam> page, Exam exam,String userId,String versionId) {
		exam.setPage(page);
		Map<String,String> map=exam.getSqlMap();
	    map.put("userId", userId);
	    map.put("versionId",versionId);
	    List<Exam> list = examDao.getExam(exam);
	    for (Exam exam2 : list) {
			if(exam2!=null&&exam2.getExamClass()!=null){
				String examId=exam2.getId();
				String examClassId=exam2.getExamClass().getId();
				String status=examDao.getExamClassMark(examId, examClassId);
				exam2.setIsTeacher(status);
				
				List<String> studentIds= examDao.getStudentByClassId(examClassId);
				Integer unSubmitCount=0;
				for (String studentId : studentIds) {
					String submitOrNot=examDao.findUnSubmit(studentId,exam2.getId());
					if((" ").equals(submitOrNot)||submitOrNot==null){
						unSubmitCount++;
					}
				}
				exam2.setUnSubmitPerson(unSubmitCount);//未答题人数
			}
		}
		page.setList(list);
		return page;
	}
	public Page<Exam> getExam2(Page<Exam> page, Exam exam,String userId,String versionId) {
		exam.setPage(page);
		Map<String,String> map=exam.getSqlMap();
	    map.put("userId", userId);
	    map.put("versionId",versionId);
	    List<Exam> list = examDao.getExam2(exam);
		page.setList(list);
		return page;
	}
	//根据教师id及版本id获取学生已提交的作业列表
	public Page<Exam> getStudentExam(Page<Exam> page, Exam exam,String userId,String versionId) {
		exam.setPage(page);
		Map<String,String> map=exam.getSqlMap();
	    map.put("userId", userId);
	    map.put("versionId",versionId);
		page.setList(examDao.getStudentExam(exam));
		return page;
	}
	//作业列表中班级内个别学生的完成情况
	public Page<StudentQuestionDTO> queryPersondetail(Page<StudentQuestionDTO> page,StudentQuestionDTO studentQuestionDTO,String examId,String examClassId,String studentId){
		studentQuestionDTO.setPage(page);
		//获取到班级测试题的详情
		Map<String,String> map=studentQuestionDTO.getSqlMap();
	    map.put("examId", examId);
	    map.put("examClassId",examClassId);
		List<StudentQuestionDTO> queryPersondetail = examDao.queryTitledetail(studentQuestionDTO);
		//获取某道题学生的回答情况
		for (StudentQuestionDTO studentQuestionDTO2 : queryPersondetail) {
			if((studentQuestionDTO2!=null&&!(("").equals(studentQuestionDTO2)))&&(studentQuestionDTO2.getTeacherVersionQuestion()!=null&&!(("").equals(studentQuestionDTO2.getTeacherVersionQuestion())))&&(studentQuestionDTO2.getTeacherVersionQuestion().getId()!=null&&!(("").equals(studentQuestionDTO2.getTeacherVersionQuestion().getId())))){
				String questionId=studentQuestionDTO2.getTeacherVersionQuestion().getId();
				StudentAnswer queryPersondetail2 = examDao.queryPersondetail(examId, questionId, studentId);
				String answer = null;
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer0()!=null&&!(("").equals(queryPersondetail2.getAnswer0()))){
					String a=queryPersondetail2.getAnswer0();
					String questionType=queryPersondetail2.getQuestionType();
					if(("11").equals(questionType)){
						if(("0").equals(a)){
							answer="正确";
						}else{
							answer="错误";
						}
					}else{
						if(a!=null){
							a.replace("<p>", "");
							a.replace("</p>", "");
							a.replace("&lt;p&gt;", "");
							a.replace("&lt;/p&gt;", "");
							answer=a;
						}
					}
					/*a.replace("<p>", "");
					a.replace("</p>", "");*/
				}
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer1()!=null&&!(("").equals(queryPersondetail2.getAnswer1()))){
					if(answer==null){
						answer=queryPersondetail2.getAnswer1();
					}else{
						answer=answer+","+queryPersondetail2.getAnswer1();
					}
				}
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer2()!=null&&!(("").equals(queryPersondetail2.getAnswer2()))){
					if(answer==null){
						answer=queryPersondetail2.getAnswer2();
					}else{
						answer=answer+","+queryPersondetail2.getAnswer2();
					}
				}
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer3()!=null&&!(("").equals(queryPersondetail2.getAnswer3()))){
					if(answer==null){
						answer=queryPersondetail2.getAnswer3();
					}else{
						answer=answer+","+queryPersondetail2.getAnswer3();
					}
				}
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer4()!=null&&!(("").equals(queryPersondetail2.getAnswer4()))){
					if(answer==null){
						answer=queryPersondetail2.getAnswer4();
					}else{
						answer=answer+","+queryPersondetail2.getAnswer4();
					}
				}
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer5()!=null&&!(("").equals(queryPersondetail2.getAnswer5()))){
					if(answer==null){
						answer=queryPersondetail2.getAnswer5();
					}else{
						answer=answer+","+queryPersondetail2.getAnswer5();
					}
				}
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer6()!=null&&!(("").equals(queryPersondetail2.getAnswer6()))){
					if(answer==null){
						answer=queryPersondetail2.getAnswer6();
					}else{
						answer=answer+","+queryPersondetail2.getAnswer6();
					}
				}
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer7()!=null&&!(("").equals(queryPersondetail2.getAnswer7()))){
					if(answer==null){
						answer=queryPersondetail2.getAnswer7();
					}else{
						answer=answer+","+queryPersondetail2.getAnswer7();
					}
				}
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer8()!=null&&!(("").equals(queryPersondetail2.getAnswer8()))){
					if(answer==null){
						answer=queryPersondetail2.getAnswer8();
					}else{
						answer=answer+","+queryPersondetail2.getAnswer8();
					}
				}
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer9()!=null&&!(("").equals(queryPersondetail2.getAnswer9()))){
					if(answer==null){
						answer=queryPersondetail2.getAnswer9();
					}else{
						answer=answer+","+queryPersondetail2.getAnswer9();
					}
				}
				if(answer!=null){
					queryPersondetail2.setAnswer0(answer);
				}
				studentQuestionDTO2.setStudentAnswer(queryPersondetail2);
				
				/**
				 * 获取本班错误率
					1、首先获取班级内所有学生的id
					2、获取答错该题学生的人数
					3、算出错误率并赋值
				 */
				
				Integer rightCount=getRight(examClassId,examId,questionId);
				Integer classTotalPerson=getPersonTotal(examClassId);//班级提交人数
				String classId=examClassId;
				List<Student> findAllStudentByClassId = examDao.findStudentByClassId(classId);//根据班级id获取班内所有学生
				studentQuestionDTO2.setClassPerson(findAllStudentByClassId.size());//班级总人数
				//答错该题学生的人数
				Integer errorCount=classTotalPerson-rightCount;
				if(classTotalPerson==0){
					studentQuestionDTO2.setErrorPercent("0%");
				}else{
					double errorPerson=(double)errorCount/(double)classTotalPerson;
					NumberFormat nt = NumberFormat.getPercentInstance();
					  nt.setMinimumFractionDigits(2);
					  String format = nt.format(errorPerson);
					studentQuestionDTO2.setErrorPercent(format);
				}
			}
		}
		
		page.setList(queryPersondetail);
		return page;
	}
	//获取班级内的所有学生
	public Page<Student> findExamStudent(Page<Student> page,Student student,String examId){
		student.setPage(page);
		List<Student> list = examDao.findAllStudent2(student);
		for (Student student2 : list) {
			if(student2!=null){
				String studentId=student2.getId();
				String state=examDao.getStudentState(studentId,examId);
				student2.setStatus(state);
			}
		}
		page.setList(list);
		return page;
	}
	//作业列表中班级内所有学生对某一题的完成情况
	public Page<ExamStudentDTO> queryClassPersondetail(Page<ExamStudentDTO> page,ExamStudentDTO examStudent,String examClassId,String questionId){
		examStudent.setPage(page);
		List<ExamStudentDTO> list = examDao.findExamStudent(examStudent);
		/*Integer errorCount=0;*/
		for (ExamStudentDTO e : list) {
			if(e!=null && e.getStudent()!=null && e.getStudent().getId()!=null && !(("").equals(e.getStudent().getId())&& examStudent.getExam().getId()!=null && !(("").equals(examStudent.getExam().getId())))){
				String id=examStudent.getExam().getId();
				String studentId=e.getStudent().getId();
				String detailQuestion=examDao.queryClassPersondetail(studentId,id,questionId);//获取学生对某道题的回答情况       对或错   0--错误；1--正确
				if(("").equals(detailQuestion)||detailQuestion==null){
					e.setDetailQuestion("0");
				}else{
					e.setDetailQuestion(detailQuestion);
				}
				if(examStudent!=null && examStudent.getExam()!=null ){
					Date time = examClassDao.findStudnetSubmitTime(studentId,id);		//获取提交时间
					e.setSubmitTime(time);
				}
			}
		}
		
		page.setList(list);
		return page;
	}
	//获取总人数
	public Integer getPersonTotal(String examClassId){
		/*return examDao.getPersonTotal(examClassId,examId);*/
		List<String> studentIds= examDao.getStudentByClassId(examClassId);
		Integer i=studentIds.size();
		return i;
	}
	//获取答对人数
	public Integer getRight(String examClassId,String examId,String questionId){
		Integer rightCount=0;
		List<String> studentIds=examDao.getStudentByClassId(examClassId);
		for (String id : studentIds) {
			StudentAnswer studentdetail = examDao.queryPersondetail(examId, questionId, id);//获取学生的答题情况
			if(studentdetail!=null ){
				String isCorrect = studentdetail.getIsCorrect()+"";
				if(("1").equals(isCorrect)){	//0错误 1正确
					rightCount++;
				}
			}
		}
		return rightCount;
	}
	
	/**
	 * 根据班级id以及测试id获取题目
	 */
	public StudentQuestionDTO  findQuestion(String examId,String classId){
		StudentQuestionDTO studentQuestion=examDao.findQuestion(examId,classId);
		return studentQuestion;
	}
	/**
	 * 查询该随堂练习的详情
	 * @param id
	 * @return
	 */
	public Page<TeacherVersionQuestion> selectStudentByExamId(Page<TeacherVersionQuestion> page,TeacherVersionQuestion question,String examId,String classId,String questionId){
		question.setPage(page);
		Map<String, String> sqlMap = question.getSqlMap();
		sqlMap.put("examId", examId);
		sqlMap.put("classId", classId);
		sqlMap.put("questionId", questionId);
		page.setList(examClassDao.selectStudentByExamId(question));
		return page;
	}
	/**
	 * 根据学生id以及试卷id得到exam_detail_id
	 */
	public String queryExamDetailId(String studentId,String examId){
		return examDao.queryExamDetailId(studentId,examId);
	}
	/**
	 * 获得学生回答的问题题目及回答情况
	 * 根据 examDetailId 和题型  查询所有试题 单选题 1 填空题 2 计算题 3 简答题 4 多选题 5
	 */
	public List<VersionQuestionAnswerServiceDTO> queryStudentAnswerDetail(String studentId,String examId,String examDetailId,String questionType){
		List<VersionQuestion> fQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,questionType);
		List<VersionQuestionAnswerServiceDTO> list=new ArrayList<VersionQuestionAnswerServiceDTO>();
		for (VersionQuestion versionQuestion : fQuestionList) {//将查询出来的所有问题放入问题与答案的DTO中
			VersionQuestionAnswerServiceDTO v=new VersionQuestionAnswerServiceDTO();
			v.setVersionQuestion(versionQuestion);
			list.add(v);
		}
		for (VersionQuestionAnswerServiceDTO vs : list) {
			if(vs!=null && vs.getVersionQuestion()!=null && vs.getVersionQuestion().getId()!=null &&!(("").equals(vs.getVersionQuestion().getId()))){
				String questionId=vs.getVersionQuestion().getId();
				StudentAnswer studentAnswer = examDao.queryStudentAnswerDetail(questionId,studentId,examId);
				vs.setStudentAnswer(studentAnswer);
			}
		}
		return list;
	}
	/**
	 * 修改answer表
	 */
	@Transactional(readOnly =false)
	 public void answerQuestion(String studentId,String questionId,String examId,String score,String isCorrect){
		 examDao.answerQuestion(studentId,questionId,examId,score,isCorrect);
	 }
	@Transactional(readOnly =false)
	 public void answerQuestionIsCorrect(String studentId,String questionId,String examId,String isCorrect){
		 examDao.answerQuestionIsCorrect(studentId,questionId,examId,isCorrect);
	 }
	 /**
	 * 计算学生总分
	 */
	 public String totalStudentScore(String studentId,String examId){
		return examDao.totalStudentScore(studentId,examId);
	 }
	 /**
	  * 获得学生的总分
	  */
	 public String studentTotalScore(String studentId,String examId){
		 return examDao.studentTotalScore(studentId, examId);
	 }
	 /**
	 * 修改task表
	 */
	@Transactional(readOnly =false)
	 public void updateTask(String studentId,String examDetailId,String totalStudentScore){
		 examDao.updateTask(studentId,examDetailId,totalStudentScore);
	 }
	/**
	 * 获取教师和版本下的所有班级以及对应的试卷
	 */
	 public Page<ExamClass> findAllOnlineExam(Page<ExamClass> page,ExamClass examClass,String userId,String versionId){
		 	examClass.setPage(page);
			Map<String, String> sqlMap = examClass.getSqlMap();
			sqlMap.put("userId", userId);
			sqlMap.put("versionId", versionId);
			page.setList(examDao.findAllOnlineExam(examClass));
			return page;
		 }
	 /**
	  * 获取试卷监控下的学生详情
	  * @param page
	  * @param examStudentDTO
	  * @param examId
	  * @param classId
	  * @return
	  */
	public Page<ExamStudentDTO> getClassStudent(Page<ExamStudentDTO> page,ExamStudentDTO examStudentDTO,String examId, String classId) {
		examStudentDTO.setPage(page);
		Map<String, String> sqlMap = examStudentDTO.getSqlMap();
		sqlMap.put("classId", classId);
		List<ExamStudentDTO> studentList = examDao.findAllStudent3(examStudentDTO);//只有学生的信息
		if(studentList!=null){
			for (ExamStudentDTO examStudentDTO2 : studentList) {
				if(examStudentDTO2!=null && examStudentDTO2.getStudent()!=null && examStudentDTO2.getStudent().getId()!=null && !(("").equals(examStudentDTO2.getStudent().getId()))){
					String studentId=examStudentDTO2.getStudent().getId();
					Date queryStudentSubmit = examDao.queryStudentSubmit(examId, studentId);
					examStudentDTO2.setSubmitTime(queryStudentSubmit);
				}
			}
		}
		//获取班级内的所有学生
		page.setList(studentList);
		return page;
	}
	public Integer getStudentSubmitPerson(String classId, String examId) {
		return examDao.getStudentSubmitPerson(classId,examId);
	}
	public Integer getStudentSubmitPerson2(String classId, String examId) {
		return examDao.getStudentSubmitPerson2(classId,examId);
	}
	/**
	 * 根据教师id及版本id获取试卷列表（可以根据试卷类型,班级,试卷名称进行查询）
	 */
	public Page<Exam> getExamByTeacherIdAndVersionId(Page<Exam> page, Exam exam,
			String userId, String versionId) {
		exam.setPage(page);
		Map<String, String> sqlMap = exam.getSqlMap();
		sqlMap.put("userId", userId);
		sqlMap.put("versionId", versionId);
		page.setList(examDao.getExamByTeacherIdAndVersionId(exam));
		return page;
	}
	/**
	 * 根据教师id及版本id获取试卷列表
	 */
	public List<Exam2> getExamByTeacherIdAndVersionIdList(Exam2 exam,String userId, String versionId,String studentId) {
		Map<String, String> sqlMap = exam.getSqlMap();
		sqlMap.put("userId", userId);
		sqlMap.put("versionId", versionId);
		sqlMap.put("studentId", studentId);
		return examDao.getExamByTeacherIdAndVersionId2(exam);
	}
	public Page<ExamStudentDTO> getAllStudentByClassId(Page<ExamStudentDTO> page, ExamStudentDTO examStudentDTO,String examId,String classId) {
		examStudentDTO.setPage(page);
		Map<String, String> sqlMap = examStudentDTO.getSqlMap();
		sqlMap.put("classId", classId);
		sqlMap.put("examId", examId);
		List<ExamStudentDTO> studentList = examDao.findAllStudentByClassId(examStudentDTO);//只有学生的信息
		page.setList(studentList);
		return page;
	}
	public String getExamDetailId(String examId,String examDetailType) {
		return examDao.getExamDetailId(examId,examDetailType);
	}
	public String queryStudentTask(String studentId, String examDetailId) {
		return examDao.queryStudentTask(studentId,examDetailId);
	}
	@Transactional(readOnly = false)
	public void updateExamClass(String examId, String examClassId) {
		examDao.updateExamClass(examId,examClassId);
	}
	public List<String> getExamDetailIdByExamId(String examId) {
		 return examDao.getExamDetailIdByExamId(examId);
	}
	/**
	 * 查询试题分数
	 * @param questionId
	 * @return
	 */
	@Transactional(readOnly =false)
	public String queryQuestionScore(String questionId) {
		return examDao.queryQuestionScore(questionId);
	}
	/**
	 * 教师判卷
	 */
	public List<VersionQuestion> queryStudentAnswer(String examDetailId,String quesType,String studentId,String examId){
		List<VersionQuestion> q = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId, quesType);
		for (VersionQuestion versionQuestion : q) {
			String questionId=versionQuestion.getId();
			StudentAnswer queryPersondetail = examDao.queryPersondetail(examId, questionId, studentId);
			versionQuestion.setStudentAnswer(queryPersondetail);
			/*if(queryPersondetail!=null){
				versionQuestion.setAnswer0(queryPersondetail.getAnswer0());
			}else{
				versionQuestion.setAnswer0(null);
			}*/
		}
		return q;
	}
	//判断试卷是否需要判卷
	public String getExamClassMark(String examId, String examClassId) {
		return examDao.getExamClassMark(examId,examClassId);
	}
	//判断教师是否判了该学生的试卷
	public String getStudentTaskState(String studentId, String examId) {
		return examDao.getStudentState(studentId,examId);
	}
	
	
	@Transactional(readOnly =false)
	public void batchRemove(){
		 examDao.batchRemove();
	}
	
	/**
	 * 处理学生提交的答案
	 */
	public List<VersionQuestion> updateStudentAnswer(List<VersionQuestion> list){
		 for (VersionQuestion versionQuestion : list) {
			 if(versionQuestion!=null&&versionQuestion.getStudentAnswer()!=null){
				 String answer0=versionQuestion.getStudentAnswer().getAnswer0();
				 String a=updateStudentAnswer0(answer0);
				 StudentAnswer studentAnswer=new StudentAnswer();
				 studentAnswer.setAnswer0(a);
				 versionQuestion.setStudentAnswer(studentAnswer);
			 }
		}
		 return list;
	}
	public String updateStudentAnswer0(String answer){
		if(answer!=null){
			answer=answer.replace("<p>", "");
			answer=answer.replace("</p>", "");
			answer=answer.replace("&lt;p&gt;", "");
			answer=answer.replace("&lt;/p&gt;", "");
		}
		return answer;
	}
	public String getAllExamAndAllStudentScore(String examId,String studentId,String examClassId) {
		List<ExamScoreDTO> list=examDao.getAllExamAndAllStudentScore(examId,examClassId);//全班成绩
		String score = studentTotalScore(studentId,examId);//获取学生分数
		if(score==null||("").equals(score)){
			score="0";
		}
		int j = 0;
		for (int i=0;i<list.size();i++) {
			ExamScoreDTO examScoreDTO=list.get(i);
			if(examScoreDTO!=null){
				String score2 = examScoreDTO.getScore();
				if(score!=null&&(score).equals(score2)){
					String a=i+1+"";
					String s=a+","+score;
					return s;
				}
				if(score2 == null){
					j++;
				}
			}
		}
		List<String> studentIds= examDao.getStudentByClassId(examClassId);
		String a=studentIds.size() - j + 1 +"";
		String s=a+","+score;
		return s;

	}
	/**
	 * 得到分数段内的学生人数
	 */
	public String getCountByExamIdAndExamClassIdAndScoreRange(String examId, String examClassId, String score) {
		String[] split = score.split("-");
		String min=split[0];
		String max=split[1];
		String count = examDao.getCountByExamIdAndExamClassIdAndScoreRange(examId,examClassId,min,max);
		return 	count;
	}
	public Integer getExamTotalScore(String examId) {
		return examDao.getExamTotalScore(examId);
	}
	//获得班级内所有人的成绩
	public List<StudentTask> getAllStudentScore(String examId, String classId) {
		return examDao.getAllStudentScore(examId,classId);
	}
	/*
	 * 根据学号查询学生
	 */
	public Student getStudent(String studentCode,String schoolId) {
		return examDao.getStudent(studentCode,schoolId);
	}
	public List<String> getExamType(String examId) {
		return examDao.getExamType(examId);
	}
	public Float getExamTotalScoreByExamDetailId(String examDetailId) {
		return examDao.getExamTotalScoreByExamDetailId(examDetailId);
	}
	//获得所有提交试卷的学生的列表
	public List<StudentTask> getAllSubmitStudent(StudentTask studentTask, String examId,
			String examClassId) {
		Map<String, String> sqlMap = studentTask.getSqlMap();
		sqlMap.put("examClassId", examClassId);
		sqlMap.put("examId", examId);
		List<StudentTask> studentList = examDao.getAllSubmitStudent(studentTask);//只有学生的信息
		return studentList;
	}
	/**
	 * 超时未进行发布的试卷
	 */
	public Page<Exam> getOvertimeExam(Page<Exam> page, Exam exam, String userId, String versionId) {
		exam.setPage(page);
		Map<String,String> map=exam.getSqlMap();
	    map.put("userId", userId);
	    map.put("versionId",versionId);
	    List<Exam> list = examDao.getOvertimeExam(exam);
		page.setList(list);
		return page;
	}
	public Exam getExamDetail(String examId) {
		return examDao.getExamDetail(examId);
	}
	//查询教师所在的学校id
	public String getTeacherCompanyId(String userId) {
		return examDao.getTeacherCompanyId(userId);
	}
	//获取某次考试下所有参加考试的学生
	public List<StudentTask> getExamStudentTaskState(String examClassId, String examId) {
		return examDao.getExamStudentTaskState(examClassId,examId);
	}
}

