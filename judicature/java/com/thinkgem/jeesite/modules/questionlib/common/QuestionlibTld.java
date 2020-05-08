package com.thinkgem.jeesite.modules.questionlib.common;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.ObjectUtils;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.CourseDao;
import com.thinkgem.jeesite.modules.questionlib.dao.CourseQuestionlibDao;
import com.thinkgem.jeesite.modules.questionlib.dao.CourseVesionDao;
import com.thinkgem.jeesite.modules.questionlib.dao.SCourseVersionDao;
import com.thinkgem.jeesite.modules.questionlib.dao.SchoolClassDao;
import com.thinkgem.jeesite.modules.questionlib.dao.SchoolQuestionlibDao;
import com.thinkgem.jeesite.modules.questionlib.dao.SpecialtyDao;
import com.thinkgem.jeesite.modules.questionlib.dto.StaticDTO;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.SCourseVersion;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.entity.TableClass;
import com.thinkgem.jeesite.modules.questionlib.entity.TableQuestionlibImportApply;
import com.thinkgem.jeesite.modules.questionlib.service.SCourseVersionService;
import com.thinkgem.jeesite.modules.questionlib.service.TableQuestionlibImportApplyService;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.teacher.dao.ExamDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExaminationDao;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

public class QuestionlibTld implements ApplicationContextAware{
	
	static SpecialtyDao specialtyDao = SpringContextHolder.getBean(SpecialtyDao.class);
	static CourseDao cousrseDao = SpringContextHolder.getBean(CourseDao.class);
	static CourseVesionDao cousrseVersionDao = SpringContextHolder.getBean(CourseVesionDao.class);
	static CourseQuestionlibDao courseQuestionlibDao = SpringContextHolder.getBean(CourseQuestionlibDao.class);
	static SchoolClassDao schoolClassDao = SpringContextHolder.getBean(SchoolClassDao.class);
	static SCourseVersionDao sCourseVersionDao = SpringContextHolder.getBean(SCourseVersionDao.class);
	static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	static ExamDao examDao=SpringContextHolder.getBean(ExamDao.class);
	static ExaminationDao examinationDao=SpringContextHolder.getBean(ExaminationDao.class);
	static SchoolQuestionlibDao schoolQuestionLibDao=SpringContextHolder.getBean(SchoolQuestionlibDao.class);
	static TableQuestionlibImportApplyService  quesImportApplyService=SpringContextHolder.getBean(TableQuestionlibImportApplyService.class);
	
    static SCourseVersionService sCourseVersionService=SpringContextHolder.getBean(SCourseVersionService.class);
	
    
    
    
    /*********************
     * 学校端课程 、课程版本开始
     *  
     * *********************************************************/
    
	/**
	 * 专业下学校选中课程集合（只有 id、title 两个字段）
	 */
	public static List<Course> getSchoolCourseBySpecialtyId(String specialtyId) {
		@SuppressWarnings("unchecked")
		List<Course> dictList = (List<Course>)CacheUtils.get("courseList");
		if (dictList==null){
			
			if(StringUtils.isNotBlank(specialtyId)){
				List<Specialty> reList = sCourseVersionService.getCoursesBySpecialtyId(specialtyId);
				if(reList !=null){
					dictList=Lists.newArrayList();
					for(Specialty s:reList){
						Course c=new Course(s.getId());
						c.setTitle(s.getTitle());
						dictList.add(c);
					}
					
				}
			}
			//CacheUtils.put("courseList", dictList);
		}
		return dictList;
	}
	/**
	 * 单条课程版本下的版本列表 （只有 id、title 两个字段）
	 * @param id
	 * @return
	 */
	public static List<CourseVesion> getSchoolCourseVersionByCourseId(String courseId) {
		@SuppressWarnings("unchecked")
		List<CourseVesion> dictList = (List<CourseVesion>)CacheUtils.get("courseVersionList");
		if (dictList==null){
			if(StringUtils.isNoneBlank(courseId)){
				List<SCourseVersion> reList = sCourseVersionService.getVersionsByCourseIdAndSchoolId(courseId);
				if(reList !=null){
					dictList=Lists.newArrayList();
					for(SCourseVersion sc:reList){
						CourseVesion e = new CourseVesion(sc.getId());
						e.setTitle(sc.getTitle());
						dictList.add(e);
					}
					
				}
				
			}
			//CacheUtils.put("courseVersionList", dictList);
		}
		return dictList;
	}
    
    
    /*********************
     * 学校端课程 、课程版本 结束
     *  
     * *********************************************************/
    
    
    
    
    
    
    
    
    
