/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.common.QuestionlibTld;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.service.CourseQuestionlibService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseService;
import com.thinkgem.jeesite.modules.questionlib.service.CourseVesionService;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolQuestionlibService;
import com.thinkgem.jeesite.modules.questionlib.service.SpecialtyService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 学校数据库Controller
 * @author ryz
 * @version 2016-09-14
 */
@Controller
@RequestMapping(value = "${adminPath}/questionlib/schoolQuestionlib")
public class SchoolQuestionlibController extends BaseController {

	@Autowired
	private SchoolQuestionlibService schoolQuestionlibService;
	
	@Autowired
	private OfficeService officeService;
	@Autowired
	private CourseVesionService courseVesionService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@Autowired
	private CourseQuestionlibService courseQuestionlibService;
	
	@ModelAttribute
	public SchoolQuestionlib get(@RequestParam(required=false) String id) {
		SchoolQuestionlib entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = schoolQuestionlibService.get(id);
		}
		if (entity == null){
			entity = new SchoolQuestionlib();
		}
		
		
		return entity;
	}
	
	@RequiresPermissions("questionlib:schoolQuestionlib:view")
	@RequestMapping(value = {"index",""})
	public String index(Model model) {
		Office office = new Office();
		office.setType("1");	//1代表office类型为机构，2代表部门，学校属于机构类型
		 List<String> List1=new ArrayList<String>();
		 List1.add("1");
		 List1.add("2");
		 Parameter param=  office.getSqlParam();
		   param.put("ids", List1);
		   
			List<Office> schoolList = officeService.findSchool(office);
		 
		model.addAttribute("schoolList", schoolList);
		return "modules/questionlib/schoolQuestionlibIndex";
	}
	
	@RequiresPermissions("questionlib:schoolQuestionlib:view")
	@RequestMapping(value = {"list", ""})
	public String list(SchoolQuestionlib schoolQuestionlib, @RequestParam(required=false) String schoolId, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isNotBlank(schoolId)){
			schoolQuestionlib.setSchoolId(schoolId);
			schoolQuestionlib.setOwnerType("1");
			//schoolQuestionlib.setQuestionlibOwner(UserUtils.getUser().getCompany().getId());
			Page<SchoolQuestionlib> page = schoolQuestionlibService.findPage(new Page<SchoolQuestionlib>(request, response), schoolQuestionlib); 
			model.addAttribute("page", page);
			model.addAttribute("schoolId", schoolId);
		}
		else{
//			Page<SchoolQuestionlib> page = schoolQuestionlibService.findPage(new Page<SchoolQuestionlib>(request, response), schoolQuestionlib); 
//			model.addAttribute("page", page);
		}
		return "modules/questionlib/schoolQuestionlibList";
	}

	@RequiresPermissions("questionlib:schoolQuestionlib:view")
	@RequestMapping(value = "form")
	public String form(SchoolQuestionlib schoolQuestionlib, @RequestParam(required=false) String schoolId, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String questionlibId = "";
		String specialtyId = "";
		String courseId = "";
		String courseVesionId = "";
		try {
			
			if(StringUtils.isNotBlank(schoolId)){
				if(schoolQuestionlib!=null && StringUtils.isNotBlank(schoolQuestionlib.getId())){
						schoolQuestionlib = schoolQuestionlibService.get(schoolQuestionlib);
						if (StringUtils.isNotBlank(schoolQuestionlib.getQuestionlibId())) {
							CourseQuestionlib l = courseQuestionlibService.get(schoolQuestionlib.getQuestionlibId());
							if(l==null){
								model.addAttribute("message", "该题库已被删除！请核对后进行后续操作或重新选择题库！");
//								return list(new SchoolQuestionlib(), schoolId, request, response, model);
							}else{
								CourseVesion v = courseVesionService.get(l.getVersionId());
								Course c = courseService.get(v.getCourseId());
								schoolQuestionlib.setCourse(c);
								schoolQuestionlib.setSpecialty(specialtyService.get(c.getSpecialtyId()));
								schoolQuestionlib.setCourseVesion(v);
							}
						}
						try {
							questionlibId = schoolQuestionlib.getQuestionlibId();
							courseVesionId = QuestionlibTld.getCourseQuestionlibById(questionlibId).getVersionId();
							courseId = QuestionlibTld.getCourseVersionByVersionId(courseVesionId).getCourseId();
							specialtyId = QuestionlibTld.getCourseByID(courseId).getSpecialtyId();
						} catch (Exception e) {
							// TODO: handle exception
						}
						
				}else{
					/*if(QuestionlibTld.getSpecialty().size()>0){
						specialtyId = QuestionlibTld.getSpecialty().get(0).getId();
						if(QuestionlibTld.getCourseBySpecialtyId(specialtyId).size()>0){
							courseId = QuestionlibTld.getCourseBySpecialtyId(specialtyId).get(0).getId();
							if(QuestionlibTld.getCourseVersionByCourseId(courseId).size()>0){
								courseVesionId = QuestionlibTld.getCourseVersionByCourseId(courseId).get(0).getId();
								if(QuestionlibTld.getCourseQuestionlibByVersionId(courseVesionId).size()>0){
									questionlibId = QuestionlibTld.getCourseQuestionlibByVersionId(courseVesionId).get(0).getId();
								}
							}
						}
					}*/
				}
			}else{
				model.addAttribute("message", "请选择学校！");
				return list(new SchoolQuestionlib(), schoolId, request, response, model);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		model.addAttribute("specialtyId", specialtyId);
		model.addAttribute("courseId", courseId);
		model.addAttribute("courseVesionId", courseVesionId);
		model.addAttribute("questionlibId", questionlibId);
		model.addAttribute("schoolId", schoolId);
		model.addAttribute("schoolQuestionlib", schoolQuestionlib);
		return "modules/questionlib/schoolQuestionlibForm";
	}

	@RequiresPermissions("questionlib:schoolQuestionlib:edit")
	@RequestMapping(value = "save")
	public String save(SchoolQuestionlib schoolQuestionlib, @RequestParam(required=false) String schoolId, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, schoolQuestionlib)){
			return form(schoolQuestionlib,schoolId, request, response, model);
		}
		
		schoolQuestionlib.setSchoolId(schoolId);
		
		SchoolQuestionlib findSq =new SchoolQuestionlib();
		findSq.setSchoolId(schoolId);
		findSq.setQuestionlibId(schoolQuestionlib.getQuestionlibId());
		
		List<SchoolQuestionlib> list =schoolQuestionlibService.findList(findSq);
		
		if (StringUtils.isBlank(schoolQuestionlib.getId())) {  //新增题库
			if (list!=null&&list.size()>0) {
			   //重复授权了
				addMessage(redirectAttributes, "此题库已经授权给此学校，不能重复授权！");
				return "redirect:"+Global.getAdminPath()+"/questionlib/schoolQuestionlib/list?repage&schoolId="+schoolId;
			} 
		} 
		if ("3".equals(schoolQuestionlib.getState())) {  //状态为过期，吧时间设置为当前
			 schoolQuestionlib.setValidEndDate(new Date());
		} 
		schoolQuestionlibService.save(schoolQuestionlib);
		addMessage(redirectAttributes, "保存学校数据库成功");
	
		return "redirect:"+Global.getAdminPath()+"/questionlib/schoolQuestionlib/list?repage&schoolId="+schoolId;
	}
	
	@RequiresPermissions("questionlib:schoolQuestionlib:delete")
	@RequestMapping(value = "delete")
	public String delete(SchoolQuestionlib schoolQuestionlib, @RequestParam(required=false) String schoolId, RedirectAttributes redirectAttributes) {
		schoolQuestionlib.setSchoolId(schoolId);
		schoolQuestionlibService.delete(schoolQuestionlib);
		addMessage(redirectAttributes, "删除学校数据库成功");
		return "redirect:"+Global.getAdminPath()+"/questionlib/schoolQuestionlib/list?repage&schoolId="+schoolId;
	}

	@ResponseBody
	@RequestMapping(value = "getSchoolList")
	public List<Map<String, Object>> getSchoolList(@RequestParam(required=false) String type,HttpServletResponse response) {
		Office office = new Office();
		office.setType(type);
		List<Office> list = officeService.findList(office);
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
//			map.put("index", i);
			map.put("id", e.getId());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "getCourseBySpecialtyId")
	public List<Map<String, Object>> getCourseBySpecialtyId(@RequestParam(required=false) String specialtyId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Course> list = QuestionlibTld.getCourseBySpecialtyId(specialtyId);
		Map<String,Object> mapFirst = Maps.newHashMap();
		mapFirst.put("id", "");
		mapFirst.put("value", "请选择");
		mapList.add(mapFirst);
		for (int i=0; i<list.size(); i++){
			Course e = list.get(i);
			{
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("value", e.getTitle());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "getCourseVersionByCouresId")
	public List<Map<String, Object>> getCourseVersionByCouresId(@RequestParam(required=false) String specialtyId, @RequestParam(required=false) String courseId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CourseVesion> list = Lists.newArrayList();
		if(StringUtils.isNotBlank(courseId)){
			list = QuestionlibTld.getCourseVersionByCourseId(courseId);
		}else{
			List<Course> courseList = QuestionlibTld.getCourseBySpecialtyId(specialtyId);
			for (Course course : courseList) {
				list.addAll(QuestionlibTld.getCourseVersionByCourseId(course.getId()));
			}
		}
		Map<String,Object> mapFirst = Maps.newHashMap();
		mapFirst.put("id", "");
		mapFirst.put("value", "请选择");
		mapList.add(mapFirst);
		for (int i=0; i<list.size(); i++){
			CourseVesion e = list.get(i);
			 {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("value", e.getTitle());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "getCourseVersionByCouresId2")
	public List<Map<String, Object>> getCourseVersionByCouresId2(@RequestParam(required=false) String specialtyId, @RequestParam(required=false) String courseId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CourseVesion> list = Lists.newArrayList();
		if(StringUtils.isNotBlank(courseId)){
			list = QuestionlibTld.getCourseVersionByCourseId(courseId);
		}else{
			List<Course> courseList = QuestionlibTld.getCourseBySpecialtyId(specialtyId);
			for (Course course : courseList) {
				list.addAll(QuestionlibTld.getCourseVersionByCourseId(course.getId()));
			}
		}
		Map<String,Object> mapFirst = Maps.newHashMap();
		mapFirst.put("id", "");
		mapFirst.put("value", "请选择");
		mapList.add(mapFirst);
		for (int i=0; i<list.size(); i++){
			CourseVesion e = list.get(i);
			 {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("value", e.getTitle());
				mapList.add(map);
			}
		}
		return mapList;
	}
	@ResponseBody
	@RequestMapping(value = "getCourseQuestionlibByVersionId")
	public List<Map<String, Object>> getCourseQuestionlibByVersionId(@RequestParam(required=false) String versionId, @RequestParam(required=false) String specialtyId, @RequestParam(required=false) String courseId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CourseQuestionlib> list = Lists.newArrayList();
		CourseQuestionlib courseQuestionlib = new CourseQuestionlib();
		//courseQuestionlib.setOwnerType("1");
		courseQuestionlib.setSchoolId(UserUtils.getUser().getCompany().getId());
	/*	if(StringUtils.isNotBlank(versionId)){
			list = QuestionlibTld.getCourseQuestionlibByVersionId(versionId);
		}else {
			if(StringUtils.isNotBlank(courseId)){
				courseQuestionlib.setCourse(new Course(courseId));
				list = courseQuestionlibService.findListByView(courseQuestionlib);
			}else{
				if(StringUtils.isNotBlank(specialtyId)){
					courseQuestionlib.setSpecialty(new Specialty(specialtyId));
					list = courseQuestionlibService.findListByView(courseQuestionlib);
				}
			}
		}*/
		
		Map<String,Object> mapFirst = Maps.newHashMap();
		mapFirst.put("id", "");
		mapFirst.put("value", "请选择");
		if(StringUtils.isNotBlank(versionId)){
			list = QuestionlibTld.getCoursePlatformQuestionlibByVersionId(versionId);
			mapList.add(mapFirst);
			for (int i=0; i<list.size(); i++){
				CourseQuestionlib e = list.get(i);
				if(e!=null)
				 {
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("value", e.getTitle());
					mapList.add(map);
				}
			}
			
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "getId")
	public List<Map<String, Object>> getId(@RequestParam(required=false) String id, @RequestParam(required=false) String type, HttpServletResponse response){
		String returnId = "";
		if("1".equals(type)){
			returnId = QuestionlibTld.getCourseByID(id).getSpecialtyId();
		}else if("2".equals(type)){
			returnId = QuestionlibTld.getCourseVersionByVersionId(id).getCourseId();
		}else if("3".equals(type)){
			returnId = QuestionlibTld.getCourseQuestionlibById(id).getVersionId();
		}else {
			
		}
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		map.put("returnId", returnId);
		mapList.add(map);
		return mapList;
	}
}