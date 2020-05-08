package com.thinkgem.jeesite.modules.teacher.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.Constants;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.entity.Examknowledge;
import com.thinkgem.jeesite.modules.teacher.entity.QuestionslibSplit;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;
import com.thinkgem.jeesite.modules.teacher.service.ExamClassService;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;
import com.thinkgem.jeesite.modules.teacher.service.QuestionslibSplitService;
import com.thinkgem.jeesite.modules.teacher.service.TestPaperService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;
/**
 * 首页的随堂作业管理
 */
@Controller
@RequestMapping("${teacherPath}/exerciseExamination")
public class ExerciseExaminationController {
	/*//cookie中的教师id以及版本id
	 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
	String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");*/
	
	@Autowired
	TestPaperService testPaperService;
	@Autowired
	ExaminationService examService;
	@Autowired
	SchoolClassService schoolClassService;
	@Autowired
	ExamClassService examClassService;
	
	
	@Autowired
	QuestionslibSplitService queslibSplitService;

	//@ModelAttribute("totalQuesCount")
	public Long  inited(){
		Double percent=queslibSplitService.getPracticePercentByTeacher();
		if(percent !=null){
			queslibSplitService.updatePracticePercentByTeacher(percent+"");
		}else{
			percent=100D;
			queslibSplitService.insertPracticePercentByTeacher(percent+"");//默认100
		}
		QuestionslibSplit qls1=queslibSplitService.getQuestionslibSplitWithTeacherInfo();
		qls1.getSqlParam().put("type", Arrays.asList("0","1"));
		Long number= queslibSplitService.countQuestionslibSplitByteacher(qls1);
		return number !=null ? number:0L; //分库后 考试 总数
	}
	
	
	
	
	@ModelAttribute("exam")
	public Examination get(String id){
		Examination exam=null;
		if(StringUtils.isNotBlank(id)){
			exam=examService.getExam(new Examination(id));
		}
		if(exam==null){
			exam=new Examination();
		}
		return exam;
	}
	/**
	 * 新增试卷
	 */
	/*@RequestMapping(value="examPager")
	public String saveExamPaper(Examination exam,String newExam){
		 String examId="";
		 if(StringUtils.isNotBlank(exam.getId())){
			 examId=exam.getId();
		 }else{
			 exam.setExamType(Constants.EXAMINATION_EXAMTYPE_TEST);
			 examId=examService.addExam(exam);
		 }
	   return "redirect:/t/exerciseExamination/selectKnowledge?id="+examId;
	}*/
	/**
	 * 选择知识点
	 */
	@RequestMapping(value="selectKnowledge")
	public String selectKnowledge(@ModelAttribute("exam")Examination exam,Model model){
		model.addAttribute("totalQuesCount", inited());
		
		List<String> examknowledgesIds=examService.getknowledgeIdsByExamId(exam.getId());
		model.addAttribute("examknowledgesIds",Collections3.convertToString(examknowledgesIds,","));//选中的知识点Ids
		model.addAttribute("exam", exam);
		model.addAttribute("examknowledge", new Examknowledge());//支持form:from 标签
		return "teacher/examination/exerciseSelectKnowledge";
	}
	/**
	 * 查询选中知识点下所有题目
	 */
	@RequestMapping(value="selectQuestion")
	public String selectQuestion(TeacherVersionQuestion question,String teacherQuestionId,Model model){
		if(question==null){
			 question=new TeacherVersionQuestion();
		 }
		 if(teacherQuestionId!=null&&!(teacherQuestionId.equals(""))){
			 CourseKnowledge courseKnowledge=new CourseKnowledge();
			 if(teacherQuestionId.equals("0")){
				 teacherQuestionId="";
			 }
			 courseKnowledge.setId(teacherQuestionId);
			 question.setCourseKnowledge(courseKnowledge);
		 }
		List<TeacherVersionQuestion> examList = examService.selectQuestionById(question);
		model.addAttribute("list", examList);
		model.addAttribute("question",question);
		model.addAttribute("teacherQuestionId",teacherQuestionId);
		return "teacher/examination/exerciseExamQuestionList";
	}
	/**
	 * 保存习题
	 */
	@RequestMapping(value="saveExercise")
	public String saveExercise(@ModelAttribute("exam")Examination exam,String classDataRelation,String questionIds,HttpServletRequest request, HttpServletResponse response,Model model){
		String examId="";
		 if(StringUtils.isNotBlank(exam.getId())){
			 examId=exam.getId();
		 }else{
			 exam.setExamType(Constants.EXAMINATION_EXAMTYPE_TEST);
			 examId=examService.saveExamination(exam).getId();
		 }
		 exam.setId(examId);
		//保存习题
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		exam.setStatus("0");//0 未发布  1 正在进行  2 已完成
		exam.setCreateDate(new Date());
		User teacher=new User(userId);
		exam.setTeacher(teacher);
		CourseVesion  version=new CourseVesion(versionId);
		exam.setVersion(version);
		
		String detailId = examClassService.queryIdByExamId(examId);
		if(detailId==null){
			detailId = examService.addExamDetail(exam);
		}
		String[] questionAndKnowId = null;
		if(questionIds!=null){
			questionAndKnowId = questionIds.split(",");
		}
		String questionId = null;
		String knowId = null;
		for (String string : questionAndKnowId) {
			String[] split = string.split(":");
			if(split!=null){
				if(questionId==null){
					questionId=split[0];
				}else{
					questionId=questionId+","+split[0];
				}
				if(knowId==null){
					knowId=split[1];
				}else{
					knowId=knowId+","+split[1];
				}
			}
		}
		examClassService.insertKnowId(examId, knowId);
		examClassService.insertQuestion(questionId, detailId);
		examService.addExerciseExam(exam,classDataRelation);
		return "redirect:/t/classWork/unpublishList";
	}
	
