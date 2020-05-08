package com.thinkgem.jeesite.modules.questionlib.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.common.QuestionlibTld;
import com.thinkgem.jeesite.modules.questionlib.dto.MultipleSelect;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.SCourseVersion;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.service.CourseService;
import com.thinkgem.jeesite.modules.questionlib.service.SCourseVersionService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.web.util.SystemUtils;

@Controller
@RequestMapping(value = "${adminPath}/questionlib/common")
public class CommonController extends BaseController{

	@Autowired
	private CourseService courseService;
	
	@ResponseBody
	@RequestMapping(value = {"getCourseJSONBySpecialtyId"})
	public String getCourseJSONBySpecialtyId(String specialtyId, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String,Object>> newList=Lists.newArrayList();
		
		if(StringUtils.isNoneBlank(specialtyId)){
			Course course = new Course();
			course.setSpecialtyId(specialtyId);
			List<Course> list = courseService.findList(course);
			if(list!=null){
		        for(Course c:list){
		        	Map<String,Object> map=Maps.newHashMap();
		        	map.put("id", c.getId());
		        	map.put("title",c.getTitle());
		        	newList.add(map);
		        }  
			}
		}
		return JSON.toJSONString(newList);
	}

	
	
	@ResponseBody
	@RequestMapping(value = {"getCourseBySpecialtyId"})
	public String getCourseBySpecialtyId(String specialtyId,HttpServletRequest request, HttpServletResponse response) {
		List<Map<String,Object>> newList=Lists.newArrayList();
		
		if(StringUtils.isNotBlank(specialtyId)){
			Course course = new Course();
			course.setSpecialtyId(specialtyId);
			List<Course> list = courseService.findList(course);
			if(list!=null){
		        for(Course c:list){
		        	Map<String,Object> map=Maps.newHashMap();
		        	map.put("id", c.getId());
		        	map.put("name",c.getTitle());
		        	newList.add(map);
		        }  
			}
		}
		return JSON.toJSONString(newList);
	}
	@ResponseBody
	@RequestMapping(value = "getCourseVersionByCouresId")
	public String getCourseQuestionByCouresId(String courseId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		if(StringUtils.isNotBlank(courseId)){
			List<CourseVesion> list = QuestionlibTld.getCourseVersionByCourseId(courseId);
			for (int i=0; i<list.size(); i++){
				CourseVesion e = list.get(i);
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("name", e.getTitle());
				mapList.add(map);
			}
		}
		return JSON.toJSONString(mapList);
	}
	
	
	
	
	/*************学校课程、课程版本 查询**********************/
	
	@Autowired
	private SCourseVersionService sCourseVersionService;
	
	@ResponseBody
	@RequestMapping(value = {"getSchoolCourseBySpecialtyId"})
	public String getSchoolCourseBySpecialtyId(String specialtyId,HttpServletRequest request, HttpServletResponse response) {
		List<Map<String,Object>> newList=Lists.newArrayList();
		
		if(StringUtils.isNotBlank(specialtyId)){
			List<Specialty> reList = sCourseVersionService.getCoursesBySpecialtyId(specialtyId);
			if(reList!=null){
		        for(Specialty s:reList){
		        	Map<String,Object> map=Maps.newHashMap();
		        	map.put("id", s.getId());
		        	map.put("name",s.getTitle());
		        	newList.add(map);
		        }  
			}
		}
		return JSON.toJSONString(newList);
	}
	
	@ResponseBody
	@RequestMapping(value = "getSchoolCourseVersionByCouresId")
	public String getSchoolCourseVersionByCouresId(String courseId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		if(StringUtils.isNotBlank(courseId)){
			List<SCourseVersion> reList = sCourseVersionService.getVersionsByCourseIdAndSchoolId(courseId);
			for (int i=0; i<reList.size(); i++){
				SCourseVersion e = reList.get(i);
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("name", e.getTitle());
				mapList.add(map);
			}
		}
		return JSON.toJSONString(mapList);
	}
	
	/*学校端 选中的 专业、课程、课程版本  三级查询*/
	
	
	@ResponseBody
	@RequestMapping("findSchoolSpecialty")
	public  String   findSchoolSpecialty(){
		List<Map<String, Object>> mapList =null;
		mapList=sCourseVersionService.findSchoolSpecialty();
		if(mapList==null){
			mapList=Lists.newArrayList();
		}
		return JSON.toJSONString(mapList);
	}
	
	@ResponseBody
	@RequestMapping("findSchoolCourseBySpecialtyId")
	public String  findSchoolCourseBySpecialtyId(String specialtyId ){
		List<Map<String, Object>> mapList =null;
		mapList=sCourseVersionService.findSchoolCourseBySpecialtyId(specialtyId);
		if(mapList==null){
			mapList=Lists.newArrayList();
		}
		return JSON.toJSONString(mapList);
		
	}
	
	@ResponseBody
	@RequestMapping("findSchoolCourseVersionByCourseId")
	public String findSchoolCourseVersionByCourseId(String courseId){	
		List<Map<String, Object>> mapList =null;
		mapList=sCourseVersionService.findSchoolCourseVersionByCourseId(courseId);
		if(mapList==null){
			mapList=Lists.newArrayList();
		}
		return JSON.toJSONString(mapList);
	}
	
	@RequestMapping("toupiosStatus")
	public String toupiosStatus(){	
		return "modules/iosStatus/status";
	}
	
	@RequestMapping("upiosStatus")
	@ResponseBody
	public String upiosStatus(String s,HttpServletResponse response){
		String profilepath= SystemUtils.getClassPath()+File.separator+"uploadpath.properties";
		Properties props = new Properties();
		try {
            props.load(new FileInputStream(profilepath));
            OutputStream fos = new FileOutputStream(profilepath);           
            props.setProperty("iosStatus", s);
            props.store(fos, null);
            String a = "1";
			return a;
        } catch (IOException e) {
        	String a = "0";
			return a;
        }
	}
	
}
