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
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 学生Controller
 * @author webcat
 * @version 2016-09-14
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/student")
public class StudentController extends BaseController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SchoolClassService schoolClassService;
	
	
	@ModelAttribute
	public Student get(@RequestParam(required=false) String id) {
		Student entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentService.get(id);
		}
		if (entity == null){
			entity = new Student();
		}
		return entity;
	}
	
	@RequiresPermissions("questionlib:student:view")
	@RequestMapping(value = {"list", ""})
	public String list(Student student, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Student> page = studentService.findPageByCompanyId(new Page<Student>(request, response), student); 
		model.addAttribute("page", page);
		return "modules/questionlib/studentList";
	}

	@RequiresPermissions("questionlib:student:view")
	@RequestMapping(value = "form")
	public String form(Student student, Model model) {
		
		String companyId=UserUtils.getUser().getCompany().getId();
		List<SchoolClass> specTitles=schoolClassService.findSchollClassSpecialtyTitle(null, companyId);
		model.addAttribute("specTitles", specTitles);
		
		model.addAttribute("student", student);
		return "modules/questionlib/studentForm";
	}
	
	@RequiresPermissions("questionlib:schoolClass:edit")
	@RequestMapping(value = "toAddOrUpdateStudentPage")
	public String toAddOrUpdateStudentPage(Student student,Model model){
		student.setStdPassword("");
		model.addAttribute("student", student);
		return "modules/questionlib/classStudentForm";
	}

	@RequiresPermissions("questionlib:student:edit")
	@RequestMapping(value = "save")
	public String save(Student student, Model model, RedirectAttributes redirectAttributes) {
		if(StringUtils.isBlank(student.getId())){
			Student loginUser = studentService.getStudentByStudentCode2(student);
			if(null != loginUser){
				addMessage(redirectAttributes, "已经存在学号为：" + student.getStdCode() + "的学生");
				return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
			}
		}
		String studentId = student.getId();
		if(StringUtils.isBlank(studentId)){
			student.setStdPassword(SystemService.entryptPassword(student.getStdPassword()));
		}else{
			if(StringUtils.isBlank(student.getStdPassword())){
				Student student2 = studentService.get(student.getId());
				student.setStdPassword(student2.getStdPassword());
			}else{
				student.setStdPassword(SystemService.entryptPassword(student.getStdPassword()));
			}
		}
		if (!beanValidator(model, student)){
			return form(student, model);
		}
		studentService.save(student);
		addMessage(redirectAttributes, "保存学生成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
	}
	
	@RequiresPermissions("questionlib:schoolClass:edit")
	@RequestMapping(value = "classStudentsave")
	public String classStudentsave(Student student, Model model, RedirectAttributes redirectAttributes){
		if(StringUtils.isBlank(student.getId())){
			Student loginUser = studentService.getStudentByStudentCode(student);
			if(null != loginUser){
				addMessage(redirectAttributes, "已经存在" + student.getStdCode() + "学号的学生");
				return "redirect:"+Global.getAdminPath()+"/questionlib/student/findClassStudentByClassId?repage&&schoolClass.id="+student.getSchoolClass().getId();
			}
		}
		String password = UserUtils.getUser().getPassword();
		if(null == student.getStdPassword() || "".equals(student.getStdPassword())){
			student.setStdPassword(password);
		}
		if (!beanValidator(model, student)){
			return form(student, model);
		}
		String stdPassword = student.getStdPassword();
		student.setStdPassword(SystemService.entryptPassword(stdPassword));
		studentService.save(student);
		addMessage(redirectAttributes, "保存学生成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/student/findClassStudentByClassId?repage&&schoolClass.id="+student.getSchoolClass().getId();
	}
	
	@RequiresPermissions("questionlib:student:delete")
	@RequestMapping("classStudentDelete")
	public String classStudentDelete(HttpServletRequest request,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes,Student student){
		studentService.delete(student);
		addMessage(redirectAttributes, "删除学生成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/student/findClassStudentByClassId?repage&&schoolClass.id="+student.getSchoolClass().getId();
	}
	
	@RequiresPermissions("questionlib:student:delete")
	@RequestMapping(value = "delete")
	public String delete(Student student, RedirectAttributes redirectAttributes) {
		studentService.delete(student);
		addMessage(redirectAttributes, "删除学生成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
	}
	
	@RequiresPermissions("questionlib:schoolClass:edit")
	@RequestMapping(value = "findClassStudentByClassId")
	public String findClassStudentByClassId(Student student, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<Student> page = studentService.findPage(new Page<Student>(request, response), student); 
		model.addAttribute("page", page);
		model.addAttribute("classId", student.getSchoolClass().getId());
		return "modules/questionlib/classStudentList";
	}
	
	/**
	 * 不同学校下的相同的学号不算做重复
	 * @param oldStdCode
	 * @param stdCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkStdCode")
	public String checkLoginName(String oldStdCode, String stdCode) {
		Student student = new Student(); 
		student.setStdCode(stdCode);
		if (stdCode !=null && stdCode.equals(oldStdCode)) {
			return "true";
		} else if (stdCode !=null && studentService.getStudentByStudentCode2(student) == null) {
			return "true";
		}
		return "false";
	}
	
}