	/**
	 * 专业
	 */
	public static List<Specialty> getSpecialty() {
		@SuppressWarnings("unchecked")
		List<Specialty> dictList =  (List<Specialty>)CacheUtils.get("specialList");
		if (dictList==null){
			Specialty a = new Specialty();
			a.setDelFlag("0");
			dictList = specialtyDao.findList(a);
			//CacheUtils.put("specialList", dictList);
		}
		return dictList;
	}
	
	/**
	 * 单条专业
	 * @param id
	 * @return
	 */
	public static  Specialty  getSpecialtyByID(String id) {
		@SuppressWarnings("unchecked")
		List<Specialty> dictList = (List<Specialty>)CacheUtils.get("specialList");
		if (dictList==null){
			Specialty a = new Specialty();
			a.setDelFlag("0");
			dictList = specialtyDao.findList(a);
			//CacheUtils.put("specialList", dictList);
		}
		
		for (Specialty specialty : dictList) {
			if (specialty.getId().equals(id)) {
				return specialty;
			}
		}
		
		return null;
	}
	
	
	 
	/**
	 * 课程
	 * @return
	 */
	public static List<Course> getCourse() {
		@SuppressWarnings("unchecked")
		List<Course> dictList = (List<Course>)CacheUtils.get("courseList");
		if (dictList==null){
			Course a = new Course();
			a.setDelFlag("0");
			dictList = cousrseDao.findList(a);
			//CacheUtils.put("courseList", dictList);
		}
		return dictList;
	}
	/**
	 * 专业下课程集合
	 */
	public static List<Course> getCourseBySpecialtyId(String specialtyId) {
		@SuppressWarnings("unchecked")
		List<Course> dictList = (List<Course>)CacheUtils.get("courseList");
		if (dictList==null){
			if(StringUtils.isNotBlank(specialtyId)){
				Course a = new Course();
				a.setSpecialtyId(specialtyId);
				a.setDelFlag("0");
				dictList = cousrseDao.findList(a);
			}
			//CacheUtils.put("courseList", dictList);
		}
		return dictList;
	}
	
	/**
	 * 单条课程
	 * @param id
	 * @return
	 */
	public static  Course  getCourseByID(String id) {
		@SuppressWarnings("unchecked")
		List<Course> dictList = (List<Course>)CacheUtils.get("courseList");
		if (dictList==null){
			Course a = new Course();
			a.setId(id) ;
			a.setDelFlag("0");
			dictList = cousrseDao.findList(a);
			//CacheUtils.put("courseList", dictList);
		}
		
		for (Course course : dictList) {
			if (course.getId().equals(id)) {
				return course;
			}
		}
		
		return null;
	}
	
	/**
	 * 课程版本
	 * @return
	 */
	public static List<CourseVesion> getCourseVersion() {
		@SuppressWarnings("unchecked")
		List<CourseVesion> dictList = (List<CourseVesion>)CacheUtils.get("courseVersionList");
		if (dictList==null){
			CourseVesion a = new CourseVesion();
			a.setDelFlag("0");
			dictList = cousrseVersionDao.findList(a);
		//	CacheUtils.put("courseVersionList", dictList);
		}
		return dictList;
	}
	
	/**
	 * 单条课程版本下的版本列表
	 * @param id
	 * @return
	 */
	public static List<CourseVesion> getCourseVersionByCourseId(String id) {
		@SuppressWarnings("unchecked")
		List<CourseVesion> dictList = (List<CourseVesion>)CacheUtils.get("courseVersionList");
		if (dictList==null){
			if(StringUtils.isNoneBlank(id)){
				CourseVesion a = new CourseVesion();
				a.setCourseId(id);
				a.setDelFlag("0");
				dictList = cousrseVersionDao.findList(a);
			}
			//CacheUtils.put("courseVersionList", dictList);
		}
		return dictList;
	}

