package com.thinkgem.jeesite.modules.teacher.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.ObjectUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.SchoolClassDao;
import com.thinkgem.jeesite.modules.questionlib.dao.SpecialtyDao;
import com.thinkgem.jeesite.modules.questionlib.dao.VersionQuestionDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.Constants;
import com.thinkgem.jeesite.modules.teacher.dao.ExamClassDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamKnowledgeQuestionDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamSpecilityDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamdetailDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamdetailQuestionDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExaminationDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamknowledgeDao;
import com.thinkgem.jeesite.modules.teacher.entity.Examdetail;
import com.thinkgem.jeesite.modules.teacher.entity.ExamdetailQuestion;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.entity.KnowledgeQuestionDetail;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 模板service
 */
@Service("teacherTemplate")
public class TemplateService {
   
	@Autowired
    ExaminationDao examinationDao;
	
	@Autowired
	ExamknowledgeDao examknowledgeDao; //考试考的知识点
	
	
	@Autowired
	ExamClassDao  examClassDao;  //适用班级
	@Autowired
	ExamSpecilityDao examSpecilityDao; //适用专业
	
	
	@Autowired
	SchoolClassDao classDao; //班级Dao
	
	@Autowired
	SpecialtyDao specialtyDao;//专业Dao
	
    @Autowired
	VersionQuestionDao  versionQuestionDao;// 试题dao
    @Autowired
    QuestionslibSplitService quesSplitService;
    @Autowired
    ExamdetailDao  examdetailDao; //试卷详细
    
    @Autowired
    ExamdetailQuestionDao examdetailQuestionDao;//试卷试题
    
    @Autowired
    ExamKnowledgeQuestionDao examKnowledgeQuestionDao;//手动出题时dao
	
	@Autowired
    ExaminationService service;
    
	/**
	 * 查询模板列表
	 * @return 模板列表
	 */ 
	public Page<Examination> findExaminationPage(Page<Examination> page,Examination exam){
		Map<String,String> map=TearcherUserUtils.getTeacherIdAndCourseVersionId();
		exam.setTeacher(new User(map.get("userId")));
		exam.setVersion(new CourseVesion(map.get("versionId")));
		exam.setPage(page);
		page.setList(examinationDao.findList(exam));
		return page;
	}

	/**
	 * 通过id查询试卷
	 * @param exam 
	 * @return 试卷详细
	 */
    public Examination get(Examination exam){
    	return examinationDao.getExam(exam);
    }
	
    
    
