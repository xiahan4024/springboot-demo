package com.xiahan.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Auther: xiahan
 * @Date: 2021/3/14 16:25
 * @Description: spring 自带的定时任务
 *          1. 导入依赖
 *          2. 添加注解： @EnableScheduling
 *          3. 写定时任务逻辑
 *
 *          线程池没有效果？ 添加注解 @EnableAsync
 *
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.xiahan.quartz", "com.xiahan.util"})
public class SpringScheduled {

    public static void main(String[] args) {
        SpringApplication.run(SpringScheduled.class, args);
    }
}
