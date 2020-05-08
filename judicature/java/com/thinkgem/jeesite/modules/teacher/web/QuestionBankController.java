package com.thinkgem.jeesite.modules.teacher.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.entity.Examknowledge;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 题库维护
 */
@Controller
@RequestMapping("${teacherPath}/questionBank")
public class QuestionBankController {
	/*//cookie中的教师id以及版本id
	 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
	String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");*/
	
	
	@Autowired
	ExaminationService examService;
	/**
	 * 选择知识点
	 */
	@RequestMapping(value="selectKnowledge")
	public String selectKnowledge(@ModelAttribute("exam")Examination exam,Model model){
		List<String> examknowledgesIds=examService.getknowledgeIdsByExamId(exam.getId());
		model.addAttribute("examknowledgesIds",Collections3.convertToString(examknowledgesIds,","));//选中的知识点Ids
		model.addAttribute("exam", exam);
		model.addAttribute("examknowledge", new Examknowledge());//支持form:from 标签
		return "teacher/exam/questionBank";
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
		return "teacher/exam/questionBankList";
	}
	/**
	 * 跳转至添加页面
	 */
	@RequestMapping(value="addQuestion")
	public String addQuestion(TeacherVersionQuestion teacherVersionQuestion,Model model){
		
		return "teacher/exam/questionBankNew";
	}
	/**
	 * 跳转至添加页面的选择知识点
	 */
	@RequestMapping(value="addQuestionSelectKnow")
	public String addQuestionSelectKnow(@ModelAttribute("exam")Examination exam,Model model){
		List<String> examknowledgesIds=examService.getknowledgeIdsByExamId(exam.getId());
		model.addAttribute("examknowledgesIds",Collections3.convertToString(examknowledgesIds,","));//选中的知识点Ids
		model.addAttribute("exam", exam);
		model.addAttribute("examknowledge", new Examknowledge());//支持form:from 标签
		return "teacher/exam/questionBankNewSelectKnow";
	}
}