	/**
	 * 根据版本id获得单个版本
	 * @param id
	 * @return
	 */
	public static CourseVesion getCourseVersionByVersionId(String id){
		@SuppressWarnings("unchecked")
		List<CourseVesion> dictList = (List<CourseVesion>)CacheUtils.get("courseVersionList");
		if (dictList==null){
			CourseVesion a = new CourseVesion();
			a.setId(id) ;
			a.setDelFlag("0");
			dictList = cousrseVersionDao.findList(a);
			//CacheUtils.put("courseVersionList", dictList);
		}
		for (CourseVesion courseVesion : dictList) {
			if (courseVesion.getId().equals(id)) {
				return courseVesion;
			}
		}
		return null;
//		CourseVesion courseVesion = cousrseVersionDao.get(id);
	}
	
	/**
	 * 获取所有题库列表
	 * @return
	 */
	public static List<CourseQuestionlib> getCourseQuestionlib() {
		@SuppressWarnings("unchecked")
		List<CourseQuestionlib> dictList = (List<CourseQuestionlib>)CacheUtils.get("courseQuestionlibList");
		if (dictList==null){
			CourseQuestionlib a = new CourseQuestionlib();
			a.setDelFlag("0");
			a.setSchoolId(UserUtils.getUser().getCompany().getId());
			dictList = courseQuestionlibDao.findList(a);
		//	CacheUtils.put("courseVersionList", dictList);
		}
		return dictList;
	}
	
	/**
	 * 根据版本id获取题库列表
	 * @return
	 */
	public static List<CourseQuestionlib> getCourseQuestionlibByVersionId(String id) {
		@SuppressWarnings("unchecked")
		List<CourseQuestionlib> dictList = (List<CourseQuestionlib>)CacheUtils.get("versionQuestionlibList");
		
		if (dictList==null){
			CourseQuestionlib a = new CourseQuestionlib();
			a.setVersionId(id);
			a.setDelFlag("0");
			dictList = courseQuestionlibDao.findList(a);
			//CacheUtils.put("versionQuestionlibList", dictList);
		}
		return dictList;
	}
	/**
	 *  根据版本id获取平台端题库列表
	 * @param id
	 * @return
	 */
	public static List<CourseQuestionlib> getCoursePlatformQuestionlibByVersionId(String id) {
		@SuppressWarnings("unchecked")
		List<CourseQuestionlib> dictList = (List<CourseQuestionlib>)CacheUtils.get("versionQuestionlibList");
		
		if (dictList==null){
			CourseQuestionlib a = new CourseQuestionlib();
			a.setVersionId(id);
			a.setDelFlag("0");
			a.setOwnerType("1"); //平台端题库
			dictList = courseQuestionlibDao.findList(a);
			//CacheUtils.put("versionQuestionlibList", dictList);
		}
		return dictList;
	}
	
	/**
	 * 根据学校id获取题库列表
	 * @return
	 */
	public static List<CourseQuestionlib> getCourseQuestionlibBySchoolId(String id) {
		@SuppressWarnings("unchecked")
		List<CourseQuestionlib> dictList = (List<CourseQuestionlib>)CacheUtils.get("schoolQuestionlibList");
		if (dictList==null){
			CourseQuestionlib a = new CourseQuestionlib();
			a.setVersionId(id);
			a.setDelFlag("0");
			dictList = courseQuestionlibDao.findList(a);
			//CacheUtils.put("schoolQuestionlibList", dictList);
		}
		return dictList;
	}
	
