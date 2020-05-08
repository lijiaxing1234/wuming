package com.thinkgem.jeesite.modules.teacher.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.teacher.Constants;
import com.thinkgem.jeesite.modules.teacher.entity.ExamClass;
import com.thinkgem.jeesite.modules.teacher.entity.ExamKnowledgeQuestion;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.entity.Examknowledge;
import com.thinkgem.jeesite.modules.teacher.entity.KnowledgeQuestionDetail;
import com.thinkgem.jeesite.modules.teacher.entity.QuestionslibSplit;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;
import com.thinkgem.jeesite.modules.teacher.service.QuestionslibSplitService;
import com.thinkgem.jeesite.modules.teacher.service.TestPaperService;
/**
 * 首页的课后作业管理
 */
@Controller
@RequestMapping("${teacherPath}/examinationHomeWork")
public class ExaminationHomeWorkController extends BaseController {
	
	
	@Autowired
	ExaminationService examService;
	@Autowired
	TestPaperService testPaperServie;
	@Autowired
	QuestionslibSplitService queslibSplitService;
	
	//@ModelAttribute("totalQuesCount")
	public Long  inited(){
		Double percent=queslibSplitService.getPracticePercentByTeacher();
		if(percent !=null){
			queslibSplitService.updatePracticePercentByTeacher(percent+"");
		}else{
			percent=100D;
			queslibSplitService.insertPracticePercentByTeacher(percent+"");//默认100
		}
		QuestionslibSplit qls1=queslibSplitService.getQuestionslibSplitWithTeacherInfo();
		qls1.getSqlParam().put("type", Arrays.asList("0","1"));
		Long number= queslibSplitService.countQuestionslibSplitByteacher(qls1);
		return number !=null ? number:0L; //分库后 考试 总数
	}
	
	
	@ModelAttribute
	public Examination get(String id){
		Examination exam=null;
		if(StringUtils.isNotBlank(id)){
			exam=examService.getExam(new Examination(id));
		}
		if(exam==null){
			exam=new Examination();
		}
		return exam;
	}
	/**
	 * 选择知识点
	 */
	@RequestMapping(value="selectKnowledge")
	public String selectKnowledge(Examination examination,Model model){
		model.addAttribute("totalQuesCount", inited());
		List<String> examknowledgesIds=examService.getknowledgeIdsByExamId(examination.getId());
		model.addAttribute("examknowledgesIds",Collections3.convertToString(examknowledgesIds,","));//选中的知识点Ids
		model.addAttribute("examination", examination);
		model.addAttribute("examknowledge", new Examknowledge());//支持form:from 标签
		return "teacher/examination/selectHomeWorkKnowledge";
	}
	/**
	 * 保存知识点
	 * 并转到出题细节页面
	 */
	@RequestMapping(value="selectKnowledge",method=RequestMethod.POST)
	public String saveSelectKnowledge(Examknowledge examknowledge,Model model){
		String examId="";
		Examination  exam= examknowledge.getExam();
		if(exam!=null&&StringUtils.isNotBlank(exam.getId())){
			examId=exam.getId();
		}else{
			 exam.setCreateDate(new Date());
			 exam.setExamType(Constants.EXAMINATION_EXAMTYPE_OPERATION);
			 examId=examService.saveExamination(exam).getId();
		 }
		Examination ex=get(examId);
		if(ex!=null&&StringUtils.isNotBlank(ex.getId())){
			examService.addExamknowledgeAndUpdateExam(examknowledge);
		}
		//1 手动    0自动  
		if(examknowledge.getExam()!=null&&("1").equals(examknowledge.getExam().getExamMode())){
			return "redirect:"+teacherPath+"/examinationHomeWork/examHandleDetails?id="+examId;//跳转至手动组卷
		}else{
			return "redirect:"+teacherPath+"/examinationHomeWork/examDetails?id="+examId;//跳转至自动组卷
		}
	}
	/**
	 * 确认手动出卷的作业出题细节
	 */
	@RequestMapping(value="examHandleDetails",method=RequestMethod.GET)
	public String examAutoDetails(Examination examination,Model model){
		if(StringUtils.isNotBlank(examination.getId())){
			List<ExamKnowledgeQuestion> list=examService.findExamKnowledgeQuestionList(examination.getId(),true);//查询知识点下试题(每题多少分、多少道试题)
			List<KnowledgeQuestionDetail> sourceList=examService.findExamKnowledgeQuestionByExamId(examination.getId());//用于回显到页面（上一步，下一步）
			model.addAttribute("list", list);
			model.addAttribute("sourceList",sourceList);
		}
		model.addAttribute("examKnowledgeQuestion", new ExamKnowledgeQuestion());
		/*Examknowledge examKonwledge=new Examknowledge();
		examKonwledge.setExam(exam);
		Page<Examknowledge> page=examService.queryExamKnowleByExamId(new Page<Examknowledge>(request,response),examKonwledge);
		model.addAttribute("examList", page);*/
		model.addAttribute("examination", examination);
		return "teacher/examination/examHomeWorkHandleDetails";
	}
	/**
	 * 确认手动出卷的作业出题细节
	 * 并跳转页面
	 */
	@RequestMapping(value="examHandleDetails",method=RequestMethod.POST)
	public String examAutoDetailsPost(Examination examination,String knowledgeQuestions,String classDataRelation,Model model,HttpServletRequest request) throws Exception{
		/*Examination exam=new Examination();
		Date d=sim.parse(time);
		exam.setBeginTime(d);
		exam.setId(id);
		exam.setTitle(title);*/
		/*if(examination!=null){
			 Date d=examination.getPublishAnswerTime();
			 if(d==null){
				Calendar calendar = Calendar.getInstance();
		        Date date = new Date(System.currentTimeMillis());
		        calendar.setTime(date);
		        calendar.add(Calendar.YEAR, +100);
		        date = calendar.getTime();
		        examination.setPublishAnswerTime(date);
			 }
		 }*/
		examination.setIsAb(Constants.EXAMINATION_ISAB_NO);//设置试卷类型为一套卷
		examService.saveExamClass2(examination,classDataRelation);
		examination.setExamType(Constants.EXAMINATION_EXAMTYPE_OPERATION);
		examService.updateExam(examination);
		if(!examService.saveExamHandleDetails(examination, knowledgeQuestions)){
			 addMessage(model, "没有符合条件的试题,请调整条件后重试。");
			return examAutoDetails(examination,model);
		}
		String examId=examination.getId();
		 return "redirect:/t/examination/adjustExam?id="+examId;
	}
	/**
	 * 设置手动出题的细节
	 */

