/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.CourseVesionDao;
import com.thinkgem.jeesite.modules.questionlib.entity.ClassCourseListVo;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.teacher.dto.ClassVesionCourseDTO;

/**
 * 课程版本Service
 * @author webcat
 * @version 2016-08-16
 */
@Service
@Transactional(readOnly = true)
public class CourseVesionService extends CrudService<CourseVesionDao, CourseVesion> {
	@Autowired
	CourseVesionDao courseVesionDao;
	public CourseVesion get(String id) {
		return super.get(id);
	}
	
	public List<CourseVesion> findList(CourseVesion courseVesion) {
		return super.findList(courseVesion);
	}
	
	public Page<CourseVesion> findPage(Page<CourseVesion> page, CourseVesion courseVesion) {
		return super.findPage(page, courseVesion);
	}
	
	@Transactional(readOnly = false)
	public void save(CourseVesion courseVesion) {
		super.save(courseVesion);
	}
	
	@Transactional(readOnly = false)
	public void delete(CourseVesion courseVesion) {
		super.delete(courseVesion);
	}
	
	public List<ClassVesionCourseDTO> getCourseVesionDaoByUserId(String userID) {
		/*第一步：根据教师id获取版本id 
		第二步：根据版本id获取课程id
		第三步：根据课程id获取课程详情 */
		List<ClassVesionCourseDTO> list=new ArrayList<ClassVesionCourseDTO>();
		
		List<ClassCourseListVo> courseVesionByUserId = courseVesionDao.getCourseVesionByUserId(userID);
		for (ClassCourseListVo classCourseListVo : courseVesionByUserId) {
			if(classCourseListVo!=null&&!(("").equals(classCourseListVo))&&(classCourseListVo.getVersionId()!=null&&!(("").equals(classCourseListVo.getVersionId())))){
				CourseVesion courseVesion = courseVesionDao.getCourseIdByVesionId(classCourseListVo.getVersionId());
				if(courseVesion!=null&&!(("").equals(courseVesion))&&(courseVesion.getCourseId()!=null&&!(("").equals(courseVesion.getCourseId())))){
					Course courseByCourseId = courseVesionDao.getCourseByCourseId(courseVesion.getCourseId());
					//存放数据
					ClassVesionCourseDTO classVesionCourseDTO=new ClassVesionCourseDTO();
					classVesionCourseDTO.setCourseVesion(courseVesion);
					classVesionCourseDTO.setCourse(courseByCourseId);
					if(list.isEmpty()){
						list.add(classVesionCourseDTO);
					}else{
						int j=0;
						 for(int i = 0; i < list.size(); i++){  
					            ClassVesionCourseDTO classVesionCourseDTO2 = list.get(i); 
					            CourseVesion courseVesion2 = classVesionCourseDTO2.getCourseVesion();//获取版本号，存在则不放入list中，不存在则放进list中
					            if(StringUtils.isNotBlank(courseVesion2.getId())&&StringUtils.isNotBlank(courseVesion.getId())){
					            	if((courseVesion2.getId().equals(courseVesion.getId()))){
					            		j++;
					            	}
					            }
					           /* if(!courseVesion2.equals(courseVesion)){
									list.add(classVesionCourseDTO);
								}*/
					        }
						 if(j==0){
							 list.add(classVesionCourseDTO);
						 }
						 //抛异常
						/*for (ClassVesionCourseDTO classVesionCourseDTO2 : list) {
							System.out.println(list.size());
							System.out.println(classVesionCourseDTO2);
							CourseVesion courseVesion2 = classVesionCourseDTO2.getCourseVesion();//获取版本号，存在则不放入list中，不存在则放进list中
							if(!courseVesion2.equals(courseVesion)){
								list.add(classVesionCourseDTO);
							}
						}*/
					}
				}
			}
		}
		return list;
	}

	public CourseVesion getVersionByVersionCode(CourseVesion courseVesion) {
		return courseVesionDao.getVersionByVersionCode(courseVesion);
	}

	public List<CourseVesion> findListBySpecialtyId(CourseVesion courseVesion) {
		return courseVesionDao.findListBySpecialtyId(courseVesion);
	}
	
	
	public Integer countCourseVesionByCourseId(String courseId){
		
		CourseVesion courseVesion=new CourseVesion();
		
		courseVesion.setCourseId(courseId);
		
		return courseVesionDao.countCourseVesionByCourseId(courseVesion);
	}
	
	
}