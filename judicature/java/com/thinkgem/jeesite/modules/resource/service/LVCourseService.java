package com.thinkgem.jeesite.modules.resource.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.sdk.util.l;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.resource.dao.LVCourseImplementMapper;
import com.thinkgem.jeesite.modules.resource.dao.LVCourseMapper;
import com.thinkgem.jeesite.modules.resource.entity.LVCourse;
import com.thinkgem.jeesite.modules.resource.entity.LVCourseExample;
import com.thinkgem.jeesite.modules.resource.entity.LVCourseImplement;
import com.thinkgem.jeesite.modules.resource.entity.LVCourseImplementExample;
import com.thinkgem.jeesite.modules.resource.util.Page;
import com.thinkgem.jeesite.modules.web.dto.MTCourse;

@Service
public class LVCourseService {
	
	@Autowired
	LVCourseMapper  courseMapper;
	
	@Autowired
	LVCourseImplementMapper  courseMapperImplementMapper;
	
	/**
	 * 第三方课程对应本系统中课程
	 * @param lvCourseId  第三方课程Id
	 * @return 第三方课程对应本系统中课程
	 */
	public LVCourse  getCourseByLVCourseId(String lvCourseId){
		
		LVCourseExample  example=new LVCourseExample();
		LVCourseExample.Criteria  criteria= example.createCriteria();
		criteria.andLvCourseIdEqualTo(lvCourseId);
		List<LVCourse> list= courseMapper.selectByExample(example);
		if(list!=null && list.size()>0){
			LVCourse lvCourse = list.iterator().next();
			return lvCourse;
		}
		return null;
	}
	
	public LVCourse  getCourseByLVCourseId(String lvCourseId,String userId){
		
		LVCourseExample  example=new LVCourseExample();
		LVCourseExample.Criteria  criteria= example.createCriteria();
		criteria.andLvCourseIdEqualTo(lvCourseId);
		List<LVCourse> list= courseMapper.selectByExample(example);
		if(list!=null && list.size()>0){
			int hasBuyByPum = courseMapper.hasBuyByPum(lvCourseId, userId);
			int hasBuyByOther = courseMapper.hasBuyByOther(lvCourseId, userId);
			LVCourse lvCourse = list.iterator().next();
			lvCourse.setPumkinPay(hasBuyByPum);
			lvCourse.setOtherPay(hasBuyByOther);
			return lvCourse;
		}
		return null;
	}

	
	
	public Long  saveLVCourse(LVCourse lvCourse){
	     Long primaryKey=null;
	    
	     if(lvCourse.getId()!=null){ //修改
	    	 
	    	 primaryKey=lvCourse.getId(); 
	    	 
	    	 courseMapper.updateByPrimaryKeySelective(lvCourse);
	    	 
	     }else{ //添加
	    	 
	    	 lvCourse.setCreateTime(new Date());
	    	 
	    	 courseMapper.insertSelective(lvCourse);
	    	 
	    	 primaryKey=lvCourse.getId();
	     }
	     
	    return primaryKey;
	}
	
	
	
    /*************************courserImplement 相关************************************/
	
	/**
	 * 课程介绍 (图文混排)
	 * @param id 本地课程Id
	 * @return 课程介绍 详细
	 */
	public List<LVCourseImplement> selectCourseImplementList(LVCourseImplement courserImplement) {
		
		List<LVCourseImplement> list=Lists.newArrayList();
		
		if(StringUtils.isNotBlank(courserImplement.getLvCourseId()) && courserImplement.getLvCourseCate() !=null){
			
		   LVCourseImplementExample example=new LVCourseImplementExample();
		
		   LVCourseImplementExample.Criteria criteria=example.createCriteria();
		
		   example.setOrderByClause("seq");
			
			criteria.andLvCourseIdEqualTo(courserImplement.getLvCourseId())
			        .andLvCourseCateEqualTo(courserImplement.getLvCourseCate());
			list.addAll(courseMapperImplementMapper.selectByExample(example));
			
		}
		return  list; 
	}


    
	public void savecourseImplement(LVCourseImplement lvCourseImple) {
		 
		 if(lvCourseImple.getId() !=null ) { //修改
			 courseMapperImplementMapper.updateByPrimaryKeySelective(lvCourseImple);
		 }else{ //插入
			long n = 0;
			if (StringUtils.isNoneBlank(lvCourseImple.getLvCourseId())&& lvCourseImple.getLvCourseCate() !=null) {
				
				LVCourseImplementExample example=new LVCourseImplementExample();
				example.createCriteria().andLvCourseIdEqualTo(lvCourseImple.getLvCourseId())
				                        .andLvCourseCateEqualTo(lvCourseImple.getLvCourseCate());
				
				n = this.courseMapperImplementMapper.selectMaxSeqByExample(example);
			}
			lvCourseImple.setSeq(new Long(n + 1).intValue());
		   
			courseMapperImplementMapper.insertSelective(lvCourseImple);
		 }
	}


