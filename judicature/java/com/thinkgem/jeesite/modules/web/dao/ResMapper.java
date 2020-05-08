package com.thinkgem.jeesite.modules.web.dao;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.web.dto.SearchResult;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.entity.Questions;
import com.thinkgem.jeesite.modules.web.entity.ResCategory;
import com.thinkgem.jeesite.modules.web.entity.Resource;
import com.thinkgem.jeesite.modules.web.entity.UserRecord;

/**
 *发现资源的详情页面
 */
@MyBatisDao
public interface ResMapper {
	//根据id查询栏目
	ResCategory selectCategoryById(String cateId);
	//根据父级栏目id查询子级栏目
	List<ResCategory> selectCategoryByParentId(String cateId);
	//根据栏目id查询该栏目下资源的集合
	List<Resource> selectResByCateId(@Param("cateId")String cateId,@Param("userId") String userId);
	
	int addUserRecord(UserRecord record);
	int hasUserRecord(UserRecord record);
	int updateUserRecord(UserRecord record);
	UserRecord getUserRecord(@Param("userId") String userId,@Param("sourceId") String sourceId);
	int addUserLiveCount(UserRecord record);
	
	/**
	 * app 搜索
	 * @param search
	 * @return
	 */
	List<SearchResult>  searchResource(SearchResult  search);
	/**
	 * 搜索
	 * @param searchKey
	 * @param start
	 * @param end
	 * @return
	 */
	List<Parameter> search(@Param("searchKey")String searchKey,@Param("start") Integer start,@Param("end")  Integer end);
	
}
