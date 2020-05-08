package com.thinkgem.jeesite.modules.web.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.web.entity.CourseInformation;
import com.thinkgem.jeesite.modules.web.entity.UserDeliveryAddress;
import com.thinkgem.jeesite.modules.web.entity.UserInfo;
import com.thinkgem.jeesite.modules.web.entity.UserIntegral;
import com.thinkgem.jeesite.modules.web.entity.UserRecord;
import com.thinkgem.jeesite.modules.web.entity.UserSign;
import com.thinkgem.jeesite.modules.web.entity.PublicationResourceVisitors;

@MyBatisDao
public interface UserInfoMapper {

	int hasUser(String phone);
	String getUserPWD(String phone);
	int userHasPWD(String id);
	boolean addUser(UserInfo userInfo);
	boolean setPW(UserInfo userInfo);
	boolean addPlayLog(PublicationResourceVisitors prv);
	int findPlayLog(PublicationResourceVisitors prv);
	boolean updateUserInfo(UserInfo userInfo);
	boolean updateUserIcon(UserInfo userInfo);
	UserInfo getUserInfoByPhone(String phone);
	UserInfo getUserInfoById(String id);
	
	int hasUserDeliveryAddress(String userId);
	boolean addDeliveryAddress(UserDeliveryAddress userDeliveryAddress);
	boolean updateDeliveryAddress(UserDeliveryAddress userDeliveryAddress);
	UserDeliveryAddress getDeliveryAddress(String userId);
	
	boolean addUserIntegral(UserIntegral userIntegral);
	int hasTodayUserIntegral(String userId);
	boolean updateUserIntegralTime(UserIntegral userIntegral);
	UserIntegral getUserIntegralTime(String userId);
	boolean updateUserIntegral(UserIntegral userIntegral);
	UserIntegral getUserIntegral(@Param("userId") String userId,@Param("createDate") String createDate);
	int getTotalIntegral(String userId);
	
	boolean updateStudyTime(UserInfo userInfo);
	int hasLiveCount(UserRecord record);
	UserInfo centerIndex(@Param("userId") String userId,@Param("createDate") String createDate);
	UserIntegral getUserPumpkinCount(@Param("userId") String userId,@Param("createDate") String createDate);
	
	List<UserSign> getUserSign(@Param("userId") String userId,@Param("list") List<String> list);
	boolean userTodaySign(UserSign userSign);
	Integer getUserSignPumpkinCountByDate(@Param("userId") String userId,@Param("date") String date);
	
	int hasUserSign(String userId);
	int getSumSignPumpkinCount(String userId);
	int hasSignToday(@Param("userId") String userId,@Param("date") String date);
	
	List<CourseInformation> getAllCourseInformation();
	
	Integer usedPumpkinCount(String userId);
	
	boolean forgetPWD(@Param("password") String password,@Param("phone") String phone);
}
