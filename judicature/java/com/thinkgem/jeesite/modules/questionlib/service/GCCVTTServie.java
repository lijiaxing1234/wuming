package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.dao.GCCVTTDao;
import com.thinkgem.jeesite.modules.questionlib.entity.ClassCourseListVo;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = false)
public class GCCVTTServie extends CrudService<GCCVTTDao, User>{
	
	@Autowired
	private GCCVTTDao gccvttDao;
	

	/**
	 * 根据老师的id查询老师已经分配的课程和班级
	 * @param teacherId
	 * @return
	 */
	public List<ClassCourseListVo> classCourseList(String teacherId) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("teacherId", teacherId);
		return gccvttDao.classCourseList(paraMap);
	}


	public void deleteClassCourse(String id) {
		Map<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("id", id);
		gccvttDao.deleteClassCourse(paraMap);
	}


	public ClassCourseListVo getClassCourseListVo(String id) {
		return gccvttDao.getClassCourseListVo(id);
	}


	public void insertCCTT(String teacherId, String classIds, String courseId, String versionId) {
		//inset into table_teacher_class_course values (id,teacher_id,class_id,course_id);
		String[] arrClassIds = classIds.split(",");
		List<ClassCourseListVo> paraList = new ArrayList<ClassCourseListVo>();
		for (String classId : arrClassIds) {
			ClassCourseListVo vo = new ClassCourseListVo();
			String id = UUID.randomUUID().toString().replace("-", "");
			vo.setId(id);
			vo.setTeacherId(teacherId);
			vo.setClassId(classId);
			vo.setCourseId(courseId);
			vo.setVersionId(versionId);
			paraList.add(vo);
		}
		gccvttDao.insertCCTT(paraList);
	}

	/**
	 * 查询老师所在学校并未分配过的班级
	 * @param teacherId
	 * @return
	 */
	public List<SchoolClass> getTeacherNotClass(String teacherId) {
		//查询已经分配的班级 select class_id from table_teacher_class_course where teacher_id = #{teacherId}
		List<String> classIdList = gccvttDao.getGivedClassId(teacherId);
		//查询这个学校下未给这个老师分配的班级 select id,title from table_class where company_id=#{schoolId} and id not in 
		String schoolId = UserUtils.getUser().getCompany().getId();
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("schoolId", schoolId);
		paraMap.put("classIdList", classIdList);
		return gccvttDao.getNotClass(paraMap);
	}


	public List<Course> getAllCourse() {
		String schoolId = UserUtils.getUser().getCompany().getId();
		//select id,title from table_course a join table_school_course b on a.id=b.course_id left join sys_office c on b.school_id=c.company_id where c.company_id=#{schoolId}
		return gccvttDao.getAllCourse(schoolId);
	}


	public List<CourseVesion> getAllVersionByCourseId(String courseId) {
		// select id,title from table_course_vesion where course_id=#{courseId}
		
		return gccvttDao.getAllVersionByCourseId(courseId);
	}


	/**
	 * 学校管理员进行分配课程班级版本的操作
	 * 查询学校下的班级
	 * 已知  哪个老师 哪个课程
	 * 
	 * 
	 * 查询学校下的班级
	 * 这个班级没有被分配这个学校下的某个课程
	 * @param courseId
	 * @param teacherId 
	 * @param versionId 
	 * @param spectitles 学校专业名称
	 * @return
	 */
	public List<SchoolClass> getNotGiveCourseClass(String courseId, String teacherId, String versionId,String spectitles) {
		//某老师已经分配了该课程的班级的id select class_id from table_teacher_class_course where course_id=#{courseId} and version_id = #{versionId}	
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("courseId", courseId);
		paraMap.put("versionId", versionId);
		List<String> classIdList = gccvttDao.getClassIdsList(paraMap);
		//若结果集未空则查询这个学校下的所有班级 select * from table_class where del_flag='0' and company_id=#{schoolId}
		if(classIdList.isEmpty()){
			String schoolId = UserUtils.getUser().getCompany().getId();
			return gccvttDao.getAllClass(schoolId,spectitles);
		}else{
			//查询班级不在这些班级id中的班级 select * from table_class where del_flag='0' and id not in ()
			Map<String,Object> paraMap2 = new HashMap<String,Object>();
			paraMap2.put("classIdList", classIdList);
			paraMap2.put("spectitles", spectitles);
			paraMap2.put("companyId", UserUtils.getUser().getCompany().getId());
			return gccvttDao.getClassList(paraMap2);
		}
	}






}
