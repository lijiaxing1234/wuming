package com.thinkgem.jeesite.modules.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.entity.Questions;
import com.thinkgem.jeesite.modules.web.service.AppIndexService;
import com.thinkgem.jeesite.modules.web.service.CourseWebService;
import com.thinkgem.jeesite.modules.web.util.GlobalConfigUtils;


/** 
 * Spring动态周期定时任务<br> 
 * 在不停应用的情况下更改任务执行周期 
 */  
@Lazy(false)
@Component
public class DynamicCronTask implements SchedulingConfigurer {

	
	/**
	 * 每日一练任务执行周期表达式
	 */
	private static final  String scheduled="dailypractice.scheduled";
	
	@Autowired
	AppIndexService appIndexService;
	
	int i=0;
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				
				 // 任务逻辑    
		        System.out.println("第"+(i)+"次开始执行操作... " +"时间：【" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date()) + "】"); 
		    
				appIndexService.initeDailyPracticeProperties();
			
				i++;
				
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				// 任务触发，可修改任务的执行周期
				String cron=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, scheduled);
				CronTrigger trigger = new CronTrigger(cron);
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		}); 
	}
	
}


