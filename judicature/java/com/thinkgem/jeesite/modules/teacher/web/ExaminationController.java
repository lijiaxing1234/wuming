package com.thinkgem.jeesite.modules.teacher.web;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.VersionQuestionService;
import com.thinkgem.jeesite.modules.teacher.Constants;
import com.thinkgem.jeesite.modules.teacher.entity.ExamKnowledgeQuestion;
import com.thinkgem.jeesite.modules.teacher.entity.Examdetail;
import com.thinkgem.jeesite.modules.teacher.entity.ExamdetailQuestion;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.entity.Examknowledge;
import com.thinkgem.jeesite.modules.teacher.entity.KnowledgeQuestionDetail;
import com.thinkgem.jeesite.modules.teacher.entity.QuestionslibSplit;
import com.thinkgem.jeesite.modules.teacher.service.ExamDetailQuestionService;
import com.thinkgem.jeesite.modules.teacher.service.ExamDetailService;
import com.thinkgem.jeesite.modules.teacher.service.ExaminationService;
import com.thinkgem.jeesite.modules.teacher.service.QuestionRecordService;
import com.thinkgem.jeesite.modules.teacher.service.QuestionslibSplitService;
import com.thinkgem.jeesite.modules.teacher.service.TemplateService;


/**
 * 试卷管理
 */
@Controller
@RequestMapping("${teacherPath}/examination")
public class ExaminationController extends BaseController {
	
	@Autowired
	ExaminationService examService; //试卷service
	
	@Autowired
	VersionQuestionService  questionService;
	@Autowired
	ExamDetailService examdetailService;
	@Autowired
	ExamDetailQuestionService examDetailQuestionService;
	@Autowired
	TemplateService  templateService;
	@Autowired
	QuestionRecordService  questionRecordService;
	
	@Autowired
	QuestionslibSplitService queslibSplitService;

	@RequestMapping("list")
	public String list(){
		return "teacher/examination/list";
	}
	
	
	//@ModelAttribute("")
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
	 * 组卷开始
	 */
	@RequestMapping(value="examPager",method=RequestMethod.GET)
	public String  examPaper(Examination examination,Model model){
		
		model.addAttribute("totalQuesCount", inited());
		model.addAttribute("exam", examination);
		return "teacher/examination/examPager";
	}
	
	/**
	 * 组卷保存
	 */
	@RequestMapping(value="examPager",method=RequestMethod.POST)
	public String saveExamPaper(Examination examination,String newExam){
		 String examId="";
		 if(StringUtils.isNotBlank(examination.getId())){
			 examId=examination.getId();
		 }else{
			examId=examService.saveExamination(examination).getId();//新增试卷
		 }
	   return "redirect:"+teacherPath+"/examination/selectKnowledge?id="+examId;
	}
	
