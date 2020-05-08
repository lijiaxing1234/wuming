package com.thinkgem.jeesite.modules.resource.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resource.entity.LVCourseImplement;
import com.thinkgem.jeesite.modules.resource.entity.LVCourseImplementExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface LVCourseImplementMapper {


	long countByExample(LVCourseImplementExample example);

	int deleteByExample(LVCourseImplementExample example);

	int deleteByPrimaryKey(Long id);

	int insert(LVCourseImplement record);

	int insertSelective(LVCourseImplement record);

	List<LVCourseImplement> selectByExample(LVCourseImplementExample example);

	LVCourseImplement selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") LVCourseImplement record,
			@Param("example") LVCourseImplementExample example);

	int updateByExample(@Param("record") LVCourseImplement record, @Param("example") LVCourseImplementExample example);

	int updateByPrimaryKeySelective(LVCourseImplement record);

	int updateByPrimaryKey(LVCourseImplement record);

	
	
    int selectMaxSeqByExample(LVCourseImplementExample example);
}