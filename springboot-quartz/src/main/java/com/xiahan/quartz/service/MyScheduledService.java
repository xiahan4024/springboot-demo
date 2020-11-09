package com.xiahan.quartz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xiahan.quartz.util.MyScheduled;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2019年10月31日 下午10:25:59
 * @describe 直接使用@Scheduled， 简单。
 *            缺点：不是动态改变，是单线程
 *            启动类添加 @EnableScheduling，该类添加@Component
 */
@Slf4j
@Component
public class MyScheduledService {
	
	@Autowired
	MyScheduled myScheduled;
	
	/**
	 * 	测试 spring 自带的定时任务
	 * @author xiahan
	 * @dateTime 2019年10月31日 下午10:44:17
	 * @param
	 */
	@Scheduled(cron = "*/2 * * * * ?")
	public void testSpringScheduled() {
		try {
			myScheduled.testScheduled("xiahan");
		} catch (Exception e) {
			log.info("测试定时任务出现异常");
		}
	}
	
}
