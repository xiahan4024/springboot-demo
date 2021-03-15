package com.xiahan.quartz.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: xiahan
 * @Date: 2021/3/14 17:21
 * @Description:
 */
@Component
public class MyExecutorEntity implements InitializingBean {

    @Value("${xiahan.task.pool.corePoolSize}")
    private Integer corePoolSize;

    @Value("${xiahan.task.pool.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${xiahan.task.pool.keepAliveSeconds}")
    private Integer keepAliveSeconds;

    @Value("${xiahan.task.pool.queueCapacity}")
    private Integer queueCapacity;

    @Value("${xiahan.task.pool.threadName}")
    private String threadName;

    public static Integer CORE_POOL_SIZE;
    public static Integer MAX_POOL_SIZE;
    public static Integer KEEP_ALIVE_SECONDS;
    public static Integer QUEUE_CAPACITY;
    public static String THREAD_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        CORE_POOL_SIZE = this.corePoolSize;
        MAX_POOL_SIZE = this.maxPoolSize;
        KEEP_ALIVE_SECONDS = this.keepAliveSeconds;
        QUEUE_CAPACITY = this.queueCapacity;
        THREAD_NAME = this.threadName;
    }
}
