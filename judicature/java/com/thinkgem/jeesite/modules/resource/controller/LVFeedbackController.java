package com.thinkgem.jeesite.modules.resource.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.resource.entity.LVFeedback;
import com.thinkgem.jeesite.modules.resource.service.LVFeedbackService;

@Controller
@RequestMapping(value ="${adminPath}/admin/LVFeedback")
public class LVFeedbackController  extends BaseController {
	
	private static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@Autowired
	LVFeedbackService  feedBackService;
	
	@Autowired
	StudentService  studentService; //用户
	
	
	 @RequestMapping({ "list", "" })
     public String feedBackList(LVFeedback feedback, Model model, String startDate, String endDate,HttpServletRequest request,HttpServletResponse response) {
	        final Page<LVFeedback> page = new Page<LVFeedback>(request, response);
	        Date startTime = null;
	        Date endTime = null;
	        try {
	            startTime = format.parse(String.valueOf(startDate) + " 00:00:00");
	        }
	        catch (Exception ex2) {}
	        try {
	            endTime = format.parse(String.valueOf(endDate) + " 23:59:59");
	        }
	        catch (Exception ex3) {}
	        
	        Long count = this.feedBackService.getFeedBackCount(feedback, startTime, endTime);
	        page.setCount(count);
	        if (count > 0) {
	            final List<LVFeedback> feedBackList = this.feedBackService.getFeedBackList(feedback, startTime, endTime, page.getFirstResult(), page.getMaxResults());
	            try {
	                for (final LVFeedback fb : feedBackList) {
	                   List<Student>  list= studentService.selectByStudent(new Student(fb.getUserId()));
	                    if (list != null && list.size() >0) {
	                    	Student student=list.iterator().next();
	                        fb.setPhone(student.getStdPhone());
	                        fb.setUserName(student.getName());
	                    }  
	                }
	            }
	            catch (Exception ex) {
	                ex.printStackTrace();
	            }
	            
	            page.setList(feedBackList);
	            model.addAttribute("page",page);
	        }
	        model.addAttribute("feedback",feedback);
	        model.addAttribute("startDate",startDate);
	        model.addAttribute("endDate",endDate);
	        return "modules/feedback/feedbackList";
	    }
	
	

}
