package com.thinkgem.jeesite.modules.teacher.service;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.teacher.dao.ExamClassDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamClassWorkDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamDao;
import com.thinkgem.jeesite.modules.teacher.dto.StudentQuestionDTO;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;

/**
 * 作业Service
 */
@Service
@Transactional(readOnly = true)
public class ExamClassWorkService extends CrudService<ExamClassWorkDao, Exam> {
	@Autowired
	ExamClassWorkDao examClassWorkDao;
	@Autowired
	ExamClassDao examClassDao;
	@Autowired
	ExamDao examDao;
	@Autowired 
	ExamService examService;
	
	
	public Exam get(String id) {
		return super.get(id);
	}
	
	public List<Exam> query(Exam examDTO){
		return super.findList(examDTO);
	}
	//预答题人数
	public Integer totalPerson(String examClassId){
		List<String> studentIds= examDao.getStudentByClassId(examClassId);
		return studentIds.size();
	}
	//未交作业人数
	public Integer unSubmitPerson(String examId,String examClassId){
		List<String> studentIds= examDao.getStudentByClassId(examClassId);
		Integer unSubmitCount=0;
		for (String studentId : studentIds) {
			String submitOrNot=examDao.findUnSubmit(studentId,examId);
			if((" ").equals(submitOrNot)||submitOrNot==null){
				unSubmitCount++;
			}
		}
		return unSubmitCount;
	}
	//以下为点击更多的显示
	public Page<Exam> publishExam(Page<Exam> page, Exam examDTO,String userId,String versionId){
		examDTO.setPage(page);
		Map<String,String> map=examDTO.getSqlMap();
	    map.put("userId", userId);
	    map.put("versionId",versionId);
	    List<Exam> publishExam = examClassWorkDao.publishExam(examDTO);
	    for (Exam exam : publishExam) {
			String examClassId=exam.getExamClass().getId();
			List<String> studentIds= examDao.getStudentByClassId(examClassId);
			exam.setTotalPerson(studentIds.size());//预答题人数
			Integer unSubmitCount=0;
			for (String studentId : studentIds) {
				String submitOrNot=examDao.findUnSubmit(studentId,exam.getId());
				if((" ").equals(submitOrNot)||submitOrNot==null){
					unSubmitCount++;
				}
			}
			exam.setUnSubmitPerson(unSubmitCount);//未答题人数
			if(exam!=null&&exam.getExamClass()!=null){
				String examId=exam.getId();
				String status=examDao.getExamClassMark(examId, examClassId);
				exam.setIsTeacher(status);
			}
		}
		page.setList(publishExam);
		return page;
	}
	public Page<Exam> unpublishExam(Page<Exam> page, Exam examDTO,String userId,String versionId){
		examDTO.setPage(page);
		Map<String,String> map=examDTO.getSqlMap();
	    map.put("userId", userId);
	    map.put("versionId",versionId);
		page.setList(examClassWorkDao.unpublishExam(examDTO));
		return page;
	}
	
