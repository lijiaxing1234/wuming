package com.thinkgem.jeesite.modules.questionlib.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.dto.TeacherDTO;
import com.thinkgem.jeesite.modules.questionlib.service.TeacherStaticsService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/questionlib/teacherStatics")
public class TeacherStaticsController extends BaseController {

	@Autowired
	TeacherStaticsService teacherStaticsService;

	@RequestMapping(value = { "teacher" })
	public String teacherStatics(TeacherDTO teacherDTO,HttpServletRequest request, HttpServletResponse response,Model model) {
		if (null == teacherDTO) {
			teacherDTO = new TeacherDTO();
		}

		teacherDTO.setSchoolId(UserUtils.getUser().getCompany().getId());
		Page<TeacherDTO> page = teacherStaticsService.getPage(new Page<TeacherDTO>(request,response),teacherDTO);
		model.addAttribute("page", page);
		model.addAttribute("teacherDTO", teacherDTO);
		return "modules/questionlib/statistics/teacherstatics";
	}
}