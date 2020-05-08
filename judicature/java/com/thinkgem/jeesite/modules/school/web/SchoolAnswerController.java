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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.UAnswer;
import com.thinkgem.jeesite.modules.questionlib.service.UAnswerService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 回答Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/school/shoolAnswer")
public class SchoolAnswerController extends BaseController {

	@Autowired
	private UAnswerService uAnswerService;
	
	@ModelAttribute
	public UAnswer get(@RequestParam(required=false) String id) {
		UAnswer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = uAnswerService.get(id);
		}
		if (entity == null){
			entity = new UAnswer();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(UAnswer uAnswer, HttpServletRequest request, HttpServletResponse response, Model model) {
	
		Map<String,String> sqlMap=uAnswer.getSqlMap();
		sqlMap.put("officeId",UserUtils.getUser().getCompany().getId());
		Page<UAnswer> page = uAnswerService.findPage(new Page<UAnswer>(request, response), uAnswer); 
		model.addAttribute("page", page);
		model.addAttribute("uAnswer", uAnswer);
		return "modules/school/uAnswerList";
	}

	@RequestMapping(value = "form")
	public String form(UAnswer uAnswer, Model model) {
		model.addAttribute("uAnswer", uAnswer);
		return "modules/school/uAnswerForm";
	}

	@RequestMapping(value = "save")
	public String save(UAnswer uAnswer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, uAnswer)){
			return form(uAnswer, model);
		}
		uAnswerService.save(uAnswer);
		addMessage(redirectAttributes, "保存回答成功");
		return "redirect:"+Global.getAdminPath()+"/school/shoolAnswer/list/?repage";
	}
	
	@RequiresPermissions({"school.web:shoolAnswer:delete","school.web:shoolAnswer:auditor"})
	@RequestMapping(value = "delete")
	public String delete(UAnswer uAnswer,Integer[] ids, RedirectAttributes redirectAttributes) {
		
		String result=null;
        if(ids!=null&&ids.length>0){
        	
        	Integer id=ids[0];
        	UAnswer entity=uAnswerService.get(id+"");
        	if(entity !=null)
        		result="&delFlag="+entity.getDelFlag();
        	
    		for (int i = 0, len = ids.length; i < len; i++) {
    			UAnswer ua = new UAnswer(ids[i] + "");
    			ua.setDelFlag(uAnswer.getDelFlag());
    			uAnswerService.save(ua);
    		}
    		addMessage(redirectAttributes,"操作成功");
        }
        else {
        	addMessage(redirectAttributes,"没有数据被修改");
		}
        
		return "redirect:"+Global.getAdminPath()+"/school/shoolAnswer/list/?repage"+result;
	}

}