package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.dao.UQuestionDao;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestion;

/**
 * 提问Service
 */
@Service
@Transactional(readOnly = true)
public class UQuestionService extends CrudService<UQuestionDao, UQuestion> {

	public UQuestion get(String id) {
		return super.get(id);
	}
	
	public List<UQuestion> findList(UQuestion uQuestion) {
		return super.findList(uQuestion);
	}
	
	public int countAllList(UQuestion uQuestion) {
		return dao.countAllList(uQuestion);
	}
	public List<UQuestion> findALLList(UQuestion uQuestion,Integer start,Integer pageSize) {
		if(start!=null&&pageSize!=null){
			Map<String,String> map=uQuestion.getSqlMap();
			map.put("limit"," limit "+start+","+pageSize);
		}
		return dao.findAllList(uQuestion);
	}
	
	
	/**
	 * 查询用户提问了那些问题
	 * @param userId 为nul 查询全部未删除的问题
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<UQuestion> findListByUserId(String userId,Integer pageNum,Integer pageSize) {
		UQuestion uQuestion=new UQuestion();
		
		int totalRecord=this.countAllList(uQuestion);
		pageSize=(pageSize<=0?30:pageSize);
		int totalPage=(totalRecord % pageSize)==0 ? (totalRecord / pageSize) : (totalRecord / pageSize)+1;
		if(totalPage>=pageNum){
			int start=((pageNum<1?1:pageNum)-1)*pageSize;
			return this.findALLList(uQuestion, start, pageSize);
		}
		return Lists.newArrayList();
	}
	
	
	public Page<UQuestion> findPage(Page<UQuestion> page, UQuestion uQuestion) {
		return super.findPage(page, uQuestion);
	}
	
	@Transactional(readOnly = false)
	public void save(UQuestion uQuestion) {
		super.save(uQuestion);
	}
	
	
}