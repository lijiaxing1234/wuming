/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.TableBookVersion;
import com.thinkgem.jeesite.modules.questionlib.service.TableBookVersionService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 书籍检查Controller
 * @author 书籍检查
 * @version 2016-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/tableBookVersion")
public class TableBookVersionController extends BaseController {

	@Autowired
	private TableBookVersionService tableBookVersionService;
	
	@ModelAttribute
	public TableBookVersion get(@RequestParam(required=false) String id) {
		TableBookVersion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tableBookVersionService.get(id);
		}
		if (entity == null){
			entity = new TableBookVersion();
		}
		return entity;
	}
	
	@RequiresPermissions("questionlib:tableBookVersion:view")
	@RequestMapping(value = {"list", ""})
	public String list(TableBookVersion tableBookVersion, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (tableBookVersion.getIsMy() != null) {
			tableBookVersion.setCheckerId(UserUtils.getUser().getId());
		}
		
		Page<TableBookVersion> page = tableBookVersionService.findPage(new Page<TableBookVersion>(request, response), tableBookVersion); 
		model.addAttribute("page", page);
		return "modules/questionlib/tableBookVersionList";
	}

	@RequiresPermissions("questionlib:tableBookVersion:view")
	@RequestMapping(value = "form")
	public String form(TableBookVersion tableBookVersion, Model model) {
		model.addAttribute("tableBookVersion", tableBookVersion);
		return "modules/questionlib/tableBookVersionForm";
	}

	@RequiresPermissions("questionlib:tableBookVersion:edit")
	@RequestMapping(value = "save")
	public String save(TableBookVersion tableBookVersion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tableBookVersion)){
			return form(tableBookVersion, model);
		}
		tableBookVersionService.save(tableBookVersion);
		addMessage(redirectAttributes, "保存书籍检查成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/tableBookVersion/?repage";
	}
	
	@RequiresPermissions("questionlib:tableBookVersion:edit")
	@RequestMapping(value = "delete")
	public String delete(TableBookVersion tableBookVersion, RedirectAttributes redirectAttributes) {
		tableBookVersionService.delete(tableBookVersion);
		addMessage(redirectAttributes, "删除书籍检查成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/tableBookVersion/?repage";
	}
	
	@RequestMapping(value = {"appList"})
	public String appList(String verIds,TableBookVersion tableBookVersion, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (StringUtils.isNotBlank(verIds)) {
			String[] aa = verIds.split(",");
			for (int i=0;i<aa.length;i++) {
				TableBookVersion tb = tableBookVersionService.get(aa[i]);
				if (tb != null) {
					tb.setCheckerId(UserUtils.getUser().getId());
					tb.setCheckState("1");
					tableBookVersionService.save(tb);
				}
			
			}
		}
		return "redirect:"+Global.getAdminPath()+"/questionlib/tableBookVersion/?repage";
	}

}