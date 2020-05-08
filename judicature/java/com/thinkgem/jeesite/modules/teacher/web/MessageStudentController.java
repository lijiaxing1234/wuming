/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.teacher.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.MessageStudent;
import com.thinkgem.jeesite.modules.questionlib.service.MessageStudentService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 提醒Controller
 * @author flychao
 * @version 2016-12-06
 */
@Controller(value="teacherMessageStudentController")
@RequestMapping("${teacherPath}/questionlib/messageStudent")
public class MessageStudentController extends BaseController {

	@Autowired
	private MessageStudentService messageStudentService;
	
	@ModelAttribute
	public MessageStudent get(@RequestParam(required=false) String id) {
		MessageStudent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = messageStudentService.get(id);
		}
		if (entity == null){
			entity = new MessageStudent();
		}
		return entity;
	}
	
/*	@RequestMapping(value = {"list", ""})
	public String list(MessageStudent messageStudent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageStudent> page = messageStudentService.findPage(new Page<MessageStudent>(request, response), messageStudent); 
		model.addAttribute("page", page);
		return "modules/questionlib/messageStudentList";
	}
*/
	@RequestMapping(value = "form")
	public String form(MessageStudent messageStudent, Model model) {
		model.addAttribute("messageStudent", messageStudent);
		return "teacher/StudentMessage/messageStudentForm";
	}
    
	@RequestMapping(value = "save")
	public String save(MessageStudent messageStudent, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		
		String param=request.getParameter("param");
		if(StringUtils.isNotBlank(param)){
			
			String[] strArr=param.split("_");
		    messageStudent.setStatus(0);
		    messageStudent.setStudentId(strArr[1]);
		    messageStudent.setExamId(strArr[2]);
		    messageStudent.setTeacherId(TearcherUserUtils.getUser().getId());
			messageStudent.setCreateDate(new Date());
			
			messageStudentService.save(messageStudent);
			
	   }
		return "teacher/StudentMessage/messageStudentTip";
		/*else{
			
			if (!beanValidator(model, messageStudent)){
				return form(messageStudent, model);
			}
			messageStudentService.save(messageStudent);
			addMessage(redirectAttributes, "保存提醒成功");
			return "redirect:"+Global.getAdminPath()+"/questionlib/messageStudent/?repage";
			
		}*/
		
	}
	
	@RequestMapping(value = "delete")
	public String delete(MessageStudent messageStudent, RedirectAttributes redirectAttributes) {
		messageStudentService.delete(messageStudent);
		addMessage(redirectAttributes, "删除提醒成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/messageStudent/?repage";
	}

}