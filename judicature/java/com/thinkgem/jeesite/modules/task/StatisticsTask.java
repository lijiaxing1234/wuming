package com.thinkgem.jeesite.modules.task;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


/**
 * 数据统计
 * 定时调用远程接口
 */
@Component
@Lazy(false)
public class StatisticsTask {

	/*@Autowired
	private ExamService examService;*/
	
	//@Scheduled(cron="59 59 23  * * ?")   //每晚上23:29:59执行一次 
	
     /**
	 *任务线程启动
	 */
	
	/*@Scheduled(cron="0 0 0/2  * * ?") //每两个小时执行一次 
	public void run() {
		System.out.println("清除多余数据开始"+new Date());
		examService.batchRemove(); //删除两个小时之前的数据
		System.out.println("清除多余数据结束");
		
	}*/

}
