package com.thinkgem.jeesite.modules.web.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.web.entity.PumpkinPay;
import com.thinkgem.jeesite.modules.web.entity.UserPayCourse;

@MyBatisDao
public interface PayMapper {

	int addPumpkinPayInfo(PumpkinPay pumpkinPay);
	int userhasByCourse(PumpkinPay pumpkinPay);
	int getPayPumpkinCountByUserId(String userId);
	List<UserPayCourse> getAllUserHasPayCourse(String userId);
}
