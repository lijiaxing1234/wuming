package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.TableClass;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.teacher.dto.Exam2;
import com.thinkgem.jeesite.modules.teacher.dto.ExamScoreDTO;
import com.thinkgem.jeesite.modules.teacher.dto.ExamStudentDTO;
import com.thinkgem.jeesite.modules.teacher.dto.LoginDTO;
import com.thinkgem.jeesite.modules.teacher.dto.StudentQuestionDTO;
import com.thinkgem.jeesite.modules.teacher.dto.StudentTaskDTO;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.ExamClass;

@MyBatisDao
public interface ExamDao extends CrudDao<Exam> {
	/**
	 * 根据教师id及版本id获取所有正在进行的在线考试的试卷
	 */
	public List<ExamClass> findAllOnlineExam(ExamClass examClass);
	/**
	 * 根据班级获取班级内所有学生
	 */
	public List<ExamStudentDTO> findAllStudentByClassId(ExamStudentDTO examStudentDTO);
	/**
	 * 根据学生id以及examId查询学生是否提交试卷
	 */
	public Date queryStudentSubmit(@Param(value="examId")String examId,
			@Param(value="studentId")String studentId);
	/**
	 * 根据班级获取班级内所有学生
	 */
	public List<StudentTaskDTO> findAllStudent(StudentTaskDTO studentTask);
	public List<ExamStudentDTO> findAllStudent3(ExamStudentDTO examStudentDTO);
	/**
	 * 根据班级获取班级内所有学生
	 */
	public List<Student> findStudentByClassId(@Param(value="classId")String classId);
	/**
	 * 根据教师id及版本id获取作业列表
	 */
	public List<Exam> getExam(Exam exam);
	public List<Exam> getExam2(Exam exam);  
	/**
	 * 根据教师id及版本id获取学生已提交的作业列表
	 */
	public List<Exam> getStudentExam(Exam exam); 
	/**
	 * 获取教师下班级详情
	 * @param exam
	 * @return
	 */
	List<Exam> getClass(@Param(value="userId")String userId,@Param(value="versionId")String versionId);
	
	/**
	 * 获取班级下的的题目详情
	 */
	List<StudentQuestionDTO> queryTitledetail(StudentQuestionDTO studentQuestionDTO);
	
	/**
	 * 获取班级下的个人的完成情况
	 */
	StudentAnswer queryPersondetail(@Param(value="examId")String examId,
												@Param(value="questionId")String questionId,
												@Param(value="studentId")String studentId);

	/**
	 * 获取某一特定题目在班级内部的完成情况
	 */
	List<StudentQuestionDTO> queryClassQuestiondetail(@Param(value="examId")String examId,
												@Param(value="examClassId")String examClassId,
												@Param(value="questionId")String questionId);
	
	/**
	 * 获取班级内所有学生的id值
	 * @param examClassId
	 * @return
	 */
	List<String> getStudentByClassId(String examClassId);
	
	
	/**
	 * 获取学生的回答情况
	 */
	String queryClassPersondetail(@Param(value="studentId")String studentId,@Param(value="id")String id,@Param(value="questionId")String questionId);
	
	/**
	 * 作业列表中班级内所有学生
	 */
	List<ExamStudentDTO> findExamStudent(ExamStudentDTO examStudent);
	/**
	 * 获取班级内的提交作业的总人数
	 */
	Integer getPersonTotal(@Param(value="examClassId")String examClassId,@Param(value="examId")String examId);
	
	/**
	 * 查询随堂测试中的未交卷
	 */
	public String findUnSubmit(@Param(value="studentId")String studentId,@Param(value="examId")String examId);
	
	/**
	 * 根据班级id以及测试id获取题目
	 */
	public StudentQuestionDTO  findQuestion(@Param(value="examId")String examId,@Param(value="classId")String classId);
	/**
	 * 根据学生id以及试卷id得到exam_detail_id
	 */
	public String queryExamDetailId(@Param(value="studentId")String studentId,@Param(value="examId")String examId);
	/**
	 * 查询学生回答的详情
	 */
	public StudentAnswer queryStudentAnswerDetail(@Param(value="questionId")String questionId,
													@Param(value="studentId")String studentId,
													@Param(value="examId")String examId);
	/**
	 * 老师判卷进行修改answer表中的分数
	 * @param studentId
	 * @param questionId
	 * @param examId
	 * @param score
	 */
	public void answerQuestion(@Param(value="studentId")String studentId,
								@Param(value="questionId")String questionId,
								@Param(value="examId")String examId,
								@Param(value="score")String score,
								@Param(value="isCorrect")String isCorrect);
	
