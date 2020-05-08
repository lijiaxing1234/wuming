package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.ClassCourseListVo;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 老师DAO接口
 * @author .36
 *
 */
@MyBatisDao
public interface GCCVTTDao extends CrudDao<User>{

	List<ClassCourseListVo> classCourseList(Map<String, String> paraMap);

	void deleteClassCourse(Map<String, String> paraMap);

	ClassCourseListVo getClassCourseListVo(String id);

	void insertCCTT(List<ClassCourseListVo> paraList);

	List<String> getGivedClassId(String teacherId);

	List<SchoolClass> getNotClass(Map<String, Object> paraMap);

	List<Course> getAllCourse(String schoolId);

	List<CourseVesion> getAllVersionByCourseId(String courseId);

	List<String> getClassIdsList(Map<String, String> paraMap);

	List<SchoolClass> getClassList(Map<String, Object> paraMap2);

	List<SchoolClass> getAllClass(@Param("schoolId")String schoolId,@Param("spectitle")String spectitles);

}
