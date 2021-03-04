package com.xiahan.security.finish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: xiahan
 * @Date: 2021/3/1 21:15
 * @Description:
 */
@SpringBootApplication
@ComponentScan(value = {"com.xiahan.security.finish", "com.xiahan.util", "com.xiahan.security.finish.config"})
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
