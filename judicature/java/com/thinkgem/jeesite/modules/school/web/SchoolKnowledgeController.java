package com.thinkgem.jeesite.modules.school.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolKnowledgeService;

@RequestMapping("${adminPath}/school/schoolKnowledge")
@Controller
public class SchoolKnowledgeController extends BaseController{

	@Autowired
	private SchoolKnowledgeService schoolKnowledgeService;
	
	@RequestMapping("list")
	public String getSchoolKnowledgeList(HttpServletRequest request,HttpServletResponse response,Model model,CourseKnowledge knowledge){
		Page<CourseKnowledge> page = schoolKnowledgeService.getSchoolKnowledgeList(new Page<CourseKnowledge>(request, response),knowledge);
		model.addAttribute("page", page);
		model.addAttribute("knowledge", knowledge);
		return "modules/school/schoolKnowledgeList";
	}
	
}
