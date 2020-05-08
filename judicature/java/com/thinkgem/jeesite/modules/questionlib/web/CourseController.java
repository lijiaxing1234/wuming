/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.entity.TestDate;
import com.thinkgem.jeesite.modules.questionlib.service.CourseService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseVesionService;
import com.thinkgem.jeesite.modules.questionlib.service.SpecialtyService;
import com.thinkgem.jeesite.modules.resource.entity.ColumnKey;
import com.thinkgem.jeesite.modules.resource.entity.LVCourse;
import com.thinkgem.jeesite.modules.resource.entity.LVCourseImplement;
import com.thinkgem.jeesite.modules.web.entity.CourseInformation;
import com.thinkgem.jeesite.modules.web.entity.UserInfo;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.PropertieUtils;
import com.thinkgem.jeesite.modules.web.util.ReturnData;

/**
 * 课程Controller
 * @author webcat
 * @version 2016-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/course")
public class CourseController extends BaseController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@Autowired
	CourseVesionService  courVesionService;
	
	
	@ModelAttribute
	public Course get(@RequestParam(required=false) String id) {
		Course entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = courseService.get(id);
		}
		if (entity == null){
			entity = new Course();
		}
		return entity;
	}
	
	@RequiresPermissions("questionlib:course:view")
	@RequestMapping(value = {"list", ""})
	public String list(Course course, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(null!=course.getTitle()){
			course.setTitle(course.getTitle());
		}
		if (null != course.getSpecialty()) {
			course.setSpecialtyId(course.getSpecialty().getId());
			course.setSpecialty(specialtyService.get(course.getSpecialty().getId()));
			model.addAttribute("course", course);
		}
		 
		
		Page<Course> page = courseService.findPage(new Page<Course>(request, response), course); 
		model.addAttribute("page", page);
		
		return "modules/questionlib/courseList";
	}

	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("questionlib:course:export")
    @RequestMapping(value = "export")
    public String exportFile(Course course, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			if(null!=course.getTitle()){
				course.setTitle(course.getTitle());
			}
			if (null != course.getSpecialty()) {
				course.setSpecialtyId(course.getSpecialty().getId());
				course.setSpecialty(specialtyService.get(course.getSpecialty().getId()));
			}
            String fileName = "课程数据导出"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Course> page = courseService.findPage(new Page<Course>(request, response,-1), course); 
    		new ExportExcel("课程数据导出", Course.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/questionlib/course";
    }
	
	
	@RequiresPermissions("questionlib:course:view")
	@RequestMapping(value = "form")
	public String form(Course course, Model model) {
		if (StringUtils.isNotBlank(course.getSpecialtyId())) {
			course.setSpecialty(specialtyService.get(course.getSpecialtyId()));
		}
		model.addAttribute("course", course);
		return "modules/questionlib/courseForm";
	}

	@RequiresPermissions("questionlib:course:edit")
	@RequestMapping(value = "save")
	public String save(Course course, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, course)){
			return form(course, model);
		}
		if (null != course.getSpecialty()) {
			course.setSpecialtyId(course.getSpecialty().getId());
		}
		courseService.save(course);
		addMessage(redirectAttributes, "保存课程成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/course";
	}
	
	@RequiresPermissions("questionlib:course:delete")
	@RequestMapping(value = "delete")
	public String delete(Course course, RedirectAttributes redirectAttributes) {
		
		Integer count=courVesionService.countCourseVesionByCourseId(course.getId());
		
		if(count>0){
			
			addMessage(redirectAttributes, "删除课程失败,请先删除课程下的版本后重试！");
			
			return "redirect:"+Global.getAdminPath()+"/questionlib/course";
		}
		
		courseService.delete(course);
		addMessage(redirectAttributes, "删除课程成功！");
		return "redirect:"+Global.getAdminPath()+"/questionlib/course";
	}
	
	
	@RequestMapping(value = "testDate")
	public String testDate(TestDate testDate,int type,Model model,RedirectAttributes redirectAttributes) {
		if (type == 1) {
		TestDate td = courseService.getTestDate();
		model.addAttribute("td", td);
		return "modules/testDate/testDate";
		}else{
			boolean b = courseService.addExamDate(testDate);
			if (b) {
				model.addAttribute("td", testDate);
				addMessage(redirectAttributes, "修改成功!");
			}else {
				model.addAttribute("td", testDate);
				addMessage(redirectAttributes, "修改失败,请重试!");
			}
			return "modules/testDate/testDate";
		}
	}
	
	@RequestMapping("courseInformationList")
	public String courseInformationList(String pageNB,Model model){
		if (pageNB == null || pageNB == "0") {
			pageNB = "1";
		}
		com.thinkgem.jeesite.common.utils.Page<CourseInformation> page = courseService.CourseInformationByPage(pageNB);
		model.addAttribute("page", page);
		return "modules/courseInformation/list";
	}
	
	@RequestMapping("toAddci")
	public String toAddci(CourseInformation courseInformation,Model model){
		model.addAttribute("courseInformation", courseInformation);
		return "modules/courseInformation/form";
	}

	@RequestMapping("courseInformationSave")
	public String courseInformationSave(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
			CourseInformation courseInformation,RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
			String name = "";
		/*	String serverPath = PropertieUtils.getProperty("icon.server");
			String filePath = PropertieUtils.getProperty("icon");*/
		  	String serverPath =request.getRequestURL().toString().replace("a/questionlib/course/courseInformationSave", "courseInformation/");
		   	String filePath = request.getSession().getServletContext().getRealPath("\\courseInformation\\");
			
			SimpleDateFormat sdf = new SimpleDateFormat("HHmmssS");
			Date date = new Date();
					String originalFileName = file.getOriginalFilename();
					File file1 = new File(filePath);
					if (!file1.exists()) {
						file1.mkdir();
					}
					try {
						UUID uuid = UUID.randomUUID();
						String string = uuid.toString();
						String substring = string.substring(0, 5);
						name = sdf.format(date)+substring+originalFileName.substring(originalFileName.lastIndexOf('.'),originalFileName.length());
						String iconUrl = filePath+"/"+name;
						FileOutputStream fileOutputStream = new FileOutputStream(iconUrl);
						fileOutputStream.write(file.getBytes());
						fileOutputStream.flush();
						fileOutputStream.close();
						courseInformation.setIcon(serverPath+"/"+name);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					boolean b = courseService.addci(courseInformation);
					if(b){
						addMessage(redirectAttributes, "添加成功!");
						return "redirect:"+Global.getAdminPath()+"/questionlib/course/courseInformationList";
					}
					addMessage(redirectAttributes, "添加失败!");
					return "redirect:"+Global.getAdminPath()+"/questionlib/course/toAddci"; 
			}
	
	@RequestMapping("delci")
	@ResponseBody
	public void delci(int id,HttpServletResponse response){
		try {
			PrintWriter writer = response.getWriter();
			boolean b = courseService.delci(id);
			if (b) {
				writer.write("true");
			}else {
				writer.write("false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ;
	}
}








