package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.teacher.dto.ExamStudentDTO;
import com.thinkgem.jeesite.modules.teacher.entity.ExamClass;
import com.thinkgem.jeesite.modules.teacher.entity.ExamKnowledgeQuestion;
import com.thinkgem.jeesite.modules.teacher.entity.KnowledgeQuestionDetail;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;

/**
 * 手动出题时用到 Dao
 * 试卷、知识点、试题类
 */
@MyBatisDao
public interface ExamKnowledgeQuestionDao{
  
  /**
   * 
   * @param ekq
   */
  List<ExamKnowledgeQuestion>  findList(ExamKnowledgeQuestion examKnowledgeQuestion);
  
  
  /**
   *先删除后添加
   * 批量保存知识点和试题数量关系表
   * @param list 是	List<KnowledgeQuestionDetail>
   * Parameter param=new Parameter(new Object[][]{
    	  {"list",list}, 
    	  {"examId",exam.getId()},
    	  {"teacherId",TearcherUserUtils.getUser().getId()}
    	});
   * 
   */
  void  batchInsertKnowledgeQuestionDetail(Parameter param);


  List<KnowledgeQuestionDetail> findExamKnowledgeQuestionByExamId(KnowledgeQuestionDetail examKnowQues);
	
}
