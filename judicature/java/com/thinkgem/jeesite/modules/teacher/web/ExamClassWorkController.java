package com.thinkgem.jeesite.modules.teacher.web;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.teacher.dto.ExamStudentDTO;
import com.thinkgem.jeesite.modules.teacher.dto.StudentQuestionDTO;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;
import com.thinkgem.jeesite.modules.teacher.service.ExamClassWorkService;
import com.thinkgem.jeesite.modules.teacher.service.ExamService;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;
import com.thinkgem.jeesite.modules.teacher.service.TestPaperService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 随堂管理
 *
 */
@Controller
@RequestMapping("${teacherPath}/classWork")
public class ExamClassWorkController extends BaseController{
	
	@Autowired
	ExamClassWorkService examClassWorkService;
	@Autowired
	private SchoolClassService schoolClassService;
	@Autowired
	ExaminationService examinationService;
	@Autowired
	private ExamService examService;
	@Autowired
	TestPaperService testPaperService;
	
	@ModelAttribute
	public Exam get(@RequestParam(required=false) String id) {
		Exam entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examClassWorkService.get(id);
		}
		if (entity == null){
			entity = new Exam();
		}
		return entity;
	}
	
	/**
	 * 试卷列表
	 */
	@RequestMapping("classWorkList")
	public String classWorkList(Exam exam,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		Page<Exam> page = examClassWorkService.publishExamLimit(new Page<Exam>(request, response), exam,userId,versionId);
		Page<Exam> unPublish=examClassWorkService.unpublishExamLimit(new Page<Exam>(request, response), exam,userId,versionId);
		model.addAttribute("publishList", page);
		model.addAttribute("unpublishList",unPublish);
		return "teacher/classWork/exam_classWork";
	}
	
	/**
	 * 未发布的随堂练习
	 */
	@RequestMapping("unpublishList")
	public String unpublishList(Exam exam,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		Page<Exam> page=examClassWorkService.unpublishExam(new Page<Exam>(request,response), exam,userId,versionId);
		model.addAttribute("examList",page);
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(exam!=null){
			Date beginTime=exam.getBeginTime();
			model.addAttribute("beginTime",beginTime);
		}
		return "teacher/classWork/exam_unpublish_list";
	}
	/**
	 * 未发布的随堂练习进行发布
	 */
	@RequestMapping("updatePublish")
	public String updatePublish(String examId,String examClassId,String limit){
		testPaperService.publishExam(examId,examClassId);
		/*if(("1").equals(limit)){
			return "redirect:"+teacherPath+"/classWork/classWorkList";	
		}else{
			return "redirect:"+teacherPath+"/classWork/unpublishList";
		}*/
		return "redirect:"+teacherPath+"/classWork/examMonitor?examId="+examId+"&examClassId="+examClassId;
	}
	/**
	 * 已发布的随堂练习
	 */
	@RequestMapping("publishList")
	public String publishList(Exam exam,String a,String type,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		//获得教师所任课程的所有试卷
		List<Exam> list=testPaperService.getAllUnSubmitExam2(userId,versionId);
		for (Exam exam2 : list) {
			boolean label=true;
			if(exam2!=null&&label){
				List<String> list2=examService.getExamType(exam2.getId());
				for (String string : list2) {
					//单选题 1 填空题 2 计算题 3 简答题 4 多选题 5 概念题 6 综合题 7 制图题 8 制表题 9 识图题 10 判断题 11
					if(("2").equals(string)||("3").equals(string)||("4").equals(string)||("6").equals(string)||("7").equals(string)||("8").equals(string)||("9").equals(string)||("10").equals(string)){
						label=false;
						break;
					}
				}
				if(label){
					if(list.size()>0){
						testPaperService.updateExamClassStatus(exam2);
					}
				}
			}
		}
		Page<Exam> page=examClassWorkService.publishExam(new Page<Exam>(request,response), exam,userId,versionId);
		model.addAttribute("examList",page);
		
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(exam!=null){
			Date beginTime=exam.getBeginTime();
			model.addAttribute("beginTime",beginTime);
		}
		if(("1").equals(type)){
			model.addAttribute("message", "该测试没有题");
		}
		if(("1").equals(a)){
			model.addAttribute("message", "该试卷已自动判卷！");
		}
		return "teacher/classWork/exam_publish_list";
	}
	/**
	 * 查看随堂练习中某个班级下的学生列表
	 */
	/*@RequestMapping("studentExamDetail")
	public String studentExamDetail(StudentQuestionDTO studentAnswer,String examId,String questionId,String examClassId,Model model,HttpServletRequest request, HttpServletResponse response){
		//获取作业名称
		Exam exam =null;
		if(examId!=null){
			exam = get(examId);
		}
		model.addAttribute("exam", exam);
		//获取班级名称
		SchoolClass school = schoolClassService.get(examClassId);
		model.addAttribute("school", school);
		//学生回答情况
		Page<StudentQuestionDTO> page=examClassWorkService.findStudentExamDetail(new Page<StudentQuestionDTO>(request,response),studentAnswer,examId,questionId,examClassId);
		model.addAttribute("page",page);
		return "teacher/classWork/exam_question_detail_student_answer";
	}*/
	/**
	 * 修改学生对错
	 */
