package com.thinkgem.jeesite.modules.teacher.web;

import java.util.HashMap;
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

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.QuestionlibImport;
import com.thinkgem.jeesite.modules.questionlib.service.QuestionlibImportService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

@Controller
@RequestMapping("${teacherPath}/doc")
public class DocController  extends BaseController{

	@Autowired
	private QuestionlibImportService questionlibImportService;
	
	@ModelAttribute
	public QuestionlibImport get(@RequestParam(required=false) String id) {
		QuestionlibImport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = questionlibImportService.get(id);
		}
		if (entity == null){
			entity = new QuestionlibImport();
		}
		return entity;
	}
	
/*	
	@RequestMapping("list")
	public String list(){
		
	  return "teacher/doc/list";

	}
	
*/	

	
	@RequestMapping(value = {"list", ""})
	public String list(QuestionlibImport questionlibImport, HttpServletRequest request, HttpServletResponse response, Model model) {
		User teacher =TearcherUserUtils.getUser();
		questionlibImport.setSchool(teacher.getCompany());
		questionlibImport.setUser(teacher);
		Page<QuestionlibImport> page = questionlibImportService.findPage(new Page<QuestionlibImport>(request, response), questionlibImport); 
		model.addAttribute("page", page);
		return "teacher/doc/questionlibImportList";
	}

	@RequestMapping(value = "form")
	public String form(QuestionlibImport questionlibImport, Model model) {
		model.addAttribute("questionlibImport", questionlibImport);
		return "teacher/doc/questionlibImportForm";
	}

	@ResponseBody
	@RequestMapping(value = "save")
	public Map<String,String> save(@RequestParam(required=false) String courseQuestionlibId, QuestionlibImport questionlibImport, Model model, HttpServletResponse response) {
		HashMap<String, String> map = Maps.newHashMap();
		if(StringUtils.isNotBlank(courseQuestionlibId)){
			questionlibImport.setQuestionlibId(courseQuestionlibId);
		}else{
			map.put("message", "保存失败！");
			map.put("importId", "");
			return map;
		}
		questionlibImportService.save(questionlibImport);
		map.put("message", "保存成功！");
		map.put("importId", questionlibImport.getId());
		return map;
	}
	
	@RequestMapping(value = "delete")
	public String delete(QuestionlibImport questionlibImport, RedirectAttributes redirectAttributes) {
		questionlibImportService.delete(questionlibImport);
		addMessage(redirectAttributes, "删除试题导入题库成功");
		return "redirect:"+Global.getTeacherPath()+"/doc/list/?repage";
	}

	
	
	
}


