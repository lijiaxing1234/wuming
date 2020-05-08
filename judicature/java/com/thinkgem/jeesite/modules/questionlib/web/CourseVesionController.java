/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.common.QuestionlibTld;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.service.CourseKnowledgeService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseVesionService;
import com.thinkgem.jeesite.modules.questionlib.service.SpecialtyService;

/**
 * 课程版本Controller
 * 
 * @author webcat
 * @version 2016-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/courseVesion")
public class CourseVesionController extends BaseController {

	@Autowired
	private CourseVesionService courseVesionService;
	@Autowired
	private SpecialtyService specialtyService;
	@Autowired
	private CourseService courseService;
	
	@Autowired
	CourseKnowledgeService courseKnowService;
	
	@ModelAttribute
	public CourseVesion get(@RequestParam(required = false) String id) {
		CourseVesion entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = courseVesionService.get(id);
		}
		if (entity == null) {
			entity = new CourseVesion();
		}

		return entity;
	}

	@RequiresPermissions("questionlib:courseVesion:view")
	@RequestMapping(value = { "list", "" })
	public String list(CourseVesion courseVesion, @RequestParam(required=false)  String courseId,@RequestParam(required=false)  String specialtyId,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<CourseVesion> page = new Page<CourseVesion>(request, response);
	 
//		if(StringUtils.isNotBlank(courseId)){
//			courseVesion.setCourseId(courseId);
//			 
//		}else if(StringUtils.isNotBlank(specialtyId)){
//			if(new Specialty(specialtyId)!=null){
//				courseVesion.setSpecialty(new Specialty(specialtyId));
//				courseVesion.setPage(page);
//				List<CourseVesion> list = courseVesionService.findListBySpecialtyId(courseVesion);
//				page = page.setList(list);
//			}
//		} 
		courseVesion.setPage(page);
		page = courseVesionService.findPage(page, courseVesion);
		
		 model.addAttribute("page", page);
		 model.addAttribute("specialtyId", specialtyId);
		 model.addAttribute("courseId", courseId);
		return "modules/questionlib/courseVesionList";
	}

	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("questionlib:courseVesion:export")
    @RequestMapping(value = "export")
    public String exportFile(CourseVesion courseVesion, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "课程数据导出"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CourseVesion> page = new Page<CourseVesion>(request, response);
            courseVesion.setPage(page);
    		page = courseVesionService.findPage(page, courseVesion);
           /* Page<CourseVesion> page = courseVesionService.findPage(new Page<CourseVesion>(request, response,-1), new CourseVesion()); */
    		new ExportExcel("课程数据导出", CourseVesion.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/questionlib/courseVesion";
    }
	
	@RequiresPermissions("questionlib:courseVesion:view")
	@RequestMapping(value = "form")
	public String form(CourseVesion courseVesion, Model model) {
		model.addAttribute("courseVesion", courseVesion);
		String specialtyId = "";
		String courseId = "";
		try {
			Course c = QuestionlibTld.getCourseByID(courseVesion.getCourseId());
			specialtyId = c.getSpecialtyId();
			courseId = c.getId();
			model.addAttribute("course", c);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (StringUtils.isNotBlank(specialtyId)) {
			courseVesion.setSpecialty(specialtyService.get(specialtyId));
		}
		
		model.addAttribute("specialtyId", specialtyId);
		model.addAttribute("courseId", courseId);
		return "modules/questionlib/courseVesionForm";
	}

	@RequiresPermissions("questionlib:courseVesion:edit")
	@RequestMapping(value = "save")
	public String save(CourseVesion courseVesion, String courseId,
			Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (!beanValidator(model, courseVesion)) {
			return form(courseVesion, model);
		}
		
		String specialtyId = "";
		if (!StringUtils.isBlank(courseId)) {
			specialtyId = QuestionlibTld.getCourseByID(courseId).getSpecialtyId();
		} else {
			// 默认设置一个
			List<Course> courseList = QuestionlibTld.getCourse();
			if (courseList != null && courseList.size() > 0) {
				courseId = courseList.get(0).getId();
				specialtyId = courseList.get(0).getSpecialtyId();

			}

		}

		courseVesion.setCourseId(courseId);
		courseVesionService.save(courseVesion);
		addMessage(redirectAttributes, "保存课程版本成功");

		model.addAttribute("specialtyId", specialtyId);
		model.addAttribute("courseId", courseId);
		return "redirect:" + Global.getAdminPath()
				+ "/questionlib/courseVesion/?repage";
	}

	@RequiresPermissions("questionlib:courseVesion:delete")
	@RequestMapping(value = "delete")
	public String delete(CourseVesion courseVesion,
			RedirectAttributes redirectAttributes) {
		Integer count = null;
		if(courseVesion!=null){
			count=courseKnowService.countCourseKnowledgeByCourseVesionId(courseVesion.getId());
		}
		if(count>0){
			
			addMessage(redirectAttributes, "删除课程版本失败,请先删除版本下的知识点后重试！");
			return "redirect:" + Global.getAdminPath()
					+ "/questionlib/courseVesion/?repage";
		}
		courseVesionService.delete(courseVesion);
		addMessage(redirectAttributes, "删除课程版本成功！");
		return "redirect:" + Global.getAdminPath()
				+ "/questionlib/courseVesion/?repage";
	}

	
	
}