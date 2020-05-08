package com.thinkgem.jeesite.modules.questionlib.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;


@Controller
@RequestMapping(value = "${frontPath}/questionlib/index")
public class WebIndexController extends BaseController{
	
	@RequestMapping(value = {""})
	public String index() {
		return "index/index";
		//return "redirect:"+Global.getAdminPath();
	}
}
