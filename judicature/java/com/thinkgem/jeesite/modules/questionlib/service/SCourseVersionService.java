/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.dao.SCourseVersionDao;
import com.thinkgem.jeesite.modules.questionlib.entity.SCourseVersion;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 课程版本Service
 * @author webcat
 * @version 2016-09-16
 */
@Service
@Transactional(readOnly = true)
public class SCourseVersionService extends CrudService<SCourseVersionDao, SCourseVersion> {
	
	@Autowired
	private SCourseVersionDao sCourseVersionDao;

	
	public SCourseVersion get(String id) {
		SCourseVersion sCourseVersion = super.get(id);
		return sCourseVersion;
	}
	
	public List<SCourseVersion> findList(SCourseVersion sCourseVersion) {
		return super.findList(sCourseVersion);
	}
	
	public Page<SCourseVersion> findPage(Page<SCourseVersion> page, SCourseVersion sCourseVersion) {
		return super.findPage(page, sCourseVersion);
	}
	
	@Transactional(readOnly = false)
	public void delete(SCourseVersion sCourseVersion) {
		super.delete(sCourseVersion);
	}

	public Page<SCourseVersion> getSchoolCourseVersionPage(Page<SCourseVersion> page, SCourseVersion sCourseVersion) {
		//学校id
		String schoolId = UserUtils.getUser().getCompany().getId();
		Map<String, String> sqlMap = sCourseVersion.getSqlMap();
		sqlMap.put("schoolId", schoolId);
		sCourseVersion.setPage(page);
		page.setList(sCourseVersionDao.getSchoolCourseVersionPage(sCourseVersion));
		return page;
	}

	public List<SCourseVersion> getVersionsByCourseId(String courseId) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("courseId", courseId);
		paraMap.put("companyId", UserUtils.getUser().getCompany().getId());
		return sCourseVersionDao.getVersionsByCourseId(paraMap);
	}

	@Transactional(readOnly = false)
	public void addSchoolCourseVersion(SCourseVersion sCourseVersion) {
		//insert into table_school_version values(#{id},#{school_id},#{version_id})
		
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("id", UUID.randomUUID().toString().replace("-", ""));
		paraMap.put("schoolId", UserUtils.getUser().getCompany().getId());
		paraMap.put("versionId", sCourseVersion.getId());
		sCourseVersionDao.addSchoolCourseVersion(paraMap);
	}
	
	@Transactional(readOnly = false)
	public void updateSchoolCourseVersion(SCourseVersion sCourseVersion) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("id", sCourseVersion.getSchoolVersionId());
		paraMap.put("versionId", sCourseVersion.getId());
		sCourseVersionDao.updateSchoolCourseVersion(paraMap);
	}

	public Map<String, String> getSchoolVersionBy2Id(String versionId) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("versionId", versionId);
		paraMap.put("schoolId", UserUtils.getUser().getCompany().getId());
		return sCourseVersionDao.getSchoolVersionBy2Id(paraMap);
	}
	
	@Transactional(readOnly = false)
	public void deleteSchoolVersion(String courseVersionId) {
		//delete from table_school_version where version_id = #{courseVersionId}
		sCourseVersionDao.deleteSchoolVersion(courseVersionId);
	}

	public List<SCourseVersion> getVersionsByCourseIdAndSchoolId(String courseId) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("courseId", courseId);
		paraMap.put("companyId", UserUtils.getUser().getCompany().getId());
		return sCourseVersionDao.getVersionsByCourseIdAndSchoolId(paraMap);
	}

	public List<Specialty> getCoursesBySpecialtyId(String specialtyId) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("specialtyId", specialtyId);
		paraMap.put("schoolId", UserUtils.getUser().getCompany().getId());
		return sCourseVersionDao.getCoursesBySpecialtyId(paraMap);
	}
	
	
	/*学校专业、课程、课程版本查询*/
	
	public List<Map<String,Object>>  findSchoolSpecialty(){
		return sCourseVersionDao.findSchoolSpecialty(new Parameter(new Object[][]{
		     { "companyId",UserUtils.getUser().getCompany().getId()}
		}));
	}
	
	public List<Map<String,Object>>  findSchoolCourseBySpecialtyId(String SpecialtyId ){
		return sCourseVersionDao.findSchoolCourseBySpecialtyId(new Parameter(new Object[][]{
			     { "companyId",UserUtils.getUser().getCompany().getId()},
			     {"specialtyId",SpecialtyId}
		}));
	}
	
	public List<Map<String,Object>>  findSchoolCourseVersionByCourseId(String courseId){
		return sCourseVersionDao.findSchoolCourseVersionByCourseId(new Parameter(new Object[][]{
			     {"companyId",UserUtils.getUser().getCompany().getId()},
			     {"courseId",courseId}
		}));
	}

	
}