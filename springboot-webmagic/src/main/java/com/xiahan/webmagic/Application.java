package com.xiahan.webmagic;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:36:38
 * @Description: webmagic 应用，里面带有spring自带定时任务、动态代理、cglib代理、AOP思想、RabbitMQ使用、下载URL 图片
 */
@EnableAsync  // 异步
@EnableRabbit  //开启基于注解的RabbitMQ模式
@EnableScheduling  // 定时
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
