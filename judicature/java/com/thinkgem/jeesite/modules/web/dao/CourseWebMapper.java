package com.thinkgem.jeesite.modules.web.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.web.entity.Course;
@MyBatisDao
public interface CourseWebMapper {

	List<Course> getAllCourse();
}
