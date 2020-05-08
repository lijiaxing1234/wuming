/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;

/**
 * 试题DAO接口
 * @author webcat
 * @version 2016-09-09
 */
@MyBatisDao
public interface VersionQuestionDao extends CrudDao<VersionQuestion> {
	
	/**
	 * 根据知识点id获取所有相关试题的id
	 * @param courseKnowledge
	 * @return
	 */
	public List<String> findQuestionIdList(VersionQuestion versionQuestion);
	
	/**
	 * 根据知识点id获取试题集合
	 * @param versionQuestion
	 * @return
	 */
	public List<VersionQuestion> findQuestionList(VersionQuestion versionQuestion);
	
	/**
	 * 根据试题id获取所有相关知识点的id
	 * @param versionQuestion
	 * @return
	 */
	public List<String> findKnowledgeIdList(VersionQuestion versionQuestion);

	/**
	 * 根据试题id获取知识点集合
	 * @param versionQuestion
	 * @return
	 */
	public List<CourseKnowledge> findKnowledgeList(VersionQuestion versionQuestion);
	
	/**
	 * 保存知识点和试题的对应关系(保存两者的id)
	 * @param versionQuestion
	 */
	public void saveKnowledgeIdAndQuestionId(VersionQuestion versionQuestion);

	/**
	 * 删除知识点和试题的对应关系(保存两者的id)
	 * @param versionQuestion
	 */
	public void deleteKnowledgeIdAndQuestionId(VersionQuestion versionQuestion);

	
	public int findCount(VersionQuestion versionQuestion);

	
//	public Page<VersionQuestion> findPage(Page<VersionQuestion> page, List<VersionQuestion> list);
	
	/**
	 *根据知识点IDs集合查询所有可以出题的试题
	 * @param CourseKnowledgeIds 知识点集合
	 * 
	 * 参数例子：
	 * 	 Parameter examknowledgeIdsParam=new  Parameter(new Object[][]{
			 {"examId",exam.getId()},
			 {"examType",examType},
			 {"examknowledgeIds",examknowledgeIds}
		 });
	 */
	@Deprecated
	public List<VersionQuestion> findVersionQuestionByCourseKnowledgeIds(Parameter param);
	
	/**
	 * 根据知识点IDs集合查询所有可以出题的试题
	 * 按题类型分组统计该类型的总数
	 * @param param
	 * @return
	 */
	@Deprecated
	List<Map<String,Object>>statisticsVersionQuestionScore(Parameter param);
	/**
	 * 根据知识点IDs集合查询所有可以出题的试题
	 * 按题类型分组统计该类型的总数
	 * @param param
	 * @return
	 */
	@Deprecated
	List<Map<String,Object>>statisticsVersionQuestionScoreInfo(Parameter param);
	
	/**
	 * 根据试卷、试题类型、所考知识点、排除试卷已出试题的试题数量
	 * @param param <br/>
	 * 调用参数
	 * Parameter param=new  Parameter(new Object[][]{
		     {"exam",exam}, //组卷对象
			 {"schoolId",teacher.getCompany().getId()},
			 {"examDetailId",examDetailId}, //AorB卷的唯一Id
			 {"quesType",quesType}  //试题的类型
		  });
	 * @return
	 */
	int countVersionQuestionByExamIdAndExamDetailIdAndQuesType(Parameter param);
	/**
	 * 根据试卷、试题类型、所考知识点、排除试卷已出试题的试题数量
	 * @param param <br/>
	 * 调用参数
	 * Parameter param=new  Parameter(new Object[][]{
				 {"examId",exam.getId()}, //试卷Id
				 {"examType",examType},  //过滤考试还是练习题
				 {"examDetailId",examDetailId}, //AorB卷的唯一Id
				 {"quesType",quesType},  //试题的类型
				 {"countNumber",countNumber}//随机补题数量
		  });
	 * @return
	 */
	List<VersionQuestion> findVersionQuestionByExamIdAndExamDetailIdAndQuesType(Parameter param);
	

	/**
	 * 保存上传试题文档和试题的对应关系
	 * @param versionQuestion
	 */
	public void saveImportIdAndQuestionId(VersionQuestion versionQuestion);
	
	
	/**
	 * 	替换题目
	 */
	public void updateQuestionExamInfo(@Param(value="examdetailId")String examdetailId,
										@Param(value="questionId")String questionId,
										@Param(value="replaceQuestionIds")String replaceQuestionId);
	/**
	 * 删除上传试题文档和试题的对应关系
	 * @param versionQuestion
	 */
	public void deleteImportIdAndQuestionId(VersionQuestion versionQuestion);
	
	/**
	 * 更改试题的删除状态
	 * @param versionQuestion
	 */
	public void updateQuestionDelFlagByImportId(VersionQuestion versionQuestion);
	
	/**
	 * 通过导入文档id查询该文档中试题id集合
	 * @param versionQuestion
	 * @return
	 */
	public List<String> findByImportId(VersionQuestion versionQuestion);
	
	/**
	 * 通过删除状态del_flag/知识点名称/题型/难度/题库 字段查询试题   0-正常(审核通过，可使用)  1-已删除   2-未审核
	 * @param versionQuestion
	 * @return
	 */
	public List<VersionQuestion> findListByDelFlagAndTypeAndLevelAndLibId(VersionQuestion versionQuestion);

	/**
	 * 根据试题id和delFlag查找试题
	 * @param versionQuestion
	 * @return
	 */
	public VersionQuestion findByIdAndDelFlag(VersionQuestion versionQuestion);

	/**
	 * 根据题库id、试题类型、试题难度等查找试题集合
	 * @param versionQuestion
	 * @return
	 */
	public List<VersionQuestion> findByQuestionLibIdAndQuesTypeAndQuesLevel(VersionQuestion versionQuestion);

	public VersionQuestion getQuestionMsg(Map<String, String> paraMap);

	public VersionQuestion getQuestionByQuestionId(String questionId);

	public VersionQuestion getQuestionMsgByQuestionId(Map<String, String> paraMap);
	
	List<Course> getAllCourse();
	List<Knowledge> getChapters(String versionId);
	List<Knowledge> getNodes(String parentId);
	String getPastYearId();
	int inKnowQue(@Param(value="knowledgeId")String knowledgeId,@Param(value="questionId")String questionId);
	VersionQuestion getQueTrueCourse(String id);
	String getck(String id);
	String getCourse(String id);
	int hasKQ(@Param(value="knowledgeId")String knowledgeId,@Param(value="questionId")String questionId);
}