package com.thinkgem.jeesite.modules.resource.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resource.entity.LVCourse;
import com.thinkgem.jeesite.modules.resource.entity.LVCourseExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface LVCourseMapper {

	long countByExample(LVCourseExample example);

	int deleteByExample(LVCourseExample example);

	int deleteByPrimaryKey(Long id);

	int insert(LVCourse record);

	int insertSelective(LVCourse record);

	List<LVCourse> selectByExample(LVCourseExample example);

	LVCourse selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") LVCourse record, @Param("example") LVCourseExample example);

	int updateByExample(@Param("record") LVCourse record, @Param("example") LVCourseExample example);

	int updateByPrimaryKeySelective(LVCourse record);

	int updateByPrimaryKey(LVCourse record);

	List<LVCourse> getLVbyPage(Map<String, Object> map);
	
	int getLVCount();
	
	LVCourse getLvRecommend(String lvId);
	
	int recommendLV(LVCourse lvCourse);
	
	int getHasRecommendLVCount();
	
	int addLvImg(LVCourse lvCourse);
	
	List<String> getRecomLvId();
	
	List<LVCourse> getRecomLv(@Param("lvCourseId") String lvCourseId,@Param("userId")String userId);
	
	List<String> getPPCourseId(String userId);
	List<String> getOPCourseId(String userId);
	
	/**
	 * 查询用户课程列表
	 * @param userId
	 * @return
	 */
	List<String> selectUserMTCourseId(String userId);
	
	/**
	 * 插入用户观看 免费课程 关联表
	 * @param userId  用户Id
	 * @param courseId 第三方直播课程Id
	 * @return
	 */
	int insertIgnoreFreeMTCourse(@Param("userId")String userId,@Param("courseId")String courseId);
	
	
	int hasBuyByPum(@Param("lvCourseId") String lvCourseId,@Param("userId")String userId);
	int hasBuyByOther(@Param("lvCourseId") String lvCourseId,@Param("userId")String userId);
	
	List<String> getAllLvId();
}