    /**
     * 修改
     * @param implementF
     */
	public int updateImplementByExample(LVCourseImplement record) {
		return  courseMapperImplementMapper.updateByPrimaryKeySelective(record);
	}


	 /**
     * 删除
     * @param implementF
     */
	public int deleteImplement(Long id) {
		return  courseMapperImplementMapper.deleteByPrimaryKey(id);
	}

	public LVCourseImplement getImplementById(Long id) {
		return courseMapperImplementMapper.selectByPrimaryKey(id);
	}
	
	public Page<LVCourse> getLVByPage(String pageNB) {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNO ;
		try {
			pageNO = Integer.parseInt(pageNB);
		} catch (NumberFormatException e) {
			pageNO = 1;
		}
		int pageSize = 20;
		pageNO = (pageNO-1)*pageSize;
		map.put("pageNO", pageNO);
		map.put("pageSize", pageSize);
		int count = courseMapper.getLVCount();
		Page<LVCourse> page = new Page<LVCourse>(pageNB, count);
		List<LVCourse> lVbyPage = courseMapper.getLVbyPage(map);
		page.setContent(lVbyPage);
		return page;
	}
	
	public int recommendLV(String lvId,int status){
		LVCourse lvCourse = new LVCourse();
		lvCourse.setLvCourseId(lvId);
		lvCourse.setRecommend(status);
		int recommendLV = courseMapper.recommendLV(lvCourse);
		return recommendLV;
	}
	
	public LVCourse getLvRecommend(String lvId){
		LVCourse lvRecommend = courseMapper.getLvRecommend(lvId);
		if (lvRecommend!=null) {
			if (lvRecommend.getRecommend()!=null) {
				return lvRecommend;
			}else {
				lvRecommend.setRecommend(2);
				return lvRecommend;
			}
		}
		LVCourse course = new LVCourse();
		course.setRecommend(2);
		return course;
	}
	
	public int getHasRecommendLVCount(){
		return courseMapper.getHasRecommendLVCount();
	}
	
	public boolean addLvImg(LVCourse lvCourse){
		return courseMapper.addLvImg(lvCourse) == 1;
	}
	
	public List<String> getRecomLvId(){
		return courseMapper.getRecomLvId();
	}
	
	public LVCourse getRecomLv(String lvCourseId,String userId){
		List<LVCourse> list = courseMapper.getRecomLv(lvCourseId,userId);
		if(list!=null && list.size()>0){
			return list.iterator().next();
		}
		return null;
	}
	
	public List<String> getPPCourseId(String userId){
		/*Set set=new HashSet();
		List<String> list = new ArrayList<String>();
		List<String> ppCourseId = courseMapper.getPPCourseId(userId);
		List<String> opCourseId = courseMapper.getOPCourseId(userId);
		set.addAll(ppCourseId);
		set.addAll(opCourseId);
		list.addAll(set);
		return list;*/
		return  courseMapper.selectUserMTCourseId(userId);
	}
	
	
	/**
	 * 插入用户观看 免费课程 关联表
	 * @param userId  用户Id
	 * @param courseId 第三方直播课程Id
	 * @return
	 */
	public int insertIgnoreFreeMTCourse(String userId,String courseId){
		/*Set set=new HashSet();
		List<String> list = new ArrayList<String>();
		List<String> ppCourseId = courseMapper.getPPCourseId(userId);
		List<String> opCourseId = courseMapper.getOPCourseId(userId);
		set.addAll(ppCourseId);
		set.addAll(opCourseId);
		list.addAll(set);
		return list;*/
		return  courseMapper.insertIgnoreFreeMTCourse(userId,courseId);
	}
	
	
	
	
	public List<String> getAllLvId(){
		return courseMapper.getAllLvId();
	}
}
