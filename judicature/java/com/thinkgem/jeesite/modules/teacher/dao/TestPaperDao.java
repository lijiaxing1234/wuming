package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
@MyBatisDao
public interface TestPaperDao extends CrudDao<Exam> {
	
	public List<Exam> getExamClass();
	//试卷管理的删除功能
	public void deleteExam(@Param(value="examId")String examId,@Param(value="examClassId")String examClassId);
	//试卷管理的发布功能
	public void publishExam(@Param(value="examId")String examId,@Param(value="examClassId")String examClassId);
	//提交
	public void submitExam(@Param(value="examId")String examId,@Param(value="examClassId")String examClassId);
	//课堂例题的发布
	public void submitExam2(@Param(value="examId")String examId,@Param(value="examClassId")String examClassId);
	//保存
	public void save(@Param(value="examId")String examId,@Param(value="examClassId")String examClassId);
	//手动出题的存为模板功能
	public void saveExam2(String examId);
	//试卷管理的存为模板功能
	public void saveExample(String examId);
	//试卷管理的结束功能
	public void submitTestPaper(@Param(value="examId")String examId,@Param(value="examClassId")String examClassId);
	//获取成绩的列表
	public List<Exam> getExamTeacherIdAndVersionId(Exam exam);
	//获取成绩的列表
	public List<Exam> getExamOnLine(Exam exam);//线上已完成
	public List<Exam> getExamOnline2(Exam exam);//线上未完成
	public List<Exam> getExam(Exam exam);//线下已完成
	public List<Exam> getExam3(Exam exam);//线下未完成
	
	public List<Exam> getOnLineExam(Exam exam);
	public List<Exam> getExam2(@Param(value="exam")Exam exam,
								@Param(value="userId")String userId,
								@Param(value="versionId")String versionId);
	public List<Exam> findExamList(Exam exam);
	//成绩管理的修改
	public void submitExamClassId(@Param(value="examId")String examId,@Param(value="examClassId")String examClassId);
	//获取在线成绩列表
	public List<Exam> getOnLineExamByTeacherIdAndVersionId(Exam exam);
	//获取所有的考试列表（除了线下考试）
	public List<Exam> getAllExam(Exam exam);
	//删除模板
	public void deleteExam2(String examId);
	//查询所有未进行判卷的不需手动判卷的试卷
	public List<Exam> getAllUnSubmitExam(@Param(value="userId")String userId,
											@Param(value="versionId")String versionId);
	public List<Exam> getAllUnSubmitExam2(@Param(value="userId")String userId,
			@Param(value="versionId")String versionId);
	//修改试卷与班级的状态
	public void updateExamClassStatus(Exam exam);
	public void updateExamClassStatus2(Exam exam);
}
