package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.dto.TeacherDTO;
@MyBatisDao
public interface TeacherStaticsDao {
	public List<TeacherDTO> getTeacherStatics(TeacherDTO teacherDTO);
	public int getTeacherStaticsCount(TeacherDTO teacherDTO);
	public List<TeacherDTO> getStaticsPage(TeacherDTO teacherDTO);
}
