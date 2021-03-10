package com.xiahan.shiro.demo.config;

import com.xiahan.shiro.demo.shiro.MyAuthorizingRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: xiahan
 * @Date: 2021/3/6 23:37
 * @Description:
 */
@Configuration
public class ShiroConfig {

    @Bean
    public SecurityManager mySecurityManager(@Qualifier("myAuthorizingRealm") MyAuthorizingRealm myAuthorizingRealm){
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(myAuthorizingRealm);
        return defaultSecurityManager;
    }

    @Bean
    public MyAuthorizingRealm myAuthorizingRealm(){
        return new MyAuthorizingRealm();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("mySecurityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // login 页面url
        shiroFilterFactoryBean.setLoginUrl("/acl/login/page");
        // 登录成功 url
        shiroFilterFactoryBean.setSuccessUrl("/acl-user/info/1");
        // 没有权限页面 url
        shiroFilterFactoryBean.setUnauthorizedUrl("/acl/unauthorize");
        // securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加一些拦截规则
//        1). anon 可以被匿名访问
//        2). authc 必须认证(即登录)后才可能访问的页面.
//        3). logout 登出.
//        4). roles 角色过滤器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/acl/login", "anon");
        filterChainDefinitionMap.put("/acl/unauthorize", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/acl-user/info/1", "roles[admin]");
        filterChainDefinitionMap.put("/acl-user/info/2", "roles[user]");

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

}
