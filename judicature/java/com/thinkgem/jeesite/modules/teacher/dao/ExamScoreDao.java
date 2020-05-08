package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.teacher.dto.StudentQuestionDTO;
import com.thinkgem.jeesite.modules.teacher.dto.StudentTaskDTO;

/**
 * 成绩管理
 */
@MyBatisDao
public interface ExamScoreDao {
	//查询班级内的学生
	public List<StudentTaskDTO> queryOnLineScore(StudentTaskDTO studentTaskDTO);
	//学生未提交作业次数
	public List<String> queryExamDetail(@Param(value="userId")String userId,
										@Param(value="versionId")String versionId,
										@Param(value="examClassId")String examClassId);
	public String queryUnsubmitCount(@Param(value="studentId")String studentId,
											@Param(value="examDetailId")String examDetailId);
	//学生未提交随堂测次数
	public List<String> queryUnClassCount(@Param(value="userId")String userId,
										@Param(value="versionId")String versionId,
										@Param(value="examClassId")String examClassId);
	//学生在该测试中的线上成绩
	public String sumOnlineScore(@Param(value="studentId")String studentId,@Param(value="examId")String examId);
	//学生在该测试中的总成绩
	public String findStudentScore(@Param(value="studentId")String studentId,@Param(value="examDetailId")String examDetailId);
	public Float findStudentScore2(@Param(value="studentId")String studentId,@Param(value="examId")String examId);
}
