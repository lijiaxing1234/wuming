package com.thinkgem.jeesite.modules.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.web.entity.AD;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.entity.Questions;
import com.thinkgem.jeesite.modules.web.entity.ResCategory;
import com.thinkgem.jeesite.modules.web.entity.Resource;

/**
 *广告管理
 */
@MyBatisDao
public interface ADMapper {
	List<AD> selectAD(String adCode);
}