	/**
	 * 根据用户id获取题库列表
	 * @return
	 */
	public static List<CourseQuestionlib> getCourseQuestionlibByUserId(String id) {
		@SuppressWarnings("unchecked")
		List<CourseQuestionlib> dictList = (List<CourseQuestionlib>)CacheUtils.get("userQuestionlibList");
		if (dictList==null){
			CourseQuestionlib a = new CourseQuestionlib();
			a.setUser(new User(id));
			a.setDelFlag("0");
			dictList = courseQuestionlibDao.findList(a);
			//CacheUtils.put("schoolQuestionlibList", dictList);
		}
		return dictList;
	}
	
	/**
	 * 根据当前用户所在机构获取题库列表
	 * @return
	 */
	public static List<CourseQuestionlib> getCourseQuestionlibByCurrentUserCompanyId() {
		@SuppressWarnings("unchecked")
		List<CourseQuestionlib> dictList = (List<CourseQuestionlib>)CacheUtils.get("userCompanyQuestionlibList");
		if (dictList==null){
			CourseQuestionlib a = new CourseQuestionlib();
//			a.setUser(UserUtils.getUser());
			a.setSchoolId(UserUtils.getUser().getCompany().getId());
			a.setDelFlag("0");
			dictList = courseQuestionlibDao.findList(a);
			//CacheUtils.put("schoolQuestionlibList", dictList);
		}
		return dictList;
	}
	
	/**
	 * 根据题库id获得单个版本
	 * @param id
	 * @return
	 */
	public static CourseQuestionlib getCourseQuestionlibById(String id){
		@SuppressWarnings("unchecked")
		List<CourseQuestionlib> dictList = (List<CourseQuestionlib>)CacheUtils.get("courseQuestionlibList");
		if (dictList==null){
			CourseQuestionlib a = new CourseQuestionlib();
			a.setId(id) ;
			a.setDelFlag("0");
			dictList = courseQuestionlibDao.findList(a);
			//CacheUtils.put("courseVersionList", dictList);
		}
		for (CourseQuestionlib courseQuestionlib : dictList) {
			if (courseQuestionlib.getId().equals(id)) {
				return courseQuestionlib;
			}
		}
		return null;
//		CourseVesion courseVesion = cousrseVersionDao.get(id);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		//specialtyDao = (SpecialtyDao)applicationContext.getBean("specialtyDao");
		
	}
	
	/**
	 * 根据学校ID 查询该学校下的所有班级
	 */
	public static List<SchoolClass> getClassBySchoolId() {
		//当前登录人所在学校
		
		@SuppressWarnings("unchecked")
		List<SchoolClass> dictList = (List<SchoolClass>)CacheUtils.get("classListBySchoolID");
		if(null == dictList){
			if(UserUtils.getUser()!=null&& UserUtils.getUser().getCompany()!=null && StringUtils.isNotBlank(UserUtils.getUser().getCompany().getId())){
				SchoolClass schoolClass = new SchoolClass();
				schoolClass.setSchool(new Office(UserUtils.getUser().getCompany().getId()));
				dictList = schoolClassDao.getClassBySchoolId(schoolClass);
			}
		}
		//CacheUtils.put("classListBySchoolID", dictList);
		return dictList;
	}
	/**
	 * 根据学校ID 查询该学校中专业下的所有班级
	 */
	public static List<SchoolClass> getClassBySpecTitle(String specTitle) {
		//当前登录人所在学校
		
		@SuppressWarnings("unchecked")
		List<SchoolClass> dictList = (List<SchoolClass>)CacheUtils.get("classListBySchoolID");
		if(null == dictList){
			if(UserUtils.getUser()!=null&& UserUtils.getUser().getCompany()!=null && StringUtils.isNotBlank(UserUtils.getUser().getCompany().getId())){
				SchoolClass schoolClass = new SchoolClass();
				schoolClass.setSchoolId(UserUtils.getUser().getCompany().getId());
				schoolClass.setSpecialtyTitle(specTitle);
				dictList = schoolClassDao.findSchoolClassBySpecTitle(schoolClass);
			}
		}
		//CacheUtils.put("classListBySchoolID", dictList);
		return dictList;
	}
	
	
	/**
	 * 根据教师id查询班级
	 */
	public static List<Exam> getCourseQuestionlibByTeacherId(){
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		
		@SuppressWarnings("unchecked")
		List<Exam> dictList = (List<Exam>)CacheUtils.get("userQuestionlibList");
		if(dictList==null){
			dictList=examDao.getClass(userId,versionId);
		}
		return dictList;
	}
	/**
	 * 查询该随堂测试对应的班级
	 */
	/*public static List<TableClass> getClassByExamId(String examId){
		return examDao.getTableClassByExamId(examId);
	}*/
/*	public static List<Exam> getCourseQuestionlibByTeacherId(){
		@SuppressWarnings("unchecked")
		List<Exam> dictList = (List<Exam>)CacheUtils.get("userQuestionlibList");
		//cookie中的教师id以及版本id
		String userId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("userId");
		String versionId=TearcherUserUtils.getTeacherIdAndCourseVersionId().get("versionId");
		if(dictList==null){
			if(StringUtils.isNoneBlank(userId)&&StringUtils.isNoneBlank(versionId)){
				dictList=examDao.getClass(userId,versionId);
			}
		}
		return dictList;
	}*/
	/**
	 * 根据id查询班级
	 * 
	 */
/*	public static SchoolClass getClassByID(String id) {
		@SuppressWarnings("unchecked")
		List<SchoolClass> dictList = (List<SchoolClass>)CacheUtils.get("specialList");
		if(null == dictList){
			SchoolClass schoolClass = new SchoolClass();
			schoolClass.setCompanyId(UserUtils.getUser().getCompany().getId());
			schoolClass.setDelFlag("0");
			dictList = schoolClassDao.findList(schoolClass);
		}
		for (SchoolClass schoolClass : dictList) {
			if(schoolClass.getId().equals(id)){
				return schoolClass;
			}
		}
		return null;
	}*/
	
