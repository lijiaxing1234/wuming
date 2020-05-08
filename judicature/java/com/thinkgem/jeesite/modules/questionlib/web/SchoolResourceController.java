package com.thinkgem.jeesite.modules.questionlib.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Edresources;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolRescourceService;

@Controller
@RequestMapping(value = "${adminPath}/questionlib/schoolRescource")
public class SchoolResourceController extends BaseController{

	@Autowired
	private SchoolRescourceService schoolResourceService;
	
	//获取学校资源列表
	@RequestMapping("getSchoolResourceList")
	public String getSchoolResourceList(Model model,HttpServletRequest request,HttpServletResponse response,Edresources edresources){
		Page<Edresources> page = schoolResourceService.getSchoolResourceList(new Page<Edresources>(request, response),edresources);
		model.addAttribute("page", page);
		return "modules/questionlib/schoolResourceList";
	}
	
}
