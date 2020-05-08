package com.thinkgem.jeesite.modules.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.web.dao.UserAnswerMapper;
import com.thinkgem.jeesite.modules.web.entity.UserRecord;
import com.thinkgem.jeesite.modules.web.entity.UserRecordAnswer;

@Service
public class UserAnswerService {
	
	
	@Autowired
	UserAnswerMapper userAnswerMapper;

	
	
	/**
	 * 保存用户答题记录
	 * @param userAnswer
	 * @return
	 */
	@Transactional(readOnly=false)
	public boolean saveUserAnswer(UserRecordAnswer userRecordAnswer) {
		
		Integer userRecordResult=0;
		Integer userRecordAnswerResult=0;
		
		String id =IdGen.uuid();
		UserRecord  userRecord=userRecordAnswer;
	    List<UserRecord> userRecordList=userAnswerMapper.selectUserRecord(userRecord);
	    if(userRecordList ==null || userRecordList.isEmpty()){
	    	userRecord.setId(id);
	    	userRecordResult=userAnswerMapper.insertUserRecordSelective(userRecord);
	    }else{
	    	id=userRecordList.iterator().next().getId();
	    }
	  
	    userRecordAnswer.setRecordId(id);
	    userRecordAnswer.setAnswerOption(StringUtils.join(userRecordAnswer.getUserAnswer(),"#$_$#"));
	    List<UserRecordAnswer> userRecordAnswerList= userAnswerMapper.selectUserRecordAnswer(userRecordAnswer);
	    
	    if(userRecordAnswerList ==null || userRecordAnswerList.isEmpty()){
	    	
	       userRecordAnswerResult=userAnswerMapper.insertUserRecordAnswerSelective(userRecordAnswer);
	    }else{
	    	userRecordAnswerResult=userAnswerMapper.updateUserRecordAnswerByPrimaryKeySelective(userRecordAnswer);
	    }
	    
		return (userRecordResult+userRecordAnswerResult) >= 2 ;
	}
	
	
	
	
	

}
