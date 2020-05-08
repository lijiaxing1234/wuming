package com.thinkgem.jeesite.modules.student.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.StudentReview;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.StudentReviewService;
import com.thinkgem.jeesite.modules.questionlib.service.VersionQuestionService;
import com.thinkgem.jeesite.modules.student.entity.SExam;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.service.StudentAnswerService;
import com.thinkgem.jeesite.modules.student.service.StudentExamService;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;

/**
 * 进入我的考试、随堂测评、课后作业、课程例题、我的页面
 * 各种测试详情查看
 * @author .36F
 *
 */
@Controller
@RequestMapping(value = "${studentPath}/studentExam")
public class StudentExamController extends BaseController {
	@Autowired
	private StudentExamService sExamService;
	@Autowired
	private StudentAnswerService studentAnswerService;
	@Autowired
	private VersionQuestionService versionQuestionService;
	@Autowired
	private StudentReviewService reviewService;
	
	/**
	 * 查询学生所在班级的所有测试
	 * 测试状态为1：已经参加了的考试	2：没有参加的考试：已经错过了		3：没有参加的考试：没有错过	正常的考试   查询结果集按照测试状态码倒序排序
	 * 操作：3：进入 2：显示已经错过考试：1：可以进入
	 * @param model
	 * @param request
	 * @param response
	 * @param exam
	 * @return
	 */
	@RequestMapping(value = {"examList","quizList","homeWorkList","exampleList"})
	public String list (Model model,HttpServletRequest request,HttpServletResponse response,SExam exam){
		try{
			//各种类型的测试查询都要加上版本ID
			String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
			String examType = exam.getExamType();
			exam.setVersionId(versionId);
			//获取学生的所有考试
			String studentId = StudentUserUtils.getUser().getId();
			exam.setStudentId(studentId);
			Page<SExam> page = sExamService.findPage(new Page<SExam>(request, response), exam);
			model.addAttribute("page", page);
			model.addAttribute("versionId", versionId);
			model.addAttribute("user", StudentUserUtils.getUser());
			//5：我的考试（在线考试） 1：随堂测评（需要作答，只有一道题目） 3：课后作业（相当于在线考试） 4：课堂例题：（只有一道题目，不需要做直接讲解）
			//根据测试的类型  返回不同页面 1随堂测 2考试 3作业 4例题
			if("1".equals(examType)){
				//随堂评测（需要作答，只有一道题目）
				//处理随堂评测的时间问题
				List<SExam> list = page.getList();
				for (SExam sExam : list) {
					String examHours = sExam.getExamHours();
					sExam.setDurationTime(examHours);
				}
				model.addAttribute("page", page);
				return "student/pages/quizList";
			}else if("5".equals(examType)){
				//我的考试（在线考试）
				return "student/pages/examList";
			}else if("3".equals(examType)){
				//课后作业（相当于在线考试）
				return "student/pages/homeWorkList";
			}else {
				//课堂例题（只有一道题目，不需要做直接讲解）
				return "student/pages/exampleList";
			}
		}catch (Exception e) {
			logger.error(e.toString());
			String examType = exam.getExamType();
			if("1".equals(examType)){
				return "student/pages/quizList";
			}else if("5".equals(examType)){
				//我的考试（在线考试）
				return "student/pages/examList";
			}else if("3".equals(examType)){
				//课后作业（相当于在线考试）
				return "student/pages/homeWorkList";
			}else {
				//课堂例题（只有一道题目，不需要做直接讲解）
				return "student/pages/exampleList";
			}
		}
		
	}
	
	/**
	 * 错题集
	 * 查询某学生的错题
	 * @return
	 */
	@RequestMapping(value = "wrongQuestionsList")
	public String wrongQuestionsList(Model model,HttpServletRequest request,HttpServletResponse response,VersionQuestion question){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		Page<VersionQuestion> page = sExamService.findMyWrongQuestionLib(new Page<VersionQuestion>(request, response),question);
		model.addAttribute("page", page);
		model.addAttribute("question", question);
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/wrongQuestionList";
	}
	
