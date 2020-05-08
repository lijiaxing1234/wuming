
package com.thinkgem.jeesite.modules.teacher.service;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.SchoolClassDao;
import com.thinkgem.jeesite.modules.questionlib.dao.SpecialtyDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.teacher.Constants;
import com.thinkgem.jeesite.modules.teacher.dao.ExamClassDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamKnowledgeQuestionDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamQuestionScoreDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamSpecilityDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamdetailDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamdetailQuestionDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExaminationDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamknowledgeDao;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.ExamClass;
import com.thinkgem.jeesite.modules.teacher.entity.ExamDetailQuestionInfo;
import com.thinkgem.jeesite.modules.teacher.entity.ExamKnowledgeQuestion;
import com.thinkgem.jeesite.modules.teacher.entity.ExamQuestionScore;
import com.thinkgem.jeesite.modules.teacher.entity.ExamSpecility;
import com.thinkgem.jeesite.modules.teacher.entity.Examdetail;
import com.thinkgem.jeesite.modules.teacher.entity.ExamdetailQuestion;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.entity.Examknowledge;
import com.thinkgem.jeesite.modules.teacher.entity.KnowledgeQuestionDetail;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 试卷Service
 */
@Service
@Transactional(readOnly=true)
public class ExaminationService {
	@Autowired
	ExamDao exam2Dao;
	@Autowired
	ExaminationDao examDao; //试题Dao
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
	//VersionQuestionDao  versionQuestionDao;// 试题dao
    QuestionslibSplitService quesSplitService;
    
    @Autowired
    ExamdetailDao  examdetailDao; //试卷详细
    
    @Autowired
    ExamdetailQuestionDao examdetailQuestionDao;//试卷试题
    
    @Autowired
    ExamKnowledgeQuestionDao examKnowledgeQuestionDao;//手动出题时dao
	
    @Autowired
    ExamQuestionScoreDao  examQuestionScoreDao; //自动出题时存储各种题型的分数
    
    
    /***********************
     * 组卷相关
     *************************/
    
	/**
	 * 得到单条试卷数据
	 * @param exam
	 */
	public Examination getExam(Examination exam) {
		return examDao.getExam(exam);
	}
	
	/**
	 * 保存试题
	 * @param exam
	 * @return
	 */
	@Transactional(readOnly=false)
	public Examination saveExamination(Examination exam){
		
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		exam.setTeacher(new User(userId));
		exam.setVersion(new CourseVesion(versionId));
		
		 String examType = exam.getExamType();
		 /*if(("2").equals(examType)){
			 exam.setStatus("1");//线下考试
		 }
		 if(("5").equals(examType)){
			 exam.setStatus("0");//在线考试
		 }*/
		 exam.setStatus("0");
		//修改
		if(StringUtils.isNotBlank(exam.getId())){
			if(("3").equals(examType)){
				examDao.updateHomeWorkExamSelect(exam);
			}else{
				examDao.updateExamSelect(exam);
			}
		}else{ //添加
			exam.preInsert();
			/*if(StringUtils.isBlank(exam.getTitle())){ //试卷名称
	    		exam.setTitle("未命名");
	    	}*/
			examDao.addExaminationSelect(exam);
		}
		return exam;
	}
	
    
	
    /**
     * 自动出题时 存储各种题型的分数
     * @param request
     */
	@Transactional(readOnly=false)
	public void saveExamQuestionScore(HttpServletRequest request) {
		
	  String examId=request.getParameter("id");
	   if(StringUtils.isNotBlank(examId)){
		   List<ExamQuestionScore> list=Lists.newArrayList();
		   Map<String,String[]> maps=request.getParameterMap();
			for(Map.Entry<String,String[]> entry:maps.entrySet()){
				if(entry.getKey().startsWith("quesScore_")){
					String quesType=entry.getKey().split("_")[1];
					String quesScore=entry.getValue()[0];
				    double score=Double.valueOf(quesScore);
					if(score>0){
						ExamQuestionScore temp=new ExamQuestionScore();
						temp.setId(IdGen.uuid());
						temp.setExamId(examId);
						temp.setQuesType(quesType);
						temp.setQuesScore(quesScore);
						list.add(temp);
					}
				}
			}
		 if(list.size()>0){
			  Parameter param=new Parameter(new Object[][]{
				 {"examId",examId},
				 {"list",list}
			  });
			  examQuestionScoreDao.batchExamQuestionScore(param);
		 }
		 
	   }
	}
	
	/**
	 * 自动出题时 查询各种题型的分数
	 * @param examId
	 * @return
	 */
	public  List<ExamQuestionScore>  findExamQuestionScoreExamId(String examId){
		return  examQuestionScoreDao.findExamQuestionScoreExamId(examId);
	}
    
    
    
    /***********************
     * 知识点相关
     *************************/
	
	
	/**
	 * 查询试卷考的知识点Ids集合
	 */
	public List<String> getknowledgeIdsByExamId(String examId){
		return examknowledgeDao.getknowledgeIdsByExamId(examId);
	}
	
	
	/**
	 * 先删除知识点后添加考试考的知识点和修改试卷
	 * @param examknowledge
	 */
	@Transactional(readOnly=false)
	public void addExamknowledgeAndUpdateExam(Examknowledge examknowledge) {
		Examination  exam=examknowledge.getExam();
		CourseKnowledge ck=examknowledge.getCourseKnowledge();
		
		String[] ids = ck.getId().split(",");
		//批量添加知识点
		Parameter param=new Parameter(new Object[][]{
			{"examId",exam.getId()},
			{"ckIds",ids}
		});
		
		if(ids.length>0 && exam !=null&&StringUtils.isNotBlank(exam.getId())){
			//先删除后
			//批量添加知识点
			examknowledgeDao.batchAddExamKnowledge(param);
			//修改试卷信息
			examDao.updateExamSelect(exam);
		}
	}
    
	
	
	/***
	 * 保存组卷的班级
	 * @param examId   组卷唯一id
	 * @param classDataRelation 以","分割的字符串
	 */
	@Transactional(readOnly=false)
	public void  saveExamClass(String examId,String classDataRelation,String status){
		 String[] classArr=classDataRelation.split(",");
		 Parameter cParam=new Parameter(new Object[][]{
				 {"examId",examId},
				 {"classArr",classArr},
				 {"status",status}
		 });
		 if(classArr.length>0){
			 examClassDao.batchAdd(cParam);
		 }
	}
	
	/***
	 * 保存组卷的专业
	 * @param examId   组卷唯一id
	 * @param specilityDataRelation 以","分割的字符串
	 */
	@Transactional(readOnly=false)
	public void  saveExamSpecilities(String examId,String specilitiesDataRelation){
		
		if(StringUtils.isNotBlank(specilitiesDataRelation)){
			String[] specilityArr=specilitiesDataRelation.split(",");
			Parameter sParam=new Parameter(new Object[][]{
					{"examId",examId},
					{"specilityArr",specilityArr}
			});
			
			if(specilityArr.length>0){
				examSpecilityDao.batchAdd(sParam);
			}
		}
	}
	
	@Transactional(readOnly=false)
	 public  void  updateExamination(Examination examination){
		  examDao.updateExamSelect(examination);
	}
	@Transactional(readOnly=false)
	 public  void  updateHomeWorkExamSelect(Examination examination){
		  examDao.updateHomeWorkExamSelect(examination);
	}
	
	/********************************
	 * 自动出题相关
	 ********************************/
	 
