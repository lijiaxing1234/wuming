package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.dto.ExamDTO;

@MyBatisDao
public interface ExamPlatformDao {

	public List<ExamDTO> getExamStatics(ExamDTO examDTO);
	public int getExamStaticsCount(ExamDTO examDTO);
	public List<ExamDTO> findExamBySchoolId(ExamDTO examDTO);
	public String getSchoolName(String schoolId);
}
