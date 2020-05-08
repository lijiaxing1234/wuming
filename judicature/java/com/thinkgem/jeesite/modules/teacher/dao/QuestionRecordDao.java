package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.entity.QuestionRecord;

/**
 * 试题记录dao
 * @author flychao
 */
@MyBatisDao
public interface QuestionRecordDao {
	
	 
	/**
	 * 获取老师已经出的试题次数
	 * @param qr
	 * @return  List<QuestionRecord> 
	 */
	 List<QuestionRecord> findQuestionRecordByTeacher(QuestionRecord qr);
 	  
	 /**
	  * 插入次数
	  * @param qr
	  */
	 void  insertSelective(QuestionRecord qr);
	 
	 /**
	  * 修改次数
	  * @param qr 
	  */
	 void  updateSelective(QuestionRecord qr);
	
	
	

}