	/**
	 * 查询这个学校没有拥有所有版本的课程
	 * @return
	 */
	public static List<Course> getSchoolNotCourse(){
		@SuppressWarnings("unchecked")
		List<Course> dictList = (List<Course>)CacheUtils.get("schoolNotCourseList");
		if(null == dictList){
			//查询这个学校没有拥有所有版本的课程
			dictList = sCourseVersionDao.getSchoolNotCourse(UserUtils.getUser().getCompany().getId());
		}
		return dictList;
	}

	/**
	 * 根据id查询学校（机构）
	 * 
	 */
	public static Office getOfficeById(String id) {
		@SuppressWarnings("unchecked")
		List<Office> dictList = (List<Office>)CacheUtils.get("officeList");
		if(null == dictList){
			Office office = new Office();
			office.setDelFlag("0");
			dictList = officeDao.findList(office);
		}
		for (Office office : dictList) {
			if(office.getId().equals(id)){
				return office;
			}
		}
		return null;
	}
	/**
	 * 查询机构类型的Office type=1:机构，type=2:部门
	 * 
	 */
	public static List<Office> getOfficeByType(String type) {
		@SuppressWarnings("unchecked")
		List<Office> dictList = (List<Office>)CacheUtils.get("officeList");
		List<Office> officeTypeList = Lists.newArrayList();
		if(null == dictList){
			Office office = new Office();
			office.setDelFlag("0");
			dictList = officeDao.findList(office);
		}
		for (Office office : dictList) {
			if(office.getType().equals(type)){
				officeTypeList.add(office);
			}
		}
		return officeTypeList;
	}
	/**
	 * 查询教师下的所有模板
	 */
	public static List<Examination> getExample(){
		Examination exam=new Examination();
		Map<String,String> map=TearcherUserUtils.getTeacherIdAndCourseVersionId();
		exam.setTeacher(new User(map.get("userId")));
		exam.setVersion(new CourseVesion(map.get("versionId")));
		return examinationDao.findExam(exam);
	}
	
	
	
