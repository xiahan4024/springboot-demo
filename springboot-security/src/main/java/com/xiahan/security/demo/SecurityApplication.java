package com.xiahan.security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @Auther: xiahan
 * @Date: 2021/2/23 21:00
 * @Description:
 *      securedEnabled=true 开启注解：
 *          @Secured：判断是否具有角色，另外需要注意的是这里匹配的字符串需要添加前缀“ROLE_“
 *      prePostEnabled 开启注解:
 *          @PreAuthorize: @PreAuthorize：注解适合进入方法前的权限验证， @PreAuthorize 可以将登录用户的 roles/permissions 参数传到方法中。
 *          @PostAuthorize: 注解使用并不多，在方法执行后再进行权限验证，适合验证带有返回值的权限.
 */
@SpringBootApplication
@ComponentScan(value = {"com.xiahan.security.demo", "com.xiahan.util"})
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
