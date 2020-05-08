package com.thinkgem.jeesite.modules.sys.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.WebApplicationContext;

import com.thinkgem.jeesite.modules.student.utils.OnlineStudentUtils;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener{
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}

	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		//System.out.println("*****************************************创建全局唯一的实列");
		OnlineStudentUtils.getIntance();
	}
	
	
	
	
	

	/*@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		System.out.println("******************************************* sessionCreated：创建");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		
		System.out.println("******************************************* sessionCreated：销毁");
	   
		Enumeration<String> attributeKey= httpSessionEvent.getSession().getAttributeNames();
		
	    while(attributeKey.hasMoreElements()){
	    	if(OnlineStudentUtils.SESSION_STUDENT_LOGIN_KEY.equals(attributeKey.nextElement())){
	    		OnlineStudentUtils.getIntance().remove(httpSessionEvent.getSession().getAttribute(OnlineStudentUtils.SESSION_STUDENT_LOGIN_KEY));
	    	}
	    }
		
	}*/
	
	/*@Override
	public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
		
		System.out.println("******************************************* attributeAdded:sessionAttribute 执行了");
		
		
		String  key=httpSessionBindingEvent.getName();
		Object value=httpSessionBindingEvent.getValue();
		
	    if(SESSION_STUDENT_LOGIN_KEY.equals(key)){
	        @SuppressWarnings("unchecked")
		    Vector<Object> Vector=(Vector<Object>) httpSessionBindingEvent.getSession().getServletContext().getAttribute(APPLICATE_KEY);
		    if(Vector !=null){
			   Vector.add(value);
		    }
		   httpSessionBindingEvent.getSession().getServletContext().setAttribute(APPLICATE_KEY, Vector);
	    }	
	}

	
	@Override
	public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
		
		System.out.println("******************************************* attributeRemoved 执行了");
		
		String  key=httpSessionBindingEvent.getName();
		Object value=httpSessionBindingEvent.getValue();
		
	    if(SESSION_STUDENT_LOGIN_KEY.equals(key)){
	    	@SuppressWarnings("unchecked")
		    Vector<Object> Vector=(Vector<Object>) httpSessionBindingEvent.getSession().getServletContext().getAttribute(APPLICATE_KEY);
		    if(Vector !=null){
			   Vector.remove(value);
		    }
		   httpSessionBindingEvent.getSession().getServletContext().setAttribute(APPLICATE_KEY, Vector);
	    }	
	}
	
	@Override
	public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
		System.out.println("******************************************* attributeReplaced 执行了");
	}*/
}
