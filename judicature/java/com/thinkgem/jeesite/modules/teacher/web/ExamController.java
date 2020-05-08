package com.thinkgem.jeesite.modules.teacher.web;

import java.text.NumberFormat;
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
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.teacher.dto.ExamStudentDTO;
import com.thinkgem.jeesite.modules.teacher.dto.StudentQuestionDTO;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.service.ExamClassService;
import com.thinkgem.jeesite.modules.teacher.service.ExamClassWorkService;
import com.thinkgem.jeesite.modules.teacher.service.ExamService;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;
import com.thinkgem.jeesite.modules.teacher.service.TestPaperService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 作业管理
 */
@Controller
@RequestMapping("${teacherPath}/exam")
public class ExamController extends BaseController {

	@Autowired
	private ExamService examService;
	@Autowired
	private ExaminationService examinationService;
	@Autowired
	private ExamClassService examClassService;
	@Autowired
	private SchoolClassService schoolClassService;
	@Autowired
	private StudentService studentService;
	@Autowired
	TestPaperService testPaperService;
	@Autowired
	ExamClassWorkService examClassWorkService;
	
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
	 * 已完成作业列表
	 */
	@RequestMapping("examList")
	public String examList(Exam exam,String a,HttpServletRequest request, HttpServletResponse response, Model model){
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
		//获取作业信息
		Page<Exam> page = examService.getExam(new Page<Exam>(request, response),exam,userId,versionId);
		model.addAttribute("examList", page);
		model.addAttribute("exams",exam);
		if(exam!=null){
			Date beginTime=exam.getBeginTime();
			model.addAttribute("beginTime", beginTime);
			Date endTime=exam.getEndTime();
			model.addAttribute("endTime", endTime);
		}
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(("1").equals(a)){
			model.addAttribute("message", "该试卷已自动判卷！");
		}
		return "teacher/exam/exam_list";
	}
	
	
	/**
	 * 未发布作业列表
	 */
	@RequestMapping("examListUnPublish")
	public String examListUnPublish(String a,Exam exam,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		//获取作业信息
		Page<Exam> page = examService.getExam2(new Page<Exam>(request, response),exam,userId,versionId);
		model.addAttribute("examList", page);
		model.addAttribute("exams",exam);
		if(exam!=null){
			Date beginTime=exam.getBeginTime();
			model.addAttribute("beginTime", beginTime);
			Date endTime=exam.getEndTime();
			model.addAttribute("endTime", endTime);
		}
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		if(("1").equals(a)){
			model.addAttribute("message", "该作业已经过了最迟完成时间，不可以再进行发布！");
		}
		return "teacher/exam/exam_list_unpublish";
	}
	/**
	 * 过时未发布的作业
	 * @param a
	 * @param exam
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	/*@RequestMapping("examListOvertimeUnPublish")
	public String examListOvertimeUnPublish(Exam exam,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		//获取作业信息
		Page<Exam> page = examService.getOvertimeExam(new Page<Exam>(request, response),exam,userId,versionId);
		model.addAttribute("examList", page);
		model.addAttribute("exams",exam);
		if(exam!=null){
			Date beginTime=exam.getBeginTime();
			model.addAttribute("beginTime", beginTime);
			Date endTime=exam.getEndTime();
			model.addAttribute("endTime", endTime);
		}
		if(exam!=null && exam.getTitle()!=null && !(("").equals(exam.getTitle()))){
			model.addAttribute("examTitle", exam.getTitle());
		}else{
			model.addAttribute("examTitle",null);
		}
		return "teacher/exam/exam_overtime_unpublish";
	}*/
	
	
	/**
	 * 如果发布未发布的作业
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("publishExam")
	public String publishExam(String examId,String examClassId){
		Exam exam =examService.getExamDetail(examId);
		/*if(exam!=null){
			Date endTime = exam.getEndTime();
			boolean flag = endTime.before(new Date());
			if(flag)
				return "redirect:"+teacherPath+"/exam/examListUnPublish?a="+1;
			
		}*/
		Examination examination=new Examination();
		examination.setId(examId);
		Date date=new Date();
		examination.setBeginTime(date);
		String endHours = exam.getEndHours();
		examination.setEndHours(endHours);
		String answerHours = exam.getAnswerHours();
		if(("NaN").equals(answerHours)){
			examination.setAnswerHours("52560000");
		}else{
			examination.setAnswerHours(answerHours);
		}
		examinationService.updateHomeWorkExamSelect(examination);
		testPaperService.publishExam(examId,examClassId);
		return "redirect:"+teacherPath+"/exam/examListUnPublish";
	}
	/**
	 * 提交正在进行的作业
	 */
	@RequestMapping("submit")
	public String submitExam(String examId,String examClassId){
		testPaperService.submitExam(examId,examClassId);
		return "redirect:"+teacherPath+"/exam/examList";
	}
	/**
	 * 删除作业
	 */
	@RequestMapping("deleteExerciseExam")
	public String deleteExerciseExam(String examId,String examClassId){
		testPaperService.deleteExam(examId,examClassId);
		return "redirect:"+teacherPath+"/exam/examListUnPublish";
	}
	/**
	 * 班级作业列表
	 */
	@RequestMapping("examClassList")
	public String examClassList(String examId,String schoolClassId,String a,ExamStudentDTO stu,HttpServletRequest request, HttpServletResponse response, Model model){
		//获取作业名称
		Exam exam=examService.get(examId);
		//获取班级名称
		SchoolClass school = schoolClassService.get(schoolClassId);
		//本班总人数
		Integer countPerson=examClassWorkService.totalPerson(schoolClassId);
		model.addAttribute("countPerson", countPerson);
		//未交作业人数
		Integer unSubmitCount= examClassWorkService.unSubmitPerson(examId,schoolClassId);
		model.addAttribute("unSubmitCount", unSubmitCount);
		//班级内的学生
		Student student=new Student();
		Exam e=new Exam();
		e.setId(examId);
		stu.setStudent(student);
		stu.setExam(e);
		stu.setTableClassId(schoolClassId);
		Page<ExamStudentDTO> page = examClassService.findExamStudent(new Page<ExamStudentDTO>(request, response), stu);
		/*if(("1").equals(a)){
			model.addAttribute("message", "您尚未判卷！");
		}*/
		model.addAttribute("student", page);
		model.addAttribute("exam", exam);
		model.addAttribute("school", school);
		return "teacher/exam/exam_class_detail";
	}
	
	/**
	 * 个人完成情况
	 */
	@RequestMapping("examPersonList")
	public String examPersonList(StudentQuestionDTO studentQuestionDTO,String studentId,String examId,String examClassId,HttpServletRequest request, HttpServletResponse response, Model model){
		String state=examService.getStudentTaskState(studentId,examId);
		/*if(!("1").equals(state)){
			return "redirect:"+teacherPath+"/exam/examClassList?examId="+examId+"&schoolClassId="+examClassId+"&a="+1;
		}else{*/
			//获取作业名称
			Exam exam=examService.get(examId);
			//获取班级名称
			SchoolClass school = schoolClassService.get(examClassId);
			//获得学生姓名
			Student student = studentService.get(studentId);
			model.addAttribute("exam", exam);
			model.addAttribute("school", school);
			model.addAttribute("student", student);
			 
			Page<StudentQuestionDTO> studentQuestion=examService.queryPersondetail(new Page<StudentQuestionDTO>(request, response),studentQuestionDTO,examId,examClassId,studentId);
			model.addAttribute("studentQuestion", studentQuestion);
		/*}*/
		return "teacher/exam/exam_person_detail";
	}
	
	/**
	 * 题目错误统计
	 */
	@RequestMapping("examTitleList")
	public String examTitleList(String examId,String examClassId,String studentId,String questionId,HttpServletRequest request, HttpServletResponse response, Model model){
		//该题在班级的回答状况
		Exam exam=new Exam(examId);
		SchoolClass schoolClass=new SchoolClass(examClassId);
		Student student=new Student();
		student.setSchoolClass(schoolClass);
		ExamStudentDTO examStudentDTO=new ExamStudentDTO();
		examStudentDTO.setExam(exam);
		examStudentDTO.setStudent(student);
		Page<ExamStudentDTO> studentQuestion=examService.queryClassPersondetail(new Page<ExamStudentDTO>(request, response),examStudentDTO,examClassId,questionId);
		model.addAttribute("studentQuestion", studentQuestion);
		
		//页面开头的字段
		//本班总人数
		Integer countPerson=examService.getPersonTotal(examClassId);
		model.addAttribute("countPerson", countPerson);
		//本次答错人数
		Integer getRight = examService.getRight(examClassId,examId,questionId);
		Integer getError=countPerson-getRight;
		model.addAttribute("gerError", getError);
		if(countPerson==0){
			model.addAttribute("errorPercent", "0.00%");
		}else{
			double errorPercent=(double)getError/(double)countPerson;
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			String format = nt.format(errorPercent);
			model.addAttribute("errorPercent", format);			
		}
		model.addAttribute("examId", examId);
		model.addAttribute("examClassId", examClassId);
		model.addAttribute("questionId", questionId);
		model.addAttribute("studentId", studentId);	
		return "teacher/exam/exam_title_detail";
	}
	
}
