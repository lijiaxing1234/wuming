package com.thinkgem.jeesite.modules.web.service;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.modules.web.dao.AppIndexMapper;
import com.thinkgem.jeesite.modules.web.dao.ResMapper;
import com.thinkgem.jeesite.modules.web.dto.SearchResult;
import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.entity.Questions;
import com.thinkgem.jeesite.modules.web.entity.ResCategory;
import com.thinkgem.jeesite.modules.web.entity.Resource;
import com.thinkgem.jeesite.modules.web.entity.UserRecord;
import com.thinkgem.jeesite.modules.web.util.GlobalConfigUtils;
import com.thinkgem.jeesite.modules.web.dao.UserInfoMapper;

/**
 *发现资源的详情页面
 */
@Service
public class ResService {
	@Autowired ResMapper resMapper;
	@Autowired
	UserInfoMapper userInfoMapper;
	//根据父级查询二级栏目
	public List<ResCategory> selectCategoryByParentId(String cateId) {
		return resMapper.selectCategoryByParentId(cateId);
	}
	//根据栏目id查询该栏目下的资源集合
	public List<Resource> selectResByCateId(String cateId,String userId) {
		return resMapper.selectResByCateId(cateId, userId);
	}

	@Transactional
	public boolean updateUserRecord(UserRecord record){
		int i = 0;
		int has = resMapper.hasUserRecord(record);
		if(has == 1){
			i = resMapper.updateUserRecord(record);
		}else {
			i = resMapper.addUserRecord(record);
		}
		return i == 1;
	}
	
	public UserRecord getUserRecord(String userId,String sourceId){
		UserRecord time = resMapper.getUserRecord(userId, sourceId);
		return time;
	}
	
	
	public boolean addLiveCount(UserRecord record){
		int i = userInfoMapper.hasLiveCount(record);
		if(i == 1){
			return true;
		}else {
			
			int j = resMapper.addUserLiveCount(record);
			return j == 1;
		}
	}
	
	
	/**
	 * app 搜索
	 * @param search
	 * @return
	 */
	public List<SearchResult>  searchResource(SearchResult  search){
		
		return resMapper.searchResource(search);
	}
	
	/**
	 * 搜索
	 * @param searchKey
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Parameter> search(String searchKey, Integer start, Integer end) {
		return resMapper.search(searchKey,start,end);
	}
	
	
	
	
	
	
}
