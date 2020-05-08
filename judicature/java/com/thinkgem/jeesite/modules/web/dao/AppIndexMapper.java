package com.thinkgem.jeesite.modules.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.web.entity.AD;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.entity.Questions;
import com.thinkgem.jeesite.modules.web.entity.ResCategory;

/**
 *  app首页 历年真题、每日一练、智能练习 接口 Dao
 */
@MyBatisDao
public interface AppIndexMapper {
	
	 /**
	  * 根据课程Id 随机查询试题
	  * @param courseId 课程Id
	  * @param quesType 试题题型
	  * @param randomNumber 随机个数
	  * @return List<Questions>  试题集合
	  */
	List<Questions>	getRandomQuestionListByCourseId(@Param("courseId")String courseId,@Param("quesType")String quesType,@Param("randomNumber")Integer randomNumber);
	
    /**
	  * 根据试题Id查询试题
	  * @param courseId 课程Id
	  * @return List<Questions> 试题信息
	  */
	 List<Questions> getQuestionById(String quesId);
	
	/**
	 * 查询随机出的试题的题型
	 * @param courseId 课程Id
	 * @return List<Map<String,String>>题型集合 <h4>Map<String,String>  key(题型Id)->id,value(题型label)->value</h4> 
	 */
	List<Map<String,String>> getRandomQuestionTypeListByCourseId(@Param("courseId")String courseId);

	
	 /**
     *  根据课程Id 查询课程下所有知识点
     * @param courseId  课程Id
     * @param treelevel 树的级别 （可为null）
     * @return List<Knowledge> 课程下所有知识点
     */
	List<Knowledge> selectKnowledgeByCourseIdList(@Param("courseId")String courseId, @Param("treelevel")Integer treelevel);
	
	List<Knowledge> selectKnowledgeByCourseIdListPage(@Param("courseId")String courseId, @Param("treelevel")Integer treelevel);
	  /**
     * 根据课程Id和知识点id 查询所有试题
     * @param courseId  课程Id
     * @param knowId   知识点id
     * @return List<Questions> 试题
     */
	List<Questions> selectQuestionByKnowIdList(@Param("courseId")String courseId, @Param("knowId")String knowId);

	
	
	
    /**
     * 根据分类父id查询所有下级分类
     * @param courseId  课程id
     * @param parentCateId  分类父id
     * @param treelevel  分类树级别
     * @return List<ResCategory> 分类列表
     */
	List<ResCategory> selectResCategoryByCourseIdList(@Param("courseIds")String[] courseIds, @Param("parentCateId")String parentCateId, @Param("treelevel")Integer treelevel);
	
	List<ResCategory> categoryListById(@Param("id")String  id);
	
	
	
	List<AD> getIndexAd();
		
	List<Knowledge> selectRecomZhentiList(@Param("courseId")String courseId, @Param("treelevel")Integer treelevel);
	String getTrueVersionId(String id);
}
