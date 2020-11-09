package com.xiahan.quartz.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 	spring 自带的定时任务。
 * 		可执行一些简单的任务
 * @author xiahan
 * @dateTime 2019年10月31日 下午10:22:42
 */
//@Slf4j
//@Component
public class MyScheduled {
	
	/**
	 * 	这里可以查看到：
	 * 		1. 当一个任务延迟，后一个任务会等上一个任务完成后再执行
	 * 		2. 出现异常不会影响到后面的定时任务，只是当前会受影响
	 * @author xiahan
	 * @dateTime 2019年10月31日 下午10:38:52
	 * @param @param name
	 * @throws Exception 
	 */
	public void testScheduled(String name) throws Exception {
		double random = Math.random();
//		if(random > 0.5) {
//			try {
//				Thread.sleep(5000);
//			} catch (Exception e) {
//				log.info("这里可以查看到，当一个任务延迟，后一个任务会等上一个任务完成后再执行");
//			}
//		}
		
//		if(random > 0.5) {
//			log.info("出现异常不会影响到后面的定时任务，只是当前会受影响");
//			throw new Exception();
//		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
		System.out.println("来了，老弟：" + name + "		" + LocalDateTime.now(ZoneOffset.of("+8")).format(formatter));
	}

}
