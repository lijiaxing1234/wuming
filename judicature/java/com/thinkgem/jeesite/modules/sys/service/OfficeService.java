/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	@Autowired
	private OfficeDao officeDao;
	@Autowired
	UserDao userDao;
	
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	public List<Office> findList(Boolean isAll,String extId){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList(extId);
		}else{
			return UserUtils.getOfficeList(extId);
		}
	}
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds("%"+office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public int checkAndDelete(Office office) {
		int result=0;
		
		if(office!=null && StringUtils.isNotBlank(office.getType())){
			User user=new User();
			if("1".equals(office.getType()))
				user.setCompany(office);
			if("2".equals(office.getType()))
			   user.setOffice(office);
			
			List<User> list=userDao.findUserByOfficeIdAndLikeChlidId(user);
			
			if(list.isEmpty()&&list.size()<=0){
				delete(office);
				result=1;
			}
		}
		return result;
	}
	
	

	public List<Office> findByType(Office office) {
		return officeDao.findByType(office);
	}
	public List<Office> findSchool(Office office) {
		return officeDao.findSchool(office);
	}
}
