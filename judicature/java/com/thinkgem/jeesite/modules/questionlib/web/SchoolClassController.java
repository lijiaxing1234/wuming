/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolClassService;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import freemarker.core._RegexBuiltins.split_reBI;

/**
 * 班级Controller
 * @author webcat
 * @version 2016-09-14
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/schoolClass")
public class SchoolClassController extends BaseController {

	@Autowired
	private SchoolClassService schoolClassService;
	@Autowired
	private StudentService studentServcie;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public SchoolClass get(@RequestParam(required=false) String id) {
		SchoolClass entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = schoolClassService.get(id);
		}
		if (entity == null){
			entity = new SchoolClass();
			Office school = new Office(UserUtils.getUser().getCompany().getId());
			entity.setSchool(school);
		}
		return entity;
	}
	
	@RequiresPermissions("questionlib:schoolClass:view")
	@RequestMapping(value = {"list", ""})
	public String list(SchoolClass schoolClass, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SchoolClass> page = schoolClassService.findClassPageByCompanyId(new Page<SchoolClass>(request, response), schoolClass); 
		model.addAttribute("page", page);
		return "modules/questionlib/schoolClassList";
	}

	@RequiresPermissions("questionlib:schoolClass:view")
	@RequestMapping(value = "form")
	public String form(SchoolClass schoolClass, Model model) {
		model.addAttribute("schoolClass", schoolClass);
		return "modules/questionlib/schoolClassForm";
	}

	@RequiresPermissions("questionlib:schoolClass:edit")
	@RequestMapping(value = "save")
	public String save(SchoolClass schoolClass,String oldClassTitle, Model model, RedirectAttributes redirectAttributes) {
		//根据班级名称查询
		/*if(StringUtils.isBlank(schoolClass.getId())){
			List<SchoolClass> class1 = schoolClassService.getSchoolClassByName(schoolClass);
			if(null != class1&&class1.size()>0){
				addMessage(redirectAttributes, "已经存在名称为" + schoolClass.getTitle() + "的班级了" );
				return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
			}
		}*/
		
		if (!beanValidator(model, schoolClass)){
			return form(schoolClass, model);
		}
		
		//验证专业下的班级是否唯一
		if(StringUtils.isBlank(schoolClass.getId())){
			
			String companyId=UserUtils.getUser().getCompany().getId();
			List<SchoolClass> schoolClassList = schoolClassService.findSchoolClassBySpecTitle(schoolClass.getSpecialtyTitle(),companyId);
			@SuppressWarnings("unchecked")
			List<Object> sourceList=Collections3.extractToList(schoolClassList,"title");
			if(sourceList.contains(schoolClass.getTitle())){
				addMessage(model, "已经存在名称为【" + schoolClass.getTitle() + "】的班级了" );
				return form(schoolClass, model);
			}
			
		}else{
			if(!(StringUtils.isNotBlank(oldClassTitle)&&oldClassTitle.equals(schoolClass.getTitle()))){
				
				String companyId=UserUtils.getUser().getCompany().getId();
				List<SchoolClass> schoolClassList = schoolClassService.findSchoolClassBySpecTitle(schoolClass.getSpecialtyTitle(),companyId);
				@SuppressWarnings("unchecked")
				List<Object> sourceList=Collections3.extractToList(schoolClassList,"title");
				if(sourceList.contains(schoolClass.getTitle())){
					addMessage(model, "已经存在名称为【" + schoolClass.getTitle() + "】的班级了" );
					schoolClass.setTitle(oldClassTitle);
					return form(schoolClass, model);
				}
				
			}
		}
		
		
		
		schoolClassService.save(schoolClass);
		addMessage(redirectAttributes, "保存班级成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
	}
	
	@RequiresPermissions("questionlib:schoolClass:delete")
	@RequestMapping(value = "delete")
	public String delete(SchoolClass schoolClass, RedirectAttributes redirectAttributes) {
		schoolClassService.delete(schoolClass);
		schoolClassService.deleteStudentByClassId(schoolClass.getId());
		addMessage(redirectAttributes, "删除班级成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
	}
	
	@ResponseBody
	@RequestMapping("findSchoolClassSpecTitle")
	public String findSchoolClassSpecTitle(String specTitle){
		
		List<Map<String,Object>> list=Lists.newArrayList();
		String companyId=UserUtils.getUser().getCompany().getId();
		
		List<SchoolClass> sourceList=schoolClassService.findSchollClassSpecialtyTitle(specTitle, companyId);
		
		for(SchoolClass schoolClass:sourceList){
			Map<String,Object> map=Maps.newHashMap();
			map.put("specialtyTitle", schoolClass.getSpecialtyTitle());
			list.add(map);
		}
		return JSON.toJSONString(list);
	}
	
	@ResponseBody
	@RequestMapping("findSchoolClassBySpecTitle")
	public String findSchoolClassBySpecTitle(String specTitle){
		
		List<Map<String,Object>> list=Lists.newArrayList();
		if(StringUtils.isNotBlank(specTitle)){
			
			String companyId=UserUtils.getUser().getCompany().getId();
			List<SchoolClass> sourceList=schoolClassService.findSchoolClassBySpecTitle(specTitle, companyId);
			for(SchoolClass schoolClass:sourceList){
				Map<String,Object> map=Maps.newHashMap();
				map.put("id", schoolClass.getId());
				map.put("name",schoolClass.getTitle());
				list.add(map);
			}
		}
		return JSON.toJSONString(list);
	}
	
	
	
	
	
	
	
	@RequiresPermissions("questionlib:schoolClass:edit")
	@RequestMapping(value = "findClassStudentByClassId")
	public String findClassStudentByClassId(Student student,HttpServletRequest request,HttpServletResponse response,Model model,String classId){
		if(null == student){
			student = new Student();
			SchoolClass schoolClass = new SchoolClass(classId);
			student.setSchoolClass(schoolClass);
		}
		/*List<Student> reList = schoolClassService.findClassStudentByClassId(student);
		model.addAttribute("studentList", reList);*/
		Page<Student> page = studentServcie.findPage(new Page<Student>(request, response), student);
		model.addAttribute("page", page);
		model.addAttribute("student", student);
		return "modules/questionlib/classStudentList";
	}
	
	/**
	 * 导入学生信息将其保存如table_student 表中
	 * 该excel表格中包含班级的信息(班级编号classNumber)	学校的信息
	 * @param request
	 * @param file
	 * @param response
	 * @param schoolClassId
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequiresPermissions("questionlib:schoolClass:download")
	@RequestMapping("importClassStudents")
	public String importClassStudents(HttpServletRequest request,@RequestParam("classStudentsFile") MultipartFile file,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes){
		String message = "";
		try {
			if(0 == file.getSize()){
				addMessage(redirectAttributes, "请选择要上传的文件");
				return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
			}else{
				ImportExcel excel = new ImportExcel(file, 1, 0);
				//表格中的列 
				List<Student> studentList = excel.getDataList(Student.class);
				if(studentList.size() == 0){
					addMessage(redirectAttributes, "excel文件中学生信息为空，请修改文档");
					return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
				}
				
				//1、判断空值
				for(int i = 0; i < studentList.size(); i++){
					Student student = studentList.get(i);
					if(StringUtils.isBlank(student.getName())){
						message += "第" + (i + 3) + "行数据，学生姓名为空；";
					}else if(StringUtils.isBlank(student.getClassCode())){
						message += "第" + (i + 3) + "行数据，班级名称为空；";
					}else if(StringUtils.isBlank(student.getStdCode())){
						message += "第" + (i + 3) + "行数据，学生学号为空；";
					}else if(StringUtils.isBlank(student.getStdSex())){
						message += "第" + (i + 3) + "行数据，学生性别为空；";
					}else if(StringUtils.isBlank(student.getStdAge())){
						message += "第" + (i + 3) + "行数据，学生年龄为空；";
					}else if(StringUtils.isBlank(student.getStdPhone())){
						message += "第" + (i + 3) + "行数据，学生电话号为空；";
					}else if(StringUtils.isBlank(student.getStdEmail())){
						message += "第" + (i + 3) + "行数据，学生邮箱为空；";
					}else if(StringUtils.isBlank(student.getStdPassword())){
						message += "第" + (i + 3) + "行数据，学生登录密码为空；";
					}else if(StringUtils.isBlank(student.getSpecialtyTitle())){
						message += "第" + (i + 3) + "行数据，学生专业为空；";
					}
				}
				if(StringUtils.isNotBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
				}
				
				for (int i = 0; i < studentList.size(); i++) {
					if(!checkEmail(studentList.get(i).getStdEmail())){
						message += "第" + (i + 3) + "行数据，学生邮箱格式错误；";
					}
					if(!checkPhone(studentList.get(i).getStdPhone())){
						message += "第" + (i + 3) + "行数据，学生手机格式错误；";
					}
				}
				if(StringUtils.isNotBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
				}
				
				//2、判断有无重复
				//2.1：判断文档中的内容有无重复
				HashSet<Student> set = new HashSet<Student>(studentList);
				if(set.size() != studentList.size()){
					addMessage(redirectAttributes, "excel文档,学号重复");
					return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
				}
				//2.2：判断文档中的内容与数据库中的内容有无重复
				//查询数据库中所有学生
				List<Student> dbStudentList = schoolClassService.getStudentListBySchoolId();
				for (int i = 0; i < studentList.size(); i++) {
					Student student = studentList.get(i);
					String stdCode = student.getStdCode();
					for (int j = 0; j < dbStudentList.size(); j++) {
						Student student2 = dbStudentList.get(j);
						String stdCode2 = student2.getStdCode();
						if(stdCode.equals(stdCode2)){
							message += "第" + (i + 3) + "行数据错误，已存在学号为"+stdCode+"的学生了，请修改excel；";
						}
					}
				}
				if(StringUtils.isNotBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
				}
				

				//3、验证这个学校下有没有文档中所填写的班级
				//根据学校id 查询该学校下的所有班级
				int k = 0;
				List<SchoolClass> schoolClassList = studentServcie.getSchoolClassesBySchoolId(UserUtils.getUser().getCompany().getId());
				for (int i = 0; i < studentList.size(); i++) {
					Student student = studentList.get(i);
					String classTitle = student.getClassCode();
					for (int j = 0; j < schoolClassList.size(); j++) {
						SchoolClass schoolClass = schoolClassList.get(j);
						String classTitle2 = schoolClass.getTitle();
						if(classTitle.equals(classTitle2)){
							k++;
						}
					}
					if(k == 0){
						message += "第" + (i + 3) + "行数据错误，本学校下不存在名称为"+ classTitle +"的班级；";
					}else{
						k = 0;
					}
				}
				if(StringUtils.isNotBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
				}
				
				int l = 0;
				//验证有无该专业specialtyTitle
				//根据当前操作人所在学校查询该学校下的所有专业
				List<Student> specialtyList = studentServcie.getSpecialtyListBySchoolId();
				for (int i = 0; i < studentList.size(); i++) {
					Student student = studentList.get(i);
					String specialtyTitle = student.getSpecialtyTitle();
					for (int j = 0; j < specialtyList.size(); j++) {
						Student student2 = specialtyList.get(j);
						String specialtyTitle2 = student2.getSpecialtyTitle();
						if(specialtyTitle.equals(specialtyTitle2)){
							l++;
						}
					}
					if(l == 0){
						message += "第" + (i + 3) + "行数据错误，本学校下不存在名称为"+ specialtyTitle +"的专业；";
					}else{
						l = 0;
					}
				}
				if(StringUtils.isNotBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
				}
				
				//计数
				for (Student student : studentList) {
					student.setId(IdGen.uuid());
					if(student.getStdSex().equals("男")){
						student.setStdSex("1");
					}else if(student.getStdSex().equals("女")){
						student.setStdSex("2");
					}else {
						student.setStdSex("3");
					}
					student.setStdPassword(systemService.entryptPassword(student.getStdPassword()));
					student.setCreateBy(UserUtils.getUser());
					student.setCreateDate(new Date());
					
					for (SchoolClass schoolClass : schoolClassList) {
						if(schoolClass.getTitle().equals(student.getClassCode())){
							student.setSchoolClass(new SchoolClass(schoolClass.getId()));
						}
					}
				}

				int insertNumber = schoolClassService.importStudents(studentList);
				if(insertNumber == studentList.size()){
					//导入成功
					message = "导入学生成功";
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
				}else{
					//导入失败
					message = studentList.size() - insertNumber+"条记录导入失败";
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
				}
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "文档错误，导入学生失败";
			addMessage(redirectAttributes, message);
		}
		return "redirect:"+Global.getAdminPath()+"/questionlib/student/?repage";
	}
	
	/**
	 * 导入班级
	 * 该操作理应由学校管理员进行操作
	 * 导入班级成功后，转跳班级列表
	 * @param request
	 * @param response
	 * @param file
	 */
	@RequestMapping("importClasses")
	public String importClasses(HttpServletRequest request,HttpServletResponse response,@RequestParam("classesFile") MultipartFile file,Model model,RedirectAttributes redirectAttributes){
		try{
			String message = "";
			long size = file.getSize();
			if(size == 0){
				addMessage(redirectAttributes, "请选择要上传的文件");
				return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
			}else{
				ImportExcel ei = new ImportExcel(file, 1, 0);
				List<SchoolClass> list = ei.getDataList(SchoolClass.class);
				if(list.size() == 0){
					addMessage(redirectAttributes, "excel文档中班级信息为空，请修改excel！");
					return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
				}
				
				//验证1：是否为空
				for (int i = 0; i < list.size(); i++) {
					SchoolClass schoolClass = list.get(i);
					String title = schoolClass.getTitle();
					String strStartDate = schoolClass.getStrStartDate();
					String strEndDate = schoolClass.getStrEndDate();
					String specialtyTitle = schoolClass.getSpecialtyTitle();
					if(StringUtils.isBlank(title)){
						message += "第"+ (i + 3) +"行数据错误，班级名称为空；";
					}else if(StringUtils.isBlank(strStartDate)){
						message += "第"+ (i + 3) +"行数据错误，班级创建时间为空；";
					}else if(StringUtils.isBlank(strEndDate)){
						message += "第"+ (i + 3) +"行数据错误，班级毕业时间为空；";
					}else if(StringUtils.isBlank(specialtyTitle)){
						message += "第"+ (i + 3) +"行数据错误，专业为空；";
					}
				}
				if(StringUtils.isNotBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
				}
				
				//验证2：班级名称是否重复
				//2.1：excel文档内有无重复
				HashSet<SchoolClass> set = new HashSet<SchoolClass>(list);
				if(set.size() != list.size()){
					addMessage(redirectAttributes, "班级名称重复，请修改excel文档");
					return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
				}
				//2.2：excel文档中的内容与数据库中的内容有无重复
				//根据学校id查询学校下的所有班级
				List<SchoolClass> dbClassList = schoolClassService.getSchoolClassListBySchoolId();
				for (int i = 0; i < list.size(); i++) {
					SchoolClass schoolClass = list.get(i);
					String title = schoolClass.getTitle();
					for (int j = 0; j < dbClassList.size(); j++) {
						SchoolClass schoolClass2 = dbClassList.get(j);
						String title2 = schoolClass2.getTitle();
						if(title.equals(title2)){
							message += "第"+ (i + 3) +"行数据错误，本学校已存在名为"+title+"的班级了；";
						}
					}
				}
				if(StringUtils.isNotBlank(message)){
					addMessage(redirectAttributes, message);
					return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
				}
				

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				for (SchoolClass schoolClass : list) {
					schoolClass.setId(IdGen.uuid());
					String schoolId = UserUtils.getUser().getCompany().getId();
					schoolClass.setSchool(new Office(schoolId));
					schoolClass.setStartDate(dateFormat.parse(schoolClass.getStrStartDate()));
					schoolClass.setEndDate(dateFormat.parse(schoolClass.getStrEndDate()));
					schoolClass.setCreateBy(UserUtils.getUser());
					schoolClass.setCreateDate(new Date());
					schoolClass.setDelFlag("0");
				}
				int no = schoolClassService.importSchoolClass(list);
				if(no != list.size()){
					addMessage(redirectAttributes, list.size() - no+"条数据 未导入");
					return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
				}else{
					addMessage(redirectAttributes, "导入成功");
					return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入班级失败！");
		}
		return "redirect:"+Global.getAdminPath()+"/questionlib/schoolClass/?repage";
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
    @RequestMapping("checkClassTitle")
    public String checkClassTitle(String oldClassTitle,String title){
    	//根据班级名称查询班级
    	String reClassTitle = schoolClassService.getClassTitleByTitle(title);
    	if(title != null && title.equals(oldClassTitle)){
    		return "true";
    	}else if(title != null && reClassTitle == null){
    		return "true";
    	}
    	return "false";
    }
    
}