/*	@RequestMapping("updateStudentExamDetail")
	public String updateStudentExamDetail(String studentId,String examId,String questionId,String isCorrect,String examClassId,Model model){
		examService.answerQuestionIsCorrect(studentId,questionId,examId,isCorrect);
		return "redirect:"+teacherPath+"/classWork/studentExamDetail?examId="+examId+"&questionId="+questionId+"&examClassId="+examClassId;
	}
	
	
	
	*/
	/**
	 * 修改状态确认判完试卷
	 */
	@RequestMapping("updateStudentExamStatus")
	public String updateStudentExamStatus(String examClassId,String examId,Model model){
		List<StudentTask> studentTask=examService.getExamStudentTaskState(examClassId, examId);
		if(studentTask.size()>0){
			return "redirect:/t/teacherStudentAnswer/examStudentList?examClassId="+examClassId+"&examId="+examId+"&status=exerciseExam&teacher=2"; 
		}else{
			examService.updateExamClass(examId,examClassId);
		}
		return "redirect:"+teacherPath+"/classWork/publishList";
	}
	
	/**
	 * 查看答题情况
	 * 单选题 1 填空题 2 计算题 3 简答题 4 多选题 5 概念题 6 综合题 7 制图题 8 制表题 9 识图题 10 判断题 11
	 */
	@RequestMapping("examDetail")
	public String examDetail(String examClassId,String examId,HttpServletRequest request, HttpServletResponse response, Model model){
		//获取作业名称
		Exam exam=examService.get(examId);
		//获取班级名称
		SchoolClass school = schoolClassService.get(examClassId);
		//获取班级测试的题目详情
		TeacherVersionQuestion teacherVersionQuestion=new TeacherVersionQuestion();
		Page<TeacherVersionQuestion> questionTitle=examClassWorkService.publishExamDetail(new Page<TeacherVersionQuestion>(request,response),teacherVersionQuestion,examClassId,examId);
		String questionId=null;
		if(questionTitle!=null){
			model.addAttribute("page",questionTitle);
			model.addAttribute("exam",exam);
			model.addAttribute("school",school);
		}else{
			return "redirect:/t/classWork/publishList?exam="+exam+"&type="+1;//该测试没有题所以返回列表界面
		}
		return "teacher/classWork/exam_detail";
		/*String isMark=examService.getExamClassMark(examId,examClassId);//做过标记的是叛过卷的
		if(("1").equals(isMark)){
			return "teacher/classWork/exam_detail";
		}
		return "redirect:/t/teacherStudentAnswer/examStudentList?examId="+examId+"&status=exerciseExam&examClassId="+examClassId;*/
	}
	
	/**
	 * 查看本班对本题回答的情况
	 */
	@RequestMapping("examWorkClassDetail")
	public String examWorkClassDetail(String examId,String examClassId,String questionId,Model model,HttpServletRequest request, HttpServletResponse response){
		//获取作业名称
		Exam exam=examService.get(examId);
		//获取班级名称
		SchoolClass school = schoolClassService.get(examClassId);
		model.addAttribute("exam",exam);
		model.addAttribute("school",school);
		model.addAttribute("questionId",questionId);
		//学生列表
		StudentQuestionDTO studentQuestionDTO=new StudentQuestionDTO();
		Page<StudentQuestionDTO> page=examClassWorkService.publishExamPersonDetail(new Page<StudentQuestionDTO>(request,response), studentQuestionDTO,examClassId,examId,questionId);
		model.addAttribute("page", page);
		return "teacher/classWork/examWork_class_detail";
	}
	/**
	 * 发布后的随堂监控
	 */
	@RequestMapping("examMonitor")
	public String examMonitor(String examId,String examClassId,Model model,HttpServletRequest request, HttpServletResponse response){
		//获取作业名称
		Exam exam=examService.get(examId);
		//获取班级名称
		SchoolClass school = schoolClassService.get(examClassId);
		model.addAttribute("exam",exam);
		model.addAttribute("school",school);
		
		/*//获取班级测试的题目详情
 		TeacherVersionQuestion question=new TeacherVersionQuestion();
 		TeacherVersionQuestion publishExamDetail = examClassWorkService.publishExamDetail(question,examClassId,examId);
 		String questionId=null;
 		if(publishExamDetail!=null && publishExamDetail.getId()!=null && !(("").equals(publishExamDetail.getId()))){
 			questionId=publishExamDetail.getId();
 		}*/
 		TeacherVersionQuestion question=new TeacherVersionQuestion();
 		Page<TeacherVersionQuestion> page = examinationService.selectStudentByExamId(new Page<TeacherVersionQuestion>(request,response),question,examId,examClassId);
		model.addAttribute("studentList", page);//学生详情
		return "teacher/classWork/examClassWorkMonitor";
		
	}
	/**
	 * 发布后的随堂进行提交
	 */
	@RequestMapping("submitExam")
	public String submitExam(String examId,String examClassId){
		testPaperService.submitExam(examId,examClassId);
		return "redirect:/t/classWork/unpublishList";
	}
	
	/**
	 * 查看未答题人员
	 */
	@RequestMapping("studentUnSubmitExam")
	public String studentUnSubmitExam(ExamStudentDTO examStudentDTO,String examId,String examClassId,Model model,HttpServletRequest request, HttpServletResponse response){
		//获取作业名称
		Exam exam=examService.get(examId);
		//获取班级名称
		SchoolClass school = schoolClassService.get(examClassId);
		model.addAttribute("exam",exam);
		model.addAttribute("school",school);
		//学生列表
		Page<ExamStudentDTO> page = examService.getAllStudentByClassId(new Page<ExamStudentDTO>(request,response),examStudentDTO,examId,examClassId);
		model.addAttribute("page",page);
		return "teacher/classWork/studentUnSubmitExam";
	}
	/**
	 * 删除随堂练习
	 */
	@RequestMapping("deleteExerciseExam")
	public String deleteExerciseExam(String examId,String examClassId){
		testPaperService.deleteExam(examId,examClassId);
		return "redirect:/t/classWork/unpublishList";
	}
}
