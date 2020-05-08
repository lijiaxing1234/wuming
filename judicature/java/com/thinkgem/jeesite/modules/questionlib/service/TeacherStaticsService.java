package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.questionlib.dao.TeacherStaticsDao;
import com.thinkgem.jeesite.modules.questionlib.dto.TeacherDTO;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
public class TeacherStaticsService {

	@Autowired
	TeacherStaticsDao teacherStaticsDao;
	public List<TeacherDTO> getTeacherStatics(TeacherDTO teacherDTO){
		return teacherStaticsDao.getTeacherStatics(teacherDTO);
	}
	public int getTeacherStaticsCount(TeacherDTO teacherDTO) {
		return teacherStaticsDao.getTeacherStaticsCount(teacherDTO);
	}
	public Page<TeacherDTO> getPage(Page<TeacherDTO> page, TeacherDTO teacherDTO) {
		teacherDTO.setSchoolId(UserUtils.getUser().getCompany().getId());
		teacherDTO.setPage(page);
		page.setList(teacherStaticsDao.getStaticsPage(teacherDTO));
		return page;
	}
}