package com.thinkgem.jeesite.modules.school.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Edresources;
import com.thinkgem.jeesite.modules.questionlib.service.EdresourcesService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;
/**
 * 教学资源Controller
 * @author yfl
 * @version 2016-09-14
 */
@Controller
@RequestMapping("${adminPath}/school/shoolsources")
public class SchoolresourcesController extends BaseController {

	@Autowired
	private EdresourcesService edresourcesService;
	
	@ModelAttribute
	public Edresources  get(String id){
		Edresources ed=null;
		if(StringUtils.isNoneBlank(id)){
			ed=edresourcesService.get(new Edresources(id));
		}
		if(ed==null){
			ed=new Edresources();
		}
		return ed;
	}
	
	@RequestMapping("index")
	public String  index(){
		return "modules/school/resources/index";
	}
	
	//教学资源列表
	@RequestMapping(value = {"list",""})
	public String list(@RequestParam(value="knowledgeId",required=false)String knowledgeId,@RequestParam(value="resName",required=false)String resName,Edresources edresources, HttpServletRequest request, HttpServletResponse response, Model model) {
		//cookie中的教师id以及版本id
//		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
//		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		if("null".equals(knowledgeId)){
			edresources.setCourseKnowledge(null);
			edresources.setKnowledgeId(null);
			knowledgeId = null;
		}
		if(StringUtils.isNotBlank(resName)){
			edresources.setResName(resName);
		}
		if(StringUtils.isNotBlank(knowledgeId)){
			edresources.setKnowledgeId(knowledgeId);
		}
//		Page<Edresources>page=edresourcesService.findPage(new Page<Edresources>(request,response), edresources);
		Page<Edresources> page = edresourcesService.findSchoolResourcePageByCompanyId(new Page<Edresources>(request, response),edresources);
		model.addAttribute("resName", resName);
		model.addAttribute("knowledgeId", knowledgeId);
		model.addAttribute("page", page);
		model.addAttribute("edresources", edresources);
		return "modules/school/resources/resourcelist";
	}
	@RequestMapping("form")
	public String form(Edresources edresources,Model model){
		//cookie中的教师id以及版本id
//		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
//		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		model.addAttribute("edresources", edresources);
		return "modules/school/resources/form";
	}
	
	@RequestMapping("modifyform")
	public String modifyform(Edresources edresources,Model model){
		
		model.addAttribute("edresources", edresources);
		return "modules/school/resources/form";
	}
	//删除教学资源
	@RequestMapping("form/delete")
	public String delete(@RequestParam(value="knowledgeId",required=false)String knowledgeId, Edresources edresources,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request){
		edresourcesService.delete(edresources);
		addMessage(redirectAttributes, "删除教学资源成功");
		return "redirect:" +  adminPath + "/school/shoolsources/list?knowledgeId="+knowledgeId;
		 
	}
  
	@RequestMapping(value="modifyform/update",method=RequestMethod.POST)
	public String updateEdresources(@RequestParam(value="id",required=false)String id,Edresources edresources,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request){
		edresourcesService.save(edresources);
		addMessage(redirectAttributes, "修改教学资源成功");
		return "redirect:" +   adminPath + "/school/shoolsources/list?repage";
	}
	
	
	//上传教学资源
	@RequestMapping(value="form/Add",method=RequestMethod.POST)
	public String saves(Edresources edresources, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if(UserUtils.getUser() != null && UserUtils.getUser().getId() != null){
			edresources.setUserId(UserUtils.getUser().getId());
			edresources.setCreateBy(UserUtils.getUser());
			edresources.setUpdateBy(UserUtils.getUser());
			edresources.setOfficeId(UserUtils.getUser().getCompany().getId());
		}
		edresources.setCreateDate(DateTime.now().toDate());
		edresourcesService.save(edresources);
		addMessage(redirectAttributes, "保存教学资源成功");
		return "redirect:" +  adminPath + "/school/shoolsources/list?repage";
	
				
	}
}