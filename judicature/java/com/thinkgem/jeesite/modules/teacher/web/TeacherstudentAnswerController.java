package com.thinkgem.jeesite.modules.teacher.web;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.student.entity.SExam;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.entity.StudentExamDetail;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.student.service.SExamService;
import com.thinkgem.jeesite.modules.student.service.StudentAnswerService;
import com.thinkgem.jeesite.modules.teacher.dto.VersionQuestionAnswerServiceDTO;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.service.ExamClassService;
import com.thinkgem.jeesite.modules.teacher.service.ExamService;
import com.thinkgem.jeesite.modules.teacher.service.TestPaperService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 教师判卷
 *
 */
@Controller
@RequestMapping("${teacherPath}/teacherStudentAnswer")
public class TeacherstudentAnswerController extends BaseController{

	@Autowired
	private ExamService examService;
	@Autowired
	private SchoolClassService schoolClassService;
	@Autowired
	private TestPaperService testPaperService;
	@Autowired
	private StudentService studentService;
	@ModelAttribute
	public Exam get(@RequestParam(required=false) String id) {
		Exam entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examService.get(id);
		}
		if (entity == null){
			entity = new Exam();
		}
		return entity;
	}
	/**
	 * 罗列出教师所布置的作业
	 */
	@RequestMapping("examList")
	public String examList(Exam exam,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		//获取作业信息
		Page<Exam> page = examService.getStudentExam(new Page<Exam>(request, response),exam,userId,versionId);
		model.addAttribute("examList", page);
		model.addAttribute("exams",exam);
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(exam!=null){
			model.addAttribute("beginTime",exam.getBeginTime());
			model.addAttribute("endTime",exam.getEndTime());
		}
		return "teacher/teacherMarkExam/homeWork_exam_list";
	}
	
	/**
	 * 罗列出在线考试
	 */
	@RequestMapping("examOnLineList")
	public String examOnLineList(Exam exam,String a,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		//获得教师所任课程的所有试卷
		List<Exam> list=testPaperService.getAllUnSubmitExam(userId,versionId);
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
		
		Page<Exam> page = testPaperService.getOnLineExam(new Page<Exam>(request, response), exam,userId,versionId);
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
			model.addAttribute("message", "该试卷已自动判卷！");
		}
		return "teacher/teacherMarkExam/onLine_exam_list";
	}
	
	/**
	 * 罗列出某一次作业下某个班级内的所有学生
	 */
	@RequestMapping("examStudentList")
	public String examStudentList(String examId,String examClassId,String status,String teacher,HttpServletRequest request, HttpServletResponse response,Model model){
		testPaperService.submitTestPaper(examId,examClassId);
		Student student=new Student();
		SchoolClass schoolClass=new SchoolClass();
		schoolClass.setId(examClassId);
		student.setSchoolClass(schoolClass);
		Page<Student> findExamStudent = examService.findExamStudent(new Page<Student>(request, response),student,examId);
		model.addAttribute("student",findExamStudent);
		Exam exam = examService.get(examId);
		model.addAttribute("exam", exam);
		//获取班级名称
		SchoolClass school = schoolClassService.get(examClassId);
		model.addAttribute("school", school);
		if(("1").equals(teacher)){
			model.addAttribute("message", "该学生未参加本次答题！");
		}else{
			if(("2").equals(teacher)){
			model.addAttribute("message", "您尚未判完所有同学的试卷请您继续判卷！");
			}else{
				model.addAttribute("message", "如果判完全班所有人的试卷，请点击最上方的“已判完班内所有人的试卷”按钮来进行归档保存!");
			}
			}
		model.addAttribute("status",status);
		List<String> list=examService.getExamType(examId);
		for (String string : list) {
			//单选题 1 填空题 2 计算题 3 简答题 4 多选题 5 概念题 6 综合题 7 制图题 8 制表题 9 识图题 10 判断题 11
			if(("2").equals(string)||("3").equals(string)||("4").equals(string)||("6").equals(string)||("7").equals(string)||("8").equals(string)||("9").equals(string)||("10").equals(string)){
				return "teacher/teacherMarkExam/exam_class_detail";
			}
		}
		examService.updateExamClass(examId,examClassId);
		if(("homeWork").equals(status)){
			return "redirect:/t/exam/examList?a=1";
		}
		if(("exerciseExam").equals(status)){
			return "redirect:/t/classWork/publishList?a=1";
		}
		if(("online").equals(status)){
			return "redirect:/t/testPaper/testPaperOnLineList?a=2";
		}
		return "redirect:/t/testPaper/testPaperList?a=2";
	}
	/**
	 * 进入需要老师判卷的页面
	 */
	@RequestMapping("teacherExamList")
	public String teacherExam(String studentId,String examId,String examClassId,Model model,String status){
		Exam exam = examService.get(examId);
		String examDetailId = examService.queryExamDetailId(studentId,examId);
		if(examDetailId==null ||("").equals(examDetailId)){
			return "redirect:/t/teacherStudentAnswer/examStudentList?examClassId="+examClassId+"&examId="+examId+"&teacher="+1+"&status="+status;
		}else{
			//2、根据 examDetailId 和题型  查询所有试题 单选题 1 填空题 2 计算题 3 简答题 4 多选题 5 概念题 6 综合题 7 制图题 8 制表题 9 识图题 10 判断题 11
			List<VersionQuestion> sQuestionList = examService.queryStudentAnswer(examDetailId,"1",studentId,examId);
			List<VersionQuestion> fQuestionList = examService.queryStudentAnswer(examDetailId,"2",studentId,examId);
			List<VersionQuestion> cQuestionList1 = examService.queryStudentAnswer(examDetailId,"3",studentId,examId);
			List<VersionQuestion> cQuestionList = examService.updateStudentAnswer(cQuestionList1);
			List<VersionQuestion> sAQuestionList1 = examService.queryStudentAnswer(examDetailId,"4",studentId,examId);
			List<VersionQuestion> sAQuestionList = examService.updateStudentAnswer(sAQuestionList1);
			List<VersionQuestion> sMQuestionList = examService.queryStudentAnswer(examDetailId,"5",studentId,examId);
			List<VersionQuestion> gainianQuestionList1 = examService.queryStudentAnswer(examDetailId,"6",studentId,examId);
			List<VersionQuestion> gainianQuestionList = examService.updateStudentAnswer(gainianQuestionList1);
			List<VersionQuestion> zongheQuestionList1 =examService.queryStudentAnswer(examDetailId,"7",studentId,examId);
			List<VersionQuestion> zongheQuestionList = examService.updateStudentAnswer(zongheQuestionList1);
			List<VersionQuestion> zhituQuestionList1 = examService.queryStudentAnswer(examDetailId,"8",studentId,examId);
			List<VersionQuestion> zhituQuestionList = examService.updateStudentAnswer(zhituQuestionList1);
			List<VersionQuestion> zhibiaoQuestionList1 = examService.queryStudentAnswer(examDetailId,"9",studentId,examId);
			List<VersionQuestion> zhibiaoQuestionList = examService.updateStudentAnswer(zhibiaoQuestionList1);
			List<VersionQuestion> shituQuestionList1 =examService.queryStudentAnswer(examDetailId,"10",studentId,examId);
			List<VersionQuestion> shituQuestionList = examService.updateStudentAnswer(shituQuestionList1);
			List<VersionQuestion> panduanQuestionList =examService.queryStudentAnswer(examDetailId,"11",studentId,examId);
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
			//获得学生姓名
			Student student = studentService.get(studentId);
			model.addAttribute("student", student);
			model.addAttribute("exam", exam);
			model.addAttribute("studentId",studentId);
			model.addAttribute("examDetailId",examDetailId);
			model.addAttribute("examClassId",examClassId);
			model.addAttribute("status",status);
		}
		model.addAttribute("message", "系统已自动判完客观题，请您继续判主观题并提交成绩！");
		return "teacher/teacherMarkExam/examQuestionsDetails";
	}
	/**
	 * 老师判卷进行修改answer表中的分数
	 */
	@RequestMapping("updateAnswer")
	public void updateAnswer(String studentId,String examId,String questionId,String score,Model model){
		int parseInt = Integer.parseInt(score);
		String isCorrect;
		if(parseInt>0){
			isCorrect="1";
		}else{
			isCorrect="0"; 
		}
		examService.answerQuestion(studentId,questionId,examId,score,isCorrect);
	}
	
	/**
	 * 计算该学生的总分
	 */
	@RequestMapping("totalStudentScore")
	public String totalStudentScore(String studentId,String examClassId,String examId,String examDetailId,String status){
		String totalStudentScore = examService.totalStudentScore(studentId,examId);
		//修改task表中的学生总分
		if(totalStudentScore==null){
			totalStudentScore="0";
		}
		examService.updateTask(studentId,examDetailId,totalStudentScore);
		return "redirect:"+Global.getTeacherPath()+"/teacherStudentAnswer/examStudentList?examId="+examId+"&examClassId="+examClassId+"&status="+status;
	}
	/**
	 * 修改试卷与班级表的状态
	 */
	@RequestMapping("updateExamClass")
	public String updateExamClass(String examId,String examClassId,String status){
		List<StudentTask> studentTask=examService.getExamStudentTaskState(examClassId, examId);
		if(studentTask.size()>0){
			return "redirect:/t/teacherStudentAnswer/examStudentList?examClassId="+examClassId+"&examId="+examId+"&status="+status+"&teacher=2"; 
		}else{
			examService.updateExamClass(examId,examClassId);
			if(("online").equals(status)){
				return "redirect:/t/testPaper/testPaperOnLineList"; 
			}else{
				return "redirect:/t/exam/examList";
			}
		}
	}
}