	/**
	 * 选择知识点
	 */
	@RequestMapping(value="selectKnowledge",method=RequestMethod.GET)
	public String selectKnowledge(Examination examination,Model model){
		List<String> examknowledgesIds=examService.getknowledgeIdsByExamId(examination.getId());
		model.addAttribute("examknowledgesIds",Collections3.convertToString(examknowledgesIds,","));//选中的知识点Ids
		model.addAttribute("examination", examination);
		model.addAttribute("examknowledge", new Examknowledge());//支持form:from 标签
		return "teacher/examination/selectKnowledge";
	}
	/**
	 * 保存知识点
	 */
	@RequestMapping(value="selectKnowledge",method=RequestMethod.POST)
	public String saveSelectKnowledge(Examination examination,Examknowledge examknowledge,HttpServletRequest request,HttpServletResponse response,Model model){
		 
		try{
           examknowledge.setExam(examination);
	       examService.addExamknowledgeAndUpdateExam(examknowledge);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		if(Constants.EXAMINATION_EXAMMODE_YES.equals(examination.getExamMode())){
			return "redirect:"+teacherPath+"/examination/examHandleDetails?id="+examination.getId();
		}
		
	    return "redirect:"+teacherPath+"/examination/examDetails?id="+examination.getId();
	}
	
	
	
	/**
	 * 手动组卷
	 * @return
	 */
	@RequestMapping(value="examHandleDetails",method=RequestMethod.GET)
	public String  examHandleDetails(Examination examination,Model model){
		
		if(StringUtils.isNotBlank(examination.getId())){
			List<ExamKnowledgeQuestion> list=examService.findExamKnowledgeQuestionList(examination.getId(),false);
			List<KnowledgeQuestionDetail> sourceList=examService.findExamKnowledgeQuestionByExamId(examination.getId());
			model.addAttribute("list", list);
			model.addAttribute("sourceList",sourceList);
		}
		model.addAttribute("examKnowledgeQuestion", new ExamKnowledgeQuestion());
		model.addAttribute("examination", examination);
		return "teacher/examination/examHandleDetails";
	}
	
	@RequestMapping(value="examHandleDetails",method=RequestMethod.POST)
	public String  saveExamHandleDetails(Examination examination,HttpServletRequest request,Model model){

		if(!examService.saveHandleExamination(examination,request)){
			 addMessage(model, "没有符合条件的试题,请调整条件后重试。");
			return examHandleDetails(examination,model);
		}
		return "redirect:"+teacherPath+"/examination/adjustExam?id="+examination.getId();
	}
	
	
	/**
	 * 自动组卷
	 */
	@RequestMapping(value="examDetails",method=RequestMethod.GET)
	public String examDetails(Examination examination,Model model){
		if(StringUtils.isNotBlank(examination.getId())){
			model.addAttribute("examQuestionScoreList", examService.findExamQuestionScoreExamId(examination.getId()));
			model.addAttribute("examHoursList", examService.getSelectKnowledgeFirstParentByExamId(examination.getId()));
		}
		model.addAttribute("quesMap", examService.statisticsVersionQuestionScoreInfo(examination));
		model.addAttribute("examination", examination);
		return "teacher/examination/examDetails";
	}
	
	
	/**
	 * 自动组卷
	 */
	@RequestMapping(value="examDetails",method=RequestMethod.POST)
	public String savExamDetails(Examination examination,Model model,HttpServletRequest request){
		 if(! examService.saveAutoExamination(examination,request)){
			 addMessage(model, "没有符合条件的试题,请调整条件后重试。");
		 }else{
			//已出试题的总分数
			List<Examdetail> examdetails= examService.findExamdetailScoresbyExamId(examination.getId());
			
			if(examdetails!=null && examdetails.size()>0){
				Examdetail examDetailInfo=examdetails.get(0);
				
				String  examClass=request.getParameter("classDataRelation");
			   if(examDetailInfo.getScores().equals(Double.valueOf(examination.getTotalScore()))){//已出总分和设置总分相等
				   //4、保存班级
				 //  String  status="1";
				    examService.saveExamClass(examination.getId(),examClass,"1");
			   }else{
				   examService.saveExamClass(examination.getId(),examClass,null);
			   }
			}
			
			model.addAttribute("examination", examination);
			model.addAttribute("examdetails", examdetails);
		    //model.addAttribute("message", new Message("成功",Message.Type.SUCCESS)); 
		 }
		return "teacher/examination/examTips";
		//return "redirect:"+teacherPath+"/examination/adjustExam?id="+exam.getId();	
	}                 
	
	@RequestMapping("examTipsSave")
	public String examTipsSave(Examination examination,String examDetailId,String quesType,Integer countNumber){
		// exam.setExamType(Constants.EXAMINATION_EXAMTYPE_EXAM);
		
		examService.saveSupplementExamDetailQuestion(examination,examDetailId,quesType,countNumber);
		return "redirect:"+teacherPath+"/examination/adjustExam?id="+examination.getId();
	}
	
	
	
	/**
	 * 查询试题数量
	 * @param exam
	 * @param examDetailId
	 * @param quesType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("countVersionQuesByExamId")
	public int countVersionQuestionByExamIdAndExamDetailIdAndQuesType(Examination examination,String examDetailId,String quesType){
		//exam.setExamType(Constants.EXAMINATION_EXAMTYPE_EXAM);
		return examService.countVersionQuestionByExamIdAndExamDetailIdAndQuesType(examination, examDetailId, quesType);
	}
	
	
	
	
	/**
	 * 题目调整 
	 */
	@RequestMapping("adjustExam")
    public  String  adjustExam(Examination examination,String examId,Model model){
		if(examId!=null&&!(("").equals(examId))){
			examination.setId(examId);
		}
		String examIds=examination.getId();
		String[] split = examIds.split(",");
		if(split.length>=1){
			examination.setId(split[0]);
		}
		Examination exam2=examService.getExam(examination);//这是为了调用模板功能所得到的新测试
		model.addAttribute("examination", exam2);
		model.addAttribute("examdetails",examService.getExamDetailByExamId(exam2));
		//查询试卷总题数
		if(examination!=null){
			String examId2=examination.getId();
			Integer i=examService.getQuestionlibCount(examId2);
			model.addAttribute("questionlibCount", i);
		}
       return "teacher/examination/adjustExam";
    }
	/**
	 * 试卷的修改
	 */
	@RequestMapping("updateExamTitle")
    public  String  updateExamTitle(Examination examination,Model model,String mainTitle_A,String subTitle_A,String mainTitle_B,String subTitle_B){
		String examIds=examination.getId();
		String[] split = examIds.split(",");
		if(split.length>=1){
			examination.setId(split[0]);
		}
		examination.setStatus("1");
		examService.updateExamination(examination);
		questionRecordService.save(examination);
		
		if(mainTitle_B==null && subTitle_B==null){
			examService.saveMainTitleAndSubTitle(split[0],"A",mainTitle_A,subTitle_A);
		}else{
			examService.saveMainTitleAndSubTitle(split[0],"A",mainTitle_A,subTitle_A);
			examService.saveMainTitleAndSubTitle(split[0],"B",mainTitle_B,subTitle_B);
		}
		String examType=examination.getExamType();
		if(("3").equals(examType)){
			return "redirect:"+teacherPath+"/exam/examListUnPublish";
		}else{
			if(("2").equals(examType)){
				return "redirect:"+teacherPath+"/testPaper/testPaperListUnSubmit";
			}
			return "redirect:"+teacherPath+"/testPaper/testPaperOnLineListUnSubmit";
		}
    }
	/**
	 * 题目调整列表
	 */
	@RequestMapping("adjustExamList")
	public  String  adjustExamList(String examdetailId,String examId,Date beginTime,Date endTime,String examType, Model model,HttpServletRequest request,HttpServletResponse response){
		
		/*List<ExamdetailQuestion> list= new ArrayList<ExamdetailQuestion>();
		list=examService.getExamdetailQuestionByExamdetailId(examdetailId,beginTime,endTime,examType);
		model.addAttribute("list", list);*/
		
		Map<String,List<ExamdetailQuestion>> dataMap=examService.getExamdetailQuestionByExamdetailIdGroupbyQuesType(examdetailId,beginTime,endTime,examType);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("examId", examId);
		model.addAttribute("examdetailId", examdetailId);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("examType", examType);
		return "teacher/examination/adjustExamList";
	}
	
	
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
		return "redirect:" + teacherPath + "/examination/adjustExamList?examdetailId="+examdetailId+"&examId="+examId;
	}
	
	
	
