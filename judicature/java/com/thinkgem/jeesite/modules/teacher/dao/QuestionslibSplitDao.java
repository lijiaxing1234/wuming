package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.teacher.entity.QuestionslibSplit;
import com.thinkgem.jeesite.modules.teacher.entity.StatisticsQuestionNumber;

/**
 * 试题分库Dao
 * @author flychao
 */
@MyBatisDao
public interface QuestionslibSplitDao {
   
   /**
    * 获得练习题的百分比
    * @param teacher 当前登录老师
    * @return Double 当前老师设置练习题的百分比
    */
   Double getPracticePercentByTeacher(QuestionslibSplit qls);
  
   /**
    * 插入练习题的百分比
    * @param teacher 当前登录老师
    * @return int 数据库受影响行数
    */
   int  insertPracticePercentByTeacher(QuestionslibSplit qls);
  
   /**
    * 修改练习题的百分比
    * @param teacher 当前登录老师
    * @return int 数据库受影响行数
    */
   int  updatePracticePercentByTeacher(QuestionslibSplit qls);
	
	
	
   /**
    * 获得所有可用试题的数量
    * @param qls QuestionslibSplit 实体
    * @return Long 所有可用试题的数量
    */
   Long getAllQuestionsNumber(QuestionslibSplit qls);
   
    /**
	 * 根据老师设置的练习题的百分比把老师所在学校的题库和老师自己的题库进行分库
	 * <br/>
	 * 例如: 30%是练习库 那么剩下的70%就是在线考试和组卷库。
	 */
    void initQuestionslibSplitByTeacher(QuestionslibSplit qls);
    
    /**
     * 删除分库表当前老师所设百分比的所有试题
     * @param qls
     */
	void deleteQuestionslibSplitByTeacher(QuestionslibSplit qls);
	
	
	/**
	 * 获得老师按百分比分库下所在学校及所教版本下的全部试题
	 * @param qls
	 * @return List<QuestionslibSplit> 分库下全部试题
	 */
	List<QuestionslibSplit> findQuestionslibSplitByteacher(QuestionslibSplit qls);
	
	
	/**
	 * 获得老师按百分比分库下所在学校及所教版本下的全部试题的数量
	 * @param qls
	 * @return Long 试题的数量
	 */
	 Long countQuestionslibSplitByteacher(QuestionslibSplit qls);
	
	/**
	 * 获得老师按百分比分库下所在学校及所教版本下的全部试题 <h3>按题类型分组统计自动出题试题信息</h3>
	 * <br> 例如：可用分数： 30.0 题的个数： 30 题的分数： 1(30(题的个数)) 
	 * @param qls
	 * @return List<QuestionslibSplit> Long
	 */
	List<Map<String,Object>> statisticsQuestionslibSplitScoreInfoByteacher(QuestionslibSplit qls);
	
	/**
	 * 查询分库后某种类型可以补题的数量
	 * @param qls
	 * @return Integer
	 */
	Integer countQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(QuestionslibSplit qls);
	/**
	 * 查询分库后某种类型随机指定数量的的集合
	 * @param qls
	 * @return Integer
	 */
	List<VersionQuestion> findRandomQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(QuestionslibSplit qls);
 
	//查询分库后 每种题型的总分数
	List<StatisticsQuestionNumber> getAllQuestionsNumberInfo(QuestionslibSplit qls);

}
