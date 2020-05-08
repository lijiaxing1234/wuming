package com.thinkgem.jeesite.modules.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.xslf.model.geom.Guide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.questionlib.dao.CourseDao;
import com.thinkgem.jeesite.modules.web.dao.PayMapper;
import com.thinkgem.jeesite.modules.web.dao.UserInfoMapper;
import com.thinkgem.jeesite.modules.web.entity.CourseInformation;
import com.thinkgem.jeesite.modules.web.entity.PublicationResourceVisitors;
import com.thinkgem.jeesite.modules.web.entity.UserDeliveryAddress;
import com.thinkgem.jeesite.modules.web.entity.UserInfo;
import com.thinkgem.jeesite.modules.web.entity.UserIntegral;
import com.thinkgem.jeesite.modules.web.entity.UserRecord;
import com.thinkgem.jeesite.modules.web.entity.UserSign;
import com.thinkgem.jeesite.modules.web.util.MD5Util;

import sun.net.www.content.text.plain;

@Service
public class UserInfoService {

	@Autowired
	UserInfoMapper userInfoMapper;
	
	@Autowired
	PayMapper payMapper;
	
	@Autowired
	CourseDao courseDao;
	public boolean hasUser(String phone){
		return userInfoMapper.hasUser(phone)==1;
	}
	
	public boolean getUserPWD(String phone,String password){
		String userPWD = userInfoMapper.getUserPWD(phone);
		String md5 = MD5Util.md5(password);
		if(userPWD.equalsIgnoreCase(md5)){
			return true;
		}
		return false;
	}
	
	public boolean userHasPWD(String id){
		int hasPWD = userInfoMapper.userHasPWD(id);
		return hasPWD == 1;
	}
	
	public boolean addUser(String phone){
		UserInfo userInfo = new UserInfo();
		userInfo.setPhone(phone);
		return userInfoMapper.addUser(userInfo);
	}
	
	public boolean setPW(UserInfo userInfo){
		String passWord = userInfo.getPassword();
		String md5 = MD5Util.md5(passWord);
		userInfo.setPassword(md5);
		return userInfoMapper.setPW(userInfo);
	}
	
	public boolean forgetPWD(UserInfo userInfo){
		String passWord = userInfo.getPassword();
		String phone = userInfo.getPhone();
		String md5 = MD5Util.md5(passWord);
		boolean b = userInfoMapper.forgetPWD(md5, phone);
		return b;
	}
	
	public boolean addPlayLog(PublicationResourceVisitors prv){
		int i= userInfoMapper.findPlayLog(prv);
		if (i==0) {
			prv.setId( UUID.randomUUID().toString().trim().replaceAll("-", ""));
			userInfoMapper.addPlayLog(prv) ;
		}
		
		return true;
	}
	
	public boolean updateUserInfo(UserInfo userInfo){
		return userInfoMapper.updateUserInfo(userInfo);
	}
	
	public boolean updateUserIcon(UserInfo userInfo){
		return userInfoMapper.updateUserIcon(userInfo);
	}
	
	public UserInfo getUserInfoByPhone(String phone){
		return userInfoMapper.getUserInfoByPhone(phone);
	}
	
	public UserInfo getUserInfoById(String id){
		return userInfoMapper.getUserInfoById(id);
	}
	
	public boolean updateDeliveryAddress(UserDeliveryAddress userDeliveryAddress){
		boolean b = false;
		int i = userInfoMapper.hasUserDeliveryAddress(userDeliveryAddress.getUserId());
		if(i == 1){
			b = userInfoMapper.updateDeliveryAddress(userDeliveryAddress);
		}if(i == 0){
			b = userInfoMapper.addDeliveryAddress(userDeliveryAddress);
		}
		return b;
	}
	
	public UserDeliveryAddress getDeliveryAddress(String userId){
		UserDeliveryAddress deliveryAddress = userInfoMapper.getDeliveryAddress(userId);
		return deliveryAddress;
	}
	
	@Transactional
	public boolean updateUserIntegral(UserIntegral userIntegral){
		boolean c = false;
		int i = userInfoMapper.hasTodayUserIntegral(userIntegral.getUserId());
		if(i == 1){
			 c = userInfoMapper.updateUserIntegralTime(userIntegral);
			/*if(c){
				UserIntegral time = userInfoMapper.getUserIntegralTime(userIntegral.getUserId());
				time.setUserId(userIntegral.getUserId());
				UserIntegral integral = play(time);
				b = userInfoMapper.updateUserIntegral(integral);
			}*/
		}if(i == 0){
			 c = userInfoMapper.addUserIntegral(userIntegral);
			/*if(c){
				UserIntegral integral = play(userIntegral);
				b = userInfoMapper.updateUserIntegral(integral);
			}*/
		}
		return c;
	}
	
