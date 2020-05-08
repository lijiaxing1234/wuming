package com.thinkgem.jeesite.modules.teacher.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.teacher.dao.ExamDao;
import com.thinkgem.jeesite.modules.teacher.dto.ExamStudentDTO;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.ExamClass;
import com.thinkgem.jeesite.modules.teacher.service.ExamService;
import com.thinkgem.jeesite.modules.teacher.service.TestPaperService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 考试监控(只有在线考试)
 */
@Controller
@RequestMapping("${teacherPath}/monitor")
public class ExamMonitorController extends BaseController {
	@Autowired
	ExamService examService;
	@Autowired
	ExamDao examDao;
	@Autowired
	private SchoolClassService schoolClassService;
	@Autowired
	private TestPaperService testPaperService;
	
	
	@RequestMapping("list")
	public String list(ExamClass exam,HttpServletRequest request, HttpServletResponse response, Model model){
		//cookie中的教师id以及版本id
		 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		 String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		Page<ExamClass> examAndClass = examService.findAllOnlineExam(new Page<ExamClass>(request, response),exam,userId, versionId);
		model.addAttribute("examAndClass", examAndClass);
		return "teacher/examMonitor/list";
	}
	@RequestMapping("findExamStudentDetail")
	public String findExamStudentDetail(ExamStudentDTO examStudentDTO,String examId,String classId,HttpServletRequest request, HttpServletResponse response, Model model){
		/*testPaperService.submitTestPaper(examId,classId);//结束考试*/
		//获取测试详情
		Exam exam = examService.get(examId);
		model.addAttribute("exam", exam);
		//获取班级名称
		SchoolClass school = schoolClassService.get(classId);
		model.addAttribute("school", school);
		//获取班级内的总人数
		List<String> studentIds= examDao.getStudentByClassId(classId);
		Integer totalPerson=studentIds.size();
		model.addAttribute("totalPerson",totalPerson );
		//获取班级内的提交人数
		Integer submitPersons=examService.getStudentSubmitPerson(classId,examId);
		model.addAttribute("submitPersons",submitPersons);
		//获取班级内的已开始作答人数
		Integer answerPersons=examService.getStudentSubmitPerson2(classId,examId);
		model.addAttribute("answerPersons",answerPersons);
		//未提交人数
		Integer unSubmitPersons=totalPerson-submitPersons;
		model.addAttribute("unSubmitPersons",unSubmitPersons);
		//获取列表
		Page<ExamStudentDTO> page = examService.getClassStudent(new Page<ExamStudentDTO>(request, response),examStudentDTO,examId,classId);
		model.addAttribute("page", page);
		return "teacher/examMonitor/class_list";
	}
}
