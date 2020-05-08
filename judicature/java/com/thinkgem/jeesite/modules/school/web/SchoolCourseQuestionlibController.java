package com.thinkgem.jeesite.modules.school.web;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.common.QuestionlibTld;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.CourseQuestionlibService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseVesionService;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolQuestionlibService;
import com.thinkgem.jeesite.modules.questionlib.service.SpecialtyService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 题库Controller
 * @author webcat
 * @version 2016-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/school/schoolcourseQuestionlib")
public class SchoolCourseQuestionlibController extends BaseController {

	@Autowired
	private CourseQuestionlibService courseQuestionlibService;
	
	@Autowired
	private CourseVesionService courseVesionService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@Autowired
	private SchoolQuestionlibService schollSchoolQuestionlibService;
	
	@ModelAttribute
	public CourseQuestionlib get(@RequestParam(required=false) String id) {
		CourseQuestionlib entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = courseQuestionlibService.get(id);
		}
		if (entity == null){
			entity = new CourseQuestionlib();
		}
		
		if (StringUtils.isNotBlank(entity.getVersionId())) {
			CourseVesion v = courseVesionService.get(entity.getVersionId());
			Course c = courseService.get(v.getCourseId());
			
			if (c!=null) {
				entity.setCourse(c);
				entity.setSpecialty(specialtyService.get(c.getSpecialtyId()));
			}
		
			
			entity.setCourseVesion(v);
		}
		
		
		return entity;
	}
	
	/**
	 * 已授权题库页面
	 * @param schoolQuestionlib
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("authorizedQuestionlibList")
	public String  authorizedList(SchoolQuestionlib schoolQuestionlib,HttpServletRequest request,HttpServletResponse response,Model model){
		schoolQuestionlib.setSchoolId(UserUtils.getUser().getCompany().getId());
		Page<SchoolQuestionlib> page=schollSchoolQuestionlibService.findAuthorizedPage(new Page<SchoolQuestionlib>(request,response), schoolQuestionlib);
		model.addAttribute("page", page);
		model.addAttribute("schoolQuestionlib", schoolQuestionlib);
		return "modules/school/authorizedQuestionlibList";
	}
	
	
	
	
	
	
	@RequiresPermissions("school:courseQuestionlib:view")
	@RequestMapping(value = {"list", ""})
	public String list(CourseQuestionlib courseQuestionlib,@RequestParam(required=false) String courseVesionId,@RequestParam(required=false) String courseId,@RequestParam(required=false) String specialtyId, @RequestParam(required=false) String ownerType, @RequestParam(required=false) String schoolId, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CourseQuestionlib> page = null;
		 
	    courseQuestionlib.setSchoolId(UserUtils.getUser().getCompany().getId());
	 
		if(StringUtils.isNotBlank(ownerType) && StringUtils.isBlank(courseQuestionlib.getOwnerType())){
			courseQuestionlib.setOwnerType(ownerType);
		}
		if(courseQuestionlib != null && StringUtils.isNotBlank(courseQuestionlib.getVersionId())){
			courseVesionId = courseQuestionlib.getVersionId();
		}
		if (StringUtils.isNotBlank(courseVesionId)) {
			courseId = QuestionlibTld.getCourseVersionByVersionId(courseVesionId).getCourseId();
			specialtyId  =QuestionlibTld.getCourseByID(courseId).getSpecialtyId();
			if(courseQuestionlib==null || StringUtils.isBlank(courseQuestionlib.getVersionId())){
				courseQuestionlib.setVersionId(courseVesionId);
			}
			page = courseQuestionlibService.findPage(new Page<CourseQuestionlib>(request, response), courseQuestionlib); 
			model.addAttribute("specialtyId", specialtyId);
			model.addAttribute("courseId", courseId);
			model.addAttribute("courseVesionId", courseVesionId);
		}else{
			if(StringUtils.isNotBlank(courseId)){
				courseQuestionlib.setCourse(new Course(courseId));
				model.addAttribute("specialtyId", specialtyId);
				model.addAttribute("courseId", courseId);
			}else if(StringUtils.isBlank(courseId) && StringUtils.isNotBlank(specialtyId)){
				courseQuestionlib.setSpecialty(new Specialty(specialtyId));
				model.addAttribute("specialtyId", specialtyId);
			}else{
			}
			page = new Page<CourseQuestionlib>(request, response);
			courseQuestionlib.setPage(page);
			List<CourseQuestionlib> list = courseQuestionlibService.findList(courseQuestionlib);
			page = page.setList(list);
		}
		model.addAttribute("schoolId", schoolId);
		model.addAttribute("ownerType", ownerType);
		model.addAttribute("page", page);
		return "modules/school/courseQuestionlibList";
	}

	@RequiresPermissions("school:courseQuestionlib:view")
	@RequestMapping(value = "form")
	public String form(CourseQuestionlib courseQuestionlib,@RequestParam(required=false) String courseVesionId,@RequestParam(required=false) String courseId,@RequestParam(required=false) String specialtyId, @RequestParam(required=false) String ownerType, @RequestParam(required=false) String schoolId, HttpServletRequest request, HttpServletResponse response, Model model) {
		 
		if(StringUtils.isNotBlank(courseVesionId)){
			try {
				courseId = QuestionlibTld.getCourseVersionByVersionId(courseVesionId).getCourseId();
				specialtyId  =QuestionlibTld.getCourseByID(courseId).getSpecialtyId();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}else if(courseQuestionlib!=null && StringUtils.isNotBlank(courseQuestionlib.getVersionId())){
			try {
				courseVesionId = courseQuestionlib.getVersionId();
				courseId = QuestionlibTld.getCourseVersionByVersionId(courseVesionId).getCourseId();
				specialtyId  =QuestionlibTld.getCourseByID(courseId).getSpecialtyId();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}else{
			if(StringUtils.isBlank(specialtyId)){
				specialtyId = null;
			}
			if(StringUtils.isBlank(courseId)){
				courseId = null;
			}
			if(StringUtils.isBlank(ownerType)){
				ownerType = null;
			}
		}
		
		model.addAttribute("courseVesionId", courseVesionId);
		model.addAttribute("courseId", courseId);
		model.addAttribute("specialtyId", specialtyId);
		model.addAttribute("ownerType", ownerType);
		model.addAttribute("schoolId", schoolId);
		model.addAttribute("courseQuestionlib", courseQuestionlib);
		return "modules/school/courseQuestionlibForm";
	}
	
	@RequiresPermissions("school:courseQuestionlib:edit")
	@RequestMapping(value = "save")
	public String save(CourseQuestionlib courseQuestionlib,@RequestParam(required=false) String courseVesionId, @RequestParam(required=false) String schoolId, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
	 
		
	
		courseQuestionlib.setSchoolId( UserUtils.getUser().getCompany().getId());
		courseQuestionlib.setOwnerType("2");//所有者为学校
		courseQuestionlibService.save(courseQuestionlib);
		
		 
		SchoolQuestionlib schoolQuestionlib=new SchoolQuestionlib();
		schoolQuestionlib.setSchoolId(UserUtils.getUser().getCompany().getId());
		schoolQuestionlib.setQuestionlibId(courseQuestionlib.getId());
		
	   List<SchoolQuestionlib> list=schollSchoolQuestionlibService.findList(schoolQuestionlib);
	   if(list==null||list.size()==0){
		   
			Calendar now = Calendar.getInstance(); 
			schoolQuestionlib.setValidStartDate(now.getTime());
			now.set(Calendar.YEAR, now.get(Calendar.YEAR)+1000);
			schoolQuestionlib.setValidEndDate(now.getTime());
			
			schollSchoolQuestionlibService.save(schoolQuestionlib);
			
	   }
	   
	/*	if(StringUtils.isBlank(courseQuestionlib.getId()))
		{
		
		   //给自己授权
		    SchoolQuestionlib schoolQuestionlib=new SchoolQuestionlib();
			schoolQuestionlib.setSchoolId(UserUtils.getUser().getCompany().getId());
			schoolQuestionlib.setQuestionlibId(courseQuestionlib.getId());
			Calendar now = Calendar.getInstance(); 
			schoolQuestionlib.setValidStartDate(now.getTime());
			now.set(Calendar.YEAR, now.get(Calendar.YEAR)+1000);
			schoolQuestionlib.setValidEndDate(now.getTime());
			schollSchoolQuestionlibService.save(schoolQuestionlib);
		   
		}*/
		addMessage(redirectAttributes, "保存题库成功");
		return "redirect:"+Global.getAdminPath()+"/school/schoolcourseQuestionlib/list";
	}
	
	@RequiresPermissions("school.web:courseQuestionlib:delete")
	@RequestMapping(value = "delete")
	public String delete(CourseQuestionlib courseQuestionlib, RedirectAttributes redirectAttributes) {
		courseQuestionlibService.delete(courseQuestionlib);
		courseQuestionlibService.deleteSchoolLib(courseQuestionlib);
		addMessage(redirectAttributes, "删除题库成功");
		return "redirect:"+Global.getAdminPath()+"/school/schoolcourseQuestionlib/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "getCourseBySpecialtyId")
	public List<Map<String, Object>> getCourseBySpecialtyId(@RequestParam(required=false) String id, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Course> list = QuestionlibTld.getCourseBySpecialtyId(id);
		/*Map<String,Object> mapFirst = Maps.newHashMap();
		mapFirst.put("id", "");
		mapFirst.put("value", "全部");
		mapList.add(mapFirst);*/
		for (int i=0; i<list.size(); i++){
			Course e = list.get(i);
			{
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("value", e.getTitle());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "getCourseQuestionByCouresId")
	public List<Map<String, Object>> getCourseQuestionByCouresId(@RequestParam(required=false) String id, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CourseVesion> list = QuestionlibTld.getCourseVersionByCourseId(id );
//		Map<String,Object> mapFirst = Maps.newHashMap();
//		mapFirst.put("id", "");
//		mapFirst.put("value", "请选择");
//		mapList.add(mapFirst);
		for (int i=0; i<list.size(); i++){
			CourseVesion e = list.get(i);
			 {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("value", e.getTitle());
				mapList.add(map);
			}
		}
		return mapList;
	}

}