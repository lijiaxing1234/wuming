package com.thinkgem.jeesite.modules.teacher.web;
import java.util.List;

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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.Edresources;
import com.thinkgem.jeesite.modules.questionlib.service.EdresourcesService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherKnowledge;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
/**
 * 教学资源Controller
 * @author yfl
 * @version 2016-09-14
 */
@Controller
@RequestMapping("${teacherPath}/sources")
public class EdresourcesController extends BaseController {

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
		return "teacher/educationresources/index";
	}
	
	//教学资源列表
	@RequestMapping("list")
	public String list(String knoId,Edresources edresources, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		User teacher=TearcherUserUtils.getUser();
		
		edresources.setUserId(teacher.getId());
		edresources.setOfficeId(teacher.getCompany().getId());
		edresources.setKnowledgeId(knoId);
		
		Page<Edresources>page=edresourcesService.findPage(new Page<Edresources>(request,response), edresources);
		model.addAttribute("page", page);
		model.addAttribute("knoId", knoId);
		model.addAttribute("edresources", edresources);
		return "teacher/educationresources/resourcelist";
	}
	
	@RequestMapping("form")
	public String form(String knoId,Edresources edresources,Model model){
		model.addAttribute("knoId", knoId);
		model.addAttribute("edresources", edresources);
		return "teacher/educationresources/form";
	}
	
	//上传教学资源
	@RequestMapping(value="save")
	public String saves(String knoId,Edresources edresources, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (TearcherUserUtils.getUser() != null && TearcherUserUtils.getUser().getId() != null) {
			edresources.setUserId(TearcherUserUtils.getUser().getId());
			edresources.setCreateBy(TearcherUserUtils.getUser().getCreateBy());
			edresources.setUpdateBy(TearcherUserUtils.getUser().getUpdateBy());
			edresources.setOfficeId(TearcherUserUtils.getUser().getCompany().getId());
			edresources.setVersionId(TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId"));
		}
		edresources.setCreateDate(DateTime.now().toDate());
		edresourcesService.save(edresources);
		addMessage(redirectAttributes, "保存教学资源成功");
		return "redirect:" + teacherPath+ "/sources/list?knoId="+knoId;
	
				
	}
	//删除教学资源
	@RequestMapping("delete")
	public String delete(String knoId,Edresources edresources,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request){
		edresourcesService.delete(edresources);
		addMessage(redirectAttributes, "删除教学资源成功");
		return "redirect:" + teacherPath+ "/sources/list?knoId="+knoId;
		 
	}

}