/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Teacher;
import com.thinkgem.jeesite.modules.questionlib.service.TeacherService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 教师Controller
 * @author hgh
 * @version 2016-09-14
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/teacher")
public class TeacherController extends BaseController {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SystemService sysService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Teacher get(@RequestParam(required=false) String id) {
		Teacher entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = teacherService.get(id);
		}
		if (entity == null){
			entity = new Teacher();
		}
		return entity;
	}
	
	/**
	 * 查询本学校的教师
	 * 根据
	 * @param teacher
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("questionlib:teacher:view")
	@RequestMapping(value = {"list", ""})
	public String list(Teacher teacher, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Teacher> page = teacherService.findTeacherPageByCompanyId(new Page<Teacher>(request, response), teacher); 
		model.addAttribute("page", page);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/questionlib/teacherList";
	}

	@RequiresPermissions("questionlib:teacher:view")
	@RequestMapping(value = "form")
	public String form(Teacher teacher, Model model) {
		teacher.setPassword(null);
		model.addAttribute("teacher", teacher);
		return "modules/questionlib/teacherForm";
	}

	@SuppressWarnings("static-access")
	@RequiresPermissions("questionlib:teacher:edit")
	@RequestMapping(value = "save")
	public String save(Teacher teacher, Model model, RedirectAttributes redirectAttributes) {
		teacher.setSchool(new Office(UserUtils.getUser().getOffice().getId()));
		//新增 还是 修改
		if(StringUtils.isBlank(teacher.getId())){
			//新增
			teacher.setPassword(SystemService.entryptPassword(teacher.getPassword()));
			//验证登录名是否已经存在
			User validateTeacher = teacherService.getUserByLoginName(teacher.getLoginName());
			if(null != validateTeacher){
				addMessage(redirectAttributes, "登录名为" + validateTeacher.getLoginName() + "的用户已经存在");
				return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage"; 
			}
		}else{
			//修改
			//密码是否为空
			if(StringUtils.isBlank(teacher.getPassword())){
				//不修改密码
				User reTeacher = systemService.getUser(teacher.getId());
				teacher.setPassword(reTeacher.getPassword());
			}else{
				//修改密码
				teacher.setPassword(SystemService.entryptPassword(teacher.getPassword()));
			}
		}
		if (!beanValidator(model, teacher)){
			return form(teacher, model);
		}
		teacherService.save(teacher);
		if(StringUtils.isNotBlank(teacher.getId())){
			User user=new User(teacher.getId());
			user.setLoginName(teacher.getName());
			user.setOffice(teacher.getOffice());
		    UserUtils.clearCache(user);
		}
		addMessage(redirectAttributes, "保存教师成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
	}
	
	@RequiresPermissions("questionlib:teacher:delete")
	@RequestMapping(value = "delete")
	public String delete(Teacher teacher, RedirectAttributes redirectAttributes) {
		teacherService.delete(teacher);
		addMessage(redirectAttributes, "删除教师成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
	}
	
	@RequestMapping("intoImportTeachersPage")
	public String intoImportTeachersPage(Model model,HttpServletRequest request,HttpServletResponse response){
		return "modules/questionlib/importTeachersPage";
	}
	@RequiresPermissions("questionlib:teacher:download")
	@RequestMapping("importTeachers")
	public String importTeachers(HttpServletResponse response,HttpServletRequest request,Model model,@RequestParam("teacherFile") MultipartFile file,RedirectAttributes redirectAttributes){
		String message = "";
		try {
			if(file.getSize() == 0){
				//文档为空
				addMessage(redirectAttributes, "请选择文档");
				return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
			}else{
				ImportExcel excel = new ImportExcel(file, 1, 0);
				//表格中所有数据 
				List<Teacher> teacherList = excel.getDataList(Teacher.class);
				if(teacherList.size() == 0){
					addMessage(redirectAttributes, "文档中无教师信息");
					return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
				}
				//验证数据是否为空
				for (int i = 0; i < teacherList.size(); i++) {
					Teacher teacher = teacherList.get(i);
					
					
					if(StringUtils.isBlank(teacher.getLoginName())){
						message += "第" + (i + 3) + "行数据，登录名为空；";
					}
					if(StringUtils.isBlank(teacher.getPassword())){
						message += "第" + (i + 3) + "行数据，登录密码为空；";
					}
					if(StringUtils.isBlank(teacher.getNo())){
						message += "第" + (i + 3) + "行数据，教师工号为空；";
					}
					if(StringUtils.isBlank(teacher.getName())){
						message += "第" + (i + 3) + "行数据，教师姓名为空；";
					}
					if(StringUtils.isBlank(teacher.getEmail())){
						message += "第" + (i + 3) + "行数据，邮箱为空；";
					}
					if(StringUtils.isBlank(teacher.getMobile())){
						message += "第" + (i + 3) + "行数据，手机号为空；";
					}
				}
				
				if(StringUtils.isNoneBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
				}
				
				for(int i = 0; i < teacherList.size(); i++){
					Teacher teacher = teacherList.get(i);
					if(!checkEmail(teacher.getEmail())){
						message += "第" + (i + 3) + "行数据，邮箱格式错误；";
					}
					if(!checkPhone(teacher.getMobile())){
						message += "第" + (i + 3) + "行数据，手机号格式错误；";
					}
					
				}
				if(StringUtils.isNoneBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
				}
				
				
				//验证excel表格中的登录名是否有重复
				HashSet<Teacher> set = new HashSet<Teacher>(teacherList);
				if(set.size() != teacherList.size()){
					addMessage(redirectAttributes, "excel表中，登录名重复");
					return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
				}
				//验证数据库与文档中的内容有无重复
				List<Teacher> dbList = teacherService.getTeacherListBySchoolId();
				for (int i = 0; i < teacherList.size(); i++) {
					Teacher teacher = teacherList.get(i);
					for (int j = 0; j < dbList.size(); j++) {
						Teacher teacher2 = dbList.get(j);
						if(teacher.getLoginName().equals(teacher2.getLoginName())){
							message += "第" + (i + 3) + "行数据，登录名为："+teacher.getLoginName()+"的教师已存在；";
						}
					}
				}
				if(StringUtils.isNoneBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
				}
				//获取某学校的所有部门
				int k = 0;
				List<Teacher> teacherOfficeIdNameList = teacherService.getOfficeIdAndOfficeNameBySchoolId();
				for (int i = 0; i < teacherList.size(); i++) {
					Teacher teacher = teacherList.get(i);
					String officeName = teacher.getOfficeName();
					for(int j = 0;j < teacherOfficeIdNameList.size(); j++){
						Teacher teacher2 = teacherOfficeIdNameList.get(j);
						String officeName2 = teacher2.getOfficeName();
						if(officeName.equals(officeName2)){
							k++;
						}
					}
					if(k == 0){
						message += "第" + (i + 3) + "行数据错误，学校中无名为"+officeName+"的机构，请现在本学校下添加该机构；";
					}else{
						k = 0;
					}
				}
				
				if(StringUtils.isNoneBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
				}
				
				
				
				//数据校验通过，可以导入老师
				for (Teacher teacher : teacherList) {
					teacher.setId(IdGen.uuid());
					teacher.setSchool(UserUtils.getUser().getCompany());
					teacher.setCreateDate(new Date());
					teacher.setCreateBy(UserUtils.getUser());
					teacher.setPassword(systemService.entryptPassword(teacher.getPassword()));
					//设置部门id
					for (Teacher officeTeacher : teacherOfficeIdNameList) {
						if(teacher.getOfficeName().equals(officeTeacher.getOfficeName())){
							teacher.setOffice(new Office(officeTeacher.getOfficeId()));
						}
					}
					teacher.setUpdateBy(UserUtils.getUser());
					teacher.setUpdateDate(new Date());
					teacher.setLoginFlag("0");
				}
				int insertNumber = teacherService.importTeachers(teacherList);
				if(insertNumber == teacherList.size()){
					message = "导入成功";
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
				}else{
					//导入失败
					message = "导入部分成功，有"+ (teacherList.size() - insertNumber) + "条记录未导入";
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
				}
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "文档格式错误，导入老师失败";
			addMessage(redirectAttributes, message);
			return "redirect:"+Global.getAdminPath()+"/questionlib/teacher/?repage";
		}
	}
	public static boolean checkEmail(String email) {
        try {
            String check = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean checkPhone(String phone) {
    	try {
    		String check = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
    		Pattern regex = Pattern.compile(check);
    		Matcher matcher = regex.matcher(phone);
    		return matcher.matches();
    	} catch (Exception e) {
    		return false;
    	}
    }
    
    @ResponseBody
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	
}