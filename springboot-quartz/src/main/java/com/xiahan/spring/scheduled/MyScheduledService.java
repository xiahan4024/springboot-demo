package com.xiahan.spring.scheduled;

import com.xiahan.spring.util.MyScheduled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Auther: xiahan
 * @Date: 2021/3/14 17:44
 * @Description:
 *     最简单的方式。适合简单的任务
 */
@Slf4j
@Component
public class MyScheduledService {

    @Autowired
    MyScheduled myScheduled;

    @Async("myTaskExecutor")
    @Scheduled(cron = "*/2 * * * * ?")
    public void testSpringScheduled() {
        try {
            myScheduled.testScheduled("xiahan");
        } catch (Exception e) {
            log.info("测试定时任务出现异常");
        }
    }

    @Scheduled(fixedDelay = 1000)  //间隔1秒
    public void first() throws InterruptedException {
        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
        Thread.sleep(1000 * 10);
    }

    @Async("myTaskExecutor")
    @Scheduled(fixedDelay = 1000)
    public void second() {
        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
    }

}
