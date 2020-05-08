package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Edresources;

@MyBatisDao
public interface SchoolRescourceDao {

	List<Edresources> getSchoolResourceList(Edresources edresources);
	
}
