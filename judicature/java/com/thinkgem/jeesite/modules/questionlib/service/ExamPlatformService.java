package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.questionlib.dao.ExamPlatformDao;
import com.thinkgem.jeesite.modules.questionlib.dto.ExamDTO;

@Service
public class ExamPlatformService {

	@Autowired
	ExamPlatformDao examDao;

	public List<ExamDTO> getExamStatics(ExamDTO examDTO) {
		return examDao.getExamStatics(examDTO);
	}
	public int getExamStaticsCount(ExamDTO examDTO) {
		return examDao.getExamStaticsCount(examDTO);
	}
	public Page<ExamDTO> findExamBySchoolId(Page<ExamDTO> page, ExamDTO examDTO) {
		examDTO.setPage(page);
		page.setList(examDao.findExamBySchoolId(examDTO));
		return page;
	}
	public String getSchoolName(String schoolId) {
		return examDao.getSchoolName(schoolId);
	}

}
