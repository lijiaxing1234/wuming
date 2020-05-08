package com.thinkgem.jeesite.modules.school.web;

import java.util.Map;

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
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.UQuestionService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 提问Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/school/shoolQuestion")
public class SchoolQuestionController extends BaseController {

	@Autowired
	private UQuestionService uQuestionService;
	
	@ModelAttribute
	public UQuestion get(@RequestParam(required=false) String id) {
		UQuestion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = uQuestionService.get(id);
		}
		if (entity == null){
			entity = new UQuestion();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(UQuestion uQuestion, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		 
		Map<String,String> sqlMap=uQuestion.getSqlMap();
		sqlMap.put("officeId",UserUtils.getUser().getCompany().getId());
		Page<UQuestion> page = uQuestionService.findPage(new Page<UQuestion>(request, response), uQuestion); 
		model.addAttribute("page", page);
		model.addAttribute("uQuestion", uQuestion);
		return "modules/school/uQuestionList";
	}

	@RequestMapping(value = "form")
	public String form(UQuestion uQuestion, Model model) {
		model.addAttribute("uQuestion", uQuestion);
		return "modules/school/uQuestionForm";
	}

	@RequestMapping(value = "save")
	public String save(UQuestion uQuestion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, uQuestion)){
			return form(uQuestion, model);
		}
		uQuestionService.save(uQuestion);
		addMessage(redirectAttributes, "保存提问成功");
		return "redirect:"+Global.getAdminPath()+"/school/shoolQuestion/?repage";
	}
	@RequiresPermissions({"school.web:shoolQuestion:delete","school.web:shoolQuestion:auditor"})
	@RequestMapping(value = "delete")
	public String delete(UQuestion uQuestion,Integer[] ids, RedirectAttributes redirectAttributes) {
		
		String result=null;
		if(ids!=null&&ids.length>0){
			
			Integer id=ids[0];
			UQuestion entity = uQuestionService.get(id+"");
			if(entity!=null)
			  result="&delFlag="+entity.getDelFlag();
			
			for (int i = 0, len = ids.length; i < len; i++) {
				UQuestion uq = new UQuestion(ids[i] + "");
				uq.setDelFlag(uQuestion.getDelFlag());
				uQuestionService.save(uq);
			}
			
			addMessage(redirectAttributes,"操作成功");
		}else{
			
			addMessage(redirectAttributes,"没有数据被更新");
		}
		
		return "redirect:"+Global.getAdminPath()+"/school/shoolQuestion/?repage"+result;
	}

}