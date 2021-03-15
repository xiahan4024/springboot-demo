package com.xiahan.spring.config;

import com.xiahan.spring.util.MyExecutorEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: xiahan
 * @Date: 2021/3/14 17:18
 * @Description:
 */
@Configuration
public class MyTaskConfig {

    @Bean("myTaskExecutor")
    public Executor myExecutor (MyExecutorEntity myExecutorEntity){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(MyExecutorEntity.CORE_POOL_SIZE);
        //最大线程数
        executor.setMaxPoolSize(MyExecutorEntity.MAX_POOL_SIZE);
        //队列容量
        executor.setQueueCapacity(MyExecutorEntity.KEEP_ALIVE_SECONDS);
        //活跃时间
        executor.setKeepAliveSeconds(MyExecutorEntity.QUEUE_CAPACITY);
        //线程名字前缀
        executor.setThreadNamePrefix(MyExecutorEntity.THREAD_NAME);

//        AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
//　　　　 CallerRunsPolicy:若已达到待处理队列长度，将由主线程直接处理请求
//　　　　 DiscardOldestPolicy:抛弃旧的任务；会导致被丢弃的任务无法再次被执行
//　　　　 DiscardPolicy:抛弃当前任务；会导致被丢弃的任务无法再次被执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
