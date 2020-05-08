package com.thinkgem.jeesite.modules.student.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.student.entity.DateVo;
import com.thinkgem.jeesite.modules.student.entity.ExamCountVo;
import com.thinkgem.jeesite.modules.student.entity.SExam;
import com.thinkgem.jeesite.modules.student.mobile.domain.StudentVo;
import com.thinkgem.jeesite.modules.student.service.SExamService;
import com.thinkgem.jeesite.modules.student.service.StudentBbsService;
import com.thinkgem.jeesite.modules.student.service.StudentExamService;
import com.thinkgem.jeesite.modules.student.utils.OnlineStudentUtils;
import com.thinkgem.jeesite.modules.student.utils.OnlineStudentUtils.Subject;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 用户登录
 * 主页数据加载
 * @author .36
 *
 */
@Controller
//@RequestMapping(value = "${studentPath}")
public class StudentIndexController extends BaseController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SExamService sExamService;
	@Autowired
	private StudentExamService studentExamService;
	@Autowired
	private StudentBbsService bbsService;
	

	
	@RequestMapping("${studentPath}")
	public String home(Model model,String answerFlag,RedirectAttributes redirectAttributes) {
		Student user = StudentUserUtils.getUser();
		model.addAttribute("user", user);
		
		//版本id 
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		if(null == versionId || "".equals(versionId)){
			List<CourseVesion> dictList = (List<CourseVesion>) CacheUtils.get(StudentUserUtils.getUser().getId()+"studentCourseVersionList");
			if(null == dictList){
				String studentId = StudentUserUtils.getUser().getId();
				dictList = studentExamService.getCourseVersionListByStudentId(studentId);
				CacheUtils.put(StudentUserUtils.getUser().getId()+"studentCourseVersionList", dictList);
				CacheUtils.put(StudentUserUtils.getUser().getId()+"versionId", dictList.get(0).getId());
			}
			versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		}
		model.addAttribute("versionId", versionId);
		
		return "student/home";
	}
	
	
	
	//目前查询测试时 不加课程的筛选条件 测试状态为1：已经参加了的考试	2：没有参加的考试：已经错过了		3：没有参加的考试：没有错过	正常的考试   查询结果集按照测试状态码倒序排序
	@SuppressWarnings("unchecked")
	/**
	 * 查询学生还没有参加的考试
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "${studentPath}/index"})
	public String index(HttpServletRequest request, HttpServletResponse response,String message, Model model,String answerFlag,RedirectAttributes redirectAttributes) {
		try {
			//查询两条最新的互问互答
			List<UQuestion> uQuestionList = bbsService.get2LatestQuestionAndAnswers();
			if (uQuestionList!=null&&uQuestionList.size()>0) {
				for (UQuestion uQuestion : uQuestionList) {
				   String s =uQuestion.getQuestionDetail() ;
				   
				   if (s.length()>16) {
					   uQuestion.setQuestionDetail(s.substring(0,16)+"...");
				   }
				   if(StringUtils.isNotBlank(uQuestion.getAnswerDetail()) && uQuestion.getAnswerDetail().length() > 16){
					   uQuestion.setAnswerDetail(uQuestion.getAnswerDetail().substring(0, 16) + "...");
				   }
				}
			}
			model.addAttribute("uQuestionList", uQuestionList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//缓存中加载课程版本
		//版本id 
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		if(null == versionId || "".equals(versionId)){
			List<CourseVesion> dictList = (List<CourseVesion>) CacheUtils.get(StudentUserUtils.getUser().getId()+"studentCourseVersionList");
			if(null == dictList){
				String studentId = StudentUserUtils.getUser().getId();
				dictList = studentExamService.getCourseVersionListByStudentId(studentId);
				CacheUtils.put(StudentUserUtils.getUser().getId()+"studentCourseVersionList", dictList);
				CacheUtils.put(StudentUserUtils.getUser().getId()+"versionId", dictList.get(0).getId());
			}
			versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		}
		//学生主页面数据加载
		//根据学生id 测试类型 examType 版本id查询
		//查询学生应该要参加但是还没有参加的测试(这些考试分为两类，没有错过可以正常参加的，已经错过的不能参加的) 按照测试类型学生id和课程id 查询 类型：1随堂测 2组卷考试 3作业 4例题 5在线考试 
		Student user = StudentUserUtils.getUser();
		model.addAttribute("user", user);
		//版本id 学生id 测试类型 examType
		List<SExam> quizList = sExamService.getNotExamByStudentIdVersionIdExamType(versionId,"1");
		List<SExam> homeWorkList = sExamService.getNotExamByStudentIdVersionIdExamType(versionId,"3");
		List<SExam> examList = sExamService.getNotExamByStudentIdVersionIdExamType(versionId,"5");
		model.addAttribute("quizList",quizList);
		model.addAttribute("quizCount", quizList.size());
		model.addAttribute("examList",examList);
		model.addAttribute("homeWorkList",homeWorkList);
		model.addAttribute("versionId", versionId);
		//新发布的课堂例题的数量 最近一周的课堂例题
		Long exampleQuestionsCount = sExamService.getLatelyExampleQuestionsCount(versionId);
		model.addAttribute("exampleQuestionsCount", exampleQuestionsCount);
		//查询学生未完成的考试 5 课后作业 3  数量
		ExamCountVo countVo = new ExamCountVo();
		int allExamSize = examList.size();
		int z = 0;
		int allHomeWorkSize = homeWorkList.size();
		int x = 0;
		for (SExam sExam : examList) {
			if(sExam.getExamStatus().equals("2")){
				allExamSize--;
			}
		}
		z = allExamSize;
		for(SExam homeWork:homeWorkList){
			if(homeWork.getExamStatus().equals("2")){
				allHomeWorkSize--;
			}
		}
		x = allHomeWorkSize;
		
		countVo.setExamCount(z+"");
		countVo.setHomeworkCount(x+"");
		model.addAttribute("examCountVo", countVo);
		if(StringUtils.isNotBlank(answerFlag) || "0".equals(answerFlag)){
			addMessage(model, "您已经参加过本次考试，请勿重复进入");
		}
		model.addAttribute("message", message);
		
		try {
			List<DateVo> reList = studentService.getStudentPassingRate();
			model.addAttribute("reList", reList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "student/pages/index";
	}

	
	@RequestMapping(value="${studentPath}/login",method=RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("user", new StudentVo());
		//return "student/login";
		return "student/login2";
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value="${studentPath}/login",method=RequestMethod.POST)
	public String loginSubmit(Student student,String loginName, String password,HttpServletRequest request, HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		model.addAttribute("user", new StudentVo());
		Student s = new Student();
		s.setStdCode(loginName);
		String schoolId = student.getSchoolId();
		if(StringUtils.isBlank(schoolId)){
			//addMessage(model, "请选择学校");
			addMessage(redirectAttributes, "请选择学校");
			return "redirect:"+studentPath+"/login";
		}
		s.setSchoolId(schoolId);
		
		if(OnlineStudentUtils.getIntance().contains(new OnlineStudentUtils.Subject(s,schoolId))){
			addMessage(model, "该账户已在它处登录.");
		}else{
			
			Student loginUser = studentService.getStudentByStudentCode(s);
			if (null != loginUser) {
				//验证班级时间
				Date now = new Date();
				if(loginUser.getEndDate().getTime() < now.getTime()){
					//addMessage(model, "班级已毕业");
					addMessage(redirectAttributes, "班级已毕业");
				}else{
					
					if (systemService.validatePassword(password,loginUser.getStdPassword())) {// 登录成功设置换成和cookie
						
						HttpSession session = request.getSession(); 
						session.setAttribute(OnlineStudentUtils.SESSION_STUDENT_LOGIN_KEY,  new OnlineStudentUtils.Subject(loginUser,schoolId));
						StudentUserUtils.putCache(loginUser);
						return "redirect:"+studentPath+"/changeCourse";
					}else{
						//addMessage(model, "密码错误");
						addMessage(redirectAttributes, "密码错误");
					}
				}
			}else{
				//addMessage(model, "用户不存在");
				addMessage(redirectAttributes, "用户不存在");
			}
			
		}
		//return "student/login";
		return "redirect:"+studentPath+"/login";
	}
	
	
	@RequestMapping(value="${studentPath}/loginOut")
	public String loginOut(HttpServletResponse response,HttpServletRequest request) {
		Student user=StudentUserUtils.getUser();
		
		CacheUtils.remove(StudentUserUtils.getUser().getId() + "");
		CacheUtils.remove(StudentUserUtils.getUser().getId() + "versionId");
		CacheUtils.remove(StudentUserUtils.getUser().getId()+"studentCourseVersionList");
		
		//CookieUtils.getCookie(request,response,StudentUserUtils.USER_COOKIE_USER_ID,true);
		//CookieUtils.getCookie(request,response,StudentUserUtils.USER_COOKIE_USER_PASSWORD,true);
		
		StudentUserUtils.removeCache(user);
		
		HttpSession session= request.getSession(false);
		Subject obj =session==null? null :(Subject)session.getAttribute(OnlineStudentUtils.SESSION_STUDENT_LOGIN_KEY);
		if(obj !=null){
			session.invalidate();
		}
		
		return "redirect:"+studentPath+"/login";
	}
	
	@RequestMapping("${studentPath}/getCourseListByStudentId")
	public void getCourseListByStudentId (HttpServletResponse response,HttpServletRequest request){
		String studentId = StudentUserUtils.getUser().getId();
		//根据学生id 查询该学生所学课程
		List<Course> reList = studentService.getCourseListByStudentId(studentId);
		String optionStr = "";
		optionStr += "<form:option value=\"\" label=\"\"/>";
		if(null == reList){
			try {
				response.getWriter().write(optionStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			for (Course course : reList) {
				optionStr += "<option value=\""+course.getId()+"\">"+course.getTitle()+"</option>";
			}
			try {
				response.getWriter().write(optionStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("${studentPath}/changeCacheCourseVersion")
	public String changeCacheCourseVersion(String courseId){
		String studentId = StudentUserUtils.getUser().getId();
		List<CourseVesion> courseVersionList = studentExamService.getCourseVersionListByStudentId(studentId);
		CacheUtils.put(StudentUserUtils.getUser().getId()+"studentCourseVersionList", courseVersionList);
		for (CourseVesion courseVesion : courseVersionList) {
			if(courseVesion.getCourse().getId().equals(courseId)){
				CacheUtils.put(StudentUserUtils.getUser().getId()+"courseVersion", courseVesion);
				CacheUtils.put(StudentUserUtils.getUser().getId()+"versionId", courseVesion.getId());
			}
		}
		return "redirect:"+studentPath;
	}
	
	@RequestMapping("${studentPath}/changeCourse")
	public String changeCourse(Model model,HttpServletRequest request,HttpServletResponse response){
		//根据学生id获取该学生所学课程有几个考试 5，几个作业 3[{"courseTitle":"物理","examCount":"3","homeworkCount":"5"}{}]
		List<Map<String, String>> list = studentService.getExamCountByCourseType();
		for (Map<String, String> map : list) {
			String string = map.get("versionName");
			String titleVersionName = map.get("versionName");
			map.put("titleVersionName", titleVersionName);
			if(string.length() >= 10){
				string = string.substring(0, 12) + "...";
				map.put("versionName", string);
			}
		}
		model.addAttribute("examCountList", list);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/chooseCourse";
	}
	
	@RequestMapping("${studentPath}/updatePasswordPage")
	public String updatePasswordPage(Model model,HttpServletRequest request,HttpServletResponse response){
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/updatePassword";
	}
	
	
	@ResponseBody
	@RequestMapping("${studentPath}/updatePassword")
	public String updatePassword(String oldPassword, String newPassword,Model model){
		String message = "";
		Student student = StudentUserUtils.getUser();
		if(StringUtils.isNotBlank(newPassword) && StringUtils.isNotBlank(oldPassword)){
			if(SystemService.validatePassword(oldPassword, student.getStdPassword())){
				studentService.updatePasswordById(student.getId(),SystemService.entryptPassword(newPassword));
				message = "修改密码成功，请重新登录";
			}else{
				message = "旧密码错误，修改密码失败";
			}
		}
		return message;
	}
	
	
}