	/* @Transactional(readOnly=false)
	 public  boolean  saveHandleExamination(Examination examination,HttpServletRequest request){
		 if(examination!=null){
			 Date d=examination.getPublishAnswerTime();
			 if(d==null){
				Calendar calendar = Calendar.getInstance();
		        Date date = new Date(System.currentTimeMillis());
		        calendar.setTime(date);
		        calendar.add(Calendar.YEAR, +100);
		        date = calendar.getTime();
		        examination.setPublishAnswerTime(date);
			 }
		 }
		 examDao.updateExamSelect(examination);
		 
		 saveExamQuestionScore(request);
		 //3、保存选择的专业
		 String examSpecilities=request.getParameter("specilityDataRelation");
		 saveExamSpecilities(examination.getId(),examSpecilities);
		 //4、保存班级
		 String  examClass=request.getParameter("classDataRelation");
		 String examType=examination.getExamType();
		 String status=null;
		 if(("2").equals(examType)){
			  status="1";
		 }else if(("5").equals(examType)){
			  status="0";
		 }
		 status="1";
		 saveExamClass(examination.getId(), examClass,status);
		 
		 
		 //5随机出题
		 String  knowledgeQuestions=request.getParameter("knowledgeQuestions");
		 return saveExamHandleDetails(examination,knowledgeQuestions);
	 }*/
    
    
    /**
     * 保存模板
     * @param exam
     * @param knowledgeQuestions
     */
 /*   @Transactional(readOnly=false)
    public boolean  saveExamHandleDetails2Template(Examination exam,String knowledgeQuestions,HttpServletRequest request){
    	int result=0;
    	 //1、修改试卷信息
    	//examinationDao.updateExamSelect(exam); //examination.setIstemplate(Examination.EXAM_TEMPLATE_YES);//0表示调用模板，保存为模板
    	//examinationDao.addExaminationSelect(exam);
    	
    	// List<String> examknowledgeIds=getknowledgeIdsByExamId(exam.getId()); 
    	
    	
    	
    	
		//2、json 转成 list 数据
    	System.out.println(knowledgeQuestions);
    	knowledgeQuestions=knowledgeQuestions.replaceAll("&quot;","\"");
		List<KnowledgeQuestionDetail> list=  (List<KnowledgeQuestionDetail>)JSON.parseArray(knowledgeQuestions,KnowledgeQuestionDetail.class);

    	for(KnowledgeQuestionDetail item:list){
            item.setExamId(exam.getId());
            item.setTeacherId(TearcherUserUtils.getUser().getId());
            item.setId(IdGen.uuid());
    	
    	}
    	
    	Parameter param=new Parameter(new Object[][]{
    	  {"list",list},
    	  {"examId",exam.getId()},
    	  {"teacherId",TearcherUserUtils.getUser().getId()}
    	});
    	
    	//先删除后添加 
    	//examKnowledgeQuestionDao.batchInsertKnowledgeQuestionDetail(param); 
    	
    	//手动出题
    	 
		 //1、所考所有知识点
		 List<String> examknowledgeIds=getknowledgeIdsByExamId(exam.getId()); 
		 //2、所有可以出卷的试题
		 
		 String  examType=" AND exam_type =2 OR exam_type=5";
		 
		 Parameter examknowledgeIdsParam=new  Parameter(new Object[][]{
			 {"examknowledgeIds",examknowledgeIds},
			 {"examType",examType}
		 });
    	
    	User teacher=TearcherUserUtils.getUser();
		 Parameter examknowledgeIdsParam=new  Parameter(new Object[][]{
			 {"exam",exam},
			 {"schoolId",teacher.getCompany().getId()}
		     //{"examId",exam.getId()},
			 //{"examType",examType},
			 //{"examknowledgeIds",examknowledgeIds}
		});
		 
		 //List<VersionQuestion> allQuestion= versionQuestionDao.findVersionQuestionByCourseKnowledgeIds(examknowledgeIdsParam);//获取所有问题
		 List<VersionQuestion> allQuestion=quesSplitService.findExamQuestionslibSplitByteacher(exam.getId());//获取所有问题
         
		 
		 
		 List<ExamdetailQuestion> examdetailQuestionList=null;
		 
		// Map<String,List<VersionQuestion>> maps=getALlMap(allQuestion);
		 
		 
		//删除已经存在的试卷（AB）卷、卷试题的关系数据
		 //examdetailDao.deleteExamDetailContactByExamId(exam.getId());
		 
			 //保存
			 examdetailQuestionList=Lists.newArrayList();
			 service.saveHandleExam(allQuestion,examdetailQuestionList,list); //出题
			 
			 if(examdetailQuestionList.size()>0){
				 //保存AB卷表
				 examdetailDao.insertSelective(detail);
				 
				 Parameter parameter=new Parameter(new Object[][]{
				    {"examDetailId",detail.getId()},
				    {"questionList",examdetailQuestionList},
				 });
				 //批量添加考试试题
				 examdetailQuestionDao.batchDeleAndInsert(parameter);
				 
				 for(ExamdetailQuestion e:examdetailQuestionList){
					 System.out.println("自动组卷筛选到的题");
					 System.out.print(e.getQuestion().getId()+"~" +e.getQuestion().getTitle()+"~"+e.getQuestion().getQuesType());
				 }
				
				 result+=1;
				 
			 }else{
				  
				 System.out.println("没有符合的试题");
				 result=0;
			 }
			 System.out.println("**************************************************");
		
	    
	     if(result==1){
	    	 
	    	 List<String> examknowledgeIds=getknowledgeIdsByExamId(exam.getId()); 
	    	 
	    	 Examination   newExam=new Examination();
	    	 newExam=exam;
	    	 newExam.setId(IdGen.uuid());
	    	 newExam.setIstemplate(Examination.EXAM_TEMPLATE_YES);
	    	 newExam.setCreateDate(new Date());

	    	 
	    	 Parameter paramKnow=new Parameter(new Object[][]{
	    				{"examId",newExam.getId()},
	    				{"ckIds",examknowledgeIds.toArray()}
	    			});
	    			
			if(examknowledgeIds.size()>0){
				examknowledgeDao.batchAddExamKnowledge(paramKnow);
			}
	    	 
	     	for(KnowledgeQuestionDetail item:list){
	             item.setExamId(newExam.getId());
	             item.setTeacherId(TearcherUserUtils.getUser().getId());
	             item.setId(IdGen.uuid());
	     	
	     	}
	    	 
	    	 Parameter param=new Parameter(new Object[][]{
	    	    	  {"list",list},
	    	    	  {"examId",newExam.getId()},
	    	    	  {"teacherId",TearcherUserUtils.getUser().getId()}
	    	 });
	    	 examKnowledgeQuestionDao.batchInsertKnowledgeQuestionDetail(param); 
	    	 
	    	 examinationDao.addExaminationSelect(newExam);
	    	 
	     }
			 
		 return result==1;
		
    }*/
	
