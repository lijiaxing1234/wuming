package com.thinkgem.jeesite.modules.teacher.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.CourseQuestionlibService;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolQuestionlibService;
import com.thinkgem.jeesite.modules.questionlib.service.VersionQuestionService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 题库Controller
 * @author webcat
 * @version 2016-08-16
 */
@Controller("teacherCourseQuestionlibController")
@RequestMapping(value = "${teacherPath}/questionlib")
public class CourseQuestionlibController extends BaseController {

	@Autowired
	private CourseQuestionlibService courseQuestionlibService;
	@Autowired
	SchoolQuestionlibService schollSchoolQuestionlibService;
	
	
	@ModelAttribute
	public CourseQuestionlib get(@RequestParam(required=false) String id) {
		CourseQuestionlib entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = courseQuestionlibService.get(id);
		}
		if (entity == null){
			entity = new CourseQuestionlib();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(CourseQuestionlib courseQuestionlib, HttpServletRequest request, HttpServletResponse response, Model model) {
	     
		 User teacher=TearcherUserUtils.getUser();
		 Map<String,String> map=TearcherUserUtils.getTeacherIdAndCourseVersionId();
		 courseQuestionlib.setUser(teacher);
		 courseQuestionlib.setVersionId(map.get("versionId"));
		 courseQuestionlib.setSchoolId(teacher.getCompany().getId());
	    Page<CourseQuestionlib> page = courseQuestionlibService.findPage(new Page<CourseQuestionlib>(request, response), courseQuestionlib); 
		model.addAttribute("page", page);
		model.addAttribute("teacher", teacher);
		return "teacher/questionlib/courseQuestionlibList";
	}

	
	@ResponseBody
	@RequestMapping(value = "checkTitle")
	public String checkLoginName(String oldTitle, String title) {
		if (title !=null && title.equals(oldTitle)) {
			return "true";
		} else if (title !=null && courseQuestionlibService.getByTitle(title) == null) {
			return "true";
		}
		return "false";
	}
	
	
	
	
	@RequestMapping(value = "form")
	public String form(CourseQuestionlib courseQuestionlib, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("courseQuestionlib", courseQuestionlib);
		return "teacher/questionlib/courseQuestionlibForm";
	}

	@RequestMapping(value = "save")
	public String save(CourseQuestionlib courseQuestionlib,@RequestParam(required=false) String courseVesionId, HttpServletRequest request, HttpServletResponse response, Model model) {
		try{
			 User teacher=TearcherUserUtils.getUser();
			 Map<String,String> map=TearcherUserUtils.getTeacherIdAndCourseVersionId();
			 
			 courseQuestionlib.setUser(teacher);
			 courseQuestionlib.setVersionId(map.get("versionId"));
			 courseQuestionlib.setSchoolId(teacher.getCompany().getId());
			 courseQuestionlib.setOwnerType("3");
			if (!beanValidator(model, courseQuestionlib)){
				return form(courseQuestionlib, request, response, model);
			}
			
			courseQuestionlibService.save(courseQuestionlib);
			

			SchoolQuestionlib schoolQuestionlib=new SchoolQuestionlib();
			schoolQuestionlib.setSchoolId(teacher.getCompany().getId());
			schoolQuestionlib.setQuestionlibId(courseQuestionlib.getId());
		   List<SchoolQuestionlib> list=schollSchoolQuestionlibService.findList(schoolQuestionlib);
		   if(list==null||list.size()==0){
				Calendar now = Calendar.getInstance(); 
				schoolQuestionlib.setValidStartDate(now.getTime());
				now.set(Calendar.YEAR, now.get(Calendar.YEAR)+1000);
				schoolQuestionlib.setValidEndDate(now.getTime());
				schollSchoolQuestionlibService.save(schoolQuestionlib);
		   }
		}catch(Exception ex){
			ex.printStackTrace();
		}
        return "teacher/questionlib/tips";
	}
	
	@RequestMapping(value = "delete")
	public String delete(CourseQuestionlib courseQuestionlib, RedirectAttributes redirectAttributes) {
		
		if(checkIsHaveQuestion(courseQuestionlib)){//表示题库下有试题
			addMessage(redirectAttributes, "删除题库失败,请先删除该题库下的试题后重试.");
		   return "redirect:"+Global.getTeacherPath()+"/questionlib/list?repage";
		}
		
		courseQuestionlibService.delete(courseQuestionlib);
		courseQuestionlibService.deleteSchoolLib(courseQuestionlib);
		addMessage(redirectAttributes, "删除题库成功");
		return "redirect:"+Global.getTeacherPath()+"/questionlib/list?repage";
	}
	
	
	@Autowired
	private VersionQuestionService versionQuestionService;
	
	/**
	 * 检查题库下是否还有试题
	 * @param courseQuestionlib
	 * @return
	 */
	private boolean  checkIsHaveQuestion(CourseQuestionlib courseQuestionlib){
		
		List<VersionQuestion> list = new ArrayList<VersionQuestion>();
		
		VersionQuestion versionQuestion=new VersionQuestion();
		
		versionQuestion.setDelFlag("0");
		
		List<String> quesLibIds=Arrays.asList(courseQuestionlib.getId());
		
		Map<String,String> sqlMap=versionQuestion.getSqlMap();
		
		sqlMap.putAll(TearcherUserUtils.getTeacherIdAndCourseVersionId()); //设置老师id 和version Id;
		
		Map<String,Object> sqlParam=versionQuestion.getSqlParam();
		
		sqlParam.put("questionLibIds",quesLibIds);
		
		list = versionQuestionService.findQuestionList(versionQuestion);
		
		return (list !=null && list.size()>0);
	}
	
	
	
	

}