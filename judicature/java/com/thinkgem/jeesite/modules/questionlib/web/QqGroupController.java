/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

import java.util.List;

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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.School;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 学生Controller
 * @author webcat
 * @version 2016-09-14
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/qqGroup")
public class QqGroupController extends BaseController {

	@Autowired
	private DictService dictService;
	
	 
	
	@ModelAttribute
	public Dict get(@RequestParam(required=false) String id) {
		Dict entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dictService.get(id);
		}
		if (entity == null){
			entity = new Dict();
		}
		return entity;
	}
	
	 

	@RequiresPermissions("questionlib:qqGroup:view")
	@RequestMapping(value = "form")
	public String form(Dict dict, Model model) {
		
		dict.setType("qqGroup");
	 
		List<Dict> dictList=dictService.findList(dict);
		if (dictList!=null&&dictList.size()>0) {
			dict =dictList.get(0);
		}
		model.addAttribute("dict", dict);
		
	 
		return "modules/questionlib/qqGroup";
	}
	
	@RequiresPermissions("questionlib:qqGroup:edit")
	@RequestMapping(value = "save")
	public String save(Dict dict, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dict)){
			return form(dict, model);
		}
		dictService.save(dict);
		addMessage(redirectAttributes, "保存QQ群成功");
		return "modules/questionlib/qqGroup";
	}
	
}