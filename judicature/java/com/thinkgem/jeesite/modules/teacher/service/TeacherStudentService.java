package com.thinkgem.jeesite.modules.teacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.service.SchoolService;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly=true)
public class TeacherStudentService  {

	@Autowired
	StudentService studentService;
	@Autowired
	SchoolService  schoolService;
	
	
	@Transactional(readOnly=false)
	public void addStudent(Page<Student> page, Student student){
		//TODO
		//1 、获得老师  
		//UserUtils.getUser();
		//studentService.findPage(page, student);
	}
	
}
