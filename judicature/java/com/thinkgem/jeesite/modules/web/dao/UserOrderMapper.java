package com.thinkgem.jeesite.modules.web.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.web.entity.pay.UserOrder;
import com.thinkgem.jeesite.modules.web.entity.pay.UserOrderExample;
import com.thinkgem.jeesite.modules.web.entity.pay.UserOrderWithBLOBs;

@MyBatisDao
public interface UserOrderMapper {
    long countByExample(UserOrderExample example);

    int deleteByExample(UserOrderExample example);

    int insert(UserOrderWithBLOBs record);

    int insertSelective(UserOrderWithBLOBs record);

    List<UserOrderWithBLOBs> selectByExampleWithBLOBs(UserOrderExample example);

    List<UserOrder> selectByExample(UserOrderExample example);

    int updateByExampleSelective(@Param("record") UserOrderWithBLOBs record, @Param("example") UserOrderExample example);

    int updateByExampleWithBLOBs(@Param("record") UserOrderWithBLOBs record, @Param("example") UserOrderExample example);

    int updateByExample(@Param("record") UserOrder record, @Param("example") UserOrderExample example);
}