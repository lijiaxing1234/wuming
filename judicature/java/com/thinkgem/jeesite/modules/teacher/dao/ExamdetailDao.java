package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.teacher.entity.Examdetail;

/**
 * 试卷详细Dao
 */
@MyBatisDao
public interface  ExamdetailDao {
  
	/**
	 * 试卷详细选择添加
	 */
	int insertSelective(Examdetail examdetail);
	/**
	 * 试卷详细选择修改
	 */
	int updateSelective(Examdetail examdetail);
	
	/**
	 * 
	 * 获得试卷详细（是AB卷有两条记录）列表
	 * @param examdetail.exam.id 为查询条件
	 */
	List<Examdetail>  getExamDetailByExamId(Examdetail examdetail);
    /**
     * 查询已出试卷的总分数
     * @param examdetail
     * @return
     */
	List<Examdetail>  findExamdetailScoresbyExamId(Examdetail examdetail);
	
	/**
	 * 删除试卷下试卷分类（A、B）卷、及试题的关系
	 * @param examId 
	 */
	void deleteExamDetailContactByExamId(String examId);
	
	public List<Examdetail> getExamdetailById(@Param(value="id")String id);
	
}
