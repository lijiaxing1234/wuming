package com.thinkgem.jeesite.modules.teacher.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.student.dao.StudentAnswerDao;
import com.thinkgem.jeesite.modules.student.entity.StudentExamDetail;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.teacher.dao.ExamDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamScoreDao;
import com.thinkgem.jeesite.modules.teacher.dto.StudentQuestionDTO;
import com.thinkgem.jeesite.modules.teacher.dto.StudentTaskDTO;

@Service
@Transactional(readOnly = true)
public class ExamScoreService {
	@Autowired
	ExamScoreDao examScoreDao;
	@Autowired
	private ExamService examService;
	@Autowired
	private ExamDao examDao;
	@Autowired
	private StudentAnswerDao dao;
	
	
	public Page<StudentTaskDTO> queryOnLineScore(Page<StudentTaskDTO> page,StudentTaskDTO studentTaskDTO,String examId,String examClassId,String userId,String versionId){
		studentTaskDTO.setPage(page);
		Map<String,String> map=studentTaskDTO.getSqlMap();
	    map.put("examId", examId);
	    map.put("examClassId",examClassId);
		List<StudentTaskDTO> queryOnLineScore = examScoreDao.queryOnLineScore(studentTaskDTO);
		for (StudentTaskDTO studentTask: queryOnLineScore) {
			if(studentTask!=null){
				String id=studentTask.getStudentId();//学生id
				//学生未提交作业次数
				List<String> queryExamDetail = examScoreDao.queryExamDetail(userId, versionId,examClassId);//获取教师所教版本下的所有作业的测试详情的id
				String queryUnsubmitCount=null;
				Integer i=0;
				if(queryExamDetail!=null){
					for (String examDetailId : queryExamDetail) {
						queryUnsubmitCount = examScoreDao.queryUnsubmitCount(id,examDetailId);//根据试卷详情id以及学生id可以查询出唯一一条数据     如果没有或为0则学生未提交作业
						if(queryUnsubmitCount==null||("0").equals(queryUnsubmitCount)){
							i++;
						}
					}
				}
				
				studentTask.setUnSubmitCount(i);
				//学生未提交随堂测次数
				List<String> queryUnClassCount = examScoreDao.queryUnClassCount(userId, versionId,examClassId);//获取教师所教版本下的所有随堂测的测试详情的id
				String queryUnClassCount2=null;
				Integer j=0;
				if(queryUnClassCount!=null){
					for (String examDetailId : queryUnClassCount) {
						queryUnClassCount2 = examScoreDao.queryUnsubmitCount(id,examDetailId);//根据试卷详情id以及学生id可以查询出唯一一条数据     如果没有或为0则学生未提交作业
						if(queryUnClassCount2==null||("0").equals(queryUnClassCount2)){
							j++;
						}
					}
				}
				studentTask.setUnClassWork(j);
				//学生总成绩
				String examDetailId = examService.queryExamDetailId(id,examId);//获取测试详情的id
				String totalStudentScore = examService.studentTotalScore(id,examId);//获得总成绩
				if(examDetailId==null ||("").equals(examDetailId)){
					studentTask.setScore(0.0F);//未参加考试
				}else{
					int s = 0;
					if(totalStudentScore!=null){
						s=Integer.parseInt(totalStudentScore);
					}
					float f=(float)s;
					//String totalStudentScore =examScoreDao.findStudentScore(id,examDetailId);//查询总成绩
					if(("").equals(totalStudentScore)||totalStudentScore==null){
						studentTask.setScore(0.00F);
					}else{
						studentTask.setScore(f);
					}
					//学生线上成绩
					String sumOnlineScore2 = examScoreDao.sumOnlineScore(id, examId);
					if(sumOnlineScore2==null){
						studentTask.setOnLineScore("0");
					}else{
						studentTask.setOnLineScore(sumOnlineScore2);
						if(("0").equals(totalStudentScore)||totalStudentScore==null){
							int a = 0;
							if(sumOnlineScore2!=null){
								a=Integer.parseInt(sumOnlineScore2);
							}
							float b=(float)a;
							studentTask.setScore(b);
						}
					}
				}
			}
		}
		page.setList(queryOnLineScore);
		return page;
	}
	/**
	 * 保存学生的线下总成绩
	 */
	@Transactional(readOnly = false)
	public void saveScore(float score, String studentId,String examDetailId) {
		StudentTask studentTask=new StudentTask(IdGen.uuid());
		StudentExamDetail studentExamDetail=new StudentExamDetail();
		studentExamDetail.setId(examDetailId);
		
		studentTask.setExamDetail(studentExamDetail);
		studentTask.setSubmittime(new Date());
		studentTask.setIsSubmit("1");
		studentTask.setScore(score);
		Student student = new Student();
		student.setId(studentId);
		studentTask.setStudent(student);
		dao.addStudentTask(studentTask);
	}
	/**
	 * 获取班内所有学生
	 */
	public Page<StudentTaskDTO> getAllClassStudent(Page<StudentTaskDTO> page,StudentTaskDTO studentTaskDTO,String examClassId,String examId){
		studentTaskDTO.setPage(page);
		studentTaskDTO.setExamid(examId);
		studentTaskDTO.setExamClassId(examClassId);
		List<StudentTaskDTO> findAllStudent = examDao.findAllStudent(studentTaskDTO);
		page.setList(findAllStudent);
		return page;
	}
	/**
	 * 获取班内所有学生以及学生的成绩
	 */
	public Page<StudentTaskDTO> getAllStudent(Page<StudentTaskDTO> page,StudentTaskDTO studentTaskDTO,String examClassId,String examId){
		studentTaskDTO.setPage(page);
		studentTaskDTO.setExamid(examId);
		studentTaskDTO.setExamClassId(examClassId);
		List<StudentTaskDTO> findAllStudent = examDao.findAllStudent(studentTaskDTO);
		for (StudentTaskDTO studentTask2 : findAllStudent) {
			if(studentTask2!=null){
				String studentId = studentTask2.getStudentId();
				Float studentScore=examScoreDao.findStudentScore2(studentId,examId);//总成绩
				String sumOnlineScore2 = examScoreDao.sumOnlineScore(studentId, examId);//学生线上考试成绩
				if(studentScore==null){
					studentScore=0.0F;
				}
				if(studentScore==0.0&&sumOnlineScore2!=null){
						int a = 0;
						if(sumOnlineScore2!=null){
							a=Integer.parseInt(sumOnlineScore2);
						}
						float b=(float)a;
						studentScore=b;
					}
				studentTask2.setScore(studentScore);
			}
		}
		page.setList(findAllStudent);
		return page;
	}
}

