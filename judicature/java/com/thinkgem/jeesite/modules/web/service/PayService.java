package com.thinkgem.jeesite.modules.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.modules.web.dao.PayMapper;
import com.thinkgem.jeesite.modules.web.entity.PumpkinPay;
import com.thinkgem.jeesite.modules.web.entity.UserIntegral;
import com.thinkgem.jeesite.modules.web.entity.UserPayCourse;

@Service
public class PayService {

	@Autowired
	PayMapper payMapper;
	
	public boolean addPumpkinPayInfo(PumpkinPay pumpkinPay){
		int i = payMapper.userhasByCourse(pumpkinPay);
		if (i == 0) {
			int pumpkinPayInfo = payMapper.addPumpkinPayInfo(pumpkinPay);
			return pumpkinPayInfo == 1;
		}else {
			return true;
		}
	}
	
	public List<UserPayCourse> getAllUserHasPayCourse(String userId){
		return payMapper.getAllUserHasPayCourse(userId);
	}
}
