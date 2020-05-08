package com.thinkgem.jeesite.modules.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.service.CourseWebService;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.ReturnData;

@RequestMapping("/web/course")
@Controller
public class CourseWebController {

	@Autowired
	CourseWebService courseService;
	
	@RequestMapping("all")
	@ResponseBody
	public String getAll(){
		ReturnData returnData = new ReturnData();
		try {
			List<Course> list = courseService.getAllCourse();
			if (list.size()>0) {
				returnData.setData(list);
				returnData.setStatus(true);
			}else {
				returnData.setData(new HashMap<Object, Object>());
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	
	
	
	/**
	 * 通过课程Id查询 课程下知识点 章节集合
	 * 统计当前课程下用户答题统计
	 * 统计章节用户答题统计
	 * 
	 * @param courseId  课程Id
	 * @param userId    用户Id
	 * @return 课程实体
	 */
	@RequestMapping("getCourseById")
	@ResponseBody
	public String getCourseById(String courseId,String userId){
		ReturnData returnData = new ReturnData();
		try {
			Course course = courseService.getCourseById(courseId,userId);
			if (course!=null) {
				returnData.setData(course);
				returnData.setStatus(true);
			}else {
				returnData.setData(new Course());
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	
}
