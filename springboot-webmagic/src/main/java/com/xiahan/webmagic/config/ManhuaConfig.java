package com.xiahan.webmagic.config;

import com.xiahan.webmagic.webmagic.ManhuaPageProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:45:03
 * @Description: 线程池设置
 */
@Configuration
public class ManhuaConfig {

    @Value("${webmagic.cookie}")
    private String cookie;

    @Value("${task.pool.corePoolSize}")
    private Integer corePoolSize;

    @Value("${task.pool.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${task.pool.keepAliveSeconds}")
    private Integer keepAliveSeconds;

    @Value("${task.pool.queueCapacity}")
    private Integer queueCapacity;

    @Value("${task.pool.threadName}")
    private String threadName;

    @Bean
    public ManhuaPageProcessor manhuaPageProcessor(){
        return new ManhuaPageProcessor(cookie);
    }

    @Bean
    public Executor myTaskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(corePoolSize);
        //最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //队列容量
        executor.setQueueCapacity(keepAliveSeconds);
        //活跃时间
        executor.setKeepAliveSeconds(queueCapacity);
        //线程名字前缀
        executor.setThreadNamePrefix(threadName);

        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}

