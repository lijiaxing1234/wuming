/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

import java.util.Date;

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
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.TableMessage;
import com.thinkgem.jeesite.modules.questionlib.service.TableMessageService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 系统消息Controller
 * @author flychao
 * @version 2016-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/tableMessage")
public class TableMessageController extends BaseController {

	@Autowired
	private TableMessageService tableMessageService;
	
	@ModelAttribute
	public TableMessage get(@RequestParam(required=false) String id) {
		TableMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tableMessageService.get(id);
		}
		if (entity == null){
			entity = new TableMessage();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TableMessage tableMessage,Date startTime,Date endTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user=UserUtils.getUser();
		tableMessage.setOffice(user.getCompany());
		
		if(startTime !=null && endTime !=null){
		   Parameter	sqlParam= tableMessage.getSqlParam();
		   sqlParam.put("startTime", startTime);
		   sqlParam.put("endTime", endTime);
		}else {
			tableMessage.setSqlParam(null);
			tableMessage.setCreatetime(null);
		}
		 
		Page<TableMessage> page = tableMessageService.findPage(new Page<TableMessage>(request, response), tableMessage); 
		model.addAttribute("page", page);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		return "modules/questionlib/tableMessageList";
	}

	@RequestMapping(value = "form")
	public String form(TableMessage tableMessage, Model model,String show) {
		model.addAttribute("tableMessage", tableMessage);
		model.addAttribute("show",show);
		if("1".equals(show))
		{
			return "modules/questionlib/tableMessageShow";
		}
		return "modules/questionlib/tableMessageForm";
	}

	@RequestMapping(value = "save")
	public String save(TableMessage tableMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tableMessage)){
			return form(tableMessage, model,"");
		}
		User user=UserUtils.getUser();
		tableMessage.setOffice(user.getCompany());
		tableMessageService.save(tableMessage);
		addMessage(redirectAttributes, "保存系统消息成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/tableMessage/";
	}
	@RequiresPermissions({"questionlib:tableMessage:delete"})
	@RequestMapping(value = "delete")
	public String delete(TableMessage tableMessage, RedirectAttributes redirectAttributes) {
		tableMessageService.delete(tableMessage);
		addMessage(redirectAttributes, "删除系统消息成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/tableMessage/?repage";
	}

}