package com.xiahan.quartz.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月7日 下午2:33:04
 * @describe 实现接口SchedulingConfigurer，可动态改变。
 *            多线程，从控制台可以看出，第一个定时任务和第二个定时任务互不影响；
 *            并且，由于开启了多线程，第一个任务的执行时间也不受其本身执行时间的限制，所以需要注意可能会出现重复操作导致数据异常。
 *            启动类添加 @EnableScheduling @EnableAsync，该类添加 @Component
 */
@Slf4j
@Component
public class MyScheduledWImplAsyncService {
    
    @Async
    @Scheduled(fixedDelay = 1000)  //间隔1秒
    public void first() throws InterruptedException {
        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
        Thread.sleep(1000 * 10);
    }

    @Async
    @Scheduled(fixedDelay = 2000)
    public void second() {
        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
    }

}
