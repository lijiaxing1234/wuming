/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

import java.io.IOException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.SCourseVersion;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.service.SCourseVersionService;

/**
 * 课程版本Controller
 * @author webcat
 * @version 2016-09-16
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/sCourseVersion")
public class SCourseVersionController extends BaseController {

	@Autowired
	private SCourseVersionService sCourseVersionService;
	
	@ModelAttribute
	public SCourseVersion get(@RequestParam(required=false) String id) {
		SCourseVersion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sCourseVersionService.get(id);
		}
		if (entity == null){
			entity = new SCourseVersion();
		}
		return entity;
	}
	
	/**
	 * 根据学校id查询这个学校已经选择了的课程版本
	 * @param sCourseVersion
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("questionlib:sCourseVersion:view")
	@RequestMapping(value = {"list", ""})
	public String list(SCourseVersion sCourseVersion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SCourseVersion> page = sCourseVersionService.getSchoolCourseVersionPage(new Page<SCourseVersion>(request, response),sCourseVersion);
		model.addAttribute("page", page);
		return "modules/questionlib/sCourseVersionList";
	}

	@RequiresPermissions("questionlib:sCourseVersion:view")
	@RequestMapping(value = "form")
	public String form(SCourseVersion sCourseVersion, Model model) {
		model.addAttribute("sCourseVersion", sCourseVersion);
		return "modules/questionlib/sCourseVersionForm";
	}

	@RequiresPermissions("questionlib:sCourseVersion:edit")
	@RequestMapping(value = "addSchoolCourseVersion")
	public String addSchoolCourseVersion(SCourseVersion sCourseVersion, Model model, RedirectAttributes redirectAttributes) {
		String versionId = sCourseVersion.getId();
		String courseId = sCourseVersion.getCourse().getId();
		if(null == versionId || "".equals(versionId) || null == courseId || "".equals(courseId)){
			addMessage(model, "请选择课程与版本信息");
			return form(sCourseVersion, model);
		}
		if(StringUtils.isBlank(sCourseVersion.getSchoolVersionId())){
			//去重
			Map<String,String> reObj = sCourseVersionService.getSchoolVersionBy2Id(versionId);
			
			if(null != reObj){
				addMessage(model, "该版本的课程已经添加过了，请重新选择");
				return form(sCourseVersion, model);
			}
			sCourseVersionService.addSchoolCourseVersion(sCourseVersion);
			addMessage(redirectAttributes, "添加课程版本成功");
		}else{
			Map<String,String> reObj = sCourseVersionService.getSchoolVersionBy2Id(versionId);
			if(null != reObj){
				addMessage(model, "该版本的课程已经存在，请重新选择");
				return form(sCourseVersion, model);
			}
			sCourseVersionService.updateSchoolCourseVersion(sCourseVersion);
			addMessage(redirectAttributes, "修改课程版本成功");		
		}
		
		return "redirect:"+Global.getAdminPath()+"/questionlib/sCourseVersion/?repage";
	}
	
	@RequiresPermissions("questionlib:sCourseVersion:delete")
	@RequestMapping(value = "delete")
	public String delete(SCourseVersion sCourseVersion, RedirectAttributes redirectAttributes) {
		sCourseVersionService.delete(sCourseVersion);
		addMessage(redirectAttributes, "删除课程版本成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/sCourseVersion/?repage";
	}
	
	/**
	 * 根据课程的id拼装版本的options
	 * @param response
	 * @param courseId
	 */
	@RequiresPermissions("questionlib:sCourseVersion:edit")
	@RequestMapping(value = "getVersionsByCourseId")
	public void getVersionsByCourseId(HttpServletResponse response,String courseId){
		List<SCourseVersion> reList = sCourseVersionService.getVersionsByCourseId(courseId);
		String optionStr = "";
		optionStr += "<option value=\"\" selected=\"selected\">请选择版本</option>";
		if(null == reList){
			try {
				response.getWriter().write(optionStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			for (SCourseVersion sCourseVersion : reList) {
				optionStr += "<option value=\""+sCourseVersion.getId()+"\">"+sCourseVersion.getTitle()+"</option>";
			}
			try {
				response.getWriter().write(optionStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequiresPermissions("questionlib:sCourseVersion:edit")
	@RequestMapping("getCoursesBySpecialtyId")
	public void getCoursesBySpecialtyId(HttpServletRequest request,HttpServletResponse response,String specialtyId){
		List<Specialty> reList = sCourseVersionService.getCoursesBySpecialtyId(specialtyId);
		String optionStr = "";
		optionStr += "<option value=\"\" selected=\"selected\">请选择课程</option>";
		if(reList.isEmpty()){
			try {
				response.getWriter().write(optionStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			for (Specialty specialty : reList) {
				optionStr += "<option value=\""+specialty.getId()+"\">"+specialty.getTitle()+"</option>";
			}
			try {
				response.getWriter().write(optionStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	@RequiresPermissions("questionlib:sCourseVersion:deleteSchoolVersion")
	@RequestMapping(value = "deleteSchoolVersion")
	public String deleteSchoolVersion(String courseVersionId,RedirectAttributes redirectAttributes){
		sCourseVersionService.deleteSchoolVersion(courseVersionId);
		addMessage(redirectAttributes, "删除课程版本成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/sCourseVersion/?repage";
	}
	
	
	// getVersionsByCourseIdAndSchoolId
	@RequiresPermissions("questionlib:sCourseVersion:edit")
	@RequestMapping(value = "getVersionsByCourseIdAndSchoolId")
	public void getVersionsByCourseIdAndSchoolId(HttpServletResponse response,String courseId){
		List<SCourseVersion> reList = sCourseVersionService.getVersionsByCourseIdAndSchoolId(courseId);
		String optionStr = "";
		optionStr += "<option value=\"\" selected=\"selected\">请选择版本</option>";
		if(null == reList){
			try {
				response.getWriter().write(optionStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			for (SCourseVersion sCourseVersion : reList) {
				optionStr += "<option value=\""+sCourseVersion.getId()+"\">"+sCourseVersion.getTitle()+"</option>";
			}
			try {
				response.getWriter().write(optionStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}