package com.xiahan.shiro.finish;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: xiahan
 * @Date: 2021/3/6 23:22
 * @Date: 2021/3/6 23:22
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.xiahan.shiro.finish", "com.xiahan.util"})
public class ShrioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShrioApplication.class, args);
    }

}
