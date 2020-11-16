package com.xiahan.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:11
 * @Description: 手动实现 慕课秒杀项目
 *                  目前只是实现了十分单纯的秒杀，使用了 redis、MQ 等技术。
 *                  秒杀的业务场景学习、
 *                  方法参数解析
 *                  拦截器
 *                  防刷接口处理：限定次数、URL 实时生成
 *                  thymeleaf 手动渲染
 *                  redis，使用JedisPool 自己封装了一个util
 *                  MQ： 更是简单的使用，没有考虑到百分百投递，重复消费，死信队列处理
 *                  前端倒是有一些功能可以借鉴（URL 获取参数、jquery 参数验证）
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
