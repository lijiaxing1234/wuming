package com.thinkgem.jeesite.modules.teacher.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.dao.ExamdetailDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamdetailQuestionDao;
import com.thinkgem.jeesite.modules.teacher.dao.QuestionRecordDao;
import com.thinkgem.jeesite.modules.teacher.entity.Examdetail;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.entity.QuestionRecord;
import com.thinkgem.jeesite.modules.teacher.entity.QuestionslibSplit;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 试题记录的服务层
 * @author flychao
 * @version v.0.1
 */
@Service
public class QuestionRecordService {
	
	 @Autowired
	 QuestionRecordDao questionRecordDao;
	
	 @Autowired
	 ExamdetailDao examdetailDao;
	 @Autowired
	 ExamdetailQuestionDao examdetailQuestionDao;
	/**
	 * 获取老师已经出的试题次数
	 * @param qr
	 * @return  List<QuestionRecord> 
	 */
	 public List<QuestionRecord> findQuestionRecordByTeacher(QuestionRecord qr){
		 return  questionRecordDao.findQuestionRecordByTeacher(qr);
	 }
	 /**
	 * 获取老师已经出的试题次数
	 * @param qr
	 * @return  List<QuestionRecord> 
	 */
	 public QuestionRecord  getQuestionRecordByTeacher(QuestionRecord qr){
		 List<QuestionRecord> list=findQuestionRecordByTeacher(qr);
		 if(list !=null&&list.size()>0){
			 return list.get(0);
		 }
		 return null;
	 }
	 
	 /**
	  * 插入次数
	  * @param qr
	  */
	 public void  insertSelective(QuestionRecord qr){
		 questionRecordDao.insertSelective(qr);
	 }
	 
	 /**
	  * 修改次数
	  * @param qr 
	  */
	 public  void  updateSelective(QuestionRecord qr){
		 questionRecordDao.updateSelective(qr);
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
     *获得A或B卷的全部试题Id集合
     * @param examDetailId A卷或B卷的唯一标识
     * @return  List<Map<String,Object>>
     */
    public List<Map<String,Object>>getExamDetailByExamId(String examDetailId){
    	  return examdetailQuestionDao.findList(examDetailId);
    }
    
    
    
    /**
     * 保存出题次数
     * @param exam
     */
	 public void  save(Examination exam){
		List<Examdetail> list=getExamDetailByExamId(exam);
		for(Examdetail ed:list){
			List<Map<String,Object>> dataList=getExamDetailByExamId(ed.getId());
		    for(Map<String,Object> map:dataList){
		    	String quesId=map.get("quesId").toString();
		    	QuestionRecord qr=getQuestionRecordInfo(quesId);
		    	
		    	QuestionRecord dbQr=getQuestionRecordByTeacher(qr);
		    	if(dbQr!=null){
		    		dbQr.setExampaperCount(dbQr.getExampaperCount()+1);
		    		updateSelective(dbQr);
		    	}else{
		    		qr.setExampaperCount(1L);
		    		insertSelective(qr);
		    	}
		    }
			
			
			
		}
	 }
	 
	 
	 public QuestionRecord getQuestionRecordInfo(String questionId){
		 User teacher=TearcherUserUtils.getUser();
		 QuestionRecord qr=new QuestionRecord();
		if(teacher !=null){
			qr.setTeacherId(teacher.getId());
			qr.setVersionId(TearcherUserUtils.getCourseVesion().getId());
			qr.setSchoolId(teacher.getCompany().getId());
			qr.setQuestionId(questionId);
		}
		return qr;
	 }

}
