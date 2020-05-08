package com.thinkgem.jeesite.modules.resource.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.resource.dao.LVFeedbackMapper;
import com.thinkgem.jeesite.modules.resource.entity.LVFeedback;
import com.thinkgem.jeesite.modules.resource.entity.LVFeedbackExample;

/**
 *课程评价
 */

@Service
public class LVFeedbackService {
	
	
	@Autowired
	LVFeedbackMapper  feedBackMapper;

	public long getFeedBackCount(LVFeedback feedback, Date startTime, Date endTime) {
		LVFeedbackExample example=createExample(feedback,startTime,endTime);
		return this.feedBackMapper.countByExample(example);
	}

	public List<LVFeedback> getFeedBackList(LVFeedback feedback, Date startTime, Date endTime, Integer start,Integer pageSize) {
		LVFeedbackExample example=createExample(feedback,startTime,endTime);;
        example.setOrderByClause("create_time desc");
        example.setStart(start);
        example.setPageSize(pageSize);
	    return this.feedBackMapper.selectByExample(example);
	}
	    
	
	private LVFeedbackExample createExample(LVFeedback feedBack, final Date startDate, final Date endDate) {
		LVFeedbackExample example = new LVFeedbackExample();
		LVFeedbackExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty((CharSequence) feedBack.getContent())) {
			criteria.andContentLike("%" + feedBack.getContent() + "%");
		}
		if (feedBack.getStatus() != null) {
			criteria.andStatusEqualTo(feedBack.getStatus());
		}
		if (feedBack.getComefrom() != null) {
			criteria.andComefromEqualTo(feedBack.getComefrom());
		}
		if (startDate != null) {
			criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
		}
		if (endDate != null) {
			criteria.andCreateTimeLessThanOrEqualTo(endDate);
		}
		if (feedBack.getUserId() != null) {
			criteria.andUserIdEqualTo(feedBack.getUserId());
		}
		
		if( StringUtils.isNotBlank(feedBack.getLvCourseId() )){
			criteria.andLvCourseIdEqualTo(feedBack.getLvCourseId());
		}
		return example;
	}
	 

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 /********************课程评论*************************/
	
	public List<LVFeedback> frontSelectFeedBackList(LVFeedback feedback,Integer start,Integer pageSize) {
		LVFeedbackExample example = new LVFeedbackExample();
		LVFeedbackExample.Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(feedback.getLvCourseId())){
			criteria.andLvCourseIdEqualTo(feedback.getLvCourseId());
		}
		
        example.setOrderByClause("create_time desc");
        example.setStart(start);
        example.setPageSize(pageSize);
	    return this.feedBackMapper.selectByExample(example);
	}
	
	
	
	public int  insertSelective(LVFeedback feedback){
		return feedBackMapper.insertSelective(feedback);
	}
	
}
