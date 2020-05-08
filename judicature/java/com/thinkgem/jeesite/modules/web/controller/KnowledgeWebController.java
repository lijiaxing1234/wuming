package com.thinkgem.jeesite.modules.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.service.KnowledgeWebService;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.ReturnData;

@RequestMapping("/web/knoledge")
@Controller
public class KnowledgeWebController {

	@Autowired
	KnowledgeWebService knowledgeWebService;
	
	
	/**
	 * 通过课程Id查询 课程下知识点 章节集合
	 * 统计当前课程下用户答题统计
	 * 
	 * @param courseId  课程Id
	 * @param userId    用户Id
	 * @return
	 */
	@Deprecated
	@RequestMapping("knowledgeByCourseId")
	@ResponseBody
	public String getKnowledgeByCourseId(String courseId,String userId){
		ReturnData returnData = new ReturnData();
		try {
			Course course = knowledgeWebService.getKnowledgeByCourseId(courseId,userId);
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
	
	@RequestMapping("addUserKnowledgeInfo")
	@ResponseBody
	public String addUserKnowledgeInfo(Knowledge knowledge){
		boolean b = false;
		boolean hasUserKnowledgeInfo = knowledgeWebService.hasUserKnowledgeInfo(knowledge);
		if (hasUserKnowledgeInfo == false) {
			b = knowledgeWebService.addUserKnowledgeInfo(knowledge);
		}else {
			b = knowledgeWebService.updateUserKnowledgeInfo(knowledge);
		}
		String json = JsonUtil.toJson(b);
		return json;
	}
}
