/*package com.thinkgem.jeesite.modules.bs.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.bs.service.StatisticsService;

@Component
@Lazy(false)
public class ReportMonthTask {
	
	@Autowired
	private StatisticsService service;
	
	@Scheduled(cron="0 0 2 1 * ?")  // 0 15 10 L * ? 每月最后一天的10点15分触发 
	//@Scheduled(cron="59 59 23  * * ?")   //每晚上23:29:59执行一次  
	//@Scheduled(cron="0/5 * *  * * ?") //每5秒执行一次  
	public void ExpireList() {
		System.out.println("********报表统计———开始******");
		//报表统计
		service.findReport();
		
		System.out.println("********报表统计———结束******");
	}
	
}
*/