	//以下为首页列表的显示
	public Page<Exam> publishExamLimit(Page<Exam> page, Exam examDTO,String userId,String versionId){
		examDTO.setPage(page);
		Map<String,String> map=examDTO.getSqlMap();
	    map.put("userId", userId);
	    map.put("versionId",versionId);
		page.setPageSize(5);
		List<Exam> list = examClassWorkDao.publishExamLimit(examDTO);
		for (Exam exam : list) {
			String examClassId=exam.getExamClass().getId();
			List<String> studentIds= examDao.getStudentByClassId(examClassId);
			exam.setTotalPerson(studentIds.size());//预答题人数
			Integer unSubmitCount=0;
			for (String studentId : studentIds) {
				String submitOrNot=examDao.findUnSubmit(studentId,exam.getId());
				if((" ").equals(submitOrNot)||submitOrNot==null){
					unSubmitCount++;
				}
			}
			exam.setUnSubmitPerson(unSubmitCount);//未答题人数
		}
		page.setList(list);
		return page;
	}
	public Page<Exam> unpublishExamLimit(Page<Exam> page, Exam examDTO,String userId,String versionId){
		examDTO.setPage(page);
		Map<String,String> map=examDTO.getSqlMap();
	    map.put("userId", userId);
	    map.put("versionId",versionId);
		page.setPageSize(5);
		page.setList(examClassWorkDao.unpublishExamLimit(examDTO));
		return page;
	}
	
	
	//获取随堂练习的某个班级的完成情况
	public Page<TeacherVersionQuestion> publishExamDetail(Page<TeacherVersionQuestion> page,TeacherVersionQuestion teacherVersionQuestion,String examClassId,String examId){
		teacherVersionQuestion.setPage(page);
		Map<String,String> map=teacherVersionQuestion.getSqlMap();
	    map.put("examClassId", examClassId);
	    map.put("examId",examId);
	    //获取随堂练习的题干
	    List<TeacherVersionQuestion> questionIds=examClassWorkDao.publishExamDetail(teacherVersionQuestion);
	   for (TeacherVersionQuestion list : questionIds) {
		   String answer = null;
			if(list!=null && list.getAnswer()!=null&&!(("").equals(list.getAnswer()))){
				String a=list.getAnswer();
				String questionType=list.getQuesType();
				if(("11").equals(questionType)){
					if(("1").equals(a)){
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
			}
			if(list!=null && list.getAnswer1()!=null&&!(("").equals(list.getAnswer1()))){
				if(answer==null){
					answer=list.getAnswer1();
				}else{
					answer=answer+","+list.getAnswer1();
				}
			}
			if(list!=null && list.getAnswer2()!=null&&!(("").equals(list.getAnswer2()))){
				if(answer==null){
					answer=list.getAnswer2();
				}else{
					answer=answer+","+list.getAnswer2();
				}
			}
			if(list!=null && list.getAnswer3()!=null&&!(("").equals(list.getAnswer3()))){
				if(answer==null){
					answer=list.getAnswer3();
				}else{
					answer=answer+","+list.getAnswer3();
				}
			}
			if(list!=null && list.getAnswer4()!=null&&!(("").equals(list.getAnswer4()))){
				if(answer==null){
					answer=list.getAnswer4();
				}else{
					answer=answer+","+list.getAnswer4();
				}
			}
			if(list!=null && list.getAnswer5()!=null&&!(("").equals(list.getAnswer5()))){
				if(answer==null){
					answer=list.getAnswer5();
				}else{
					answer=answer+","+list.getAnswer5();
				}
			}
			if(list!=null && list.getAnswer6()!=null&&!(("").equals(list.getAnswer6()))){
				if(answer==null){
					answer=list.getAnswer6();
				}else{
					answer=answer+","+list.getAnswer6();
				}
			}
			if(list!=null && list.getAnswer7()!=null&&!(("").equals(list.getAnswer7()))){
				if(answer==null){
					answer=list.getAnswer7();
				}else{
					answer=answer+","+list.getAnswer7();
				}
			}
			if(list!=null && list.getAnswer8()!=null&&!(("").equals(list.getAnswer8()))){
				if(answer==null){
					answer=list.getAnswer8();
				}else{
					answer=answer+","+list.getAnswer8();
				}
			}
			if(list!=null && list.getAnswer9()!=null&&!(("").equals(list.getAnswer9()))){
				if(answer==null){
					answer=list.getAnswer9();
				}else{
					answer=answer+","+list.getAnswer9();
				}
			}
			list.setAnswer(answer);
		    //根据题干和班级以及班级内的学生 获取正确率
		    if(list!=null && list.getId()!=null && list.getId()!=""){
		    	String questionId=list.getId();
		    	 Integer personTotal = examService.getPersonTotal(examClassId);//首先获取班级内的所有学生
		    	Integer right = examService.getRight(examClassId,examId,questionId);//答对人数
		    	if(personTotal!=0){
		    		double rightPercent=(double)right/(double)personTotal;
		    		NumberFormat nt = NumberFormat.getPercentInstance();
					nt.setMinimumFractionDigits(2);
					String format = nt.format(rightPercent);
			    	list.setRightPercent(format);
		    	}else{
		    		list.setRightPercent("0.00%");
		    	}
		    	
		    }
	}
	   page.setList(questionIds);
		return page;
	}
	//获取随堂练习中的出题详情
	/*public TeacherVersionQuestion getVersionQuestion(TeacherVersionQuestion teacherVersionQuestion,String examClassId,String examId){
		Map<String,String> map=teacherVersionQuestion.getSqlMap();
	    map.put("examClassId", examClassId);
	    map.put("examId",examId);
	    //获取随堂练习的题干
	   return examClassWorkDao.publishExamDetail(teacherVersionQuestion);
	}*/
	//随堂练习的个人完成情况
	public Page<StudentQuestionDTO> publishExamPersonDetail(Page<StudentQuestionDTO> page, StudentQuestionDTO examDTO,String examClassId,String examId,String questionId){
		examDTO.setPage(page);
		Map<String,String> map=examDTO.getSqlMap();
	    map.put("examClassId", examClassId);
	    map.put("examId",examId);
	    map.put("questionId",questionId);
	    List<StudentQuestionDTO> list = examClassWorkDao.publishExamPersonDetail(examDTO);//学生的信息
	    //获取所有学生的id
	    for (int i=0;i<list.size();i++) {
	    	StudentQuestionDTO studentQuestionDTO =list.get(i);
			if(studentQuestionDTO!=null && studentQuestionDTO.getStudent()!=null && studentQuestionDTO.getStudent().getId()!=null && !(("").equals(studentQuestionDTO.getStudent().getId()))){
				String studentId=studentQuestionDTO.getStudent().getId();
					Date time = examClassDao.findStudnetSubmitTime(studentId,examId);		//获取提交时间
					studentQuestionDTO.setSubmitTime(time);
					//获取回答情况
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
					StudentAnswer studentAnswer=new StudentAnswer();
					studentAnswer.setAnswer0(answer);
					studentQuestionDTO.setStudentAnswer(studentAnswer);
				}
			}
		
		page.setList(list);
		return page;
	}
	/**
	 * 罗列学生随堂管理中的答题详情
	 * @param page
	 * @param studentAnswer
	 * @param examId
	 * @param questionId
	 * @param examClassId
	 * @return
	 */
	/*public Page<StudentQuestionDTO> findStudentExamDetail(Page<StudentQuestionDTO> page, StudentQuestionDTO examDTO, String examId,
			String questionId, String examClassId) {
		examDTO.setPage(page);
		Map<String,String> map=examDTO.getSqlMap();
	    map.put("examClassId", examClassId);
	    map.put("examId",examId);
	    map.put("questionId",questionId);
	    List<StudentQuestionDTO> list = examClassWorkDao.publishExamPersonDetail(examDTO);//学生的信息
	    for (StudentQuestionDTO studentQuestionDTO : list) {
	    	if(studentQuestionDTO!=null&&studentQuestionDTO.getStudent()!=null){
	    		String studentId=studentQuestionDTO.getStudent().getId();
	    		Date time = examClassDao.findStudnetSubmitTime(studentId,examId);		//获取提交时间
				studentQuestionDTO.setSubmitTime(time);
				StudentAnswer queryPersondetail2 = examDao.queryPersondetail(examId, questionId, studentId);
				String answer = null;
				if(queryPersondetail2!=null && queryPersondetail2.getAnswer0()!=null&&!(("").equals(queryPersondetail2.getAnswer0()))){
					String a=queryPersondetail2.getAnswer0();
					if(a!=null){
						a.replace("<p>", "");
						a.replace("</p>", "");
						a.replace("&lt;p&gt;", "");
						a.replace("&lt;/p&gt;", "");
						answer=a;
					}
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
	    		StudentAnswer studentAnswer=new StudentAnswer();
	    		String isRight=examClassWorkDao.getErrorStudent(studentId,examId,questionId);//判断学生是否回答正确 0错误1正确
	    		Integer isCorrect=2;
	    		if(isRight!=null){
	    			isCorrect=Integer.parseInt(isRight);
	    		}
	    		studentAnswer.setIsCorrect(isCorrect);
				studentAnswer.setAnswer0(answer);
				studentQuestionDTO.setStudentAnswer(studentAnswer);
	    	}
		}
		page.setList(list);
		return page;
	}*/
}

