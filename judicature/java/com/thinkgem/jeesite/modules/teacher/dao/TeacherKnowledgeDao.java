package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.StaticKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;

@MyBatisDao
public interface TeacherKnowledgeDao{
	
	
	/**
	 *根据老师Id查询 选中节点以及所有子节点课程版本知识点 且带有老师标识过知识点难易程度
	 * @param teacherKnowledge
	 */
	List<TeacherKnowledge>  findTeacherKnowledgeByCourseKnowledgeIdOrLikeParentIds(TeacherKnowledge teacherKnowledge);
	
	/**
	 * 根据老师Id查询、选中节点以及所有子节点课程版本知识点
	 * <br/> 携带当前知识点下出过总题数、回答正确的数。 
	 * @param teacherKnowledge
	 * @return
	 */
	List<TeacherKnowledge> findTeacherKnowledgesListWithPassingRate(	TeacherKnowledge teacherKnowledge);

	/**
	 * 批量添加老师标识过的知识
	 */
	void batchAddTeacherKnowledge(List<TeacherKnowledge> list);
	
	/**
	 * 批量删除老师标识过的知识点
	 */
	int batchDeleteTeacherKnowledge(Parameter parameter);
	
	/**
	 * 查询老师标识过的知识点
	 */
	List<TeacherKnowledge> findTeacherKnowledgeList(TeacherKnowledge teacherKnowledge);
	
	
	
	/**
	 * 某次组卷或在线考试所考知识点，某班级的答题情况
	 * @param staticKnowledge
	 * @return
	 */
     List<StaticKnowledge> staticKnowledgeByexamIdAndClassId(StaticKnowledge staticKnowledge);
    
    /**
     * *根据组卷或在线考试id 查询所考知识点
     * @param examId
     * @return
     */
	List<CourseKnowledge> findCourseKnowledgeByExamId(String examId);

	/** 根据知识点Id 查询其下所有的试题
	 * @param question
	 * @return
	 */
	List<TeacherVersionQuestion> selectQuestionBykonwId(TeacherVersionQuestion question);
	
	/**
	 * 查询打错题学生列表
	 * @param param 
	 * @return
	 */
	List<Map<String,Object>> findWrongAnswerStudents(Parameter param);

	List<TeacherKnowledge> getKnowledgesPassingRate(Map<String, String> paraMap);

	List<TeacherKnowledge> getKnowledgesPassingRate2(Map<String, String> paraMap);

	List<StaticKnowledge> staticKnowledgeByexamIdAndClassId2(StaticKnowledge staticKnowledge);
	
}
