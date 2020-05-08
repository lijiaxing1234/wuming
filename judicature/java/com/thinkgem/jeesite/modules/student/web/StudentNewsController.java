package com.thinkgem.jeesite.modules.student.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.dto.MessageStudentDTO;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.Edresources;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.TableClass;
import com.thinkgem.jeesite.modules.questionlib.entity.UAnswer;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestion;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestionAnswer;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.CourseKnowledgeService;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.student.entity.DateVo;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.student.service.StudentBbsService;
import com.thinkgem.jeesite.modules.student.service.StudentExamService;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;
import com.thinkgem.jeesite.modules.teacher.dto.Exam2;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.service.ExamService;

/**
 * 学生相关消息controller
 * 1、bbs
 * 
 * @author hanguohui
 *
 */
@Controller
@RequestMapping("${studentPath}/studentNews")
public class StudentNewsController extends BaseController {
	@Autowired
	private StudentBbsService bbsService;
	@Autowired
	private StudentService studentService;
	@Autowired
	ExamService examService;
	@Autowired
	private CourseKnowledgeService courseKnowledgeService;
	@Autowired
	private StudentExamService sExamService;
	
	/**
	 * 获取问答列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("questionList")
	public String getQuestionList (HttpServletRequest request,HttpServletResponse response,Model model){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		Page<UQuestion> page = bbsService.findQuestionPage(new Page<UQuestion>(request, response),new UQuestion());
		model.addAttribute("page", page);
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/bbs/questionList";
	}
	
	@RequestMapping("saveQuesForm")
	public String  saveQuesForm(String content,RedirectAttributes redirectAttributes,Model model){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		try{
			UQuestion uQuestion =new UQuestion();
			uQuestion.setDetail(content);
			bbsService.insertQuestion(uQuestion);
		    addMessage(redirectAttributes,"保存成功！");
		}catch(Exception e){
			e.printStackTrace();
			addMessage(redirectAttributes,e.getMessage());
		}
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "redirect:"+studentPath+"/studentNews/questionList";
	}
	
	@RequestMapping("answerlist")
	public String answerlist(UAnswer answer,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<UAnswer> page=bbsService.findAnswerPage(new Page<UAnswer>(request, response),answer);
		model.addAttribute("page", page);
		if(answer.getQuestion()!=null&& StringUtils.isNotBlank(answer.getQuestion().getId()))
		model.addAttribute("ques", bbsService.getQuestionbyId(answer.getQuestion().getId()));
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/bbs/answerlist";
	}
	
	/**
	 * 根据问答id获取问答的详细信息
	 * 问题及所有回答
	 * @param uQuestionId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("getUQuestionDetail")
	public String getUQuestionDetail(String uQuestionId,HttpServletRequest request,HttpServletResponse response,Model model,UQuestionAnswer formAnswer){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		//查询最新回复的十条讨论
		List<UQuestionAnswer> uQuestionAnswerList = bbsService.getUQuestionDetail(uQuestionId);
		model.addAttribute("uQuestionAnswerList", uQuestionAnswerList);
		UQuestionAnswer answer = new UQuestionAnswer();
		model.addAttribute("answer", answer);
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/bbs/questionAnswerDetail";
	}
	
	@RequestMapping("saveAnswer")
	public String saveAnswer(HttpServletRequest request,HttpServletResponse response,Model model,String detial){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		model.addAttribute("versionId", versionId);
		return null;
	}
	
	/**
	 * 我的成绩查询列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("myGrade")
	public String myGrade (HttpServletRequest request,HttpServletResponse response,Model model,StudentTask studentTask){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		Page<StudentTask> page = studentService.getMyGradePage(new Page<StudentTask>(request, response),studentTask);
		model.addAttribute("page", page);
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		model.addAttribute("studentTask", studentTask);
		return "student/pages/studentGrade";
	}
	
	@RequestMapping("sourcesList")
	public String sourcesList(Model model,HttpServletRequest request, HttpServletResponse response,Edresources edresources){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		//获取该学生所学课程的所有学习资源
		edresources.setVersionId(versionId);
		Page<Edresources> page = studentService.findStudentSourcesPage(new Page<Edresources>(request, response),edresources);
		model.addAttribute("page", page);
		model.addAttribute("edresources", edresources);
		return "student/pages/studentSourcesList";
	}
	
	@RequestMapping("saveAnswerForm")
	public String  saveAnswerForm(String content,String quesId,RedirectAttributes redirectAttributes,Model model){
		try{
			UAnswer answer=new UAnswer();
			answer.setDetail(content);
			answer.setQuestion(new UQuestion(quesId));
			bbsService.insertAnswer(answer);
		    addMessage(redirectAttributes,"保存成功！");
		}catch(Exception e){
			e.printStackTrace();
			addMessage(redirectAttributes,e.getMessage());
		}
		model.addAttribute("user", StudentUserUtils.getUser());
		return "redirect:"+studentPath+"/studentNews/answerlist?question.id="+quesId;
	}
	
	@RequestMapping("gradeChange")
	public String gradeChange(Model model,HttpServletRequest request ,HttpServletResponse response){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		model.addAttribute("versionId", versionId);
		model.addAttribute("student", StudentUserUtils.getUser());
		model.addAttribute("user", StudentUserUtils.getUser());
		Exam exam = new Exam();
		Student student = studentService.get(StudentUserUtils.getUser().getId());
		exam.setExamClass(new TableClass(student.getSchoolClass().getId()));
		model.addAttribute("exam", exam);
		 Integer countPerson=examService.getPersonTotal(student.getSchoolClass().getId());//全班总人数
		 model.addAttribute("countPerson", countPerson);
		return "student/pages/studentGradeChange";
	}
	
	@RequestMapping("indexGradeChange")
	public String indexGradeChange(Model model,HttpServletRequest request,HttpServletResponse response){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		model.addAttribute("versionId", versionId);
		model.addAttribute("student", StudentUserUtils.getUser());
		model.addAttribute("user", StudentUserUtils.getUser());
		Exam exam = new Exam();
		Student student = studentService.get(StudentUserUtils.getUser().getId());
		exam.setExamClass(new TableClass(student.getSchoolClass().getId()));
		model.addAttribute("exam", exam);
		 Integer countPerson=examService.getPersonTotal(student.getSchoolClass().getId());//全班总人数
		 model.addAttribute("countPerson", countPerson);
		return "student/pages/indexStudentGradeChange";
	}
	
	@RequestMapping("indexGradeChange1")
	public String indexGradeChange1(Model model,HttpServletRequest request,HttpServletResponse response){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		model.addAttribute("versionId", versionId);
		model.addAttribute("student", StudentUserUtils.getUser());
		model.addAttribute("user", StudentUserUtils.getUser());
		Exam exam = new Exam();
		Student student = studentService.get(StudentUserUtils.getUser().getId());
		exam.setExamClass(new TableClass(student.getSchoolClass().getId()));
		model.addAttribute("exam", exam);
		 Integer countPerson=examService.getPersonTotal(student.getSchoolClass().getId());//全班总人数
		 model.addAttribute("countPerson", countPerson);
		return "student/pages/indexStudentGradeChange1";
	}
	
	@RequestMapping("indexGradeChange2")
	public String indexGradeChange2(Model model,HttpServletRequest request,HttpServletResponse response){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		model.addAttribute("versionId", versionId);
		model.addAttribute("student", StudentUserUtils.getUser());
		model.addAttribute("user", StudentUserUtils.getUser());
		Exam exam = new Exam();
		Student student = studentService.get(StudentUserUtils.getUser().getId());
		exam.setExamClass(new TableClass(student.getSchoolClass().getId()));
		model.addAttribute("exam", exam);
		 Integer countPerson=examService.getPersonTotal(student.getSchoolClass().getId());//全班总人数
		 model.addAttribute("countPerson", countPerson);
		return "student/pages/indexStudentGradeChange2";
	}
	
	@RequestMapping("indexGradeChange3")
	public String indexGradeChange3(Model model,HttpServletRequest request,HttpServletResponse response){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		model.addAttribute("versionId", versionId);
		model.addAttribute("student", StudentUserUtils.getUser());
		model.addAttribute("user", StudentUserUtils.getUser());
		Exam exam = new Exam();
		Student student = studentService.get(StudentUserUtils.getUser().getId());
		exam.setExamClass(new TableClass(student.getSchoolClass().getId()));
		model.addAttribute("exam", exam);
		 Integer countPerson=examService.getPersonTotal(student.getSchoolClass().getId());//全班总人数
		 model.addAttribute("countPerson", countPerson);
		return "student/pages/indexStudentGradeChange3";
	}
	
	@ResponseBody
	@RequestMapping("personScoreList")
	public String personScoreList(Exam exam,HttpServletRequest request, HttpServletResponse response,String duration) throws Exception{
		String studentId = StudentUserUtils.getUser().getId();
		//cookie中的教师id以及版本id
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		request.setCharacterEncoding("UTF-8");    //设定客户端提交给servlet的内容按UTF-8编码
        response.setCharacterEncoding("UTF-8");    //设定servlet传回给客户端的内容按UTF-8编码
        response.setContentType("text/html;charset=UTF-8");    //告知浏览器用UTF-8格式解析内容
		//获得所有测试
//		List<Exam> examList = examService.getExamByTeacherIdAndVersionIdList(exam,userId,versionId);
        List<Exam2> examList = studentService.getStudentGradChange(studentId,versionId,duration);
      /*  String ranking=null;
        for (Exam2 exam2 : examList) {
			if(exam2!=null){
				String examId=exam2.getId();
				ranking=examService.getAllExamAndAllStudentScore(examId,studentId,StudentUserUtils.getUser().getSchoolClass().getId());
				exam2.setRanking(ranking);
			}
		}
        return examList;*/
        List<Map<String,Object>>  list =Lists.newArrayList();
        Map<String,Object> map=null;
        Integer countPerson=examService.getPersonTotal(StudentUserUtils.getUser().getSchoolClass().getId());//全班总人数
		
