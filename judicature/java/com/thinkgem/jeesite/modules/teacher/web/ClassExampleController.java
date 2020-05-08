package com.thinkgem.jeesite.modules.teacher.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.student.service.StudentAnswerService;
import com.thinkgem.jeesite.modules.teacher.Constants;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
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
 * 首页的课堂例题管理
 */
@Controller
@RequestMapping("${teacherPath}/classExample")
public class ClassExampleController {
	
	@Autowired
	ExaminationService examService;
	@Autowired
	ExamClassService examClassService;
	@Autowired
	TestPaperService testPaperService;
	@Autowired
	StudentAnswerService studentAnswerService;
	
	@Autowired
	QuestionslibSplitService queslibSplitService;
	/*@ModelAttribute("exam")
	public Examination get(String id){
		Examination exam=null;
		if(StringUtils.isNotBlank(id)){
			exam=examService.getExam(new Examination(id));
		}
		if(exam==null){
			exam=new Examination();
		}
		return exam;
	}*/
	
	/**
	 * 新增试卷类型为例题
	 */
	/*@RequestMapping(value="examPager")
	public String saveExamPaper(Examination exam,String newExam){
		 String examId="";
		 if(StringUtils.isNotBlank(exam.getId())){
			 examId=exam.getId();
		 }else{
			 exam.setCreateDate(new Date());
			 exam.setExamType(Constants.EXAMINATION_EXAMTYPE_EXAMPLE);
			 examId=examService.addExam(exam);
		 }
	   return "redirect:/t/classExample/selectKnowledge?id="+examId;
	}*/
	
	
	
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
		return "teacher/examination/classExample";
	}
	
	/**
	 * 查询选中知识点下所有题目
	 */
	
	 @RequestMapping(value="selectQuestion")
	public String selectQuestion(TeacherVersionQuestion question,String teacherQuestionId,String examId,Examknowledge examknowledge,Model model){
		//cookie中的教师id以及版本id
		/* String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");*/
		
		
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
		return "teacher/examination/classExampleList";
	}
	/**
	 * 保存课堂例题
	 */
	@RequestMapping(value="saveClassExample")
	public String saveClassExample(Examination exam,String classDataRelation,String questionIds,String examTitle){
		String examId="";
		 if(StringUtils.isNotBlank(exam.getId())){
			 examId=exam.getId();
		 }else{
			 if(examTitle!=null){
				 exam.setTitle(examTitle);
			 }
			 exam.setCreateDate(new Date());
			 exam.setExamType(Constants.EXAMINATION_EXAMTYPE_EXAMPLE);
			 examId=examService.saveExamination(exam).getId();
		 }
		exam.setId(examId);
		exam.setStatus("0");//试卷状态 0 未发布 1已发布( 正在进行) 2测试完成 
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
		return "redirect:/t/classExample/exampleList";
	}
	/**
	 * 随堂例题管理的列表
	 */
	@RequestMapping(value="exampleList")
	public String exampleList(Exam exam,Date beginTime1,HttpServletRequest request,HttpServletResponse response,Model model){
		exam.setBeginTime(beginTime1);
		Page<Exam> page = examService.getExampleList(new Page<Exam>(request,response),exam);
		model.addAttribute("page", page);
		if(exam!=null){
			model.addAttribute("examTitle", exam.getTitle());
			model.addAttribute("beginTime", beginTime1);
		}
		return "teacher/examination/classExample_list";
	}
	//发布课堂例题 
	@RequestMapping(value="publish")
	public String submitExam(String examId,String examClassId){
		testPaperService.submitExample(examId,examClassId);
		return "redirect:/t/classExample/exampleList";
	}
	//删除课堂例题
	@RequestMapping(value="delete")
	public String examDelete(String examId,String examClassId){
		testPaperService.deleteExam(examId,examClassId);
		return "redirect:/t/classExample/exampleList";
	}
	//查看例题
	@RequestMapping(value="update")
	public String update(String examId,String examClassId,HttpServletRequest request,HttpServletResponse response ,Model model){
		String examDetailId=examService.getExamDetailId2(examId);
		//题型 1：单选题 2：填空题 3：计算题 4：简答题 5：多选题 6：概念题 7：综合题 8：制图题 9：制表题 10：识图题 11：判断题
		List<VersionQuestion> sQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"1");
		List<VersionQuestion> fQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"2");
		List<VersionQuestion> cQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"3");
		List<VersionQuestion> sAQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"4");
		List<VersionQuestion> sMQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"5");
		List<VersionQuestion> gainianQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"6");
		List<VersionQuestion> zongheQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"7");
		List<VersionQuestion> zhituQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"8");
		List<VersionQuestion> zhibiaoQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"9");
		List<VersionQuestion> shituQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"10");
		List<VersionQuestion> panduanQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examDetailId,"11");
		model.addAttribute("sQuestionList", sQuestionList);
		model.addAttribute("fQuestionList", fQuestionList);
		model.addAttribute("cQuestionList", cQuestionList);
		model.addAttribute("sAQuestionList", sAQuestionList);
		model.addAttribute("sMQuestionList", sMQuestionList);
		model.addAttribute("gainianQuestionList", gainianQuestionList);
		model.addAttribute("zongheQuestionList", zongheQuestionList);
		model.addAttribute("zhituQuestionList", zhituQuestionList);
		model.addAttribute("zhibiaoQuestionList", zhibiaoQuestionList);
		model.addAttribute("shituQuestionList", shituQuestionList);
		model.addAttribute("panduanQuestionList", panduanQuestionList);
		return "teacher/examination/classExample_question_details";
	}
}

