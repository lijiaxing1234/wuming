package com.thinkgem.jeesite.modules.questionlib.web;

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
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.CourseQuestionlibService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseVesionService;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolQuestionlibService;
import com.thinkgem.jeesite.modules.questionlib.service.SpecialtyService;
import com.thinkgem.jeesite.modules.questionlib.service.VersionQuestionService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 题库Controller
 * @author webcat
 * @version 2016-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/courseQuestionlib")
public class CourseQuestionlibController extends BaseController {

	@Autowired
	private CourseQuestionlibService courseQuestionlibService;
	
	@Autowired
	private CourseVesionService courseVesionService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private SpecialtyService specialtyService;
	@Autowired
	private VersionQuestionService versionQuestionService;
	

	@Autowired
	private OfficeService officeService;
	
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
	
	@RequiresPermissions("questionlib:courseQuestionlib:view")
	@RequestMapping(value = {"list", ""})
	public String list(CourseQuestionlib courseQuestionlib,@RequestParam(required=false) String ownerType, @RequestParam(required=false) String schoolId, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CourseQuestionlib> page = null;
		
		String  specialtyId =request.getParameter("specialty.id");
		 if(StringUtils.isNotBlank(specialtyId))
		 {
			 courseQuestionlib.setSpecialtyId(specialtyId);
			 courseQuestionlib.setSpecialty(specialtyService.get(courseQuestionlib.getSpecialtyId()));
		 }
		 try {
			 courseQuestionlib.setCourseId(courseQuestionlib.getCourseId().replace("请选择",""));
			 courseQuestionlib.setVersionId(courseQuestionlib.getVersionId().replace("请选择",""));
			 courseQuestionlib.setSpecialtyId(courseQuestionlib.getSpecialtyId().replace("请选择",""));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(StringUtils.isNotBlank(schoolId)){
			courseQuestionlib.setSchoolId(schoolId);
		}else{
			schoolId = UserUtils.getUser().getCompany().getId();
			courseQuestionlib.setSchoolId(schoolId);
		}
		if(StringUtils.isNotBlank(ownerType) && StringUtils.isBlank(courseQuestionlib.getOwnerType())){
			courseQuestionlib.setOwnerType(ownerType);
		}else{
			courseQuestionlib.setOwnerType("1");
		}
	    
		
		page = courseQuestionlibService.findPage(new Page<CourseQuestionlib>(request, response), courseQuestionlib); 
		model.addAttribute("schoolId", schoolId);
	    model.addAttribute("office", officeService.get(schoolId));
		model.addAttribute("ownerType", ownerType);
		model.addAttribute("page", page);
		model.addAttribute("courseQuestionlib", courseQuestionlib);
		
		model.addAttribute("specialtyId", courseQuestionlib.getSpecialtyId());
		 
		
		
		return "modules/questionlib/courseQuestionlibList";
	}

	@RequiresPermissions("questionlib:courseQuestionlib:view")
	@RequestMapping(value = "form")
	public String form(CourseQuestionlib courseQuestionlib, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(StringUtils.isNoneBlank(courseQuestionlib.getId())){
			CourseVesion v = courseVesionService.get(courseQuestionlib.getVersionId());
			Course c = courseService.get(v.getCourseId());
			courseQuestionlib.setCourse(c);
			courseQuestionlib.setSpecialty(specialtyService.get(c.getSpecialtyId()));
			courseQuestionlib.setCourseVesion(v);
		}
		model.addAttribute("courseQuestionlib", courseQuestionlib);
		return "modules/questionlib/courseQuestionlibForm";
	}
	
	@RequiresPermissions("questionlib:courseQuestionlib:edit")
	@RequestMapping(value = "save")
	public String save(CourseQuestionlib courseQuestionlib, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		CourseVesion courseVesion =courseVesionService.get(courseQuestionlib.getVersionId());
		if (courseVesion!=null) {
			
			
			CourseQuestionlib ucq =new CourseQuestionlib();
			ucq.setTitle(courseQuestionlib.getTitle());
			ucq.setSchoolId(UserUtils.getUser().getCompany().getId());
//			ucq.setCourseId(courseQuestionlib.getCourseId());
//			ucq.setSpecialtyId(courseQuestionlib.getSpecialtyId());
//			ucq.setVersionId(courseQuestionlib.getVersionId());
			
			model.addAttribute("courseQuestionlib",courseQuestionlib);
			 
			if(StringUtils.isBlank(courseQuestionlib.getId()))
			{
				//判断是否有重复的名称
				List<CourseQuestionlib> List= courseQuestionlibService.getByTitle(ucq);
				if(List!=null&&List.size()>0)
				{
					addMessage(model, "题库名称重复，请更换名称");
					
					return  form(  courseQuestionlib,   request,   response,   model) ;
				}
				
			}else {
				CourseQuestionlib	courseQuestionlibOld=	courseQuestionlibService.get(courseQuestionlib);
				if (!courseQuestionlibOld.getTitle().equals(courseQuestionlib.getTitle())) {  //名字改变了，查看是否与已有的名字重复
						 
					List<CourseQuestionlib> List= courseQuestionlibService.getByTitle(ucq);
					if(List!=null&&List.size()>0)
					{
						addMessage(model, "题库名称重复，请更换名称");
						
						return  form(  courseQuestionlib,   request,   response,   model) ;
					}
	                 
				}
			}
			
			
			courseQuestionlib.setCourseVesion(courseVesion);
			if(StringUtils.isBlank(courseQuestionlib.getSchoolId())){
			   courseQuestionlib.setSchoolId(UserUtils.getUser().getCompany().getId());
			}
			courseQuestionlib.setUser(UserUtils.getUser());
			courseQuestionlib.setVersionId(courseVesion.getId());
			
			if(StringUtils.isBlank(courseQuestionlib.getOwnerType())){
				courseQuestionlib.setOwnerType("1");	//平台的题库为1，学校的为2，教师的 为3，添加时
			}
			
			courseQuestionlibService.save(courseQuestionlib);
			
			addMessage(redirectAttributes, "保存题库成功");
		}else {
			addMessage(redirectAttributes, "保存题库失败");
		}
		
		/*return "redirect:"+Global.getAdminPath()+"/questionlib/courseQuestionlib/list?repage&courseVesionId="+courseVesionId;*/
		return "redirect:"+Global.getAdminPath()+"/questionlib/courseQuestionlib/list?repage&courseVesionId=";
	}
	
	@RequiresPermissions("questionlib:courseQuestionlib:delete")
	@RequestMapping(value = "delete")
	public String delete(CourseQuestionlib courseQuestionlib, RedirectAttributes redirectAttributes) {
		
		//找找题库下是否有试题
		
		VersionQuestion versionQuestion  =new VersionQuestion();
		versionQuestion.setQuestionlibId(courseQuestionlib.getId());
		int count = versionQuestionService.findCount(versionQuestion);
	 
		 if (count!=0) {
			 addMessage(redirectAttributes, "删除题库失败！此题库下存在试题，请先删除试题！");
		}else {
			courseQuestionlibService.delete(courseQuestionlib);
			courseQuestionlibService.deleteSchoolLib(courseQuestionlib);
			
			addMessage(redirectAttributes, "删除题库成功");
		}
		 
		 
		
		return "redirect:"+Global.getAdminPath()+"/questionlib/courseQuestionlib/list";
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