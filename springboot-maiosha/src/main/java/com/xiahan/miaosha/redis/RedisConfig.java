package com.xiahan.miaosha.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:29
 * @Description: redis key，设计
 *                  接口：两个方法。一个返回过期时间，一个返回key 前缀
 *                  抽象类：实现接口，两个构造方法，一个默认不过期、参数前缀，一个参数前缀、参数过期时间
 *                                 返回key 设计： getClass().getSimpleName() + ":" + prefix;
 *                  真正的 key。集成抽象类，选择一个构造函数，添加一些key
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix="redis")
public class RedisConfig {
    private String host;
    private int port;
    private int timeout;//秒
    private String password;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;//秒
}