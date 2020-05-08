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
import com.thinkgem.jeesite.modules.questionlib.entity.TableQuestionlibImportApply;
import com.thinkgem.jeesite.modules.questionlib.service.TableQuestionlibImportApplyService;

/**
 * 申请入平台Controller
 * @author flychao
 * @version 2016-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/tableQuestionlibImportApply")
public class TableQuestionlibImportApplyController extends BaseController {

	@Autowired
	private TableQuestionlibImportApplyService tableQuestionlibImportApplyService;
	
	/*@ModelAttribute
	public TableQuestionlibImportApply get(@RequestParam(required=false) String id) {
		TableQuestionlibImportApply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tableQuestionlibImportApplyService.get(id);
		}
		if (entity == null){
			entity = new TableQuestionlibImportApply();
		}
		return entity;
	}*/
	
	/*@RequiresPermissions("questionlib:tableQuestionlibImportApply:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(TableQuestionlibImportApply tableQuestionlibImportApply, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TableQuestionlibImportApply> page = tableQuestionlibImportApplyService.findPage(new Page<TableQuestionlibImportApply>(request, response), tableQuestionlibImportApply); 
		model.addAttribute("page", page);
		model.addAttribute("tableQuestionlibImportApply", tableQuestionlibImportApply);
		return "modules/questionlib/tableQuestionlibImportApplyList";
	}

	/*@RequiresPermissions("questionlib:tableQuestionlibImportApply:view")*/
	@RequestMapping(value = "form")
	public String form(TableQuestionlibImportApply tableQuestionlibImportApply, Model model) {
		model.addAttribute("tableQuestionlibImportApply", tableQuestionlibImportApply);
		return "modules/questionlib/tableQuestionlibImportApplyForm";
	}

	/*@RequiresPermissions("questionlib:tableQuestionlibImportApply:edit")*/
	@RequestMapping(value = "save")
	public String save(TableQuestionlibImportApply tableQuestionlibImportApply, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tableQuestionlibImportApply)){
			return form(tableQuestionlibImportApply, model);
		}
		tableQuestionlibImportApplyService.save(tableQuestionlibImportApply);
		addMessage(redirectAttributes, "保存操作成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/tableQuestionlibImportApply/?repage";
	}
	
	/*@RequiresPermissions("questionlib:tableQuestionlibImportApply:edit")*/
	@RequestMapping(value = "delete")
	public String delete(TableQuestionlibImportApply tableQuestionlibImportApply, RedirectAttributes redirectAttributes) {
		tableQuestionlibImportApplyService.delete(tableQuestionlibImportApply);
		addMessage(redirectAttributes, "删除操作成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/tableQuestionlibImportApply/?repage";
	}
   
	@RequestMapping("audit")
	public String audit(String quesImportId,RedirectAttributes redirectAttributes){
		
		try{
			if(StringUtils.isNotBlank(quesImportId)){
				
				String[] param=quesImportId.split("_");
				
				TableQuestionlibImportApply quesImportApply=new TableQuestionlibImportApply();
				quesImportApply.setQueslibImportId(param[0]);
				quesImportApply.setStatus(Integer.valueOf(param[1]));
				quesImportApply.setSchoolId(param[2]);
				tableQuestionlibImportApplyService.audit(quesImportApply);
			}else{
				addMessage(redirectAttributes, "参数丢失!");
			}
		}catch(Exception e){
			addMessage(redirectAttributes, "操作失败!");
		}
		return "redirect:"+Global.getAdminPath()+"/questionlib/tableQuestionlibImportApply/?repage";
	}
	
	
	
}