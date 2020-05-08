/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SendMailUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.Teacher;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.questionlib.service.TeacherService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.EmailUtils;

 
@Controller
@RequestMapping(value = "/pass")
public class PasswordController extends BaseController {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private StudentService studentService;
  
	@RequestMapping(value = "form")
	public String form(String loginName,String type,String schoolId,Model model, RedirectAttributes redirectAttributes) {
		int random=(int)(Math.random()*1000000); 
		String message ="您发起了找回密码申请，验证码为：" + random +"  请不要提供给他人。" ;
		
		model.addAttribute("okFlag",false);
		if("1".equals(type)||"3".equals(type))  //管理员/教师找回密码
		{
			
			CacheUtils.remove("userCache", "ln" + loginName);
			
			User user= systemService.getUserByLoginName(loginName);
		    if (user==null) {
		    	model.addAttribute("message", "用户不存在！");
		    	
		    	 
			}else
			{
				 // 生成随机码   并发送邮件 
				
				//保存随机码 
				if(StringUtils.isNotBlank(user.getEmail()))
				{
					String em= "";
		    		try {
		    			em=user.getEmail();
		    			em =em.substring(em.indexOf("@"));
		    			
					} catch (Exception e) {
						// TODO: handle exception
					}
		    		
					
					EmailUtils.put(user.getId(), ""+random);
					SendMailUtil.sendCommonMail(user.getEmail(), "找回密码验证码", message);
					 
					
					model.addAttribute("message", "验证码已经发送到您的 "+em+"邮箱！请收邮件,取得验证码，填入下面验证码框内");
					model.addAttribute("loginName", loginName);
					
					model.addAttribute("okFlag",true);
				}else
				{
					model.addAttribute("message", "用户没有登记邮箱，不能找回密码！");
			    	 
				}
				
				
			}
		}
		if("2".equals(type))  //学生找回密码
		{
			Student student = new Student();
			student.setStdCode(loginName);
			student.setSchoolId(schoolId);
			student = studentService.getStudentByStudentCode(student);
		    
		    if(student==null)
		    {
		    	model.addAttribute("message", "用户不存在！");
		    	 
		    }else
		    {
		    	 // 生成随机码   并发送邮件 
		    	//保存随机码 
		    	
		    	if(StringUtils.isNotBlank(student.getStdEmail()))
		    	{
		    		String em= "";
		    		try {
		    			em=student.getStdEmail();
		    			em =em.substring(em.indexOf("@"));
		    			
					} catch (Exception e) {
						// TODO: handle exception
					}
		    		
			    	EmailUtils.put(student.getId(), ""+random);
					SendMailUtil.sendCommonMail(student.getStdEmail(), "找回密码验证码", message);
					model.addAttribute("message", "已经行您 "+em+"邮箱发送验证邮件！请收邮件,然后取得验证码，填入下面验证码框内");
			    	model.addAttribute("okFlag",true);
		    	}else
		    	{
		    		model.addAttribute("message", "用户没有登记邮箱，不能找回密码！");
		    		 
		    	}
		    	model.addAttribute("loginName", loginName);
		    }
		    
		}
		
		 model.addAttribute("type", type);
		return "modules/sys/passwordForm";
	}
	
	 
	@RequestMapping(value = "save")
	public String save(String loginName,String code ,String password,String type, Model model, RedirectAttributes redirectAttributes) {
		
		model.addAttribute("okFlag",false);
		if("1".equals(type))
		{
			User user= systemService.getUserByLoginName(loginName);
		    if (user==null) {
		    	model.addAttribute("message", "用户不存在！");
		    	 
			}else
			{
				
			  String key = EmailUtils.get(user.getId()) ;
			  if(StringUtils.isNotBlank(key)&&key.equals(code))
			  {
				  //验证通过  可以修改密码了
			 
				  user.setPassword(SystemService.entryptPassword(password)); //密码加密
				  systemService.saveUser(user);
				  model.addAttribute("message", "密码修成功，请重新登录！");
				  CacheUtils.remove("userCache", "ln" + loginName);
			  }else {
				  model.addAttribute("message", "验证码不正确！");
					model.addAttribute("okFlag",true);
			}
				 
			}
		}
		
		if("3".equals(type))
		{
			Teacher user  =new Teacher();
			user.setLoginName(loginName);
			
			List<Teacher> list=teacherService.findList(user);
			
		//	User user= systemService.getUserByLoginName(loginName);
		    if (list==null ||list.size()==0) {
		    	model.addAttribute("message", "用户不存在！");
		    	 
			}else
			{
				user =list.get(0);
			  String key = EmailUtils.get(user.getId()) ;
			  if(StringUtils.isNotBlank(key)&&key.equals(code))
			  {
					  //验证通过  可以修改密码了
				 try {
					 user.setPassword(SystemService.entryptPassword(password)); //密码加密
					  teacherService.save(user);
					  model.addAttribute("message", "密码修成功，请重新登录！");
					 
				} catch (Exception e) {
					 model.addAttribute("message", "找回密码失败！");
						model.addAttribute("okFlag",true);
				}
				 EmailUtils.remove(user.getId());
			  }else {
				  model.addAttribute("message", "验证码不正确！");
					model.addAttribute("okFlag",true);
			}
				 
			}
		    
		}
		
		if("2".equals(type))
		{
			Student student = new Student();
			student.setStdCode(loginName);
			student = studentService.getStudentByStudentCode(student);
		    
		    if(student==null)
		    {
		    	model.addAttribute("message", "用户不存在！");
		    	 
		    }else
		    { 
		    	String key = EmailUtils.get(student.getId()) ;
				  if(StringUtils.isNotBlank(key)&&key.equals(code))
				  {
					  //验证通过  可以修改密码了
					  
					try {
						  student.setStdPassword(SystemService.entryptPassword(password)); //密码加密
						  studentService.save(student);
						  model.addAttribute("message", "密码修成功，请重新登录！");
						 
					} catch (Exception e) {
						 model.addAttribute("message", "找回密码失败！");
							model.addAttribute("okFlag",true);
					}
					  EmailUtils.remove(student.getId());
				  }else {
					  model.addAttribute("message", "验证码不正确！");
						model.addAttribute("okFlag",true);
				}
				
		    }
		}
		
		model.addAttribute("loginName", loginName);
		model.addAttribute("type", type);
		model.addAttribute("code", code);
		return "modules/sys/passwordForm";
	}
	
	 
 
}
