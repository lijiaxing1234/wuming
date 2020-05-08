package com.thinkgem.jeesite.modules.teacher.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.UAnswer;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestion;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;
import com.thinkgem.jeesite.modules.teacher.service.BBSService;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

@Controller
@RequestMapping("${teacherPath}/bbs")
public class BBSConstroller extends BaseController {

	@Autowired
	BBSService bbsService;
	
	/**
	 * 问题列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("queslist")
	public String  queslist(Model model,HttpServletRequest request,HttpServletResponse response){
		Page<UQuestion> page=bbsService.findQuestionPage(new Page<UQuestion>(request, response), new UQuestion());
		model.addAttribute("page", page);
		return "teacher/bbs/queslist";
	}
	
	/**
	 * 新建讨论
	 * @param content
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("saveQuesForm")
	public String  saveQuesForm(String content,RedirectAttributes redirectAttributes){
		try{
			UQuestion uQuestion =new UQuestion();
			uQuestion.setDetail(content);
			bbsService.insertQuestion(uQuestion);
		    addMessage(redirectAttributes,"保存成功，请等待审核！");
		}catch(Exception e){
			e.printStackTrace();
			addMessage(redirectAttributes,e.getMessage());
		}
		return "redirect:"+teacherPath+"/bbs/queslist";
	}
	
	
	/**
	 * 回答问题列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("answerlist")
	public String  answerlist(UAnswer answer,Model model,HttpServletRequest request,HttpServletResponse response){
		
		Page<UAnswer> page=bbsService.findAnswerPage(new Page<UAnswer>(request, response),answer);
		model.addAttribute("page", page);
		if(answer.getQuestion()!=null&& StringUtils.isNotBlank(answer.getQuestion().getId()))
		model.addAttribute("ques", bbsService.getQuestionbyId(answer.getQuestion().getId()));
		return "teacher/bbs/answerlist";
	}
	
	/**
	 * 新建回答讨论
	 * @param content
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("saveAnswerForm")
	public String  saveAnswerForm(String content,String quesId,RedirectAttributes redirectAttributes){
		try{
			UAnswer answer=new UAnswer();
			answer.setDetail(content);
			answer.setQuestion(new UQuestion(quesId));
			bbsService.insertAnswer(answer);
		    addMessage(redirectAttributes,"保存成功，请等待审核！");
		}catch(Exception e){
			e.printStackTrace();
			addMessage(redirectAttributes,e.getMessage());
		}
		return "redirect:"+teacherPath+"/bbs/answerlist?question.id="+quesId;
	}
	
	/**
	 * 系统广播消息
	 * 
	 */
	@ResponseBody
	@RequestMapping("getTMsgCount")
	public String getTMsgCount(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		int i = bbsService.getTMsgCount();
		jsonObject.put("msgNo", i);
		return jsonObject.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("getTMsgText")
	public String getTMsgText(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> reList = bbsService.getTMsgText();
		List<String> idList = new ArrayList<String>();
		String msg = "";
		if(reList.isEmpty()){
			msg = "暂无提示消息！";
		}else{
			for (Map<String, Object> map : reList) {
				String mapMsg = (String) map.get("msg");
				msg += " " + mapMsg + "  ";
				idList.add((String) map.get("id"));
			}
		}
		jsonObject.put("msg", msg);
		return jsonObject.toString();
	}
	
	@RequestMapping("getTeacherCharts")
	public String getTeacherCharts(HttpServletRequest request,HttpServletResponse response,String duration){
		if(StringUtils.isBlank(duration)){
			return "teacher/teacherChart";
		}else if("1".equals(duration)){
			return "teacher/teacherChart1";
		}else if("2".equals(duration)){
			return "teacher/teacherChart2";
		}else if("3".equals(duration)){
			return "teacher/teacherChart3";
		}else{
			return "teacher/teacherChart"; 
		}
	}
	
	@ResponseBody
	@RequestMapping("getTeacherChartsData")
	public String getTeacherChartsData(HttpServletRequest request,HttpServletResponse httpServletResponse,String duration){
		JSONObject jsonObject = new JSONObject();
		List<String> legendList = new ArrayList<String>();
		
		List<Map<String, Object>> quizList = bbsService.getTeacherChartsData(duration, "1");  //随堂练习
		JSONArray arrayQuiz = new JSONArray();
		JSONArray arrayQuiz2 = new JSONArray();
		if(quizList == null || quizList.size() == 0){
			jsonObject.put("arrayQuiz", arrayQuiz);
			jsonObject.put("arrayQuiz2", arrayQuiz2);
		}else{
			legendList.add("随堂练习");
			for (Map<String, Object> map : quizList) {
				String month = (String) map.get("month");
				arrayQuiz.add(month);
				jsonObject.put("arrayQuiz", arrayQuiz);
				Long count = (Long) map.get("count");
				arrayQuiz2.add(count);
				jsonObject.put("arrayQuiz2", arrayQuiz2);
			}
		}
		
		
		List<Map<String, Object>> examList = bbsService.getTeacherChartsData(duration, "5");  //在线考试
		JSONArray arrayExam = new JSONArray();
		JSONArray arrayExam2 = new JSONArray();
		if(null == examList || examList.size() == 0){
			jsonObject.put("arrayExam", arrayExam);
			jsonObject.put("arrayExam2", arrayExam2);
		}else{
			legendList.add("在线考试");
			for (Map<String, Object> map : examList) {
				String month = (String) map.get("month");
				arrayExam.add(month);
				jsonObject.put("arrayExam", arrayExam);
				Long count = (Long) map.get("count");
				arrayExam2.add(count);
				jsonObject.put("arrayExam2", arrayExam2);
			}
		}
		
		
		List<Map<String, Object>> homeWorkList = bbsService.getTeacherChartsData(duration, "3");  //课后作业
		JSONArray arrayHomeWork = new JSONArray();
		JSONArray arrayHomeWork2 = new JSONArray();
		if(homeWorkList == null || homeWorkList.size() == 0){
			jsonObject.put("arrayHomeWork", arrayHomeWork);
			jsonObject.put("arrayHomeWork2", arrayHomeWork2);
		}else{
			legendList.add("课后作业");
			for (Map<String, Object> map : homeWorkList) {
				String month = (String) map.get("month");
				arrayHomeWork.add(month);
				jsonObject.put("arrayHomeWork", arrayHomeWork);
				Long count = (Long) map.get("count");
				arrayHomeWork2.add(count);
				jsonObject.put("arrayHomeWork2", arrayHomeWork2);
			}
		}
		
		
		List<Map<String, Object>> exampleList = bbsService.getTeacherChartsData(duration, "4");  //课堂例题
		JSONArray arrayExample = new JSONArray();
		JSONArray arrayExample2 = new JSONArray();
		if(exampleList == null || exampleList.size() == 0){
			jsonObject.put("arrayExample", arrayExample);
			jsonObject.put("arrayExample2", arrayExample2);
		}else{
			legendList.add("课堂例题");
			for (Map<String, Object> map : exampleList) {
				String month = (String) map.get("month");
				arrayExample.add(month);
				jsonObject.put("arrayExample", arrayExample);
				Long count = (Long) map.get("count");
				arrayExample2.add(count);
				jsonObject.put("arrayExample2", arrayExample2);
			}
		}
		JSONArray legendArray = (JSONArray) JSONArray.toJSON(legendList);
		jsonObject.put("legendArray", legendArray);
		return jsonObject.toString();
	}

	/**
	 * 正在进行中的考试 与全部的考试
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getTeacherMain1")
	public String getTeacherMain1(HttpServletRequest request,HttpServletResponse response){
		/*[
         {value:335, name:'进行中'},
         {value:1548, name:'全部'}
        ]*/
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> map = bbsService.getTeacherMain1();
		Long runTimeCount = (Long) map.get("runtimecount");
		Long allcount = (Long) map.get("allcount");
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("value", runTimeCount);
		m1.put("name", "进行中");
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("value", (allcount - runTimeCount));
		m2.put("name", "未进行");
		list.add(m1);
		list.add(m2);
		JSONArray array = (JSONArray) JSONArray.toJSON(list);
		jsonObject.put("data", array);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping("getTeacherMain2")
	public String getTeacherMain2(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//全部作业和未批改的作业
		Map<String, Object> map = bbsService.getTeacherMain2();
		Long allHomeWorkCount = (Long) map.get("allHomeWorkCount");
		Long isMarkCount = (Long) map.get("isMarkCount");
		Map<String, Object> m1 = new HashMap<String, Object>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m1.put("value", isMarkCount);
		m1.put("name", "已批改");
		m2.put("value", (allHomeWorkCount - isMarkCount));
		m2.put("name", "未批改");
		list.add(m1);
		list.add(m2);
		JSONArray array = (JSONArray) JSONArray.toJSON(list);
		jsonObject.put("data", array);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping("getTeacherMain3")
	public String getTeacherMain3(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = bbsService.getTeacherMain3();
		Long allHomeWorkCount = (Long) map.get("allHomeWorkCount");
		Long isMarkCount = (Long) map.get("isMarkCount");
		Map<String, Object> m1 = new HashMap<String, Object>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m1.put("value", isMarkCount);
		m1.put("name", "录入");
		m2.put("value", (allHomeWorkCount - isMarkCount));
		m2.put("name", "未录入");
		list.add(m1);
		list.add(m2);
		JSONArray array = (JSONArray) JSONArray.toJSON(list);
		jsonObject.put("data", array);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping("courseSchedule")
	public String courseSchedule(HttpServletRequest request,HttpServletResponse response){
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = bbsService.teacherCourseSchedule(versionId);
		Long totalCount = (Long) map.get("totalCount");
		Long examCount = (Long) map.get("examCount");
		Map<String, Object> m1 = new HashMap<String, Object>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m1.put("value", examCount);
		m1.put("name", "已进行");
		m2.put("value", (totalCount - examCount));
		m2.put("name", "未进行");
		list.add(m1);
		list.add(m2);
		JSONArray array = (JSONArray) JSONArray.toJSON(list);
		jsonObject.put("data", array);
		return jsonObject.toString();
	}
	
	
	
}
