package com.thinkgem.jeesite.modules.teacher.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.StudentReview;
import com.thinkgem.jeesite.modules.questionlib.service.StudentReviewService;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.questionlib.service.TableClassService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

@Controller("teacherStudentController")
@RequestMapping("${teacherPath}/examination")
public class StudentManageController extends BaseController {
	/*//cookie中的教师id以及版本id
	 String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
	String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");*/
	
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentReviewService studentReviewService;
	@Autowired
	private TableClassService tableClassService;
	
	@Autowired
	@Qualifier("teacherCommonController")
	CommonController  commonController;
	
	
	
	@ModelAttribute
	public Student get(@RequestParam(required=false) String id) {
		Student entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentService.get(id);
		}
		if (entity == null){
			entity = new Student();
		}
		return entity;
	}
	
	
	
	//学生管理
	@RequestMapping("student")
	public String list(Student student, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SchoolClass> teacherClasss=commonController.teacherClasss();
		student.getSqlParam().put("teacherClasss", teacherClasss);
		
		Page<Student>page=studentService.findPage(new Page<Student>(request,response), student);
		model.addAttribute("page", page);
		model.addAttribute("student",student);
		model.addAttribute("teacherClasss", teacherClasss);
		return "teacher/student/studentlist";
	}
	
	@RequestMapping("stuReviewForm")
	public String studentReviewForm(String studentId,Model model){
		Student currentStudent=studentService.get(new Student(studentId));
		model.addAttribute("student",currentStudent);
		model.addAttribute("studentReview", new StudentReview());
		return "teacher/student/studentReview";
	}
	
	
	//保存对学生的评论
	@RequestMapping("save")
	public String save(Student currentStudent, StudentReview studentReview, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
	  try{
			User teacher=TearcherUserUtils.getUser();
			if(teacher !=null){
				studentReview.setTeacherId(teacher.getId());
			}
			studentReview.setIsShow("0");
			studentReview.setCrateTime(DateTime.now().toDate());
			studentReviewService.save(studentReview);
			addMessage(model, "保存学生评论成功");
			model.addAttribute("success", true);
		}catch(Exception ex){
			addMessage(model, "保存学生评论成功");
			model.addAttribute("success", false);
		}
		//addMessage(redirectAttributes, "保存学生评论成功");
		//return "redirect:" + Global.getTeacherPath()+ "/examination/student?repage";
		return "teacher/student/tips";
	}
	
	

	@RequestMapping(value = "reviewList")
	public String myReview(Model model,StudentReview studentReview,HttpServletRequest request,HttpServletResponse response){
		
		studentReview.setTeacherId(TearcherUserUtils.getUser().getId());
		Page<StudentReview> page = studentReviewService.findPage(new Page<StudentReview>(request, response),studentReview);
		model.addAttribute("page",page);
		model.addAttribute("studentReview", studentReview);
		return "teacher/student/reviewList";
	}
	
	@RequestMapping(value = "reviewList/delete")
	public String delete(StudentReview studentReview, RedirectAttributes redirectAttributes) {
		studentReview.setTeacherId(TearcherUserUtils.getUser().getId());
		studentReviewService.delete(studentReview);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + teacherPath + "/examination/reviewList?studentId="+studentReview.getStudentId();
	}
	
	
	
}
