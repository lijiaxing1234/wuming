package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.dao.UAnswerDao;
import com.thinkgem.jeesite.modules.questionlib.entity.UAnswer;

/**
 * 回答Service
 */
@Service
@Transactional(readOnly = true)
public class UAnswerService extends CrudService<UAnswerDao, UAnswer> {

	public UAnswer get(String id) {
		return super.get(id);
	}
	
	public List<UAnswer> findList(UAnswer uAnswer) {
		return super.findList(uAnswer);
	}
	
	public Page<UAnswer> findPage(Page<UAnswer> page, UAnswer uAnswer) {
		return super.findPage(page, uAnswer);
	}
	
	public int countAllList(UAnswer uAnswer){
		return dao.countAllList(uAnswer);
	}
	
	public List<UAnswer> findAllList(UAnswer uAnswer,Integer start, Integer pageSize){
		if(start!=null&&pageSize!=null){
			Map<String,String> map=uAnswer.getSqlMap();
			map.put("limit"," limit "+start+","+pageSize);
		}
		return dao.findAllList(uAnswer);
	}
	
	@Transactional(readOnly = false)
	public void save(UAnswer uAnswer) {
		super.save(uAnswer);
	}
	
	@Transactional(readOnly = false)
	public void delete(UAnswer uAnswer) {
		super.delete(uAnswer);
	}

	public List<UAnswer> findListByQuestionId(String questionId,Integer pageNum, Integer pageSize) {
		UAnswer uanswer=new UAnswer();
		/*uanswer.setQuestionid(questionId);*/
		int totalRecord=this.countAllList(uanswer);
		pageSize=(pageSize<=0?30:pageSize);
		int totalPage=(totalRecord % pageSize)==0 ? (totalRecord / pageSize) : (totalRecord / pageSize)+1;
		if(totalPage>=pageNum){
			return this.findAllList(uanswer, ((pageNum<1?1:pageNum)-1)*pageSize, pageSize);
		}
		return Lists.newArrayList();
	}
	
}