	public static Object getStaticDTOValue(String dictValue,StaticDTO obj){
		
		/*Map<String,Object> map=Maps.newHashMap();  
		for(int i=0;i<len;i++){*/
			try {
				//map.put(i+"",);
				
				return PropertyUtils.getProperty(obj, "count"+dictValue);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
			/*}
		
		return map.get(dictValue);*/
	  
	}
	
	public static List<TableClass> getClassbyExamId(String examId){
		 List<TableClass> tableClassByExamId = examDao.getTableClassByExamId(examId);
		 return tableClassByExamId;
	}
	
	
	/**
	 * 得到申请入平台的题库
	 * @param quesImportId
	 * @return
	 */
	public static TableQuestionlibImportApply getQuesImportApply(String quesImportId){
		
		List<TableQuestionlibImportApply>  list=quesImportApplyService.findQuesImportApplys();
		
		for(TableQuestionlibImportApply item:list){
			if(item.getQueslibImportId().equals(quesImportId)){
				return item;
			}
		}
		
		return new TableQuestionlibImportApply();
	}
	
	public static List<SchoolQuestionlib> getQuestionLibs(){
		return schoolQuestionLibDao.getQuestionLibs();
	}
	/**
	 * 当前登录者所在机构的题库
	 * @return
	 */
	/*public static List<SchoolQuestionlib> getQuestionLibsByLoginUser(){
		
		SchoolQuestionlib entity=new SchoolQuestionlib();
		entity.setSchoolId(UserUtils.getUser().getCompany().getId());
		return schoolQuestionLibDao.findList(entity);
	}*/
	
	
     /********平台端、学校端 测评试卷查询课程、版本 开始 **********/
	
	/**
	 * 专业下课程集合
	 */
	public static List<Course> findCourseBySpecialtyId(String specialtyId) {
		@SuppressWarnings("unchecked")
		List<Course> dictList = (List<Course>)CacheUtils.get("courseList");
		if (dictList==null){
			if(StringUtils.isNotBlank(specialtyId)){
				User user=UserUtils.getUser();
				String userType=user.getUserType();
				
				if("1".equals(userType)||StringUtils.isBlank(userType)){
					dictList=null;
				}else if("2".equals(userType)){
					Course a = new Course();
					a.setSpecialtyId(specialtyId);
					a.setDelFlag("0");
					dictList = cousrseDao.findList(a);
				}else{
					dictList=Lists.newArrayList();
					List<Map<String,Object>> list=sCourseVersionService.findSchoolCourseBySpecialtyId(specialtyId);
					for(Map<String,Object> map:list){
						Course c=new Course();
						Reflections.setFieldValue(c,"id", map.get("id"));
						Reflections.setFieldValue(c,"title", map.get("name"));
						dictList.add(c);
					}
				}
			
			}
			//CacheUtils.put("courseList", dictList);
		}
		return dictList;
	}
	
	/**
	 * 单条课程版本下的版本列表
	 * @param id
	 * @return
	 */
	public static List<CourseVesion> findCourseVersionByCourseId(String id) {
		@SuppressWarnings("unchecked")
		List<CourseVesion> dictList = (List<CourseVesion>)CacheUtils.get("courseVersionList");
		if (dictList==null){
			if(StringUtils.isNoneBlank(id)){
				
				User user=UserUtils.getUser();
				String userType=user.getUserType();
				
				if("1".equals(userType)||StringUtils.isBlank(userType)){ //系统管理员
					dictList=null;
				}else if("2".equals(userType)){//化工社用户
					
					CourseVesion a = new CourseVesion();
					a.setCourseId(id);
					a.setDelFlag("0");
					dictList = cousrseVersionDao.findList(a);
					
				}else{ //学校用户
					dictList=Lists.newArrayList();
					List<Map<String,Object>> list=sCourseVersionService.findSchoolCourseVersionByCourseId(id);
					for(Map<String,Object> map:list){
						CourseVesion c = new CourseVesion();
						Reflections.setFieldValue(c,"id", map.get("id"));
						Reflections.setFieldValue(c,"title", map.get("name"));
						dictList.add(c);
					}
				}
			}
			//CacheUtils.put("courseVersionList", dictList);
		}
		return dictList;
	}
	
	 /********平台端、学校端 测评试卷 查询课程、版本 结束 **********/
}
