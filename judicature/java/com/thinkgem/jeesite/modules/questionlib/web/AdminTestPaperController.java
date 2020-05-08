package com.thinkgem.jeesite.modules.questionlib.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.TestPaper;
import com.thinkgem.jeesite.modules.questionlib.service.AdminTestPaperService;

/**
 * 平台试卷Controller
 * @author .36
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/adminTestPaper")
public class AdminTestPaperController extends BaseController{
	
	@Autowired
	private AdminTestPaperService testPaperService;
	
	/**
	 * 平台试卷查看
	 * @param model
	 * @param testPaper
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionlib:adminTestPaper:view")
	@RequestMapping(value = "list")
	public String list (Model model,TestPaper testPaper,HttpServletRequest request,HttpServletResponse response){
		Page<TestPaper> page = testPaperService.findPage(new Page<TestPaper>(request, response), testPaper);
		model.addAttribute("page", page);
		model.addAttribute("testPaper", testPaper);
		return "modules/questionlib/testPaperList";
	}

	/**
	 * 试卷中所有试题的查看
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("questionlib:adminTestPaper:view")
	@RequestMapping(value = "testPaperQuestions")
	public String testPaperQuestions(Model model,HttpServletRequest request,HttpServletResponse response){
		return null;
	}
	
	
}