	/*@RequestMapping(value="examHandleDetails2")
	public void examHandleDetails2(String examId,String questionType,String questionNumber,String courseKnowledgeId,String questionDegree){
		KnowledgeQuestionDetail knowledge=new KnowledgeQuestionDetail();
		knowledge.setExamId(examId);
		knowledge.setQuestionType(questionType);
		knowledge.setQuestionNumber(questionNumber);
		knowledge.setKnowledgeId(courseKnowledgeId);
		knowledge.setQuestionDegree(questionDegree);
		examService.addKnowledgeQuestionDetail(knowledge);
	}*/
	
	/**
	 * 确认自动出卷的考试细节
	 */
	@RequestMapping(value="examDetails",method=RequestMethod.GET)
	public String examDetails(Examination examination,Model model){
		
		if(StringUtils.isNotBlank(examination.getId())){
			model.addAttribute("examQuestionScoreList", examService.findExamQuestionScoreExamId(examination.getId()));
		}
		model.addAttribute("quesMap", examService.statisticsVersionQuestionTypeCount(examination));
		model.addAttribute("examination", examination);
		return "teacher/examination/examHomeWorkDetails";
	}
	
	/**
	 *确认自动出卷的作业出题细节
	 * 并转到题目调整页面
	 * @throws Exception 
	 */
	@RequestMapping(value="examDetails",method=RequestMethod.POST)
	public String saveExamDetails(Examination examination,String classDataRelation,Model model,HttpServletRequest request) throws Exception{
		/*if(examination!=null){
			 Date d=examination.getPublishAnswerTime();
			 if(d==null){
				Calendar calendar = Calendar.getInstance();
		        Date date = new Date(System.currentTimeMillis());
		        calendar.setTime(date);
		        calendar.add(Calendar.YEAR, +100);
		        date = calendar.getTime();
		        examination.setPublishAnswerTime(date);
			 }
		 }*/
		 //存储各种题型的分数
		examService.saveExamQuestionScore(request);
		/*SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d=sim.parse(time);
		Date endTime=sim.parse(answerTime);
		examination.setBeginTime(d);
		examination.setEndTime(endTime);*/
		examService.saveExamClass2(examination,classDataRelation);
		String examId="";
		if(StringUtils.isNotBlank(examination.getId())){
			examId=examination.getId();
		}
		if(!examService.saveHomeWorkExamination(examination)){
			 addMessage(model, "没有符合条件的试题,请调整条件后重试。");
			 return examDetails(examination,model);
		 }
	    return "redirect:/t/examination/adjustExam?id="+examId;
	}
	/**
	 * 题目微调
	 *//*
	@RequestMapping(value="examHomeWorkTitle",method=RequestMethod.GET)
	public String examHomeWorkTitle(Examination examination,String examId,Model model){
		if(examId!=null&&!(("").equals(examId))){
			examination=get(examId);
		}
		String examIds=examination.getId();
		String[] split = examIds.split(",");
		if(split.length>=1){
			examination.setId(split[0]);
		}
		model.addAttribute("examination", examination);
		model.addAttribute("examdetails",examService.getExamDetailByExamId(examination));
		return "teacher/examination/examHomeWorkTitle";
	}
	*//**
	 * 题目调整列表
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping("adjustExamList")
	public  String  adjustExamList(String examdetailId,String examId,Date beginTime,Date endTime,String examType, Model model,HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		List<ExamdetailQuestion> list2 = (List<ExamdetailQuestion>)session.getAttribute("list2");
		if(examId!=null&&!(("").equals(examId))){
			String[] split = examId.split(",");
			if(split.length>1){
				examId=split[0];
			}
		}
		List<ExamdetailQuestion> list= new ArrayList<ExamdetailQuestion>();
		list=examService.getExamdetailQuestionByExamdetailId(examdetailId,beginTime,endTime,examType);
		model.addAttribute("list", list);
		
		Map<String,List<ExamdetailQuestion>> dataMap=examService.getExamdetailQuestionByExamdetailIdGroupbyQuesType(examdetailId,beginTime,endTime,examType);
		model.addAttribute("dataMap", dataMap);
		
		model.addAttribute("examId", examId);
		model.addAttribute("examdetailId", examdetailId);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("examType", examType);
		return "teacher/examination/examHomeWorkAdjustExamList2";
	}
	*//**
	 * 保存排序
	 *//*
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts,String examdetailId,String examId, RedirectAttributes redirectAttributes) {
    	for (int i = 0; i < ids.length; i++) {
    		ExamdetailQuestion eq=new ExamdetailQuestion();
    		eq.setQuestion(new VersionQuestion(ids[i]));
    		eq.setExamdetail(new Examdetail(examdetailId));
    		eq.setSort(sorts[i]);
    		
    		examService.updateExamDetailQuesSort(eq);
    	}
    	addMessage(redirectAttributes, "保存菜单排序成功!");
		return "redirect:" + teacherPath + "/examinationHomeWork/adjustExamList?examdetailId="+examdetailId+"&examId="+examId;
	}
	*//**
	 * 保存课后作业
	 *//*
	@RequestMapping(value="saveHomeWorkExam")
	public String saveHomeWorkExam(String examId,String title,HttpServletRequest request,HttpServletResponse response){
		Examination examination = get(examId);
		examService.updateExamination(examination);
		HttpSession session = request.getSession();
		String classDataRelation=(String)session.getAttribute("ids");
		if(classDataRelation!=null){
			String[] classArr=classDataRelation.split(",");
			for (String examClassId : classArr) {
				testPaperServie.save(examId,examClassId);
			}
		}
		 return "redirect:" + teacherPath + "/exam/examList";
	}
	*//**
	 * 发布课后作业
	 *//*
	@RequestMapping(value="publishHomeWorkExam")
	public String publishHomeWorkExam(String examId,String title,HttpServletRequest request,HttpServletResponse response){
		Examination examination2 = get(examId);
		examService.updateExamination(examination2);
		HttpSession session = request.getSession();
		String classDataRelation=(String)session.getAttribute("ids");
		if(classDataRelation!=null){
			String[] classArr=classDataRelation.split(",");
			for (String examClassId : classArr) {
				Examination examination=get(examId);
				Date beginTime=new Date();
				examination.setBeginTime(beginTime);
				examService.updateExamination(examination);
				testPaperServie.publishExam(examId,examClassId);
			}
		}
		 return "redirect:/t/exam/examList";
	}
	*/
	
	/**
	 * 查询选中的班级
	 */
	@ResponseBody
	@RequestMapping(value = "findClassByExamId")
	public String findClassByExamId(String examId) {
		String reuslt="";
		if(StringUtils.isNotBlank(examId)){
			List<Map<String,Object>> mapList=Lists.newArrayList();
			List<ExamClass> list = examService.getExamClass(examId);
			for(ExamClass item:list){
				SchoolClass s=item.getSchoolClass();
				if(s!=null){
					Map<String,Object> map=Maps.newHashMap();
					map.put("id", s.getId());
					map.put("title", s.getTitle());
					mapList.add(map);
				}
			}
			reuslt=JsonMapper.toJsonString(mapList);
		}
		return reuslt;
	}
	
	
}