	/**
	 * 查询试卷考的知识点Ids集合
	 */
	public List<String> getknowledgeIdsByExamId(String examId){
		return examknowledgeDao.getknowledgeIdsByExamId(examId);
	}
	

   /**
    * @param examination 
    * 
    */
	@Transactional(readOnly=false)
   public boolean  saveUseTemplateGroupQuestions(Examination newExam,Examination examination,String specilityDataRelation,String classDataRelation){
		int result=0,result1=0;
		
		//当前登录的用户
	   User user=TearcherUserUtils.getUser();
	   
	   //1、定义一个新的试卷对象
	  /* Examination newExam=(Examination) ObjectUtils.deeplyCopy(examination);
	   newExam.setId(IdGen.uuid());
	   newExam.setIstemplate(Examination.EXAM_TEMPLATE_NO);*/
	   newExam.setStatus("1");
	   newExam.setCreateDate(new Date());
	   newExam.setUseTemplatId(examination.getId());
	   
	   
	   //转存知识点
	   List<String> examknowledgeIds=getknowledgeIdsByExamId(examination.getId()); 
	   
	   if(examknowledgeIds!=null && examknowledgeIds.size()>0){
		   Parameter paramKnow=new Parameter(new Object[][]{
				{"examId",newExam.getId()},
				{"ckIds",examknowledgeIds.toArray()}
			});
			
	    	examknowledgeDao.batchAddExamKnowledge(paramKnow);
	   }
	   
	   //取出模板中的知识点和出题的数量
	   KnowledgeQuestionDetail knowledgeQuestionDetail =new KnowledgeQuestionDetail();
	   knowledgeQuestionDetail.setExamId(examination.getId());
	   knowledgeQuestionDetail.setTeacherId(user.getId());
	   List<KnowledgeQuestionDetail> list=examKnowledgeQuestionDao.findExamKnowledgeQuestionByExamId(knowledgeQuestionDetail);
	   
	   for(KnowledgeQuestionDetail item:list){
	       item.setExamId(newExam.getId());
	       item.setTeacherId(user.getId());
	       item.setId(IdGen.uuid());
	   }
	   
	   
	   
	   //转存知识点对应的试题
       Parameter param=new Parameter(new Object[][]{
	   	  {"list",list},
	   	  {"examId",newExam.getId()},
	   	  {"teacherId",user.getId()}
	   });
   	    examKnowledgeQuestionDao.batchInsertKnowledgeQuestionDetail(param);
	  
   	   //保存测评试卷
  
	   examinationDao.addExaminationSelect(newExam);
	   
	   if(StringUtils.isNotBlank(specilityDataRelation)){
		   
	    //2、先删除试卷专业
		 String[] specilityArr=specilityDataRelation.split(",");
		 
		 Parameter sParam=new Parameter(new Object[][]{
			{"examId",newExam.getId()},
			{"specilityArr",specilityArr}
		 });
		 if(specilityArr.length>0){
			 //1先删除专业
			 //2先添加专业
			 examSpecilityDao.batchAdd(sParam);
		 }
		 
	   }
	   if(StringUtils.isNotBlank(classDataRelation)){
	     //3、添加试卷班级
		 String[] classArr=classDataRelation.split(",");
		 Parameter cParam=new Parameter(new Object[][]{
				 {"examId",newExam.getId()},
				 {"classArr",classArr},
				 {"status","1"}
		 });
		 
		 if(classArr.length>0){
			 //1先删除班级
			 //2后添加班级
			examClassDao.batchAdd(cParam);
		 }
	   } 
	   
   	  
   	   /*  //1、所考所有知识点
		 List<String> examknowledgeIds=getknowledgeIdsByExamId(examination.getId()); 
		 //2、所有可以出卷的试题
		 
		 String examTypeValue=examination.getExamType(); //1随堂测 2组卷考试 3作业 4例题 5在线考试
		 String examType="";
		 if("2".equals(examTypeValue) || "5".equals(examTypeValue)){
			 examType=" AND exam_type !=2 AND exam_type!=5";
		 }else if("1".equals(examTypeValue)||"3".equals(examTypeValue)||"4".equals(examTypeValue)){
			 examType=" AND exam_type =2 OR exam_type=5";
		 }
		 
		 Parameter examknowledgeIdsParam=new  Parameter(new Object[][]{
			 {"examknowledgeIds",examknowledgeIds},
			 {"examType",examType}
		 });*/
		 
		 /*User teacher=TearcherUserUtils.getUser();
		 Parameter examknowledgeIdsParam=new  Parameter(new Object[][]{
			 {"exam",examination},
			 {"schoolId",teacher.getCompany().getId()}
				// {"examId",exam.getId()},
			 //{"examType",examType},
			 //{"examknowledgeIds",examknowledgeIds}
		 });*/
		 
		 //List<VersionQuestion> allQuestion= versionQuestionDao.findVersionQuestionByCourseKnowledgeIds(examknowledgeIdsParam);//获取所有问题
		 //List<VersionQuestion> allQuestion=quesSplitService.findExamQuestionslibSplitByteacher(examination.getId());
		 List<VersionQuestion> allQuestion=quesSplitService.findExamQuestionslibSplitByteacher(examination.getId());//获取所有问题
        Examdetail examdetail=null;
		 
		 //存储A卷试题
		 List<ExamdetailQuestion> examdetailAQuestionList=Lists.newArrayList();
		 
			//删除已经存在的试卷（AB）卷、卷试题的关系数据
		 examdetailDao.deleteExamDetailContactByExamId(newExam.getId());
		 
		 //Map<String,List<VersionQuestion>> maps=getALlMap(allQuestion);
		 
		 examdetail=new Examdetail();
		 examdetail.setId(IdGen.uuid());
		 examdetail.setExam(newExam);
		 examdetail.setType("A");
		 
		 service.saveHandleExam(allQuestion,examdetailAQuestionList,list); //出题
		 result=service.saveExaminationToDataBase(examdetailAQuestionList,examdetail);
		 if(Constants.EXAMINATION_ISAB_YES.equals(newExam.getIsAb())){ //是AB卷  在出B卷试题 （B卷试题是在A卷上替换）
			 
			 if(examdetailAQuestionList.size()>0&&result>0){
				 
				 examdetail=new Examdetail();
				 examdetail.setId(IdGen.uuid());
				 examdetail.setExam(newExam);
				 examdetail.setType("B");
				 
				 List<ExamdetailQuestion> examdetailBQuestionList=Lists.newArrayList(); //存储B卷筛选的试题 
				 //A卷所有试题
				 List<VersionQuestion> quesAList=Collections3.extractToList(examdetailAQuestionList,"question");
				 
				 //A卷题型和难易程度分类
				 Map<String,List<VersionQuestion>> aMap=service.getALlMap(quesAList);
				 Map<String,List<VersionQuestion>> maps=service.getALlMap(allQuestion);
				 for(Map.Entry<String, List<VersionQuestion>> entry:aMap.entrySet()){
					 //A卷具体试题集合
					 List<VersionQuestion> entryValue=entry.getValue();
					 //可以出题的集合
					 List<VersionQuestion> allList=maps.get(entry.getKey());
					 
					 //A卷 试题按分数分组
					 Map<Double,VersionQuestion> scoreMaps=service.groupbyList(entryValue);
					 
					 for(Map.Entry<Double,VersionQuestion> scoreEnry:scoreMaps.entrySet()){
						 Double  scoreEnryKey=scoreEnry.getKey();
						 VersionQuestion scoreEnryValue=scoreEnry.getValue();
						 
						//可以出题的集合（相同分值）
						List<VersionQuestion> temp=service.getQuestion(allList,scoreEnryKey);
						 
						service.randomAutoGroupQuestion(temp,scoreEnryValue.getList().size(),examdetailBQuestionList);
						
						 //b卷已出的试题
						 List<VersionQuestion> bQuestions=Collections3.extractToList(examdetailBQuestionList,"question");
						 Map<String,List<VersionQuestion>> bQuestionMaps=service.getALlMap(bQuestions);
						 List<VersionQuestion> bTemp=bQuestionMaps.get(entry.getKey());
						 if(bTemp==null){
							 bTemp=new ArrayList<VersionQuestion>();
						 }
						 //判断b卷试题是否和A卷相等
						 int sub=entryValue.size()-bTemp.size();
						 if(sub>0){
							 service.randomAutoGroupQuestion(entryValue,sub,examdetailBQuestionList);
						 }
					 }
						 
					 
				 }
				 
				 //保存到数据库
				 result1=service.saveExaminationToDataBase(examdetailBQuestionList,examdetail);
				 
			 }
			 
			 return (result+result1)==2;
		 }
		 
		 return result==1;
	   
   }
	
	
    
}