	public UserIntegral getUserIntegral(String userId,String date){
		int hasUserSign = userInfoMapper.hasSignToday(userId,date);
		int pumpkinCountByUserId = 0;
		try {
			//pumpkinCountByUserId =payMapper.getPayPumpkinCountByUserId(userId);
		} catch (Exception e) {
		}
		if (hasUserSign  > 0) {
			UserIntegral integral = userInfoMapper.getUserIntegral(userId,date);
			if (integral == null ) {
				UserIntegral userIntegral2 = new UserIntegral();
				int totalIntegral = userInfoMapper.getTotalIntegral(userId);
				userIntegral2.setTotal(totalIntegral);
				Integer i = userInfoMapper.getUserSignPumpkinCountByDate(userId,date);
				int sumSignPumpkinCount = userInfoMapper.getSumSignPumpkinCount(userId);
					if (i != null) {
						userIntegral2.setSignIntegral(i);
					}
					int total =0;
					try {
						if (userIntegral2.getTotal() != 0) {
							total = userIntegral2.getTotal();
						}
					} catch (Exception e) {
					}
					//integral.setTotal(i+total);
					//if (pumpkinCountByUserId > 0) {
						total =total+sumSignPumpkinCount;
						userIntegral2.setTotal(total>0?total:0);
					//}
					return userIntegral2;
			}else{
				UserIntegral play = play(integral);
				play.setUserId(userId);
				userInfoMapper.updateUserIntegral(play);
				int totalIntegral = userInfoMapper.getTotalIntegral(userId);
				play.setTotal(totalIntegral);
				Integer i = userInfoMapper.getUserSignPumpkinCountByDate(userId,date);
				int sumSignPumpkinCount = userInfoMapper.getSumSignPumpkinCount(userId);
				if (i != null) {
					play.setSignIntegral(i);
				}
				int total =0;
				try {
					if (play.getTotal() != 0) {
						total = play.getTotal();
					}
				} catch (Exception e) {
				}
				//integral.setTotal(i+total);
				//if (pumpkinCountByUserId > 0) {
					total =total+sumSignPumpkinCount;
					play.setTotal(total>0?total:0);
				//}
				return play;
			}
			
		}/*if (pumpkinCountByUserId > 0) {
			UserIntegral userIntegral = userInfoMapper.getUserIntegral(userId,date);
			int total = userIntegral.getTotal();
			userIntegral.setTotal(total-pumpkinCountByUserId);
			return userIntegral;
		}*/
		UserIntegral userIntegral = userInfoMapper.getUserIntegral(userId,date);
		if (userIntegral!=null) {
			UserIntegral play = play(userIntegral);
			play.setUserId(userId);
			userInfoMapper.updateUserIntegral(play);
			int totalIntegral = userInfoMapper.getTotalIntegral(userId);
			
			int sumSignPumpkinCount =0;
			 try {
				 if(StringUtils.isNotBlank(userId)){
				 sumSignPumpkinCount = userInfoMapper.getSumSignPumpkinCount(userId);
				 }
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			play.setTotal(totalIntegral+sumSignPumpkinCount);
			return play;
		}else{
			UserIntegral userIntegral2 = new UserIntegral();
			int totalIntegral = userInfoMapper.getTotalIntegral(userId);
			int sumSignPumpkinCount = userInfoMapper.getSumSignPumpkinCount(userId);
			userIntegral2.setTotal(totalIntegral+sumSignPumpkinCount);
			return userIntegral2;
		}
	}
	
	public int getTotalIntegral(String userId){
		int sumSignPumpkinCount = userInfoMapper.getSumSignPumpkinCount(userId);
		int totalIntegral = userInfoMapper.getTotalIntegral(userId);
		Integer usedPumpkinCount = userInfoMapper.usedPumpkinCount(userId);
		if (usedPumpkinCount == null) {
			usedPumpkinCount = 0;
		}
		int a = sumSignPumpkinCount+totalIntegral;
		int b = a - usedPumpkinCount;
		return b;
	}
	
	public boolean updateStudyTime(UserInfo userInfo){
		boolean b = userInfoMapper.updateStudyTime(userInfo);
		return b;
	}
	
	public int hasLiveCount(UserRecord record){
		return userInfoMapper.hasLiveCount(record);
	}
	
	public UserInfo centerIndex(String userId,String date) throws ParseException{
		UserInfo userInfo = userInfoMapper.centerIndex(userId,date);
		String examDate = userInfo.getExamDate();
		if (StringUtils.isNotBlank(examDate)) {
			int day = howDay(examDate);
			userInfo.setLongDay(day);
		}
		Integer i = userInfoMapper.getUserSignPumpkinCountByDate(userId, date);
		if (i != null) {
			int pumpkinCount = userInfo.getPumpkinCount();
			userInfo.setPumpkinCount(pumpkinCount+i);
		}
		return userInfo;
	}
	
	public List<UserSign> getUserSign(String userId){
		Date currentDate = new Date();
		List<String> list = dateToWeek(currentDate);
		List<UserSign> userSign = userInfoMapper.getUserSign(userId,list);
		return userSign;
	}
	
	
	public boolean userTodaySign(String userId,String pTime) throws ParseException{
		int j = userInfoMapper.hasSignToday(userId, pTime);
		if (j > 0) {
			return true;
		}else {
		UserSign userSign = new UserSign();
		userSign.setUserId(userId);
		userSign.setSignDate(pTime);
		userSign.setPumpkinCount(1);
		boolean b = userInfoMapper.userTodaySign(userSign);
		return b;
		}
		/*Integer i = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); //得到前一天
		Date date = calendar.getTime();
		String dateS = format.format(date);
		
		calendar.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1){
			dayForWeek = 7;
		}else{
			dayForWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		}
		if (dayForWeek==1) {
			userSign.setPumpkinCount(dayForWeek);
			boolean b = userInfoMapper.userTodaySign(userSign);
			return b;
		}
		
		i = userInfoMapper.getUserSignPumpkinCountByDate(userId,dateS);
		if (i == null) {
			userSign.setPumpkinCount(1);
			boolean b = userInfoMapper.userTodaySign(userSign);
			return b;
		}else {
			userSign.setPumpkinCount(dayForWeek);
			boolean b = userInfoMapper.userTodaySign(userSign);
			return b;
		}
		}*/
	}
	
	public Integer getUserSignPumpkinCountByDate(String userId,String date){
		Integer i = userInfoMapper.getUserSignPumpkinCountByDate(userId,date);
		return i;
	}
	
	public List<CourseInformation> getAllCourseInformation(){
		List<CourseInformation> allCourseInformation = userInfoMapper.getAllCourseInformation();
		return allCourseInformation;
	}
	
	public com.thinkgem.jeesite.common.utils.Page<CourseInformation> CourseInformationByPage(String pageNB,int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNO;
		try {
			pageNO = Integer.parseInt(pageNB);
		} catch (Exception e) {
			pageNO = 1;
		}
		pageNO = (pageNO-1)*pageSize;
		map.put("pageNO", pageNO);
		map.put("pageSize", pageSize);
		int allciCount = courseDao.getAllciCount();
		com.thinkgem.jeesite.common.utils.Page<CourseInformation> page = new com.thinkgem.jeesite.common.utils.Page<CourseInformation>(pageNB, allciCount);
		List<CourseInformation> allCourseInformation = courseDao.getAllCourseInformation(map);
		page.setContent(allCourseInformation);
		return page;
	}
	
	
	
	//以下是工具方法
	UserIntegral play(UserIntegral userIntegral){
		int liveTime = userIntegral.getLiveTime();
		int questionNum = userIntegral.getQuestionNum();
		int videoTime = userIntegral.getVideoTime();
		int liveInt = (liveTime/60)/25;
		int questionInt = questionNum/15;
		int videoInt = (videoTime/60)/25;
		userIntegral.setLiveIntegral(liveInt);
		userIntegral.setQuestionIntegral(questionInt);
		userIntegral.setVideoIntegral(videoInt);
		return userIntegral;
	}
	
	List<String> dateToWeek(Date mdate) {  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    int b = mdate.getDay();  
	    Date fdate;  
	    List<String> list = new ArrayList<String>();  
	    Long fTime = mdate.getTime() - b * 24 * 3600000;  
	    for (int a = 1; a <= 7; a++) {  
	        fdate = new Date();  
	        fdate.setTime(fTime + (a * 24 * 3600000));  
	        list.add(a-1, format.format(fdate));  
	    }  
	    return list;  
	}  
	
	int howDay(String day) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar aCalendar = Calendar.getInstance();
		
		aCalendar.setTime(new Date());
		
		int day11 = aCalendar.get(Calendar.DAY_OF_YEAR);
		
		aCalendar.setTime(format.parse(day));
		
		int day22 = aCalendar.get(Calendar.DAY_OF_YEAR);
		
		return day22 - day11;
	}

	
}