	/**
	 * 计算学生总分
	 */
	 public String totalStudentScore(@Param(value="studentId")String studentId,
			 						@Param(value="examId")String examId);
	 /**
	  * 在task表中获得学生的总分
	  */
	 public String studentTotalScore(@Param(value="studentId")String studentId,
										@Param(value="examId")String examId);
	 /**
	  * 根据examId查询examDetailId
	  */
	 public String studentExamDetailId(@Param(value="studentId")String studentId,
										@Param(value="examId")String examId);
	 /**
	 * 修改task表
	 */
	 public void updateTask(@Param(value="studentId")String studentId,
								 @Param(value="examDetailId")String examDetailId,
								 @Param(value="totalStudentScore") String totalStudentScore);
	public Integer getStudentSubmitPerson(@Param(value="classId")String classId,
			@Param(value="examId")String examId);
	public Integer getStudentSubmitPerson2(@Param(value="classId")String classId,
			@Param(value="examId")String examId);
	/**
	 * 根据教师id及版本id获取试卷列表（可以根据试卷类型,班级,试卷名称进行查询）
	 */
	public List<Exam> getExamByTeacherIdAndVersionId(Exam exam);
	public List<Exam2> getExamByTeacherIdAndVersionId2(Exam2 exam);
	/**
	 * 根据examId查询examDetailId
	 * @param examId
	 * @return
	 */
	public String getExamDetailId(@Param(value="examId")String examId,@Param(value="examDetailType")String examDetailType);
	public String getExamDetailId2(String examId);
	//查询是否存在学生分数
	public String queryStudentTask(@Param(value="studentId")String studentId, @Param(value="examDetailId")String examDetailId);
	public List<Student> findAllStudent2(Student student);
	public void updateExamClass(@Param(value="examId")String examId,@Param(value="examClassId")String examClassId);
	public List<TableClass> getTableClassByExamId(String examId);
	public List<String> getExamDetailIdByExamId(String examId);
	/**
	 * 查询试题分数
	 * @param questionId
	 * @return
	 */
	public String queryQuestionScore(String questionId);
	public String getExamClassMark(@Param(value="examId")String examId,@Param(value="examClassId")String examClassId);
	public void answerQuestionIsCorrect(@Param(value="studentId")String studentId,@Param(value="questionId") String questionId,@Param(value="examId") String examId,@Param(value="isCorrect") String isCorrect);
	//根据学生id以及试卷id查询数据
	public String queryStudentTaskId(@Param(value="studentId")String studentId, @Param(value="examId")String examId);
	//查询该学生是否已经判过卷
	public String getStudentState(@Param(value="studentId")String studentId, @Param(value="examId")String examId); 
	//查询多余的试卷id
	public void  batchRemove();
	//查询所有试卷下该学生所在班级中所有学生的成绩
	public List<ExamScoreDTO> getAllExamAndAllStudentScore(@Param(value="examId")String examId, @Param(value="examClassId")String examClassId);
	public String getCountByExamIdAndExamClassIdAndScoreRange(@Param(value="examId")String examId,
															@Param(value="examClassId")String examClassId,
															@Param(value="min")String min,
															@Param(value="max")String max);
	public Integer getExamTotalScore(String examId);
	/**
	 * 获取班级内所有学生的成绩
	 */
	public List<StudentTask> getAllStudentScore(@Param(value="examId")String examId, @Param(value="classId")String classId);
	//根据学号查询学生
	public Student getStudent(@Param(value="studentCode")String studentCode,@Param(value="schoolId")String schoolId);
	//查询试卷的详情
	public List<String> getExamType(String examId);
	public Float getExamTotalScoreByExamDetailId(String examDetailId);
	//获取试卷总题数
	public Integer getQuestionlibCount(String examId);
	//获取所有交卷学生列表
	public List<StudentTask> getAllSubmitStudent(StudentTask studentTask);
	//根据试卷详情id查询试卷
	public Exam getExamByExamDetailId(String quesDetailId);
	//获取超时未发布的试卷
	public List<Exam> getOvertimeExam(Exam exam);
	//获取试卷信息
	public Exam getExamDetail(String examId);
	//查询教师所在的学校id
	public String getTeacherCompanyId(String userId);
	//查询教师首页的一些信息
	public LoginDTO getTeacherLogin(@Param(value="userId")String userId,@Param(value="versionId")String versionId);
	//获取某次考试下所有参加考试的学生
	public List<StudentTask> getExamStudentTaskState(@Param(value="examClassId")String examClassId,@Param(value="examId")String examId);
 }
