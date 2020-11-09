package com.xiahan.quartz.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月7日 下午2:26:51
 * @describe 实现接口SchedulingConfigurer，可动态改变。
 *            缺点：是单线程
 *            启动类添加 @EnableScheduling，该类添加@Component
 */
@Slf4j
@Component
public class MyScheduledWImplService implements SchedulingConfigurer{

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime());
        }, (triggerContext) -> {
            // 可替换成冲数据库取得数据
            String cron = "*/2 * * * * ?";
            return new CronTrigger(cron).nextExecutionTime(triggerContext);
        });
    }

}
