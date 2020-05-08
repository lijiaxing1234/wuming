package com.thinkgem.jeesite.modules.questionlib.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.thinkgem.jeesite.modules.questionlib.entity.ClassCourseListVo;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.service.GCCVTTServie;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/questionlib/gccvtt")
public class GCCVTTController extends BaseController{
	
	@Autowired
	private GCCVTTServie gccvttService;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private SchoolClassService schoolClassService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		String schoolId = UserUtils.getUser().getCompany().getId();
		if (StringUtils.isNotBlank(id)){
			User teacher = systemService.getUser(id);
			teacher.setCompany(new Office(schoolId));
			return teacher;
		}else{
			User teacher = new User();
			teacher.setCompany(new Office(schoolId));
			return teacher;
		}
	}
	
	@RequiresPermissions("questionlib:teacher:view")
	@RequestMapping(value = {"list",""})
	public String list (User teacher,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<User> page = gccvttService.findPage(new Page<User>(request, response), teacher);
		model.addAttribute("page", page);
		return "modules/questionlib/giveClassCourseToT";
	}
	
	
	@RequiresPermissions("questionlib:teacher:view")
	@RequestMapping(value = "classCourseList")
	public String classCourseList(HttpServletRequest request,HttpServletResponse response,Model model,String teacherId){
		logger.info("TeacherId" + teacherId);
		List<ClassCourseListVo> ccListVoList = gccvttService.classCourseList(teacherId);
		model.addAttribute("ccListVoList", ccListVoList);
		model.addAttribute("teacherId", teacherId);
		
		String companyId=UserUtils.getUser().getCompany().getId();
		List<SchoolClass> specTitles=schoolClassService.findSchollClassSpecialtyTitle(null, companyId);
		model.addAttribute("specTitles", specTitles);
		
		
		return "modules/questionlib/teacherClassCoursePage";
	}
	
	@RequiresPermissions("questionlib:teacher:deleteClassCourse")
	@RequestMapping(value = "deleteClassCourse")
	public String deleteClassCourse(HttpServletRequest request,HttpServletResponse response,Model model,String voId,RedirectAttributes redirectAttributes){
		String teacherId = "";
		if(null != voId && !"".equals(voId)){
			ClassCourseListVo vo = gccvttService.getClassCourseListVo(voId);
			teacherId = vo.getTeacherId();
		}
		if(null == voId || "".equals(voId)){
			addMessage(redirectAttributes, "请选择要删除的记录");
			return "redirect:"+Global.getAdminPath()+"/questionlib/gccvtt/classCourseList?teacherId="+teacherId;
		}else{
			gccvttService.deleteClassCourse(voId);
			addMessage(redirectAttributes, "删除数据成功");
		}
		return "redirect:"+Global.getAdminPath()+"/questionlib/gccvtt/classCourseList?teacherId="+teacherId;
	}
	
	
	/**
	 * 给老师分配班级和课程
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @param classId
	 * @param courseIds
	 * @param teacherId
	 * @return
	 */
	@RequiresPermissions("questionlib:teacher:view")
	@RequestMapping(value = "giveCCTT")
	public String giveCCTT(RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response,String classIds,String courseId,String teacherId,String versionId){
		if(null == classIds || "".equals(classIds) || null == courseId || "".equals(courseId)){
			addMessage(redirectAttributes, "请选择班级,课程与版本信息");
			return "redirect:"+Global.getAdminPath()+"/questionlib/gccvtt/classCourseList?teacherId="+teacherId;
		}else{
			gccvttService.insertCCTT(teacherId,classIds,courseId,versionId);
			addMessage(redirectAttributes, "分配班级课程成功");
			return "redirect:"+Global.getAdminPath()+"/questionlib/gccvtt/classCourseList?teacherId="+teacherId;
		}
		
	}
	
	@RequiresPermissions("questionlib:teacher:view")
	@RequestMapping(value = "getTeacherNotClass")
	public void getTeacherNotClass(HttpServletRequest request,HttpServletResponse response,String teacherId){
		List<SchoolClass> teacherNotClassList = gccvttService.getTeacherNotClass(teacherId);
		String strOptions = "";
		for (SchoolClass schoolClass : teacherNotClassList) {
			strOptions += "<option value=\""+schoolClass.getId()+"\">"+schoolClass.getTitle()+"</option>";
		}
		try {
			response.getWriter().write(strOptions);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//得到学校下所有的课程
	@RequiresPermissions("questionlib:teacher:view")
	@RequestMapping(value = "getAllCourse")
	public void getAllCourse(HttpServletResponse response){
		List<Course> courseList = gccvttService.getAllCourse();
		String courseOptions = "";
		courseOptions += "<option value=\"\" selected=\"selected\">请选择课程</option>";
		for (Course course : courseList) {
			courseOptions += "<option value=\""+course.getId()+"\">"+course.getTitle()+"</option>";
		}
		try {
			response.getWriter().write(courseOptions);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//查询学校下没有分配某个课程的班级
	@RequiresPermissions("questionlib:teacher:view")
	@RequestMapping(value = "getNotGiveCourseClass")
	public void getNotGiveCourseClass(HttpServletResponse response,String courseId,String versionId,String teacherId,String spectitles){
		List<SchoolClass> classList = gccvttService.getNotGiveCourseClass(courseId,teacherId,versionId,spectitles);
		String ClassTable = "";
		ClassTable += "<tr><td></td><td>班级名称</td></tr>";
		for (SchoolClass schoolClass : classList) {
			ClassTable += "<tr><td><input name=\"classId\" type=\"checkbox\" value=\""+schoolClass.getId()+"\"/></td><td>"+schoolClass.getTitle()+"</td></tr>";
		}
		if(classList.isEmpty()){
			ClassTable += "<tr><td><input name=\"\" type=\"hidden\" value=\"\"/></td><td><span style=\"color:red;\">无可选班级</span></td></tr>";
		}
		try {
			response.getWriter().write(ClassTable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
