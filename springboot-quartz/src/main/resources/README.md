# 一：Spring自带定时任务
> com.xiahan.quartz.spring;包即为spring自带的定时任务
> 目前共有两种方式

# 二：其他准备

> 1. 导入依赖：
>
>    ```xml
>    <dependency>
>       <groupId>org.springframework</groupId>
>       <artifactId>spring-context-support</artifactId>
>    </dependency>
>    ```
>
> 2. 添加注解：@EnableScheduling

# 三：Scheduled 注解使用

> Scheduled注解，简单，但是不能动态改变定时时间

# 四：quartz 使用

## 1.  导入依赖
> ```shell
> <dependency>
>  <groupId>org.springframework.boot</groupId>
>     <artifactId>spring-boot-starter-quartz</artifactId>
>    </dependency>
> ```
>
## 2. 配置 
> springboot 与 quartz 集成十分简单。不需要花里胡哨的配置。只需在yml 里面配置即可
>
>    ```shell
>    spring:
>      datasource:
>        type: com.alibaba.druid.pool.DruidDataSource
>        driver-class-name: com.mysql.cj.jdbc.Driver
>        url: jdbc:mysql://192.168.110.100:3306/springboot_quartz?characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Taipei
>        username: root
>        password: xiahan
>      quartz:
>        ## 采用jdbc 方式存储
>        job-store-type: JDBC
>        ## 每次启动 都会删除库 然后重建
>        jdbc:
>    #      initialize-schema: ALWAYS
>          initialize-schema: NEVER
>        properties:
>          ## 原生的properties 这里都可以
>          org:
>            quartz:
>              scheduler:
>                ## 集群名称
>                instanceName: muQuartz
>                ## 该节点名称 这里自动。可以手动便于调试
>                instanceId: AUTO
>              jobStore:
>                class: org.quartz.impl.jdbcjobstore.JobStoreTX
>                driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate #StdJDBCDelegate说明支持集群
>                tablePrefix: QRTZ_
>                isClustered: true
>                clusterCheckinInterval: 1000
>                useProperties: false
>              threadPool:
>                class: org.quartz.simpl.SimpleThreadPool
>                threadCount: 20
>                threadPriority: 5
>                threadsInheritContextClassLoaderOfInitializingThread: true
>    ```
>
> 3. 数据源问题：单数据源，只需要使用项目中的数据源即可。多数据源，只需要在创建数据源的时候使用注解@QuartzDataSource告诉 quartz 即可。
>
> 4. 集群问题：集群必须在 jdbc 方式下才可以进行。且 isClustered: true需要设置对

## 3. 操作工具类

```java
package com.xiahan.quartz.util;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author xiahan
 * @dateTime 2019年11月1日 下午9:54:17
 *      TODO 添加分区 
 */
@Slf4j
@Component
public class ScheduledManagerUtil {

    @Autowired
    Scheduler scheduler;

    /**
     * 	添加一个定时任务
     * @author xiahan
     * @dateTime 2019年11月1日 下午10:08:12
     * @param jobName：定时任务名
     * @param jobGroupName：定时任务组名
     * @param triggerName：触发器名
     * @param triggerGroupName：触发器组名
     * @param jobClass：任务类
     * @param cron：cron表达式。详见quartz官网
     * @return：添加成功返回true，失败返回false
     */
    public boolean addJob(String jobName, String jobGroupName,
                          String triggerName, String triggerGroupName, Class jobClass, String cron) {
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .usingJobData("partition", "orderserver-1")
                .withIdentity(jobName, jobGroupName)
                .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();

        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
            log.info("添加[{}]任务成功",jobClass);
            return true;
        } catch (SchedulerException e) {
            log.error("添加[{}],[{}],[{}],[{}],[{}],[{}]定时任务出错："+ e,jobName,jobGroupName,triggerName,triggerGroupName,jobClass,cron);
            return false;
        }
    }

    /**
     *	删除一个定时任务。会删除触发器
     * @author xiahan
     * @dateTime 2019年11月1日 下午10:17:17
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @return
     */
    public boolean deleteJob(String jobName, String jobGroupName,
                             String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            scheduler.pauseTrigger(triggerKey);  //停止触发器
            scheduler.unscheduleJob(triggerKey); //删除触发器

            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName)); //删除任务
            log.info("删除定时任务成功");
            return true;
        } catch (SchedulerException e) {
            log.error("删除[{}],[{}],[{}],[{}]定时任务出错："+ e,jobName,jobGroupName,triggerName,triggerGroupName);
            return false;
        }
    }

    public boolean modifyJobTime(String jobName,
                                 String jobGroupName, String triggerName, String triggerGroupName, String cron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
            if(trigger == null ) {
                log.info("修改添加[{}],[{}],[{}],[{}],[{}]定时任务出错：trigger不保存在",jobName,jobGroupName,triggerName,triggerGroupName,cron);
                return false;
            }

            String cronExpression = trigger.getCronExpression();
            if(cronExpression.equals(cron)) {
                log.error("修改添加[{}],[{}],[{}],[{}],[{}]定时任务出错：时间并没改变",jobName,jobGroupName,triggerName,triggerGroupName,cron);
                return false;
            }

            /**
             * 	创建新的cron trigger
             */
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();

            /**
             * 	方式一：先删除，后创建
             */
//			JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
//			Class<? extends Job> jobClass = jobDetail.getJobClass();
//			removeJob(jobName,jobGroupName,triggerName,triggerGroupName);
//			addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);

            /**
             * 	直接修改
             */
            scheduler.rescheduleJob(triggerKey,cronTrigger);
            log.info("修改定时任务成功");
            return true;
        } catch (SchedulerException e) {
            log.error("修改添加[{}],[{}],[{}],[{}],[{}]定时任务出错："+ e,jobName,jobGroupName,triggerName,triggerGroupName,cron);
            return false;
        }
    }

    public boolean removeJob(String jobName,
                             String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            scheduler.pauseTrigger(triggerKey); //停止触发器
            scheduler.unscheduleJob(triggerKey); //删除触发器

            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName)); //删除任务
            return true;
        } catch (SchedulerException e) {
            log.error("修改添加[{}],[{}],[{}],[{}]定时任务出错："+ e,jobName,jobGroupName,triggerName,triggerGroupName);
            return false;
        }
    }

    /**
     *	启动调度
     * @author xiahan
     * @dateTime 2019年11月1日 下午10:43:38
     * @return
     */
    public boolean startAll() {
        try {
            scheduler.start();
            return true;
        } catch (Exception e) {
            log.error("启动调度"+ e);
            return false;
        }
    }

    /**
     *	停止调度
     * @author xiahan
     * @dateTime 2019年11月1日 下午10:43:53
     * @return
     */
    public boolean stopAll() {
        try {
            if(!scheduler.isShutdown()) {
                scheduler.isShutdown();
            }
            return true;
        } catch (Exception e) {
            log.error("停止调度"+ e);
            return false;
        }
    }

    /**
     *	停止所有的任务
     * @author xiahan
     * @dateTime 2019年11月1日 下午10:50:32
     * @return
     */
    public boolean pauseAll() {
        try {
            scheduler.pauseAll();
            return true;
        } catch (Exception e) {
            log.error("停止所有的任务"+ e);
            return false;
        }
    }

    /**
     *	恢复所有的任务
     * @author xiahan
     * @dateTime 2019年11月1日 下午10:50:20
     * @return
     */
    public boolean resumeAll() {
        try {
            scheduler.resumeAll();
            return true;
        } catch (Exception e) {
            log.error("恢复所有的任务"+ e);
            return false;
        }
    }
}

```





















