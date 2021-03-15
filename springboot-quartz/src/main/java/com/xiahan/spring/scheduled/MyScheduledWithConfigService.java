package com.xiahan.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @Auther: xiahan
 * @Date: 2021/3/14 18:04
 * @Description:
 */
@Slf4j
@Service
public class MyScheduledWithConfigService implements SchedulingConfigurer {

    @Autowired
    @Qualifier("myTaskExecutor")
    Executor myExecutor;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.setScheduler(taskScheduler());
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));
        taskRegistrar.addTriggerTask(() -> {
            System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime() + " -- "+ Thread.currentThread().getName());
        }, (triggerContext) -> {
            // 可替换成冲数据库取得数据
            String cron = "*/2 * * * * ?";
            return new CronTrigger(cron).nextExecutionTime(triggerContext);
        });
    }

    public Executor taskScheduler() {
        //设置线程名称
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        //创建线程池
//        return Executors.newScheduledThreadPool(5, namedThreadFactory);
        return null;
    }

}