	@RequestMapping(value="updateExercise")
	public String updateExercise(@ModelAttribute("exam")Examination exam,HttpServletRequest request, HttpServletResponse response,Model model){
		/*String examId = null;
		if(exam!=null){
			examId=exam.getId();
		}
		*/
		if(StringUtils.isNotBlank(exam.getId())){
			String questionIds=examService.getquestionidsAndKnowId(exam.getId());
			
			//父知识点IDS
			/*List<CourseKnowledge> list=examService.getSelectKnowledgeFirstParentByExamId(exam.getId());
			String knowledgeIds=Collections3.extractToString(list,"id",",");*/
			String knowledgeIds=examService.getKnowIdsByExamId(exam.getId());
			model.addAttribute("knowledgeIds", knowledgeIds);
		    //对应的问题Ids
			//String questionIds=examService.getQuestionidsByExamId(exam.getId());
			model.addAttribute("questionIds", questionIds);
			
			model.addAttribute("exam", exam);
			//model.addAttribute("classDataRelation", classDataRelation);
		}
		return "teacher/examination/exerciseSelectKnowledge";
	}
	/**
	 * 发布习题并跳转到随堂监控页面
	 */
	@RequestMapping(value="selectQuestionMonitor")
	public String selectQuestionMonitor(Examination exam,String classDataRelation,String questionIds,HttpServletRequest request, HttpServletResponse response,Model model){
		String examId="";
		 if(StringUtils.isNotBlank(exam.getId())){
			 examId=exam.getId();
		 }else{
			 exam.setExamType(Constants.EXAMINATION_EXAMTYPE_TEST);
			 examId=examService.saveExamination(exam).getId();
		 }
		 exam.setId(examId);
		//发布习题
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		exam.setStatus("1");//0 未发布  1 正在进行  2 已完成
		exam.setCreateDate(new Date());
		User teacher=new User(userId);
		exam.setTeacher(teacher);
		CourseVesion  version=new CourseVesion(versionId);
		exam.setVersion(version);
		exam.setBeginTime(new Date());
		String detailId = examClassService.queryIdByExamId(examId);
		if(detailId==null){
			detailId = examService.addExamDetail(exam);
		}
		
		String[] questionAndKnowId = null;
		if(questionIds!=null){
			questionAndKnowId = questionIds.split(",");
		}
		String questionId = null;
		String knowId = null;
		for (String string : questionAndKnowId) {
			String[] split = string.split(":");
			if(split!=null){
				if(questionId==null){
					questionId=split[0];
				}else{
					questionId=questionId+","+split[0];
				}
				if(knowId==null){
					knowId=split[1];
				}else{
					knowId=knowId+","+split[1];
				}
			}
		}
		examClassService.insertKnowId(examId, knowId);
		examClassService.insertQuestion(questionId, detailId);
		examService.addExerciseExam(exam,classDataRelation);
		String examId1 = null;
		if(exam!=null){
			 examId1=exam.getId();
		}
		return "redirect:/t/exerciseExamination/seleceClassMonitor?classIds="+classDataRelation+"&examId="+examId1;
	}
	/**
	 *随堂监控各个班级
	 *进入随堂监控页面默认获取第一个id值
	 *进行跳转
	 */
	@RequestMapping(value="seleceClassMonitor")
	public String seleceClassMonitor(String classId,String classIds,String examId,HttpServletRequest request,HttpServletResponse response,Model model){
		//获取班级id从而获取学生的监控信息
		String[] classArr = null;
		if(classIds!=null){
			classArr=classIds.split(",");
		}
	 		/*List<SchoolClass> studentList=new ArrayList<SchoolClass>();
	 		if(classArr!=null){
	 			for (String string : classArr) {
					SchoolClass schoolClass = schoolClassService.get(string);
					studentList.add(schoolClass);
				}
	 		}*/
	 		
 		
	 		//判断是否是随堂监控页面传来的参数
	 		if(("").equals(classId)||classId==null){
	 			if(classArr!=null){
	 				classId=classArr[0];
	 			}
	 		}
 		
		Examination exam=new Examination();
		if(examId!=null){
			exam=get(examId);
		}
 		SchoolClass schoolClass = schoolClassService.get(classId);
 		if((examId==null||!(("").equals(examId)))&&exam!=null&&StringUtils.isNotBlank(exam.getId())){
 			examId=exam.getId();
 		}
 		TeacherVersionQuestion question=new TeacherVersionQuestion();
		Page<TeacherVersionQuestion> page = examService.selectStudentByExamId(new Page<TeacherVersionQuestion>(request,response),question,examId,classId);
		model.addAttribute("studentList", page);//学生详情
		model.addAttribute("exam",exam);//作业名称
		model.addAttribute("schoolClass",schoolClass);//作业名称
		model.addAttribute("classIds", classIds);//班级的集合
		model.addAttribute("classId", classId);
		return "teacher/examination/exerciseExamMonitor";
	}
	/**
	 * 结束随堂练习
	 */
	@RequestMapping(value="submitExam")
	public String submitExam(String examId,String classIds,HttpServletRequest request,HttpServletResponse response){
		String[] classArr = null;
		if(classIds!=null){
			classArr=classIds.split(",");
		}
 		List<SchoolClass> studentList=new ArrayList<SchoolClass>();
 		if(classArr!=null){
 			for (String string : classArr) {
				SchoolClass schoolClass = schoolClassService.get(string);
				studentList.add(schoolClass);
			}
 		}
		if(studentList.size()>0){
			for (SchoolClass schoolClass : studentList) {
				if(schoolClass!=null&&schoolClass.getId()!=null){
					String schoolClassId=schoolClass.getId();
					testPaperService.submitExam(examId,schoolClassId);
				}
			}
		}
		return "redirect:/t/classWork/publishList";
	}
}