        try{
	        for (Exam2 exam2 : examList) {
				if(exam2!=null){
					map=Maps.newHashMap();
					
					 String examId=exam2.getId();
					 String ranking=examService.getAllExamAndAllStudentScore(examId,studentId,StudentUserUtils.getUser().getSchoolClass().getId());
					 String[] split = ranking.split(",");
					 exam2.setRanking(split[0]);
					 exam2.setTotal_score(split[1]);
					 
					 map.put("title", exam2.getTitle());
					 map.put("ranking", exam2.getRanking());
					 map.put("totalScore", (StringUtils.isBlank(exam2.getTotal_score()) || "null".equals(exam2.getTotal_score())) ? 0 : exam2.getTotal_score());
					 map.put("count", countPerson);
					 list.add(map);
				}
			}
        }catch(Exception e){
			 
		}
        return JSON.toJSONString(list);
        
        
        
//		List<ExamScoreDTO> list=new ArrayList<ExamScoreDTO>();
//		
//		for (Exam exam2 : examList) {
//			ExamScoreDTO examScoreDTO=new ExamScoreDTO();
//			if(exam2!=null && exam2.getId()!=null && !(("").equals(exam2.getId())) && exam2.getTitle()!=null && !(("").equals(exam2.getTitle()))){
//				String str=exam2.getTitle();
//				examScoreDTO.setExamTitle(str);
//			}
//			if(exam2!=null && exam2.getId()!=null && !(("").equals(exam2.getId()))){
//				String examId=exam2.getId();
//				String score = examService.studentTotalScore(studentId,examId);//获取测试的总分
//				if(score==null||("").equals(score)){
//					examScoreDTO.setScore("0");
//				}else{
//					examScoreDTO.setScore(score);
//				}
//				
//			}
//			list.add(examScoreDTO);
//		}
//		
//		String json2 = (String)JSON.toJSONString(list);
//		//将json数据返回给客户端
//        response.setContentType("text/html; charset=utf-8");
//        response.getWriter().write(json2);
	}
	
	
	@RequestMapping(value = {"knowledgePointsList", ""})
	public String knowledgePointsList(CourseKnowledge courseKnowledge, String parentIds,   String id, HttpServletRequest request, HttpServletResponse response, Model model,VersionQuestion question) {
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		Map<String, String> sqlMap = question.getSqlMap();
		if(StringUtils.isBlank(parentIds)){
			parentIds = "";
		}
		sqlMap.put("parentIds", parentIds);
		sqlMap.put("versionId", versionId);
		sqlMap.put("studentId", StudentUserUtils.getUser().getId());
		if(StringUtils.isBlank(id)){
			id = null;
		}
		sqlMap.put("id", id);
		Page<VersionQuestion> page = sExamService.findMyQuestionLib(new Page<VersionQuestion>(request, response), question);
		model.addAttribute("page", page);
		model.addAttribute("question", question);
		model.addAttribute("versionId", versionId);
		model.addAttribute("user", StudentUserUtils.getUser());
		return "student/pages/myQuestionLibList";
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,@RequestParam(required=false) String courseVesionId,HttpServletResponse response) {
		courseVesionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		//验证前台数据
		if("请选择".equals(courseVesionId)){
			return null;
		}else{
			List<Map<String, Object>> mapList = Lists.newArrayList();
			CourseKnowledge ckl =new CourseKnowledge();
			ckl.setVersionId(courseVesionId);
			List<CourseKnowledge> list = courseKnowledgeService.findList(ckl);
			list.add(courseKnowledgeService.get("1"));
			for (int i=0; i<list.size(); i++){
				CourseKnowledge e = list.get(i);
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getTitle());
				mapList.add(map);
			}
			return mapList;
		}
	}
	
	@ResponseBody
	@RequestMapping("getStudentMsg")
	public String getStudentMsg(HttpServletRequest request,HttpServletResponse response,Model model){
		List<MessageStudentDTO> list = studentService.getStudentMsg();
		JSONObject jsonObject = new JSONObject();
		String msg = "";
		if(list.isEmpty()){
			msg = "暂无提示消息";
		}else{
			for (MessageStudentDTO obj : list) {
				msg += "<p>关于"+obj.getTeacherName()+"老师，"+obj.getExamTitle()+"考试的提示消息："+obj.getMessage()+"</p>";
			}
		}
		jsonObject.put("msg", msg);
		jsonObject.put("msgNo", list.size());
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping("getStudentMsgCount")
	public String getStudentMsgCount(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		int i = studentService.getStudentMsgCount(); //提示消息数量
		jsonObject.put("msgNo", i);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping("getExercisesCount")
	public String getExercisesCount(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = studentService.getExercisesCount();
		Long allQuestionsCount = (Long) map.get("allQuestionsCount");
		Long exercisesCount = (Long) map.get("exercisesCount");
		//正确率
		Map<String, Object> m1 = new HashMap<String, Object>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m1.put("value", exercisesCount);
		m1.put("name", "正确率");
		m2.put("value", allQuestionsCount);
		m2.put("name", "错误率");
		list.add(m1);
		list.add(m2);
		JSONArray array = (JSONArray) JSONArray.toJSON(list);
		jsonObject.put("data", array);
		return jsonObject.toString();
	}
	
//	@ResponseBody
//	@RequestMapping("someKnowledgeStatistic")
//	public String someKnowledgeStatistic(HttpServletRequest request,HttpServletResponse response){
//		JSONObject jsonObject = new JSONObject();
//		List<Long> yList = new ArrayList<Long>();
//		List<String> xList = new ArrayList<String>();
//		List<DateVo> reList = studentService.getStudentPassingRate();
//		for (int i = 0; i < reList.size(); i++) {
//			Long passingRate = reList.get(i).getPassingRate();
//			yList.add(passingRate);
//			String examCode = reList.get(i).getExamCode();
//			xList.add(examCode);
//		}
//		JSONArray yArray = (JSONArray) JSONArray.toJSON(yList);
//		JSONArray xArray = (JSONArray) JSONArray.toJSON(xList);
//		jsonObject.put("yArray", yArray);
//		jsonObject.put("xArray", xArray);
//		return jsonObject.toString();
//	}
	
	@ResponseBody
	@RequestMapping("courseSchedule")
	public String courseSchedule(HttpServletRequest request,HttpServletResponse response){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = studentService.courseSchedule(versionId);
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
	
	@ResponseBody
	@RequestMapping("getStudentGradeChange")
	public String getStudentGradeChange(HttpServletRequest request,HttpServletResponse response){
		String versionId = (String) CacheUtils.get(StudentUserUtils.getUser().getId()+"versionId");
		//查询 某学生 某版本 所有的考试在班级内的排名变化
		JSONObject jsonObject = new JSONObject();
		List<Object> list = new ArrayList<Object>();
		List<Object> list2 = new ArrayList<Object>();
		//1、 查询某学生某版本的所有的考试及分数
		List<Map<String, Object>> allExamAndScore = studentService.getStudentGradeChange(versionId);
		for (Map<String, Object> map : allExamAndScore) {
			Long studentRanking = studentService.getStudentRanking(map);
			list.add(map.get("examName"));
			list2.add(studentRanking);
		}
		JSONArray array = (JSONArray) JSONArray.toJSON(list);
		JSONArray array2 = (JSONArray) JSONArray.toJSON(list2);
		jsonObject.put("array", array);
		jsonObject.put("array2", array2);
		return jsonObject.toString();
	}
	
	
//	@RequestMapping("knowledgePassingRate")
//	public String knowledgePassingRate(HttpServletRequest request,HttpServletResponse response,Model model){
//		List<DateVo> reList = studentService.getStudentPassingRate();
//		model.addAttribute("reList", reList);
//		return "student/pages/studentKnowledgePassingRatePage";
//	}

}