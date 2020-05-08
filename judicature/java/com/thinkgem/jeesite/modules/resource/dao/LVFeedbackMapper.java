package com.thinkgem.jeesite.modules.resource.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resource.entity.LVFeedback;
import com.thinkgem.jeesite.modules.resource.entity.LVFeedbackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface LVFeedbackMapper {
    long countByExample(LVFeedbackExample example);

    int deleteByExample(LVFeedbackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LVFeedback record);

    int insertSelective(LVFeedback record);

    List<LVFeedback> selectByExample(LVFeedbackExample example);

    LVFeedback selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LVFeedback record, @Param("example") LVFeedbackExample example);

    int updateByExample(@Param("record") LVFeedback record, @Param("example") LVFeedbackExample example);

    int updateByPrimaryKeySelective(LVFeedback record);

    int updateByPrimaryKey(LVFeedback record);
}