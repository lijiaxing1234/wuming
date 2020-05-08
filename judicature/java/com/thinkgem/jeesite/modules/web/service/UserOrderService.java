package com.thinkgem.jeesite.modules.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.web.dao.UserOrderMapper;
import com.thinkgem.jeesite.modules.web.entity.pay.UserOrderExample;
import com.thinkgem.jeesite.modules.web.entity.pay.UserOrderWithBLOBs;

/**
 * 支付相关服务层
 *
 */
@Service
@Transactional(readOnly=true)
public class UserOrderService {
	
	

	@Autowired
	UserOrderMapper  userOrderMapper;
	
	@Transactional(readOnly=false)
	public int  insertUserOrderSelective(UserOrderWithBLOBs   record){
		return userOrderMapper.insertSelective(record);
	}
	
	@Transactional(readOnly=false)
	public int  updateUserOrderSelective(UserOrderWithBLOBs   record){
		UserOrderExample  example = new UserOrderExample();
		example.createCriteria().andOrderNoEqualTo(record.getOrderNo());
		return userOrderMapper.updateByExampleSelective(record,example);
	}
	

}