	/*
	@RequestMapping(value = "myQuestionLib")
	public String myQuestionLib(Model model,HttpServletRequest request,HttpServletResponse response,VersionQuestion question){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		Page<VersionQuestion> page = sExamService.findMyQuestionLib(new Page<VersionQuestion>(request, response), question);
		model.addAttribute("page", page);
		model.addAttribute("question", question);
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/myQuestionLibList";
	}*/
	
	@RequestMapping(value = "myQuestionLib")
	public String myQuestionLib(Model model,HttpServletRequest request,HttpServletResponse response,VersionQuestion question){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/studentQuestionLib";
	}
	
	
	/**
	 * 查询某考试的相关信息
	 * 根据测试id和学生地查询某试卷的全部试题
	 * @param model
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "searchExamQuestions")
	public String searchExamQuestions(Model model,String examId,String examDetailId,String examType){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		//题型 1：单选题 2：填空题 3：计算题 4：简答题 5：多选题 6：概念题 7：综合题 8：制图题 9：制表题 10：识图题 11：判断题
		List<VersionQuestion> sQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"1");
		List<VersionQuestion> fQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"2");
		List<VersionQuestion> cQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"3");
		List<VersionQuestion> sAQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"4");
		List<VersionQuestion> sMQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"5");
		List<VersionQuestion> gainianQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"6");
		List<VersionQuestion> zongheQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"7");
		List<VersionQuestion> zhituQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"8");
		List<VersionQuestion> zhibiaoQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"9");
		List<VersionQuestion> shituQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"10");
		List<VersionQuestion> panduanQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType2(examId,"11");
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
		
		
		model.addAttribute("examId", examId);
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/examQuestionsDetails";
	}
	
	/**
	 * 根据考试id查询已经错过的考试的全部试题
	 * @param request
	 * @param response
	 * @param model
	 * @param examId
	 * @return
	 */
	@RequestMapping("getMissQuestions")
	public String getMissQuestions(HttpServletRequest request,HttpServletResponse response ,Model model,String examId){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		model.addAttribute("examId", examId);
		//题型 1：单选题 2：填空题 3：计算题 4：简答题 5：多选题 6：概念题 7：综合题 8：制图题 9：制表题 10：识图题 11：判断题
		List<VersionQuestion> sQuestionList = studentAnswerService.getMissQuestions(examId,"1");
		List<VersionQuestion> fQuestionList = studentAnswerService.getMissQuestions(examId,"2");
		List<VersionQuestion> cQuestionList = studentAnswerService.getMissQuestions(examId,"3");
		List<VersionQuestion> sAQuestionList = studentAnswerService.getMissQuestions(examId,"4");
		List<VersionQuestion> sMQuestionList = studentAnswerService.getMissQuestions(examId,"5");
		List<VersionQuestion> gainianQuestionList = studentAnswerService.getMissQuestions(examId,"6");
		List<VersionQuestion> zongheQuestionList = studentAnswerService.getMissQuestions(examId,"7");
		List<VersionQuestion> zhituQuestionList = studentAnswerService.getMissQuestions(examId,"8");
		List<VersionQuestion> zhibiaoQuestionList = studentAnswerService.getMissQuestions(examId,"9");
		List<VersionQuestion> shituQuestionList = studentAnswerService.getMissQuestions(examId,"10");
		List<VersionQuestion> panduanQuestionList = studentAnswerService.getMissQuestions(examId,"11");
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
		return "student/pages/examQuestionsDetails";
	}
	
