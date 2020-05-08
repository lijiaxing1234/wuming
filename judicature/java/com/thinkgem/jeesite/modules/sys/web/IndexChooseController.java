/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping(value = "/")
public class IndexChooseController extends BaseController{
	
	 
	 
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "index/index";
	}*/

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String help(String tp, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		model.addAttribute("tp", tp);
		return "help/"+tp;
	}
}
