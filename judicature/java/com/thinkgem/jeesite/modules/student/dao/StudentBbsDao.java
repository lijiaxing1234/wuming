package com.thinkgem.jeesite.modules.student.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.UAnswer;
import com.thinkgem.jeesite.modules.questionlib.entity.UQuestionAnswer;

@MyBatisDao
public interface StudentBbsDao {

	String getOfficeIdByStudentId(String studentId);

	List<UQuestionAnswer> getUQuestionDetail(String uQuestionId);

	String getSchoolIdByStudentId(String studentId);

	int insert(UAnswer uAnswer);

}
