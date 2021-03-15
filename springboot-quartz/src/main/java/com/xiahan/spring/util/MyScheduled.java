package com.xiahan.spring.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @Auther: xiahan
 * @Date: 2021/3/14 17:46
 * @Description:
 */
@Slf4j
@Component
public class MyScheduled {
    /**
     * 	这里可以查看到：
     * 		1. 当一个任务延迟，后一个任务会等上一个任务完成后再执行(默认是单线程，可以使用多线程技术)
     * 		2. 出现异常不会影响到后面的定时任务，只是当前会受影响
     * @author xiahan
     * @dateTime 2019年10月31日 下午10:38:52
     * @param @param name
     * @throws Exception
     */
    public void testScheduled(String name) throws Exception {
				Thread.sleep(5000);
        double random = Math.random();
//		if(random > 0.5) {
//			try {
//			} catch (Exception e) {
//				log.info("这里可以查看到（默认单线程），当一个任务延迟，后一个任务会等上一个任务完成后再执行");
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