	/**
	 * 根据题目id获取题目的相关信息
	 * @param model
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "getQuestionExplain")
	public String getQuestionExplain(Model model,String questionId,String examId){
		if(StringUtils.contains(examId, ",")){
			examId = StringUtils.substringAfter(examId, ",");
		}
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		//根据题目的id 和考试的id 查询试题   若answer0 到 answer9 中有值则表示老师已经公布了这个考试的所有试题的答案，讲解也是类似
		VersionQuestion question = null;
		if(StringUtils.isBlank(examId)){
			question = versionQuestionService.getQuestionMsg(questionId);
		}else{
			question = versionQuestionService.getQuestionMsg(questionId,examId);
		}
		model.addAttribute("question", question);
		//查询某人在某测试中的某题目答案
		StudentAnswer studentAnswer = sExamService.getStudentAnswer(questionId,examId);
		model.addAttribute("studentAnswer", studentAnswer);
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/questionExplain";
	}
	
	/**
	 * 根据试题id查询试题的详细信息  我的答案
	 * @param questionId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getQuestionDetails")
	public String getQuestionDetails(String questionId,Model model){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		VersionQuestion question = versionQuestionService.getQuestionByQuestionId(questionId);
		model.addAttribute("question", question);
		//查询某人 某题目的答案  
		List<StudentAnswer> studentAnswerList = sExamService.getStudentAnswerByQuestionIdAndStudentId(questionId);
		model.addAttribute("studentAnswerList", studentAnswerList);
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/questionDetails";
	}
	
	@RequestMapping(value = "questionLibGetQuestionDetails")
	public String questionLibGetQuestionDetails(String questionId,Model model){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		//两场考试有同一题目 只要有一场考试设置了该题目的答案可以查看，则该题目讲解可查看
		VersionQuestion question = versionQuestionService.getQuestionByQuestionId(questionId);
		model.addAttribute("question", question);
		//查询某人 某题目的答案  
		List<StudentAnswer> studentAnswerList = sExamService.getStudentAnswerByQuestionIdAndStudentId(questionId);
		model.addAttribute("studentAnswerList", studentAnswerList);
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/questionLibQuestionDetails";
	}
	
	
	/**
	 * 我的评价页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "myReview")
	public String myReview(Model model,StudentReview review,HttpServletRequest request,HttpServletResponse response){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		Page<StudentReview> page = reviewService.getMyReview(new Page<StudentReview>(request, response),review);
		model.addAttribute("page",page);
		StudentReview studentReview = null;
		if(review == null){
			studentReview  = new StudentReview();
			model.addAttribute("studentReview", studentReview);
		}else{
			model.addAttribute("studentReview", review);
		}
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/studentReviewList";
	}
	
	/**
	 * 根据questionId 获取同类知识点下的三道试题
	 * @param model
	 * @param request
	 * @param response
	 * @param questionId
	 * @return
	 */
	@RequestMapping("getCongenericQuestions")
	public String getCongenericQuestions (Model model,HttpServletRequest request,HttpServletResponse response,String questionId){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		//根据questionId 获取同类知识点下的同类型的三个题目
		List<VersionQuestion> list = sExamService.getCongenericQuestions(questionId,3);
		if(list.isEmpty() || list.size() == 0){
			addMessage(model, "当前无可练习的试题");
			return "student/pages/examQuestionsDetailsCongeneric";
		}else{
			String questionType = list.get(0).getQuesType();
			if("1".equals(questionType)){
				model.addAttribute("sQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else if("2".equals(questionType)){
				model.addAttribute("fQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else if("3".equals(questionType)){
				model.addAttribute("cQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else if("4".equals(questionType)){
				model.addAttribute("sAQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else if("5".equals(questionType)){
				model.addAttribute("sMQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else if("6".equals(questionType)){
				model.addAttribute("gainianQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else if("7".equals(questionType)){
				model.addAttribute("zongheQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else if("8".equals(questionType)){
				model.addAttribute("zhituQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else if("9".equals(questionType)){
				model.addAttribute("zhibiaoQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else if("10".equals(questionType)){
				model.addAttribute("shituQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else if("11".equals(questionType)){
				model.addAttribute("panduanQuestionList", list);
				return "student/pages/examQuestionsDetailsCongeneric";
			}else{
				return "student/pages/examQuestionsDetailsCongeneric";
			}
		}
	}
	
	
	/**
	 * 教学资源下载
	 * 
	 */
	@ResponseBody
	@RequestMapping("downloadResource")
	public String downloadResource(String resourceId,HttpServletRequest request,HttpServletResponse response){
		String filePath = sExamService.getResourceFilePath(resourceId);
		String userfilesBaseDir = Global.getUserfilesBaseDir();
		String message = FileUtils.downFile(new File(userfilesBaseDir + filePath), request, response);
		if(StringUtils.isNotBlank(message)){
			return "文件资源不存在";
		}else{
			return "";
		}
		
		
	}
	
}
