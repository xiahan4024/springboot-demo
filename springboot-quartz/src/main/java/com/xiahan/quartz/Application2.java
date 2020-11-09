package com.xiahan.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 	springboot 整合 自带的定时任务
 * 		1.pom添加 spring-context-support
 * 		2. 开启注解 @EnableScheduling
 * @author xiahan
 * @dateTime 2019年10月31日 下午10:18:01
 */
@EnableScheduling
@SpringBootApplication
public class Application2 {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application2.class, args);
	}

}