	/**
	 * 题目调整 （题目替换）
	 */
	@RequestMapping("adjustExamInfo")
	public  String  adjustExamInfo(String examdetailId,String examId,String questype,String questionId,Date beginTime,Date endTime,String examType,Model model,HttpServletRequest request,HttpServletResponse response){
		
		String examLevel=request.getParameter("examLevel");
		List<ExamdetailQuestion> quesList=examService.getExamdetailQuestionByExamdetailId(examdetailId,beginTime,endTime,examType);
		if(quesList!=null){
			List<String> questionIds=Collections3.extractToList(quesList, "question.id");
			
			VersionQuestion question= questionService.get(new VersionQuestion(questionId));  
			Examdetail examdetail=  examdetailService.getExamdetailById(examdetailId);  
			model.addAttribute("question", question);
			model.addAttribute("examdetail", examdetail);
			List<ExamdetailQuestion> list= examService.getExamdetailQuestions(questionIds,beginTime,endTime,examType,examId,questype,examLevel,question.getQuesRealPoint());//将调整列表中的所有id在题目替换中都去掉从而避免冲突
			model.addAttribute("list",list);
		}
		model.addAttribute("examLevel", examLevel);
		model.addAttribute("examId", examId);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("questype", questype);
		model.addAttribute("endTime", endTime);
		model.addAttribute("examType", examType);
		return "teacher/examination/adjustExamInfo";
	}
	
	/**
	 * 替换题目
	 */
	@RequestMapping("adjustExamInfo2")
	public  void  adjustExamInfo2(String examdetailId,String examId,String questionId,String replaceQuestionId,Integer count,Model model,HttpServletRequest request,HttpServletResponse response){
		if(replaceQuestionId!=null&&!(("").equals(replaceQuestionId))){
			questionService.updateQuestionExamInfo(examdetailId,questionId,replaceQuestionId);
		}
		model.addAttribute("examdetailId", examdetailId);
		//return "teacher/examination/adjustSuccess";//替换完成返回题目列表页面
	}
	/**
	 * 保存为模板
	 */
	/*@ResponseBody
	@RequestMapping("saveEmple")
	public String saveEmple(Examination examination,String knowledgeQuestions,HttpServletRequest request,HttpServletResponse response,Model model){
		//examination.setId(IdGen.uuid());
		//examination.setIstemplate(Examination.EXAM_TEMPLATE_YES);//0表示调用模板，保存为模板
		exam.setExamType(Constants.EXAMINATION_EXAMTYPE_EXAM);
		boolean result=templateService.saveExamHandleDetails2Template(examination, knowledgeQuestions,request);
		if(!result){
			//addMessage(model, "没有符合条件的试题,请调整条件后重试。");
			if(StringUtils.isNotBlank(examination.getId())){
				List<ExamKnowledgeQuestion> list=examService.findExamKnowledgeQuestionList(examination.getId(),false);
				List<KnowledgeQuestionDetail> sourceList=examService.findExamKnowledgeQuestionByExamId(examination.getId());
				model.addAttribute("list", list);
				model.addAttribute("sourceList",sourceList);
			}
			model.addAttribute("examKnowledgeQuestion", new ExamKnowledgeQuestion());
			//return "teacher/examination/examHandleDetails";
			model.addAttribute("examination", examination);
			//return examHandleDetails(examination,model);
			return "没有符合条件的试题,请调整条件后重试。";
		}else{
			//修改
			//addMessage(model, "保存成功！！！");
			//return "redirect:"+teacherPath+"/examination/examHandleDetails?id="+exam.getId();//跳转至手动组卷
			//return "redirect:"+teacherPath+"/template/list";
			return "保存成功！";
		}
		
	}*/
	
	
	
}
