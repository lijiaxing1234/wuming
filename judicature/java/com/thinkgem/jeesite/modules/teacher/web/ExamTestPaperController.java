package com.thinkgem.jeesite.modules.teacher.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.TableClass;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.student.service.StudentAnswerService;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.Examdetail;
import com.thinkgem.jeesite.modules.teacher.entity.ExamdetailQuestion;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.service.ExamService;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;
import com.thinkgem.jeesite.modules.teacher.service.TestPaperService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;
/**
 * 试卷列表
 * @author xmkc3
 *
 */

@Controller
@RequestMapping("${teacherPath}/testPaper")
public class ExamTestPaperController extends BaseController{
	
	@Autowired
	TestPaperService testPaperService;
	@Autowired
	ExaminationService examService; //试卷service
	@Autowired
	ExamService examService2; //试卷service
	@Autowired
	StudentAnswerService studentAnswerService;
	
	@ModelAttribute
	public Exam get(@RequestParam(required=false) String id) {
		Exam entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testPaperService.get(id);
		}
		if (entity == null){
			entity = new Exam();
		}
		return entity;
	}
	
	/**
	 * 线上试卷列表
	 * 已完成
	 */
	@RequestMapping("testPaperOnLineList")
	public String testPaperOnLineList(Exam exam,String a,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		//获得教师所任课程的所有试卷
		List<Exam> list=testPaperService.getAllUnSubmitExam(userId,versionId);
		for (Exam exam2 : list) {
			boolean label=true;
			if(exam2!=null&&label){
				List<String> list2=examService2.getExamType(exam2.getId());
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
		//获得教师所任课程的所有试卷
		Page<Exam> page = testPaperService.getExamOnLine(new Page<Exam>(request, response), exam,userId,versionId);

		model.addAttribute("examList", page);
		
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(exam!=null){
			model.addAttribute("beginTime",exam.getBeginTime());
			model.addAttribute("endTime",exam.getEndTime());
		}
		if(("1").equals(a)){
			model.addAttribute("message","该班没有成绩");
		}
		return "teacher/exam/exam_testpaper_online";
	}
	//未完成
	@RequestMapping("testPaperOnLineListUnSubmit")
	public String testPaperOnLineListUnSubmit(Exam exam,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		//获得教师所任课程的所有试卷
		Page<Exam> page = testPaperService.getExamOnLine2(new Page<Exam>(request, response), exam,userId,versionId);

		model.addAttribute("examList", page);
		
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(exam!=null){
			model.addAttribute("beginTime",exam.getBeginTime());
			model.addAttribute("endTime",exam.getEndTime());
		}
		return "teacher/exam/exam_testpaper_online_unsubmit";
	}
	/**
	 * 线下试卷列表
	 * 已完成
	 * @param exam
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("testPaperList")
	public String testPaperList(Exam exam,String a,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		//获得教师所任课程的所有试卷
		List<Exam> list=testPaperService.getAllUnSubmitExam(userId,versionId);
		for (Exam exam2 : list) {
			boolean label=true;
			if(exam2!=null&&label){
				List<String> list2=examService2.getExamType(exam2.getId());
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
		//获得教师所任课程的所有试卷
		Page<Exam> page = testPaperService.getExam(new Page<Exam>(request, response), exam,userId,versionId);

		model.addAttribute("examList", page);
		
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(exam!=null){
			model.addAttribute("beginTime",exam.getBeginTime());
			model.addAttribute("endTime",exam.getEndTime());
		}
		if(("1").equals(a)){
			model.addAttribute("message","该班没有成绩");
		}
		return "teacher/exam/exam_testpaper";
	}
	//未完成
	@RequestMapping("testPaperListUnSubmit")
	public String testPaperListUnSubmit(Exam exam,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		//获得教师所任课程的所有试卷
		Page<Exam> page = testPaperService.getExam2(new Page<Exam>(request, response), exam,userId,versionId);

		model.addAttribute("examList", page);
		
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(exam!=null){
			model.addAttribute("beginTime",exam.getBeginTime());
			model.addAttribute("endTime",exam.getEndTime());
		}
		return "teacher/exam/exam_testpaper_unsubmit";
	}
	/**
	 * 查看功能
	 */
	@RequestMapping("testPaperUpExam")
	public String testPaperUpExam(String examId,Model model,String examStatus){
		Examination exam=new Examination(examId);
		Examination exam2 = examService.getExam(exam);
		List<Examdetail> examDetailByExamId = examService.getExamDetailByExamId(exam2);
		model.addAttribute("exam", exam2);
		model.addAttribute("examdetails",examDetailByExamId);//区分A B卷还是一般卷的
		//查询试卷总题数
		Integer i=examService.getQuestionlibCount(examId);
		model.addAttribute("questionlibCount", i);
		model.addAttribute("examStatus", examStatus);
		return "teacher/exam/exam_testpaper_examTitle";
	}
	/*@RequestMapping("adjustExamList")
	public  String  adjustExamList(String examdetailId,String examId,Date beginTime,Date endTime,String examType, Model model,HttpServletRequest request,HttpServletResponse response){
		Map<String,List<ExamdetailQuestion>> dataMap=examService.getExamdetailQuestionByExamdetailIdGroupbyQuesType(examdetailId,beginTime,endTime,examType);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("examId", examId);
		model.addAttribute("examdetailId", examdetailId);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("examType", examType);
		return "teacher/exam/exam_testpaper_adjustExamList";
	}*/
	@RequestMapping(value="adjustExamList")
	public String adjustExamList(String examdetailId,Model model){
		//String examDetailId=examService.getExamDetailId2(examId);
		//题型 1：单选题 2：填空题 3：计算题 4：简答题 5：多选题 6：概念题 7：综合题 8：制图题 9：制表题 10：识图题 11：判断题
		List<VersionQuestion> sQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"1");
		List<VersionQuestion> fQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"2");
		List<VersionQuestion> cQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"3");
		List<VersionQuestion> sAQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"4");
		List<VersionQuestion> sMQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"5");
		List<VersionQuestion> gainianQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"6");
		List<VersionQuestion> zongheQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"7");
		List<VersionQuestion> zhituQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"8");
		List<VersionQuestion> zhibiaoQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"9");
		List<VersionQuestion> shituQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"10");
		List<VersionQuestion> panduanQuestionList = studentAnswerService.getQuestionListByExamDetailIdAndQuesType(examdetailId,"11");
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
	/**
	 * 试卷管理的删除功能
	 */
	@RequestMapping("deleteTestPaper")
	public String deleteTestPaper(String examId,String examClassId,String status){
		testPaperService.deleteExam(examId,examClassId);
		if(("no").equals(status)){//线下
			return "redirect:"+teacherPath+"/testPaper/testPaperListUnSubmit";
		}else{//线上
			return "redirect:"+teacherPath+"/testPaper/testPaperOnLineListUnSubmit";
		}
		/*return "redirect:"+teacherPath+"/testPaper/testPaperList";*/
	}
	/**
	 * 试卷管理的发布
	 */
	@RequestMapping("publishExam")
	public String publishExam(String examId,String examClassId){
		testPaperService.publishExam(examId,examClassId);
		return "redirect:"+teacherPath+"/testPaper/testPaperList";
	}
	/**
	 * 结束考试
	 */
	@RequestMapping("endExam")
	public String endExam(String examId,String examClassId){
		testPaperService.submitTestPaper(examId,examClassId);
		return "redirect:"+teacherPath+"/testPaper/testPaperList";
	}
	/**
	 * 试卷管理的存为模板功能
	 */
	@RequestMapping("saveExam")
	public String saveExam(String examId,String status,String examStatus){
		testPaperService.saveExam(examId);
		if(("no").equals(status)){//线下
			if(("no").equals(examStatus)){
				return	"redirect:"+teacherPath+"/testPaper/testPaperListUnSubmit";
			}
			return "redirect:"+teacherPath+"/testPaper/testPaperList";
		}else{//线上
			if(("no").equals(examStatus)){
				return	"redirect:"+teacherPath+"/testPaper/testPaperOnLineListUnSubmit";
			}
			return "redirect:"+teacherPath+"/testPaper/testPaperOnLineList";
		}
	}
}