	 /**
	  * 保存自动出题
	  * @param examination
	  * @param request
	  */
	 @Transactional(readOnly=false)
	 public  boolean  saveAutoExamination(Examination examination,HttpServletRequest request){
		 
		  //1、修改组卷信息
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
		  //2、保存各种题型的分数
		   saveExamQuestionScore(request);
		  //3、保存选择的专业
		   String examSpecilities=request.getParameter("specilityDataRelation");
		   saveExamSpecilities(examination.getId(),examSpecilities);
		  //4、保存班级
		   /*String  examClass=request.getParameter("classDataRelation");
		  
		   String  status="1";
		   saveExamClass(examination.getId(),examClass,status);*/
		   
		   //5随机出题
		  return examination(examination);
	 }
	 /**
	  * 保存手动出题
	  * @param examination
	  * @param request
	  */
	 @Transactional(readOnly=false)
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
		/* if(("2").equals(examType)){
			  status="1";
		 }else if(("5").equals(examType)){
			  status="0";
		 }*/
		 status="1";
		 saveExamClass(examination.getId(), examClass,status);
		 
		 
		 //5随机出题
		 String  knowledgeQuestions=request.getParameter("knowledgeQuestions");
		 return saveExamHandleDetails(examination,knowledgeQuestions);
	 }
	
	 
	 /**
	  * 保存课后作业
	  * @param examination
	  * @param request
	  */
	
	 @Transactional(readOnly=false)
	 public boolean saveHomeWorkExamination(Examination exam){
		 /*List<String> examknowledgeIds=getknowledgeIdsByExamId(exam.getId()); 
		 String examTypeValue=exam.getExamType(); //1随堂测 2组卷考试 3作业 4例题  5在线考试
		 String examType=" AND exam_type =2 OR exam_type =5 "; //其他
		 Parameter examknowledgeIdsParam=new  Parameter(new Object[][]{
			 {"examId",exam.getId()},
			 {"examType",examType},
			 {"examknowledgeIds",examknowledgeIds}
		 });*/
		 
		/* User teacher=TearcherUserUtils.getUser();
		 Parameter examknowledgeIdsParam=new  Parameter(new Object[][]{
			 {"exam",exam},
			 {"schoolId",teacher.getCompany().getId()}
		 });
		 
		 
		 
		 //2、所有可以出卷的试题
		 List<VersionQuestion> allQuestion= versionQuestionDao.findVersionQuestionByCourseKnowledgeIds(examknowledgeIdsParam);*/
		 List<VersionQuestion> allQuestion= quesSplitService.findExamQuestionslibSplitByteacher(exam.getId());
		 
		 Examdetail examdetail=null;
		 
		 List<ExamdetailQuestion> examdetailAQuestionList=Lists.newArrayList(); //存储A卷筛选的试题
		 
		 //删除已经存在的试卷（AB）卷、卷试题的关系数据
		 examdetailDao.deleteExamDetailContactByExamId(exam.getId());
		 
		 Map<String,List<VersionQuestion>> maps=getALlMap(allQuestion);
		 Map<String,List<VersionQuestion>> mapgroupByType=getALlMapByQuesType(allQuestion);
		 
		 /**********不管是不是AB卷都需要出A卷 所以提了出来*********************/
		 
		 examdetail=new Examdetail();
		 examdetail.setId(IdGen.uuid());
		 examdetail.setExam(exam);
		 examdetail.setType("A");
	
		  double  difficult_Number=getNumber(exam.getDifficult());
		  double  general_Number=getNumber(exam.getGeneral());
		  double  simple_Number=getNumber(exam.getSimple());
		  
		  //全部各种题型的分数
		  List<ExamQuestionScore> list= examQuestionScoreDao.findExamQuestionScoreExamId(exam.getId());
		  for(ExamQuestionScore examQues:list){
			String quesType= examQues.getQuesType(); //题的类型
			Integer  totalScore=Integer.valueOf(examQues.getQuesScore()); // 题的总分
			
			 int simCount=new Long(Math.round(totalScore*simple_Number)).intValue();
			 int genCount=new Long(Math.round(totalScore*general_Number)).intValue();
			 int difCount=new Long(Math.round(totalScore*difficult_Number)).intValue(); 
			 
            if(simCount==0 && genCount==0&&difCount==0){
            	List<VersionQuestion> allList=mapgroupByType.get(quesType);
            	randomAutoGroupQuestion(allList,totalScore,examdetailAQuestionList);
            	
            }else{			 
				 if(difCount>0){
					   List<VersionQuestion> difficultList = maps.get(quesType + "_" + 3);
					   if(difficultList!=null){
					    randomAutoGroupQuestion(difficultList,difCount,examdetailAQuestionList);
					   }
				 }
				 if(genCount>0){
					 List<VersionQuestion> genList = maps.get(quesType + "_" + 2);
					 if(genList !=null){
					   randomAutoGroupQuestion(genList,genCount,examdetailAQuestionList);
					 }
				 }
				 if(simCount>0){
					 List<VersionQuestion> simList = maps.get(quesType + "_" + 1);
					 if(simList !=null){
					 randomAutoGroupQuestion(simList,simCount,examdetailAQuestionList);
					 }
				 }
				
				 int sub=totalScore-simCount-genCount-difCount;
				 if(sub>0){
					 List<VersionQuestion> simList = maps.get(quesType + "_" + 1);
					 List<VersionQuestion> genList = maps.get(quesType + "_" + 2);
					 List<VersionQuestion> difficultList = maps.get(quesType + "_" + 3);
					 
					 if(simList !=null && simList.size()>0){
					   randomAutoGroupQuestion(simList,sub,examdetailAQuestionList);
					 }else if(genList !=null&&genList.size()>0){
						 randomAutoGroupQuestion(genList,sub,examdetailAQuestionList);
					 }else if(difficultList !=null && difficultList.size()>0){
						 randomAutoGroupQuestion(difficultList,sub,examdetailAQuestionList);
					 }
				 }
				 
            }
			 
		  }
		 
		 //保存到数据库
		 return saveExaminationToDataBase(examdetailAQuestionList,examdetail)==1;
		 
		 
	 }
	 
	
	
    
    /**********************************************************************************************/
    
    
    
    /**
     * 查询当前试卷 已出试题的总分数
     * @param examId 试卷id
     * @return 所有试卷试题的总分
     */
    public List<Examdetail> findExamdetailScoresbyExamId(String examId){
    	Examdetail examdetail=new Examdetail();
    	examdetail.setExam(new Examination(examId));
    	return examdetailDao.findExamdetailScoresbyExamId(examdetail);
    }
    
    
    
    /**
     * 通过试卷id查询知识点和问题的详细列表
     * 用于回填页面
     */
    public List<KnowledgeQuestionDetail>  findExamKnowledgeQuestionByExamId(String examId){
    	KnowledgeQuestionDetail detail=new KnowledgeQuestionDetail();
    	detail.setExamId(examId);
    	detail.setTeacherId(TearcherUserUtils.getUser().getId());
    	return  examKnowledgeQuestionDao.findExamKnowledgeQuestionByExamId(detail);
    	
    }
    /**
     * 存为模板的功能
     */
    @Transactional(readOnly=false)
    public void  saveExamKnowledgeQuestionDetails(Examination exam,String knowledgeQuestions){
    	 //1、修改试卷信息
		 examDao.updateExamSelect(exam);
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
    	examKnowledgeQuestionDao.batchInsertKnowledgeQuestionDetail(param); 
    }
    
    /**
     * 保存手动组卷
     * @param exam
     * @param knowledgeQuestions
     */
    @Transactional(readOnly=false)
    public boolean  saveExamHandleDetails(Examination exam,String knowledgeQuestions){
    	int result=0,result1=0;
		//2、json 转成 list 数据
    	knowledgeQuestions=knowledgeQuestions.replaceAll("&quot;","\"");
    	System.out.println(knowledgeQuestions);
    	
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
    	examKnowledgeQuestionDao.batchInsertKnowledgeQuestionDetail(param); 
    	
    	//手动出题
		 /*//1、所考所有知识点
		 List<String> examknowledgeIds=getknowledgeIdsByExamId(exam.getId()); 
		 //2、所有可以出卷的试题
		 
		 String examTypeValue=exam.getExamType(); //1随堂测 2组卷考试 3作业 4例题 5在线考试
		 String examType="";
		 if("2".equals(examTypeValue) || "5".equals(examTypeValue)){
			 examType=" AND exam_type !=2 AND exam_type!=5";
		 }else if("1".equals(examTypeValue)||"3".equals(examTypeValue)||"4".equals(examTypeValue)){
			 examType=" AND exam_type =2 OR exam_type=5";
		 }
		 
		 Parameter examknowledgeIdsParam=new  Parameter(new Object[][]{
			 {"examknowledgeIds",examknowledgeIds},
			 {"examType",examType},
			 {"examId",exam.getId()}
		 });*/
    	
	   	/* User teacher=TearcherUserUtils.getUser();
		 Parameter examknowledgeIdsParam=new  Parameter(new Object[][]{
			 {"exam",exam},
			 {"schoolId",teacher.getCompany().getId()}
				 
		 });
		 
		 List<VersionQuestion> allQuestion= versionQuestionDao.findVersionQuestionByCourseKnowledgeIds(examknowledgeIdsParam);//获取所有问题*/
    	 List<VersionQuestion> allQuestion=quesSplitService.findExamQuestionslibSplitByteacher(exam.getId());
         
		 Examdetail examdetail=null;
		 
		 //存储A卷试题
		 List<ExamdetailQuestion> examdetailAQuestionList=Lists.newArrayList();
		 
			//删除已经存在的试卷（AB）卷、卷试题的关系数据
		 examdetailDao.deleteExamDetailContactByExamId(exam.getId());
		 
		 //Map<String,List<VersionQuestion>> maps=getALlMap(allQuestion);
		 
		 examdetail=new Examdetail();
		 examdetail.setId(IdGen.uuid());
		 examdetail.setExam(exam);
		 examdetail.setType("A");
		 
		 saveHandleExam(allQuestion,examdetailAQuestionList,list); //出题
		 result=saveExaminationToDataBase(examdetailAQuestionList,examdetail);
		 if(Constants.EXAMINATION_ISAB_YES.equals(exam.getIsAb())){ //是AB卷  在出B卷试题 （B卷试题是在A卷上替换）
			 
			 if(examdetailAQuestionList.size()>0&&result>0){
				 
				 examdetail=new Examdetail();
				 examdetail.setId(IdGen.uuid());
				 examdetail.setExam(exam);
				 examdetail.setType("B");
				 
				 List<ExamdetailQuestion> examdetailBQuestionList=Lists.newArrayList(); //存储B卷筛选的试题 
				 //A卷所有试题
				 List<VersionQuestion> quesAList=Collections3.extractToList(examdetailAQuestionList,"question");
				 
				 //A卷题型和难易程度分类
				 Map<String,List<VersionQuestion>> aMap=getALlMap(quesAList);
				 Map<String,List<VersionQuestion>> maps=getALlMap(allQuestion);
				 for(Map.Entry<String, List<VersionQuestion>> entry:aMap.entrySet()){
					 //A卷具体试题集合
					 List<VersionQuestion> entryValue=entry.getValue();
					 //可以出题的集合
					 List<VersionQuestion> allList=maps.get(entry.getKey());
					 
					 //A卷 试题按分数分组
					 Map<Double,VersionQuestion> scoreMaps=groupbyList(entryValue);
					 
					 for(Map.Entry<Double,VersionQuestion> scoreEnry:scoreMaps.entrySet()){
						 Double  scoreEnryKey=scoreEnry.getKey();
						 VersionQuestion scoreEnryValue=scoreEnry.getValue();
						 
						//可以出题的集合（相同分值）
						List<VersionQuestion> temp=getQuestion(allList,scoreEnryKey);
						 
						randomAutoGroupQuestion(temp,scoreEnryValue.getList().size(),examdetailBQuestionList);
						
						 //b卷已出的试题
						 List<VersionQuestion> bQuestions=Collections3.extractToList(examdetailBQuestionList,"question");
						 Map<String,List<VersionQuestion>> bQuestionMaps=getALlMap(bQuestions);
						 List<VersionQuestion> bTemp=bQuestionMaps.get(entry.getKey());
						 if(bTemp==null){
							 bTemp=new ArrayList<VersionQuestion>();
						 }
						 //判断b卷试题是否和A卷相等
						 int sub=entryValue.size()-bTemp.size();
						 if(sub>0){
							 randomAutoGroupQuestion(entryValue,sub,examdetailBQuestionList);
						 }
					 }
						 
					 
				 }
				 
				 //保存到数据库
				 result1=saveExaminationToDataBase(examdetailBQuestionList,examdetail);
				 
			 }
			 
			 return (result+result1)==2;
		 }
		 
		 return result==1;
    }
    
	/**
	 *手动出题
	 */
	public void  saveHandleExam(List<VersionQuestion> targetList,List<ExamdetailQuestion> examdetailQuestionList,List<KnowledgeQuestionDetail> list){
		
		for(KnowledgeQuestionDetail item:list){
			List<VersionQuestion> quesitonList=getQuestion(targetList,item.getKnowledgeId(),item.getQuestionType(),item.getQuestionDegree());
			if(StringUtils.isNotBlank(item.getQuestionNumber())&&Integer.parseInt(item.getQuestionNumber())>0){
				randomAutoGroupQuestion(quesitonList,Integer.parseInt(item.getQuestionNumber()),examdetailQuestionList);
		    }
			
		}
		
	}
	
    /**
     * 手动出题
     * 查询知识点下试题(每题多少分、多少道试题)
     */
    
    public List<ExamKnowledgeQuestion> findExamKnowledgeQuestionList(String examId,boolean isHomeWork){
    	
    	ExamKnowledgeQuestion ekq=new ExamKnowledgeQuestion();
    	ekq.setExam(new Examination(examId));
    	
    	Map<String,String> sqlMap=ekq.getSqlMap();
    	
    	/*if(isHomeWork){
    		sqlMap.put("isHomeWork","true");
    	}else{
    		sqlMap.put("isHomeWork", "false");
    	}*/
    	
    	sqlMap.put("schoolId", TearcherUserUtils.getUser().getCompany().getId());
    	sqlMap.put("teacherId",  TearcherUserUtils.getUser().getId());
    	sqlMap.put("versionId",  TearcherUserUtils.versionId());
    	
    	List<ExamKnowledgeQuestion> dataList=examKnowledgeQuestionDao.findList(ekq);
    	
    	List<ExamKnowledgeQuestion> list =Lists.newArrayList();
    	
    	Map<String,ExamKnowledgeQuestion> tempMap=Maps.newTreeMap();
    	
    	for(ExamKnowledgeQuestion item:dataList){
    		if(tempMap.containsKey(item.getCourseKnowledge().getId())){
    			ExamKnowledgeQuestion tempList=tempMap.get(item.getCourseKnowledge().getId());
    			tempList.getQuestions().add(item.getQuestion());
    		}else{
    			ExamKnowledgeQuestion temp=new ExamKnowledgeQuestion();
    			temp.setExam(item.getExam());
    			temp.setCourseKnowledge(item.getCourseKnowledge());
    			temp.getQuestions().add(item.getQuestion());
    			tempMap.put(item.getCourseKnowledge().getId(),temp);
    		}
    	}
    	
        for(Entry<String, ExamKnowledgeQuestion> entry:tempMap.entrySet()){
        	list.add(entry.getValue());
        }    	
    	return list;
    }
    
    
    
   /**
    *查询全部试题
    * @param questionId 不包括这条数据
    * @param beginTime  组卷或在线考试 开始时间
    * @param endTime   组卷或在线考试结束时间
    * @param examType 组卷或在线考试的类型集合 已‘,’分割
    * @param examId  组卷、或在线考试表 唯一Id
    * @param quesType 试题的类型（单选题、或其他的题型）
    * @return
    */
	public List<ExamdetailQuestion> getExamdetailQuestions(List<String> questionId,Date beginTime, Date endTime, String examType,String examId,String quesType,String examLevel,String quesRealPoint) {
		List<String> examTypeList=null;
		if(StringUtils.isNotBlank(examType)){
    		examTypeList=Arrays.asList(examType.split(","));
    		
    	}
		User teacher=TearcherUserUtils.getUser();
    	Parameter param=new Parameter(new Object[][]{
    	   {"questionId",questionId}, 
    	   {"beginTime",beginTime},
    	   {"endTime",endTime},
    	   {"examType",examTypeList},
    	   {"examId",examId},
    	   {"schoolId",teacher.getCompany().getId()},
    	   {"quesType",quesType},
    	   {"examLevel",examLevel},
    	   {"quesRealPoint",quesRealPoint}
    	});
    	param.put("versionId", TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId"));
    	param.put("teacherId", teacher.getId());
    	return examdetailQuestionDao.getExamdetailQuestions(param);
	}
    
    /**
     *获得试卷下AB卷详细（是AB有两条记录否则一条记录）
     * @param exam exam.id 查询条件
     */
    public List<Examdetail> getExamDetailByExamId(Examination exam){
    	Examdetail examdetail=new Examdetail();
    	examdetail.setExam(exam);
    	return  examdetailDao.getExamDetailByExamId(examdetail);
    }
    
    /**
     * 获得某个试卷（A,B卷）试题列表
     * @param  examdetailId  AB卷Id
     * @param examType 
     * @param endTime 
     * @param beginTime 
     */
    public List<ExamdetailQuestion> getExamdetailQuestionByExamdetailId(String examdetailId, Date beginTime, Date endTime, String examType){
    	
    	/*ExamdetailQuestion  edq=new ExamdetailQuestion();
    	edq.setExamdetail(new Examdetail(examdetailId));*/
    	
    	List<String> examTypeList=null;
    	if(StringUtils.isNotBlank(examType)){
    		examTypeList=Arrays.asList(examType.split(","));
    		
    	}
    	
    	Parameter param=new Parameter(new Object[][]{
    	   {"examdetailId",examdetailId}, 
    	   {"beginTime",beginTime},
    	   {"endTime",endTime},
    	   {"examType",examTypeList},
    	});
    	return examdetailQuestionDao.getExamdetailQuestionByExamDetailId(param);
    }
    
    /**
     * 获得某个试卷（A,B卷）试题列表   按题的类型分组
     * @param  examdetailId  AB卷Id
     * @param examType 
     * @param endTime 
     * @param beginTime 
     */
    public Map<String,List<ExamdetailQuestion>>  getExamdetailQuestionByExamdetailIdGroupbyQuesType(String examdetailId, Date beginTime, Date endTime, String examType){
    	Map<String,List<ExamdetailQuestion>> dataMap=Maps.newTreeMap(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
    	
    	List<ExamdetailQuestion> dataSource=getExamdetailQuestionByExamdetailId(examdetailId,beginTime,endTime,examType);
    	for(ExamdetailQuestion edq:dataSource){
    		if(dataMap.containsKey(edq.getQuesType())){
    			List<ExamdetailQuestion> list=dataMap.get(edq.getQuesType());
    			list.add(edq);
    		}else{
    			List<ExamdetailQuestion> list =Lists.newArrayList();
    			list.add(edq);
    			dataMap.put(edq.getQuesType(),list);
    		}
    	}
    	return dataMap;
    }
    
    
    
    
    
    
    
    
    
    /**
     * 试题类型的数量
     * @param exam  试卷类型和id必须字段
     * @param examDetailId A卷或B卷的唯一id
     * @param quesType 试题类型
     * @return 符合条件试题的数量
     */
    public int countVersionQuestionByExamIdAndExamDetailIdAndQuesType(Examination exam,String examDetailId,String quesType){
     
/*    	 String examTypeValue=exam.getExamType(); //1随堂测 2组卷考试 3作业 4例题  5在线考试
	     String examType="";
		 if("2".equals(examTypeValue)||"5".equals(examTypeValue)){
			 examType=" AND exam_type !=2 AND exam_type != 5 "; //考试
		 }else if("1".equals(examTypeValue)||"3".equals(examTypeValue)||"4".equals(examTypeValue)){
			 examType=" AND exam_type =2 OR exam_type =5 "; //其他
		 }*/
    	
    	/* User teacher=TearcherUserUtils.getUser();
		 Parameter param=new  Parameter(new Object[][]{
			 {"exam",exam},
			 {"schoolId",teacher.getCompany().getId()},
			 {"examDetailId",examDetailId}, //AorB卷的唯一Id
			 {"quesType",quesType}  //试题的类型
		 });*/
    	
    	
		/* Parameter param=new  Parameter(new Object[][]{
				 {"examId",exam.getId()}, //试卷Id
				 {"examType",examType},  //过滤考试还是练习题
				 {"examDetailId",examDetailId}, //AorB卷的唯一Id
				 {"quesType",quesType}  //试题的类型
		  });*/
		 
		//return versionQuestionDao.countVersionQuestionByExamIdAndExamDetailIdAndQuesType(param); 
    	
    	
    	Map<String,Object> map=Maps.newConcurrentMap();
    	map.put("examId", exam.getId());//在线考试和组卷Id 
    	map.put("examDetailId", examDetailId); //AB表Id 
    	map.put("quesType", quesType);
    	
    	Integer result=0;
    	//if(examList.contains(exam.getExamType())){ //考试用的
    		result=quesSplitService.countExamQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(map);
    	/*}else{//练习题用的
    		result=quesSplitService.countPracticeQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(map);
    	}*/
    	return result;
    }
    
    @Transactional(readOnly=false)
	public void saveSupplementExamDetailQuestion(Examination exam,
			String examDetailId, String quesType, Integer countNumber) {
    	
    	/* String examTypeValue=exam.getExamType(); //1随堂测 2组卷考试 3作业 4例题  5在线考试
	     String examType="";
		 if("2".equals(examTypeValue)||"5".equals(examTypeValue)){
			 examType=" AND exam_type !=2 AND exam_type != 5 "; //考试
		 }else if("1".equals(examTypeValue)||"3".equals(examTypeValue)||"4".equals(examTypeValue)){
			 examType=" AND exam_type =2 OR exam_type =5 "; //其他
		 }
    	
		 Parameter param=new  Parameter(new Object[][]{
				 {"examId",exam.getId()}, //试卷Id
				 {"examType",examType},  //过滤考试还是练习题
				 {"examDetailId",examDetailId}, //AorB卷的唯一Id
				 {"quesType",quesType},  //试题的类型
				 {"countNumber",countNumber}//随机补题数量
		  });*/
    	
    	 /*User teacher=TearcherUserUtils.getUser();
		 Parameter param=new  Parameter(new Object[][]{
				 {"exam",exam},
				 {"schoolId",teacher.getCompany().getId()},
				 {"examDetailId",examDetailId}, //AorB卷的唯一Id
				 {"quesType",quesType},  //试题的类型
				 {"countNumber",countNumber}//随机补题数量
		  });*/
		 
    	
    	//saveExamClass( exam.getId(),examClass,null);
    	
    	//修改班级表状态。
    	ExamClass ec=new ExamClass();
    	ec.setExam(new Examination(exam.getId()));
    	examClassDao.updateExamClassStatusByExamId(ec);
    	
    	
    	
		 
    	Map<String,Object> map=Maps.newConcurrentMap();
    	map.put("examId", exam.getId());//在线考试和组卷Id 
    	map.put("examDetailId", examDetailId); //AB表Id 
    	map.put("quesType", quesType);
    	map.put("countNumber", countNumber);
    	
    	List<VersionQuestion> aList=null;
    	//if(examList.contains(exam.getExamType())){ //考试用的
    		aList=quesSplitService.findRandomExamQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(map);
    	/*}else{//练习题用的
    		aList=quesSplitService.findRandomPracticeQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(map);
    	}*/
		 if(aList==null){
			 aList=Lists.newArrayList();
		 }
		 //A卷补题
		 //List<VersionQuestion> aList=versionQuestionDao.findVersionQuestionByExamIdAndExamDetailIdAndQuesType(param); 
		 InsertSupplementExamDetailQuestion(aList,examDetailId,quesType);
		 
		 if(Constants.EXAMINATION_ISAB_YES.equals(exam.getIsAb())){ //是AB卷  在出B卷试题 （B卷试题是在A卷上替换）
			 
			     Examdetail examdetail=new Examdetail();
		    	 examdetail.setExam(exam);
		    	 examdetail.getSqlMap().put("notInId",examDetailId);
		         List<Examdetail> examdetailList= examdetailDao.getExamDetailByExamId(examdetail);
		         Examdetail bExamdetail= examdetailList.iterator().next();
		         
		         map.remove("examDetailId");
		         map.put("examDetailId", bExamdetail.getId());
		         //List<VersionQuestion> bList=versionQuestionDao.findVersionQuestionByExamIdAndExamDetailIdAndQuesType(param);    
		        
		         List<VersionQuestion> bList=null;
		     	//if(examList.contains(exam.getExamType())){ //考试用的
		     		aList=quesSplitService.findRandomExamQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(map);
		     	/*}else{//练习题用的
		     		aList=quesSplitService.findRandomPracticeQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(map);
		     	}*/
		     	 if(bList==null){
		     		bList=Lists.newArrayList();
				 }
		         
		         List<VersionQuestion> dataList=Lists.newArrayList();
		         int sub=countNumber-bList.size();
		         if(sub==0){
		        	 dataList.addAll(bList);
		         }else if(sub>0){
		        	 dataList.addAll(bList);
		        	 for(int i=0;i<sub;i++){
		        		 VersionQuestion v= aList.get(i);
		        		 dataList.add(v);
		        	 }
		         }
		      InsertSupplementExamDetailQuestion(dataList,bExamdetail.getId(),quesType);  
		 }
		 
    }
    
    /**
     * 保存试卷补题到数据库
     * @param list 所补试题
     * @param examDetailId AB卷Id
     * @param examId 所属试卷Id
     */
    @Transactional(readOnly=false)
    public void  InsertSupplementExamDetailQuestion(List<VersionQuestion> list,String examDetailId,String quesType){
    	
    	 Parameter param=new  Parameter(new Object[][]{
				 {"list",list},  //随机补题集合
				 {"examDetailId",examDetailId}, //AorB卷的唯一Id
				 {"quesType",quesType}
		  });
    	examdetailQuestionDao.InsertSupplementExamDetailQuestion(param);
    }
    
    /**
	 * 根据知识点IDs集合查询所有可以出题的试题
	 * 按题类型分组统计该类型的分数总值
	 * @param param
	 * @return
	 */
    public Map<String,List<Map<String,Object>>> statisticsVersionQuestionScoreInfo(Examination exam){
//    	User teacher=TearcherUserUtils.getUser();
//    	Parameter param=new  Parameter(new Object[][]{
//    			{"exam",exam},
//    			{"schoolId",teacher.getCompany().getId()}
//    	});
//    	List<Map<String,Object>> list=versionQuestionDao.statisticsVersionQuestionScoreInfo(param);
    	
    	List<Map<String,Object>> list=null;
    	//if(examList.contains(exam.getExamType())){ //在线考试或组卷
    		list =quesSplitService.statisticsExamQuestionslibSplitScoreInfoByteacher(exam.getId());
    	/*}else{ //练习题
    		list=quesSplitService.statisticsPracticeQuestionslibSplitScoreInfoByteacher(exam.getId());
    	}*/
    	
    	Map<String,List<Map<String,Object>>> map=Maps.newHashMap();
    	for(Map<String,Object> sourceMap:list){
    		//String value=sourceMap.get("totalScore").toString();
    		String key= sourceMap.get("ques_type").toString();
    		//List<Map<String,String>> strList=Lists.newArrayList();
    		if(map.containsKey(key)){
    			List<Map<String,Object>> strList=map.get(key);
    			strList.add(sourceMap);
    		}else{
    			List<Map<String,Object>> strList=Lists.newArrayList();
    			strList.add(sourceMap);
    			map.put(key, strList);
    		}
    	}
    	return map;
    	
    }
    
    
    /**
     * 在线考试和组卷的试卷类型List
     */
    public static  List<String> examList=Arrays.asList("2","5"); //1随堂测 2组卷考试 3作业 4例题 5在线考试
    
    
    
    
    /**
     * 查询该类型有多少个试题
     * @param exam
     * @return
     */
    public Map<String,String> statisticsVersionQuestionTypeCount(Examination exam){
    	/*User teacher=TearcherUserUtils.getUser();
    	Parameter param=new  Parameter(new Object[][]{
    			{"exam",exam},
    			{"schoolId",teacher.getCompany().getId()}
    	});
    	List<Map<String,Object>> list=versionQuestionDao.statisticsVersionQuestionScore(param);*/
    	List<Map<String,Object>> list=null;
    	//if(examList.contains(exam.getExamType())){ //在线考试或组卷
    		list =quesSplitService.statisticsExamQuestionslibSplitScoreInfoByteacher(exam.getId());
    	/*}else{ //练习题
    		list=quesSplitService.statisticsPracticeQuestionslibSplitScoreInfoByteacher(exam.getId());
    	}*/
    	
    	Map<String,String> map=Maps.newHashMap();
    	for(Map<String,Object> sourceMap:list){
    		String value=sourceMap.get("quesType_count").toString();
    		String key= sourceMap.get("ques_type").toString();
    		map.put(key, value);
    	}
    	return map;
    }
    
	/**
	 * 随机自动组卷
	 */
    @Transactional(readOnly=false)
	public  boolean  examination(Examination exam){
    	int result=0,result1=0;
		 //1、所考所有知识点
		/* List<String> examknowledgeIds=getknowledgeIdsByExamId(exam.getId()); 
		 String examTypeValue=exam.getExamType(); //1随堂测 2组卷考试 3作业 4例题  5在线考试
		 String examType="";
		 if("2".equals(examTypeValue)||"5".equals(examTypeValue)){
			 examType=" AND exam_type !=2 AND exam_type != 5 "; //考试
		 }else if("1".equals(examTypeValue)||"3".equals(examTypeValue)||"4".equals(examTypeValue)){
			 examType=" AND exam_type =2 OR exam_type =5 "; //其他
		 }
		 User teacher=TearcherUserUtils.getUser();
		 Parameter examknowledgeIdsParam=new  Parameter(new Object[][]{
			 {"exam",exam},
			 {"schoolId",teacher.getCompany().getId()}
				 /*{"examId",exam.getId()},
			 {"examType",examType},
			 {"examknowledgeIds",examknowledgeIds}
		 });*/
		 
		 //2、查询老师所在学校所有可以出卷的试题
		 //List<VersionQuestion> allQuestion= versionQuestionDao.findVersionQuestionByCourseKnowledgeIds(examknowledgeIdsParam);
		
    	List<VersionQuestion> allQuestion= quesSplitService.findExamQuestionslibSplitByteacher(exam.getId());
		 
		 Examdetail examdetail=null;
		 
		 List<ExamdetailQuestion> examdetailAQuestionList=Lists.newArrayList(); //存储A卷筛选的试题
		 
		 //删除已经存在的试卷（AB）卷、卷试题的关系数据
		 examdetailDao.deleteExamDetailContactByExamId(exam.getId());
		 
		 //Map<String,List<VersionQuestion>> maps=getALlMap(allQuestion);
		 
		 Map<String,List<VersionQuestion>> maps=getALlMapByQuesType(allQuestion);
		 
		 /**********不管是不是AB卷都需要出A卷 所以提了出来*********************/
		 
		 examdetail=new Examdetail();
		 examdetail.setId(IdGen.uuid());
		 examdetail.setExam(exam);
		 examdetail.setType("A");
		 //随机出题
		 //autoRandomQuestion(maps,exam,examdetailAQuestionList); 
		 
		 autoRandomQuestion1(maps,exam,examdetailAQuestionList); 
		 
		 //保存到数据库
		 result=saveExaminationToDataBase(examdetailAQuestionList,examdetail);
		 
		 if(Constants.EXAMINATION_ISAB_YES.equals(exam.getIsAb())){ //是AB卷  在出B卷试题 （B卷试题是在A卷上替换）
			 
			 
			 if(examdetailAQuestionList.size()>0&&result>0){
				 examdetail=new Examdetail();
				 examdetail.setId(IdGen.uuid());
				 examdetail.setExam(exam);
				 examdetail.setType("B");
				 
				 List<ExamdetailQuestion> examdetailBQuestionList=Lists.newArrayList(); //存储B卷筛选的试题 
				 //A卷所有试题
				 List<VersionQuestion> quesAList=Collections3.extractToList(examdetailAQuestionList,"question");
				 
				    Map<String,List<VersionQuestion>> aMap=getALlMapByQuesType(quesAList);
					 
					 for(Map.Entry<String, List<VersionQuestion>> entry:aMap.entrySet()){
						 //A卷具体试题集合
						 List<VersionQuestion> entryValue=entry.getValue();
						 //可以出题的集合
						 List<VersionQuestion> allList=maps.get(entry.getKey());
						 
						 //A卷 试题按分数分组
						 Map<Double,VersionQuestion> scoreMaps=groupbyList(entryValue);
						 
						 for(Map.Entry<Double,VersionQuestion> scoreEnry:scoreMaps.entrySet()){
							 Double  scoreEnryKey=scoreEnry.getKey();
							 VersionQuestion scoreEnryValue=scoreEnry.getValue();
							 
							//可以出题的集合（相同分值）
							List<VersionQuestion> temp=getQuestion(allList,scoreEnryKey);
							 
							randomAutoGroupQuestion(temp,scoreEnryValue.getList().size(),examdetailBQuestionList);
							
							 //b卷已出的试题
							 List<VersionQuestion> bQuestions=Collections3.extractToList(examdetailBQuestionList,"question");
							 
							 Map<String,List<VersionQuestion>> bQuestionMaps=getALlMapByQuesType(bQuestions);
							 
							 
							 List<VersionQuestion> bTemp=bQuestionMaps.get(entry.getKey());
							 if(bTemp==null){
								 bTemp=new ArrayList<VersionQuestion>();
							 }
							 
							 //判断b卷试题是否和A卷相等
							 int sub=entryValue.size()-bTemp.size();
							 if(sub>0){
								 randomAutoGroupQuestion(entryValue,sub,examdetailBQuestionList);
							 }
						 }
							 
						 
					 }
					 //保存到数据库
					 result1=saveExaminationToDataBase(examdetailBQuestionList,examdetail);
				 
			 }
			 
			 
			 /*if(examdetailAQuestionList.size()>0&&result>0){
				 
				 examdetail=new Examdetail();
				 examdetail.setId(IdGen.uuid());
				 examdetail.setExam(exam);
				 examdetail.setType("B");
				 
				 List<ExamdetailQuestion> examdetailBQuestionList=Lists.newArrayList(); //存储B卷筛选的试题 
				 //A卷所有试题
				 List<VersionQuestion> quesAList=Collections3.extractToList(examdetailAQuestionList,"question");
				 
				 //A卷题型和难易程度分类
				 Map<String,List<VersionQuestion>> aMap=getALlMap(quesAList);
				// Map<String,List<VersionQuestion>> aMap=getALlMapByQuesType(quesAList);
				 
				 for(Map.Entry<String, List<VersionQuestion>> entry:aMap.entrySet()){
					 //A卷具体试题集合
					 List<VersionQuestion> entryValue=entry.getValue();
					 //可以出题的集合
					 List<VersionQuestion> allList=maps.get(entry.getKey());
					 
					 //A卷 试题按分数分组
					 Map<Double,VersionQuestion> scoreMaps=groupbyList(entryValue);
					 
					 for(Map.Entry<Double,VersionQuestion> scoreEnry:scoreMaps.entrySet()){
						 Double  scoreEnryKey=scoreEnry.getKey();
						 VersionQuestion scoreEnryValue=scoreEnry.getValue();
						 
						//可以出题的集合（相同分值）
						List<VersionQuestion> temp=getQuestion(allList,scoreEnryKey);
						 
						randomAutoGroupQuestion(temp,scoreEnryValue.getList().size(),examdetailBQuestionList);
						
						 //b卷已出的试题
						 List<VersionQuestion> bQuestions=Collections3.extractToList(examdetailBQuestionList,"question");
						 Map<String,List<VersionQuestion>> bQuestionMaps=getALlMap(bQuestions);
						 List<VersionQuestion> bTemp=bQuestionMaps.get(entry.getKey());
						 if(bTemp==null){
							 bTemp=new ArrayList<VersionQuestion>();
						 }
						 
						 //判断b卷试题是否和A卷相等
						 int sub=entryValue.size()-bTemp.size();
						 if(sub>0){
							 randomAutoGroupQuestion(entryValue,sub,examdetailBQuestionList);
						 }
					 }
						 
					 
				 }
				 
				 //保存到数据库
				 result1=saveExaminationToDataBase(examdetailBQuestionList,examdetail);
				 
			 }*/
			 
			 return (result+result1)==2;
		 }
		 
		 return result==1;
		 
	}
	/**
	 * 符合的试题
	 * 保存到数据
	 * @param maps  题型分类后的数据集合
	 * @param exam  试卷基本信息
	 * @param examdetailQuestionList 保存试卷试题的集合
	 * @param detail AB卷信息
	 * @return
	 */
    @Transactional(readOnly=false)
    protected int saveExaminationToDataBase(List<ExamdetailQuestion> examdetailQuestionList, Examdetail detail){
    	int reuslt=0;
		 if(examdetailQuestionList.size()>0){
			 //保存到数据库
			 examdetailDao.insertSelective(detail);
			 
			 
			 
			 //存储试题分组后的 数据集合
			 Map<String,List<ExamdetailQuestion>> map=Maps.newTreeMap(new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			 
			//收集数据
			for(ExamdetailQuestion edq:examdetailQuestionList){
				if(edq!=null && edq.getQuestion()!=null){
					if(map.containsKey(edq.getQuestion().getQuesType())){
						List<ExamdetailQuestion> list=map.get(edq.getQuestion().getQuesType());
						list.add(edq);
					}else{
						List<ExamdetailQuestion> list=Lists.newArrayList();
						list.add(edq);
						map.put(edq.getQuestion().getQuesType(), list);
						
					}
				}
			}
		    
			//根据题型分组，分题型进行插入数据库（主要解决 按每个类型 进行从1排序的问题）
			for(Map.Entry<String, List<ExamdetailQuestion>> entry:map.entrySet()){
				
				 Parameter param=new Parameter(new Object[][]{
						    {"examDetailId",detail.getId()},
						    {"questionList",entry.getValue()},
				 });
				 examdetailQuestionDao.batchDeleAndInsert(param);
			}
			 
			/* for(ExamdetailQuestion e:examdetailQuestionList){
				 System.out.println("自动组卷筛选到的题");
				 System.out.print(e.getQuestion().getId()+"~" +e.getQuestion().getTitle()+"~"+e.getQuestion().getQuesType());
			 }*/
			 reuslt=1;
		 }else{
			 if(com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(detail.getType())){
				 System.out.print(detail.getType()+"卷             ");
			 }else{
				 System.out.print("一般卷           ");
			 }
			  
			 System.out.println("没有符合的试题");
			 reuslt=0;
		 }
		 System.out.println("**************************************************");
		 
		 return reuslt;
	 
    }
    
    
    public Map<String,Double>  getQuesScore(){
    	 List<Map<String, Object>> regionMap = examDao.findQuesScore();
	    Map<String, Double> resultMap =Maps.newHashMap();
	    for (Map<String, Object> map : regionMap) {
	      String quesType = null;
	      Double score = null;
	      for (Map.Entry<String, Object> entry : map.entrySet()) {
	        if ("quesType".equals(entry.getKey())) {
	        	quesType = (String) entry.getValue();
             } else if ("quesScore".equals(entry.getKey())) {
            	 score =Double.valueOf(entry.getValue().toString());
	        }
	      }
	      resultMap.put(quesType, score);
	    }
	    return resultMap;
    }
    
    
    
	/**
	 *随机出题 去掉难易程度
	 */
	private void  autoRandomQuestion1(Map<String,List<VersionQuestion>> allMaps,Examination exam,List<ExamdetailQuestion> examdetailQuestionList){
      
	 double  difficult_Number=getNumber(exam.getDifficult());
	  double  general_Number=getNumber(exam.getGeneral());
	  double  simple_Number=getNumber(exam.getSimple());
	  
	  //全部各种题型的分数
	  List<ExamQuestionScore> list= examQuestionScoreDao.findExamQuestionScoreExamId(exam.getId());
	  Map<String,Double> quesMap=getQuesScore();
	  for(ExamQuestionScore examQues:list){
		  
		String quesType= examQues.getQuesType(); //题的类型
		
		Double  totalScore=Double.valueOf(examQues.getQuesScore()); // 题的总分
		double singleQuesTypeScore=-1;
		Double  singleScore=quesMap.get(quesType);//固定分值
		
		List<VersionQuestion> quesList = allMaps.get(quesType); //本类型全部题
		
		
			if(difficult_Number==0&&simple_Number==0&&general_Number==0){
			
				if(singleScore!=null && singleScore>0){//固定分值
					if(singleScore.equals(2D)){ 
						int quesCount=new Double(Math.floor(totalScore/singleScore)).intValue();
						if(quesCount>0){
							randomAutoGroupQuestion(quesList,quesCount,examdetailQuestionList);
						}
					}else{
						randomAutoGroupQuestion(quesList,totalScore.intValue(),examdetailQuestionList);
					}
				}else{
					Set<VersionQuestion> target = Sets.newHashSet();
					randomAutoGroupQuestionSub(quesList,totalScore, target);
					if (target.size() > 0) {
						for (VersionQuestion v : target) {
							examdetailQuestionList.add(new ExamdetailQuestion(v));
						}
						
					}
					
				}
			}else{
				//难题
				if(difficult_Number>0){
					singleQuesTypeScore=Math.floor(totalScore*difficult_Number);
					if(singleQuesTypeScore>0){
						List<VersionQuestion> difficultList = allMaps.get(quesType + "_" + 3);
						
						if (singleScore != null) { //随机抽题
							int quesCount=new Double(Math.floor(singleQuesTypeScore/singleScore)).intValue();
							if(quesCount>0){
								randomAutoGroupQuestion(difficultList,quesCount,examdetailQuestionList);
							}
							
							
						} else { //分值递减抽题
							
							Set<VersionQuestion> target = Sets.newHashSet();
							randomAutoGroupQuestionSub(difficultList,singleQuesTypeScore, target);
							if (target.size() > 0) {
								for (VersionQuestion v : target) {
									examdetailQuestionList.add(new ExamdetailQuestion(v));
								}
								
							}
							
						}
					}
				}
				//一般题
				if(general_Number >0){
					singleQuesTypeScore=Math.floor(totalScore*general_Number);
					
					if(singleQuesTypeScore>0){
						List<VersionQuestion> generalList=allMaps.get(quesType+"_"+2);
						
						if (singleScore != null) { //随机抽题
							int quesCount=new Double(Math.floor(singleQuesTypeScore/singleScore)).intValue();
							if(quesCount>0){
								randomAutoGroupQuestion(generalList,quesCount,examdetailQuestionList);
							}
							
						} else { //分值递减抽题
							Set<VersionQuestion> target=Sets.newHashSet();
							randomAutoGroupQuestionSub(generalList,singleQuesTypeScore,target);
							if(target.size()>0){
								for(VersionQuestion v:target){
									examdetailQuestionList.add(new ExamdetailQuestion(v)); 
								}
								
							}
						}
					}
				}
				//简单题
				if(simple_Number>0){
					singleQuesTypeScore=Math.floor(totalScore*simple_Number);
					
					if(singleQuesTypeScore>0){
						List<VersionQuestion> simpleList=allMaps.get(quesType+"_"+1);
						if (singleScore != null) { //随机抽题
							int quesCount=new Double(Math.floor(singleQuesTypeScore/singleScore)).intValue();
							if(quesCount>0){
								randomAutoGroupQuestion(simpleList,quesCount,examdetailQuestionList);
							}
							
						} else { //分值递减抽题
							Set<VersionQuestion> target=Sets.newHashSet();
							randomAutoGroupQuestionSub(simpleList,singleQuesTypeScore,target);
							if(target.size()>0){
								for(VersionQuestion v:target){
									examdetailQuestionList.add(new ExamdetailQuestion(v)); 
								}
								
							}
						}
					}
				}
				
			}
			
		
		}
	  
		
		//难题
	   //根据题型排序
	   Collections.sort(examdetailQuestionList,new Comparator<ExamdetailQuestion>() {
			@Override
			public int compare(ExamdetailQuestion o1, ExamdetailQuestion o2) {
				return  o1.getQuestion().getQuesType().compareToIgnoreCase(o2.getQuestion().getQuesType());
			}
	   });
	}
	private void  autoRandomQuestion(Map<String,List<VersionQuestion>> allMaps,Examination exam,List<ExamdetailQuestion> examdetailQuestionList){
		
		double  difficult_Number=getNumber(exam.getDifficult());
		double  general_Number=getNumber(exam.getGeneral());
		double  simple_Number=getNumber(exam.getSimple());
		
		//全部各种题型的分数
		List<ExamQuestionScore> list= examQuestionScoreDao.findExamQuestionScoreExamId(exam.getId());
		Map<String,Double> quesMap=getQuesScore();
		for(ExamQuestionScore examQues:list){
			
			String quesType= examQues.getQuesType(); //题的类型
			Double  totalScore=Double.valueOf(examQues.getQuesScore()); // 题的总分
			double singleQuesTypeScore=-1;
			
			Double  singleScore=quesMap.get(quesType);//固定分值
			
			//难题
			if(difficult_Number>0){
				singleQuesTypeScore=Math.floor(totalScore*difficult_Number);
				if(singleQuesTypeScore>0){
					List<VersionQuestion> difficultList = allMaps.get(quesType + "_" + 3);
					
					if (singleScore != null) { //随机抽题
						int quesCount=new Double(Math.floor(singleQuesTypeScore/singleScore)).intValue();
						if(quesCount>0){
							randomAutoGroupQuestion(difficultList,quesCount,examdetailQuestionList);
						}
						
						
					} else { //分值递减抽题
						
						Set<VersionQuestion> target = Sets.newHashSet();
						randomAutoGroupQuestionSub(difficultList,singleQuesTypeScore, target);
						if (target.size() > 0) {
							for (VersionQuestion v : target) {
								examdetailQuestionList.add(new ExamdetailQuestion(v));
							}
							
						}
						
					}
				}
			}
			//一般题
			if(general_Number >0){
				singleQuesTypeScore=Math.floor(totalScore*general_Number);
				
				if(singleQuesTypeScore>0){
					List<VersionQuestion> generalList=allMaps.get(quesType+"_"+2);
					
					if (singleScore != null) { //随机抽题
						int quesCount=new Double(Math.floor(singleQuesTypeScore/singleScore)).intValue();
						if(quesCount>0){
							randomAutoGroupQuestion(generalList,quesCount,examdetailQuestionList);
						}
						
					} else { //分值递减抽题
						Set<VersionQuestion> target=Sets.newHashSet();
						randomAutoGroupQuestionSub(generalList,singleQuesTypeScore,target);
						if(target.size()>0){
							for(VersionQuestion v:target){
								examdetailQuestionList.add(new ExamdetailQuestion(v)); 
							}
							
						}
					}
				}
			}
			//简单题
			if(simple_Number>0){
				singleQuesTypeScore=Math.floor(totalScore*simple_Number);
				
				if(singleQuesTypeScore>0){
					List<VersionQuestion> simpleList=allMaps.get(quesType+"_"+1);
					if (singleScore != null) { //随机抽题
						int quesCount=new Double(Math.floor(singleQuesTypeScore/singleScore)).intValue();
						if(quesCount>0){
							randomAutoGroupQuestion(simpleList,quesCount,examdetailQuestionList);
						}
						
					} else { //分值递减抽题
						Set<VersionQuestion> target=Sets.newHashSet();
						randomAutoGroupQuestionSub(simpleList,singleQuesTypeScore,target);
						if(target.size()>0){
							for(VersionQuestion v:target){
								examdetailQuestionList.add(new ExamdetailQuestion(v)); 
							}
							
						}
					}
				}
			}
		}
		//根据题型排序
		Collections.sort(examdetailQuestionList,new Comparator<ExamdetailQuestion>() {
			@Override
			public int compare(ExamdetailQuestion o1, ExamdetailQuestion o2) {
				return  o1.getQuestion().getQuesType().compareToIgnoreCase(o2.getQuestion().getQuesType());
			}
		});
		
		System.out.println(difficult_Number+"~"+general_Number+"~"+simple_Number);
		
		
	}
	
	/**
	 * 随机出 填空题和计算题  
	 * 出题方法（总分数递减试题分数）
	 * @param sourceList
	 * @param score
	 * @param targetList
	 */
	private  void  randomAutoGroupQuestionSub(List<VersionQuestion> sourceList,double score,Set<VersionQuestion> targetList){
	  if(sourceList!=null &&sourceList.size()>0){
		int tmp= RandomUtils.nextInt(0, sourceList.size());
		VersionQuestion v=sourceList.get(tmp);
		//当前选中题的分数
		double sd=Double.valueOf(v.getQuesPoint());
		double sub=score-sd;
		
		/*if(targetList.contains(v)){
			randomAutoGroupQuestionSub(sourceList,score,targetList);
		}*/
		if(sub>0){
			targetList.add(v);
			sourceList.remove(v);//删除当前数据
			randomAutoGroupQuestionSub(sourceList,sub,targetList);
		}else if(sub==0){
			targetList.add(v);
			sourceList.remove(v);//删除当前数据
		}
	  }
	}
	
	
	
	
	/**
	 * 随机出题 适用  单选题、多选题、和简答题，分数一致的题型
	 * @param sourceList  出题的数据源
	 * @param count 出多少道题
	 * @param examdetailQuestionList 存放随机后的试题
	 */
	protected void  randomAutoGroupQuestion(List<VersionQuestion> sourceList,int count,List<ExamdetailQuestion> examdetailQuestionList){
		  for(int i=0;i<count;i++){
			  if(sourceList!=null&&sourceList.size()>0){
				 int tmp=RandomUtils.nextInt(0, sourceList.size());
				 VersionQuestion v=sourceList.get(tmp);
				 sourceList.remove(tmp);
		    	 examdetailQuestionList.add(new ExamdetailQuestion(v));
			  }
		  }
	}

	
	
	
	
	
	/**
	 * 按试题分数分组收集同分的试题集合
	 * @param list
	 * @return
	 */
    public Map<Double,VersionQuestion> groupbyList(List<VersionQuestion> list){
    	Map<Double,VersionQuestion> map=Maps.newTreeMap(new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				return o1.compareTo(o2); 
			}
    		
		});
    	
        for(VersionQuestion v:list){
        	Double key=Double.valueOf(v.getQuesPoint());
        	if(map.containsKey(key)){
        		VersionQuestion temp=map.get(key);
        		temp.getList().add(v);
        		map.put(key, temp);
        	}else{
        		v.getList().add(v);
        		map.put(key,v);
        	}
        }
        
        return map;
    }
	
	
	
	public Double  getNumber(String str){
		 if(StringUtils.isBlank(str)){
			 return 0D;
		 }
		 if(str.contains("%")) {  
			 str = str.replaceAll("%", "");
		 }
	     Double d= Double.valueOf(str);
		 return d/100;
	}
	
	
	
	
	/**
	 * 得到分类完毕的全部题型
	 */
    public Map<String,List<VersionQuestion>>  getALlMapByQuesType(List<VersionQuestion> allQuestion){
    	
    	Map<String,List<VersionQuestion>>  maps=Maps.newHashMap();
    	// 各种题型 
    	List<Dict> quesType=DictUtils.getDictList("question_type"); //各种题型
    //	List<Dict> quesLevel=DictUtils.getDictList("question_level"); //难易级别
    	for(Dict type:quesType){
    	    List<VersionQuestion> list=getQuestion(allQuestion,type.getValue());
			 if(list.size()>0){
			    maps.put(type.getValue(), list);
			 }
    	}
    	return  maps;
    }
	
	
	
	/**
	 * 得到分类，难易程度 的全部题型
	 */
    public Map<String,List<VersionQuestion>>  getALlMap(List<VersionQuestion> allQuestion){
    	
    	Map<String,List<VersionQuestion>>  maps=Maps.newHashMap();
    	// 各种题型 
    	List<Dict> quesType=DictUtils.getDictList("question_type"); //各种题型
    	List<Dict> quesLevel=DictUtils.getDictList("question_level"); //难易级别
    	for(Dict type:quesType){
    		for(Dict level:quesLevel){
    			 List<VersionQuestion> list=getQuestion(allQuestion,type.getValue(),level.getValue());
    			 if(list.size()>0){
    			    maps.put(type.getValue()+"_"+level.getValue(), list);
    			 }
    		}
    	}
    	return  maps;
    }
	
	
	/**
	 * 分组
	 * @param all 全部能用题
	 * @param quesType 题的类型
	 * @param quesLevel  题的难易级别
	 * @return
	 */
	public List<VersionQuestion>  getQuestion(List<VersionQuestion> all,String quesType){
		List<VersionQuestion> list=Lists.newArrayList();
		for(VersionQuestion v:all){
			if(quesType.equals(v.getQuesType())){
				list.add(v);
			}
		}
		return list;
	}
	
	
    /**
     * 
     * @param all       试题集合
     * @param knowLedgeId 所属知识点
     * @param quesType   题的类型
     * @param quesLevel  题的难易程度
     * @return 
     */
    public List<VersionQuestion>  getQuestion(List<VersionQuestion> all,String knowLedgeId,String quesType ,String quesLevel){
    	
    	List<VersionQuestion> list=Lists.newArrayList();
    	for(VersionQuestion v:all){
    		if(quesType.equals(v.getQuesType())&&quesLevel.equals(v.getQuesLevel())&&v.getCourseKnowledge()!=null && v.getCourseKnowledge().getId().equals(knowLedgeId)){
    			list.add(v);
    		}
    	}
    	return list;
    }
	/**
	 * 分组
	 * @param all 全部能用题
	 * @param quesType 题的类型
	 * @param quesLevel  题的难易级别
	 * @return
	 */
	public List<VersionQuestion>  getQuestion(List<VersionQuestion> all,String quesType ,String quesLevel){
		List<VersionQuestion> list=Lists.newArrayList();
		for(VersionQuestion v:all){
			if(quesType.equals(v.getQuesType())&&quesLevel.equals(v.getQuesLevel())){
				list.add(v);
			}
		}
		return list;
	}
	/**
	 * 
	 * @param all  试题集合
	 * @param score 试题的分数
	 * @return 筛选分数相同的试题集合
	 */
	public List<VersionQuestion>  getQuestion(List<VersionQuestion> all,Double score){
		List<VersionQuestion> list=Lists.newArrayList();
		for(VersionQuestion v:all){
			Double quesPoint=Double.valueOf(v.getQuesPoint());
			if(score.equals(quesPoint)){
				list.add(v);
			}
		}
		return list;
	}
	
	
	
	
	
	
	
	/**
	 * 保存试卷专业
	 * @param examId  试卷id
	 * @param specilityDataRelation 以","分割
	 * @param classDataRelation 以","分割
	 */
	/*@Transactional(readOnly=false)
	public void  saveExamSpecilities(Examination exam,String specilityDataRelation,String classDataRelation){
	   
		 //1、修改试卷信息
		
		 examDao.updateExamSelect(exam);
		 
		 //2、先删除试卷专业
		 String[] specilityArr=specilityDataRelation.split(",");
		 
		 Parameter sParam=new Parameter(new Object[][]{
			{"examId",exam.getId()},
			{"specilityArr",specilityArr}
		 });
		 if(specilityArr.length>0){
			 //1先删除专业
			 //2先添加专业
			 examSpecilityDao.batchAdd(sParam);
		 }
		 
	     //3、添加试卷班级
		 String[] classArr=classDataRelation.split(",");
		 Parameter cParam=new Parameter(new Object[][]{
				 {"examId",exam.getId()},
				 {"classArr",classArr}
		 });
		 
		 if(classArr.length>0){
			 //1先删除班级
			 //2后添加班级
			 examClassDao.batchAdd(cParam);
		 }
	}*/
	
	/**
	 * 保存试卷班级
	 * @param examId  试卷id
	 * @param classDataRelation 以","分割
	 */
	@Transactional(readOnly=false)
	public void  saveExamClass(Examination exam,String classDataRelation){
		 //1、修改试卷信息
		 examDao.updateExamSelect(exam);
		 
	     //2、添加试卷班级
		 String[] classArr=classDataRelation.split(",");
		 Parameter cParam=new Parameter(new Object[][]{
				 {"examId",exam.getId()},
				 {"classArr",classArr},
				 {"status","1"}
		 });
		 
		 if(classArr.length>0){
			 //1先删除班级
			 //2后添加班级
			 examClassDao.batchAdd(cParam);
		 }
	}
	@Transactional(readOnly=false)
	public void  saveExamClass2(Examination exam,String classDataRelation){
		 //1、修改试卷信息
		 examDao.updateExamSelect(exam);
		 
	     //2、添加试卷班级
		 String[] classArr=classDataRelation.split(",");
		 Parameter cParam=new Parameter(new Object[][]{
				 {"examId",exam.getId()},
				 {"classArr",classArr},
				 {"status","0"}
		 });
		 
		 if(classArr.length>0){
			 //1先删除班级
			 //2后添加班级
			 examClassDao.batchAdd(cParam);
		 }
	}
	
	
	/**
	 * 查询试卷适用的专业
	 */
	public List<ExamSpecility> getExamSpecilities(String examId){
		return examSpecilityDao.getExamSpecilitiesByExamId(examId);
	}
	
	/**
	 * 查询试卷适用的班级
	 */
	public List<ExamClass> getExamClass(String examId){
		
		return examClassDao.getExamClassByExamId(examId);
	}
	
	
	
	/**
	 * 修改试卷信息
	 */
	@Transactional(readOnly=false)
	public int  updateExam(Examination exam){
		return examDao.updateExamSelect(exam);
	}
	
	


    /**
     * 全部专业
     * @param page
     * @param specialty
     * @return
     */
	public Page<Specialty> findSpecilityList(Page<Specialty> page,Specialty specialty) {
		//TODO 老师选的专业
		specialty.setPage(page);
		page.setList(specialtyDao.findList(specialty));
		return page;
	}
	/**
	 * 全部班级
	 * @param page
	 * @param specialty
	 * @return
	 */
	public Page<SchoolClass> findClassList(Page<SchoolClass> page,SchoolClass schoolClass) {
		schoolClass.setPage(page);
		Map<String,String> sqlMaps=schoolClass.getSqlMap();
		sqlMaps.putAll(TearcherUserUtils.getTeacherIdAndCourseVersionId());
		page.setList(classDao.findList(schoolClass));
		return page;
	}
    
	
	/**
	 * 课后作业手动生成组卷的确认细节
	 * 根据id罗列所选中的知识点
	 */
	public Page<Examknowledge> queryExamKnowleByExamId(Page<Examknowledge> page, Examknowledge exam){
			String examId="";
			if(exam!=null&&!(exam.equals(""))&&StringUtils.isNotBlank(exam.getExam().getId())){
				examId=exam.getExam().getId();
			}
			exam.setPage(page);
			page.setList(examknowledgeDao.queryExamKnowleByExamId(examId));
			return page;
	}
	
	/**
	 * 随堂练习及课堂例题中根据知识点id查询题目
	 */
	public List<TeacherVersionQuestion> selectQuestionById(TeacherVersionQuestion question){
		//根据知识点查询该知识点下的所有孩子
		///question.getSqlMap().put("schoolId", TearcherUserUtils.getUser().getCompany().getId());
		Map<String,String> sqlMap=question.getSqlMap();
		sqlMap.putAll(TearcherUserUtils.getTeacherIdAndCourseVersionId());
		return examknowledgeDao.selectQuestionById(question);
	}
	
	
	/**
	 * 保存随堂例题的试卷,班级,知识点以及题目
	 * @param examId  试卷id
	 * @param classDataRelation 以","分割
	 */
	@Transactional(readOnly=false)
	public void  addClassExample(Examination exam,String classId,String questionId,String knowId){
		 //1、修改试卷信息
		 examDao.updateExamSelect(exam);
	     //2、添加试卷班级
		 String[] classArr=classId.split(",");
		 Parameter cParam=new Parameter(new Object[][]{
				 {"examId",exam.getId()},
				 {"classArr",classArr},
				 {"questionId",questionId},
				 {"knowId",knowId}
		 });
		 
		 if(classArr.length>0){
			 //1先删除班级
			 //2后添加班级
			 examClassDao.addClassExample(cParam);
		 }
	}
	
	
	
	
	/**
	 * 保存随堂测试的试卷,班级,知识点以及题目
	 * @param examId  试卷id
	 * @param classDataRelation 以","分割
	 */
	@Transactional(readOnly=false)
	public String addExamDetail(Examination exam){
		 //2、添加到examDetail表中默认类型为0并查询examDetail的id
		 String detailId=IdGen.uuid();
		 examClassDao.addExerciseExam(exam.getId(),detailId);
		 return detailId;
	}
	@Transactional(readOnly=false)
	public void  addExerciseExam(Examination exam,String classId){
		 //1、修改试卷信息
		 examDao.updateExamSelect(exam);
		 
		/* //2、添加到examDetail表中默认类型为0并查询examDetail的id
		 String detailId=IdGen.uuid();
		 examClassDao.addExerciseExam(exam.getId(),detailId);*/
		 String status=exam.getStatus();
		 if(status==null||(("").equals(status))){
			 status="0";
		 }
		//3、添加试卷班级
		 String[] classArr=classId.split(",");
		 Parameter cParam=new Parameter(new Object[][]{
				 {"examId",exam.getId()},
				 {"classArr",classArr},
				 {"status",status}
		 });
		 if(classArr.length>0){
			 //1先删除班级
			 //2后添加班级
			 examClassDao.addExercise(cParam);
		 }
	}
	
	
	/**
	 * 查询该随堂练习的详情
	 * @param id
	 * @return
	 */
	public Page<TeacherVersionQuestion> selectStudentByExamId(Page<TeacherVersionQuestion> page,TeacherVersionQuestion question,String examId,String classId){
		question.setPage(page);
		Map<String, String> sqlMap = question.getSqlMap();
		sqlMap.put("examId", examId);
		sqlMap.put("classId", classId);
		List<TeacherVersionQuestion> selectStudentByExamId = examClassDao.selectStudentByExamId(question);
		for (TeacherVersionQuestion teacherVersionQuestion : selectStudentByExamId) {
			if(teacherVersionQuestion!=null &&teacherVersionQuestion.getStudent()!=null && teacherVersionQuestion.getStudent().getId()!=null){
				String studentId=teacherVersionQuestion.getStudent().getId();
				/*StudentAnswer answer = exam2Dao.queryPersondetail(examId, questionId, studentId);*/
				Date submit=examClassDao.findStudnetSubmitTime(studentId,examId);		//获取提交时间
				teacherVersionQuestion.setSubmitTime(submit);
				/*if(answer!=null){
					teacherVersionQuestion.setAnswer(answer.getAnswer0());//答案
					teacherVersionQuestion.setIsRight(answer.getIsCorrect());//is_correct是否正确：0--错误；1--正确
					teacherVersionQuestion.setCreateDate(submit);
				}else{
					teacherVersionQuestion.setAnswer(null);
					teacherVersionQuestion.setIsRight(0);
				}*/
			}
		}
		page.setList(selectStudentByExamId);
		return page;
	}
	/**
	 * 手动添加课后练习
	 */
	@Transactional(readOnly=false)
	public void addKnowledgeQuestionDetail(KnowledgeQuestionDetail know){
		know.setId(IdGen.uuid());
		examknowledgeDao.addKnowledgeQuestionDetail(know);
	}
	
	
	/**试题调整顺序
	 */
	@Transactional(readOnly=false)
	public void updateExamDetailQuesSort(ExamdetailQuestion eq) {
		examdetailQuestionDao.updateExamDetailQuesSort(eq);
		
	}
    
	/**
	 * 自动组卷时 选中知识点的第一级父节点的知识
	 * @param examId
	 * @return
	 */
	public List<CourseKnowledge> getSelectKnowledgeFirstParentByExamId(String examId){
		CourseKnowledge courseKnowledge=new CourseKnowledge();
		courseKnowledge.getSqlParam().put("examId", examId);
		courseKnowledge.getSqlMap().putAll(TearcherUserUtils.getTeacherIdAndCourseVersionId());
	   return examknowledgeDao.getSelectKnowledgeFirstParentByExamId(courseKnowledge);
	}
    
	/**
	 * 查询试卷A或B的试题 
	 * @param param examDetailId
	 * @return
	 */
	public Map<ExamDetailQuestionInfo,List<VersionQuestion>> findQuestionsByExamDetailId(String examDetailId){
		Parameter param=new Parameter(new Object[][]{
				{"examDetailId",examDetailId}
		});
		
		List<ExamDetailQuestionInfo> dataList=examdetailQuestionDao.findQuestionsByExamDetailId(param);
		
		 Map<ExamDetailQuestionInfo,List<VersionQuestion>>  map=Maps.newTreeMap(new Comparator<ExamDetailQuestionInfo>() {
			@Override
			public int compare(ExamDetailQuestionInfo o1,
					ExamDetailQuestionInfo o2) {
				return o1.getQuesTypes().compareTo(o2.getQuesTypes());
			}
		 });
		
		for(ExamDetailQuestionInfo edqi:dataList){
			if(map.containsKey(edqi)){
				List<VersionQuestion> list=map.get(edqi);
				list.add((VersionQuestion)edqi);
			}else{
				List<VersionQuestion> list=Lists.newArrayList();
				list.add((VersionQuestion)edqi);
				map.put(edqi, list);
			}
		}
		return map;
	}
	
	
	static String[] chinaNumber = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十"};
	
	/**
	 * 查询试卷A或B的试题 转为Json
	 * @param examDetailId AB卷Id
	 * @param strArr  string数组 第一个是主标题，第二值是副标题
	 * @return json 格式的数据
	 */
	public String  findQuesTionsByExamDetailId2Json(String examDetailId,String...strArr){
		
        Map<String, Object> jsonRootMap =Maps.newHashMap();
		List<Map<String, Object>> jsonQuesCateList = Lists.newArrayList();
		
		Map<ExamDetailQuestionInfo,List<VersionQuestion>> dataMaps=findQuestionsByExamDetailId(examDetailId);
		Map<String,Double> quesScoreMap=getQuesScore();
    	String  strQuesTitle1="{0}、{1} (每题{2}分,共{3}分)"; 
    	String  strQuesTitle2="{0}、{1} (共{2}分)"; 
    	
    	int i=1;
    	for(Map.Entry<ExamDetailQuestionInfo,List<VersionQuestion>> entry:dataMaps.entrySet()){
    		
    		Map<String, Object> jsonQuesCateMap =Maps.newHashMap();
    		
    		
    		ExamDetailQuestionInfo info=entry.getKey();
    		
    		String quesType=info.getQuesTypes();
    		
    		Double singleScore=quesScoreMap.get(quesType);
    		
    		String title=DictUtils.getDictLabel(quesType, "question_type", "");
    		
    		
    		String quesCateTitle="";
    		
    		if(singleScore !=null){
    			quesCateTitle=MessageFormat.format(strQuesTitle1, chinaNumber[i],title,info.getQuesScore(),info.getTotalScore());
    		}else{
    			quesCateTitle=MessageFormat.format(strQuesTitle2, chinaNumber[i],title,info.getTotalScore());
    		}
    		
    		//System.out.println(quesCateTitle);
    		
    		jsonQuesCateMap.put("quesCateTitle", quesCateTitle);
    		
    		List<VersionQuestion>  list=entry.getValue();
    		
    		
    		
    		List<Map<String, Object>> jsonQuesList = Lists.newArrayList();
    		
    		int j=1;
    		for(VersionQuestion vq:list){
    			
    			Map<String, Object> jsonQuesMap =Maps.newHashMap();
    			
    			String quesTitle="";
    			
    			if(StringUtils.isNotBlank(vq.getTitle())){
    				quesTitle=vq.getTitle().replaceAll(" ", " ");
    				quesTitle= StringEscapeUtils.escapeHtml4(quesTitle).replace(" ", " ");
    			}
    			
    			
    			if(singleScore !=null){
    				//quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle());
    				quesTitle=j+"、"+quesTitle;
    			}else{
    				//quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle())+"("+vq.getQuesPoint()+")";
    				quesTitle=j+"、"+quesTitle+"("+vq.getQuesPoint()+"分)";
    				
    				//quesTitle=j+"、"+HtmlUtils.htmlEscape(vq.getTitle())+"("+vq.getQuesPoint()+")";
    			}
    			//System.out.println(quesTitle);
    			
    			jsonQuesMap.put("quesTitle",quesTitle);
    			
    			 List< Map<String, Object>> choiceList=Lists.newArrayList();
    			 for(int k=0,len=9;k<len;k++){
    				 Map<String, Object> jsonQuesChoiceMap =Maps.newHashMap();
    				 
    				 Object obj=null;
    				 try {
						obj=PropertyUtils.getProperty(vq,"choice"+k);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
    				 
					if(obj !=null){
						
						String choiceTitle="";
						if("11".equals(quesType)){ //判断题
							//choiceTitle=obj.toString();
							
						}
						else{
							String tile=obj.toString().replace(" ", " ").trim();
							if (StringUtils.isBlank(tile)) {
								continue;
							}
							 
							/*if(!tile.contains((char)(k+65)+"")){
							  choiceTitle=(char)(k+65)+"、"+obj.toString();
							}else{
								choiceTitle=tile.toString();
							}*/
							
							if("1".equals(quesType)||"5".equals(quesType)){ 
								if(tile.indexOf((char)(k+65)+"、")==0||tile.indexOf((char)(k+65)+".")==0)
								{
									choiceTitle=tile.toString();
								}else {
									choiceTitle=(char)(k+65)+"、"+obj.toString();
								}
								
							}
							 
						}
						 System.out.println(choiceTitle);
						
						jsonQuesChoiceMap.put("choice", choiceTitle);
						
						choiceList.add(jsonQuesChoiceMap);
						
						continue;
					}
					break;
    			 }
    
    			 jsonQuesMap.put("choiceList",choiceList);
    			 jsonQuesList.add(jsonQuesMap);
    			 
    			j++;
    		}
    		
    		jsonQuesCateMap.put("quesList", jsonQuesList);
    		jsonQuesCateList.add(jsonQuesCateMap);
    		i++;
    	}
    	
    	if(strArr.length>=2){
    	   jsonRootMap.put("mainTitle", strArr[0]);
           jsonRootMap.put("subTitle", strArr[1]);
    	}else if(strArr.length>=1){
    	   jsonRootMap.put("mainTitle", strArr[0]);
    	   jsonRootMap.put("subTitle","");
    	}else{
    	   jsonRootMap.put("mainTitle","");
      	   jsonRootMap.put("subTitle","");
    	}
    	
    	jsonRootMap.put("quesCateList", jsonQuesCateList);
    	
    	//System.out.println(JsonMapper.toJsonString(jsonRootMap));
    	return JsonMapper.toJsonString(jsonRootMap);
	}
	
	/**
	 * 查询试卷A或B的试题 转为Json
	 * @param examDetailId AB卷Id
	 * @param strArr  string数组 第一个是主标题，第二值是副标题
	 * @return json 格式的数据
	 */
	 public String findQuesTionsAnswerByExamDetailId2Json(String examDetailId,String...strArr){
			
			Map<String, Object> jsonRootMap =Maps.newHashMap();
			
			List<Map<String, Object>> jsonQuesCateList = Lists.newArrayList();
			
			Map<ExamDetailQuestionInfo,List<VersionQuestion>> dataMaps=findQuestionsByExamDetailId(examDetailId);
			/*Map<String,Double> quesScoreMap=examService.getQuesScore();
	    	String  strQuesTitle1="{0}、{1} (每题{2}分,共{3}分)"; 
	    	String  strQuesTitle2="{0}、{1} (共{2}分)"; */
	    	String  strQuesTitle3="{0}、{1}"; 
	    	
	    	int i=1;
	    	for(Map.Entry<ExamDetailQuestionInfo,List<VersionQuestion>> entry:dataMaps.entrySet()){
	    		
	    		Map<String, Object> jsonQuesCateMap =Maps.newHashMap();
	    		
	    		
	    		ExamDetailQuestionInfo info=entry.getKey();
	    		
	    		String quesType=info.getQuesTypes();
	    		
	    		//Double singleScore=quesScoreMap.get(quesType);
	    		
	    		String title=DictUtils.getDictLabel(quesType, "question_type", "");
	    		
	    		
	    		String quesCateTitle=MessageFormat.format(strQuesTitle3, chinaNumber[i],title);
	    		
	    		/*if(singleScore !=null){
	    			quesCateTitle=MessageFormat.format(strQuesTitle1, chinaNumber[i],title,info.getQuesScore(),info.getTotalScore());
	    		}else{
	    			quesCateTitle=MessageFormat.format(strQuesTitle2, chinaNumber[i],title,info.getTotalScore());
	    		}*/
	    		
	    		
	    		
	    	    //System.out.println(quesCateTitle);
	    		
	    		jsonQuesCateMap.put("quesCateTitle", quesCateTitle);
	    		
	    		List<VersionQuestion>  list=entry.getValue();
	    		
	    		List<Map<String, Object>> jsonQuesList = Lists.newArrayList();
	    		
	    		int j=1;
	    		for(VersionQuestion vq:list){
	    			
	    			Map<String, Object> jsonQuesMap =Maps.newHashMap();
	    			
	    			/*String quesTitle="";
	    			
	    			if(singleScore !=null){
	    				//quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle());
	    				quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle());
	    			}else{
	    				//quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle())+"("+vq.getQuesPoint()+")";
	    				quesTitle=j+"、"+StringEscapeUtils.escapeHtml4(vq.getTitle())+"("+vq.getQuesPoint()+")";
	    				
	    				//quesTitle=j+"、"+HtmlUtils.htmlEscape(vq.getTitle())+"("+vq.getQuesPoint()+")";
	    			}
	    			System.out.println(quesTitle);
	    			
	    			jsonQuesMap.put("quesTitle",quesTitle);*/
	    			
	    			 //List< Map<String, Object>> choiceList=Lists.newArrayList();
	    			  List<String> answerList=Lists.newArrayList();
	    			 for(int k=0,len=9;k<len;k++){
	    				// Map<String, Object> jsonQuesChoiceMap =Maps.newHashMap();
	    				 
	    				 Object obj=null;
	    				 try {
							obj=PropertyUtils.getProperty(vq,"answer"+k);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
	    				 
						if(obj !=null){
							//String choiceTitle=(char)(k+65)+"、"+obj.toString();
							//String choiceTitle=obj.toString();
							
							//System.out.println(obj.toString());
							if("11".equals(quesType)){
								if("1".equals(obj.toString())){
									answerList.add("对");
								}else if("2".equals(obj.toString())){
									answerList.add("错");
								}
							}else{
								String ans =StringEscapeUtils.escapeHtml4(obj.toString()).replace(" ", " ");
								if(StringUtils.isNotBlank(ans))
								{
								   answerList.add(ans+";");
								}
							}
							//jsonQuesChoiceMap.put("answer", choiceTitle);
							
							//choiceList.add(jsonQuesChoiceMap);
							
							continue;
						}
						break;
	    			 }
	                 String  desc=StringEscapeUtils.escapeHtml4(vq.getDescription()).replace(" ", " ");
	    			 jsonQuesMap.put("quesTitle",j+"、"+StringUtils.join(answerList,"")+"$此处换行$ 讲解："+desc);
	    			// System.out.println(j+"、"+StringUtils.join(answerList,""));
	    			 //jsonQuesMap.put("choiceList",choiceList);
	    			  
	    			 jsonQuesList.add(jsonQuesMap);
	    			 
	    			j++;
	    		}
	    		
	    		jsonQuesCateMap.put("quesList", jsonQuesList);
	    		jsonQuesCateList.add(jsonQuesCateMap);
	    		i++;
	    	}
	    	
	    	
	    	if(strArr.length>=2){
	     	   jsonRootMap.put("mainTitle", strArr[0]);
	            jsonRootMap.put("subTitle", strArr[1]);
	     	}else if(strArr.length>=1){
	     	   jsonRootMap.put("mainTitle", strArr[0]);
	     	   jsonRootMap.put("subTitle","");
	     	}else{
	     	   jsonRootMap.put("mainTitle","答案");
	       	   jsonRootMap.put("subTitle","");
	     	}
	    	jsonRootMap.put("quesCateList", jsonQuesCateList);
	    	
	    	//System.out.println(JsonMapper.toJsonString(jsonRootMap));
	    	return JsonMapper.toJsonString(jsonRootMap).replace(" ", " ");
	    }
	 /**
	  * 根据examId查询问题以及知识点
	  * 格式    问题：知识点
	  * @param examId
	  * @return
	  */
	public String getquestionidsAndKnowId(String examId) {
		List<String> list=examDao.getKnowIds(examId);
		String questionIds = null;
		for (String knowId : list) {
			List<String> questionIdsList=examDao.getQuestionIds(examId);
			for (String questionId : questionIdsList) {
				if(questionIds==null){
					questionIds=questionId+":"+knowId;
				}else{
					questionIds=questionIds+","+questionId+":"+knowId;
				}
			}
		}
		return questionIds;
	}
	
	 public String  getKnowIdsByExamId(String examId){
		 List<String> list=examDao.getKnowIds(examId);
	        return Collections3.convertToString(list,",");
	    }
	 
    public String  getQuestionidsByExamId(String examId){
    	List<String> list=examDao.getQuestionIds(examId);
        return Collections3.convertToString(list,",");
    }
    //随堂例题的列表
	public Page<Exam> getExampleList(Page<Exam> page, Exam exam) {
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		exam.setPage(page);
		Map<String, String> sqlMap = exam.getSqlMap();
		sqlMap.put("userId", userId);
		sqlMap.put("versionId", versionId);
		page.setList(examDao.getExampleList(exam));
		return page;
	}

	public String getExamDetailId2(String examId) {
		return exam2Dao.getExamDetailId2(examId);
	}
	@Transactional(readOnly=false)
	public void saveMainTitleAndSubTitle(String examId, String examtype, String mainTitle_A, String subTitle_A) {
		examDao.saveMainTitleAndSubTitle(examId,examtype,mainTitle_A,subTitle_A);
	}
	//获取试卷总题数
	public Integer getQuestionlibCount(String examId) {
		return exam2Dao.getQuestionlibCount(examId);
	}

	public Exam getExamByExamDetailId(String quesDetailId) {
		return exam2Dao.getExamByExamDetailId(quesDetailId);
	}
	
}
