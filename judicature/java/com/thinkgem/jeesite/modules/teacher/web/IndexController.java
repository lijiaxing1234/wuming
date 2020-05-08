package com.thinkgem.jeesite.modules.teacher.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.TableMessage;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.CourseVesionService;
import com.thinkgem.jeesite.modules.questionlib.service.TableMessageService;
import com.thinkgem.jeesite.modules.student.service.StudentBbsService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.teacher.dto.ClassVesionCourseDTO;
import com.thinkgem.jeesite.modules.teacher.dto.LoginDTO;
import com.thinkgem.jeesite.modules.teacher.entity.QuestionslibSplit;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherKnowledge;
import com.thinkgem.jeesite.modules.teacher.service.KnowledgeService;
import com.thinkgem.jeesite.modules.teacher.service.QuestionslibSplitService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

@Controller
public class IndexController extends BaseController {

	@Autowired
	SystemService systemService;
	@Autowired
	CourseVesionService  courseVesionService;
	@Autowired
	private TableMessageService tableMessageService;
	@Autowired
	QuestionslibSplitService queslibSplitService;
	@Autowired
	KnowledgeService knowledgeService;
	@Autowired
	private StudentBbsService bbsService;
	

	@RequestMapping("${teacherPath}")
	
	
	public String home(Model model) {
		
		
		
		List<ClassVesionCourseDTO> list= TearcherUserUtils.getTeacherCourseVesion();
		model.addAttribute("list", list);
		
		return "teacher/home";
	}
    
	@RequestMapping("${teacherPath}/index")
	public String  index(Model model){
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		User teacher=TearcherUserUtils.getUser();
		TableMessage tableMessage =new TableMessage();
		tableMessage.setOffice(teacher.getCompany());
		
		tableMessage.setCreatetime(new Date());
		
	   Page<TableMessage> page=tableMessageService.findPage(new Page<TableMessage>(1,10), tableMessage);
		model.addAttribute("messagePage", page);
		
		model.addAttribute("teacher", teacher);
		LoginDTO teacherLogin = tableMessageService.getTeacherLogin(userId, versionId);
		model.addAttribute("teacherLogin", teacherLogin);
		try {
			List<TeacherKnowledge> sourcelist =  knowledgeService.getKnowledgesPassingRate(new TeacherKnowledge());
			for (TeacherKnowledge teacherKnowledge : sourcelist) {
				if(teacherKnowledge != null && teacherKnowledge.getKnowledgeTitle().length() >= 6){
					teacherKnowledge.setKnowledgeTitle(teacherKnowledge.getKnowledgeTitle().substring(0, 6) + "...");
				}
			}
			
			model.addAttribute("sourcelist", sourcelist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "teacher/index";
	}
	
	
	
	@RequestMapping(value="${teacherPath}/login",method=RequestMethod.GET)
	public String login() {
		//return "teacher/login";
		return "teacher/login2";
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value="${teacherPath}/login",method=RequestMethod.POST)
	public String loginSubmit(String loginName, String password,
			HttpServletRequest request, HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		
		User loginUser = systemService.getUserByLoginName(loginName);
		if (null != loginUser) { //老师下必须包含版本
			if (systemService.validatePassword(password,loginUser.getPassword())) {// 登录成功设置换成和cookie
				List<ClassVesionCourseDTO> list=courseVesionService.getCourseVesionDaoByUserId(loginUser.getId());
				if(null !=list && list.size()>0){
					TearcherUserUtils.putCache(loginUser);
					CookieUtils.setCookie(response,TearcherUserUtils.USER_COOKIE_USER_ID,loginUser.getId(), "/");
					CookieUtils.setCookie(response,TearcherUserUtils.USER_COOKIE_USER_PASSWORD,loginUser.getPassword(), "/");
					//List<ClassVesionCourseDTO> list=courseVesionService.getCourseVesionDaoByUserId(loginUser.getId()); //"ef9527ccdd3b4fad9eae8717d0d440b2"
					ClassVesionCourseDTO classVesionCourse = list.get(0);
					TearcherUserUtils.setCourseVesion(classVesionCourse.getCourseVesion(),loginUser);
					return "redirect:"+teacherPath;
				}else{
					 addMessage(redirectAttributes, "当前老师没有分配教授课程!");
				}
			}else{
				 addMessage(redirectAttributes, "密码不正确！");
			}
		}else{
		    addMessage(redirectAttributes, "用户不存在!");
		}
		redirectAttributes.addFlashAttribute("loginName", loginName);
		return "redirect:"+teacherPath+"/login";
	}
	
	
	@RequestMapping(value="${teacherPath}/loginOut")
	public String loginOut(HttpServletResponse response,HttpServletRequest request) {
		User user=TearcherUserUtils.getUser();
		TearcherUserUtils.removeCacheCourseVesion();
		CookieUtils.getCookie(request,response,TearcherUserUtils.USER_COOKIE_USER_ID,true);
		CookieUtils.getCookie(request,response,TearcherUserUtils.USER_COOKIE_USER_PASSWORD,true);
		TearcherUserUtils.removeCache(user);
		//CookieUtils.getCookie(request,response,TearcherUserUtils.USER_COURSE_COOKIE_ID,true);
		
		// 清除当前用户缓存
		if(user!=null){
	     UserUtils.clearCache(user);
		}
		
		return "redirect:"+teacherPath+"/login";
	}
	
	
	@RequestMapping("${teacherPath}/header")
	public String header(Model model){
		 User user=TearcherUserUtils.getUser();
		 model.addAttribute("user", user);
		 model.addAttribute("courseVesion", TearcherUserUtils.getCourseVesion());
		return "teacher/include/header";
	}
	
	
	
	@RequestMapping("${teacherPath}/changeTeacherCourseId")
	public String changeTeacherCourseId(CourseVesion courseVesion,HttpServletRequest request, HttpServletResponse response){
	   //CookieUtils.setCookie(response,TearcherUserUtils.USER_COURSE_COOKIE_ID,courseId, "/");
		String backUrl=request.getParameter("currentUrl");
		TearcherUserUtils.setCourseVesion(courseVesion,TearcherUserUtils.getUser());
		/*if(StringUtils.isNoneBlank(backUrl)){
			return "redirect:"+backUrl;
		}*/
		return "redirect:"+teacherPath;
	}
	
	@RequestMapping("${teacherPath}/updatePasswordPage")
	public String updatePasswordPage(Model model){
		model.addAttribute("user", TearcherUserUtils.getUser());
		return "teacher/updatePassword";
	}
	
	@ResponseBody
	@RequestMapping(value = "${teacherPath}/updatePassword")
	public String updatePassword(String oldPassword, String newPassword, Model model,RedirectAttributes redirectAttributes) {
		String message = "";
		User user = TearcherUserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				message = "密码修改成功，请重新登录";
			}else{
				message = "旧密码错误，密码修改失败！";
			}
		}
		return message;
	}
	
}
