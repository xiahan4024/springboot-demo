package com.xiahan.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: xiahan
 * @Date: 2021/3/14 18:36
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.xiahan.quartz", "com.xiahan.util"})
public class QuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }
}
