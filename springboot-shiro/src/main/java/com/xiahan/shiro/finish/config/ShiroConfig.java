package com.xiahan.shiro.finish.config;

import com.xiahan.shiro.finish.shiro.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Auther: xiahan
 * @Date: 2021/3/6 23:37
 * @Description:
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Autowired
    RedisTemplate redisTemplate;

    private long CACHE_EXPIRETIME = 120;

    private long SESSION_EXPIRETIME = 1800;

    private static final String LOGIN_IN_PAGE_URL = "/acl/login/page";
    public static final String LOGIN_UNAUTH_URL = "/acl/unauthorize";
    public static final String LOGIN_OUT_URL = "/logout";
    public static final String LOGIN_IN_URL = "/acl/login";
    private static final String LOGIN_SUCCESS_URL = "/acl-user/info/1";

    @Bean
    public SecurityManager mySecurityManager(){
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(myAuthorizingRealm());
        defaultSecurityManager.setCacheManager(myCacheManager());
        defaultSecurityManager.setSessionManager(sessionManager());
        return defaultSecurityManager;
    }

    /**
     *  redis 接管
     *  权限缓存： key 为用户名。value 为 SimpleAuthorizationInfo
     *  session： key 为
     * @return
     */
    public CacheManager myCacheManager(){
        final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
        return new CacheManager() {
            @Override
            public <K, V> Cache<K, V> getCache(String name) throws CacheException {
                log.info("myCacheManager --> getCache 参数： 【{}】 ", name);
                Cache cache = caches.get(name);
                if(cache == null){
                    cache = new MyShiroRedisCache<K, V>(redisTemplate, CACHE_EXPIRETIME);
                }
                return cache;
            }
        };
    }

    private DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1800000L);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // redis 接管 session
        sessionManager.setSessionDAO(new MyShiroSessionDaoConfig(redisTemplate, SESSION_EXPIRETIME));
        return sessionManager;
    }

    public MyAuthorizingRealm myAuthorizingRealm(){
        // 自定义密码验证逻辑
        MyCredentialsMatcher myCredentialsMatcher = new MyCredentialsMatcher();
        return new MyAuthorizingRealm(myCredentialsMatcher);
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("mySecurityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // login 页面url
        shiroFilterFactoryBean.setLoginUrl(LOGIN_IN_PAGE_URL);
        // 登录成功 url
        shiroFilterFactoryBean.setSuccessUrl(LOGIN_SUCCESS_URL);
        // 没有权限页面 url
        shiroFilterFactoryBean.setUnauthorizedUrl(LOGIN_UNAUTH_URL);
        // securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> mapFilter = new HashMap<>();
        mapFilter.put("logout", new MyShiroLogoutFilter());
        mapFilter.put("login", new MyShiroLoginFilter(LOGIN_IN_PAGE_URL, LOGIN_SUCCESS_URL));
        mapFilter.put("roles", new MyShiroAnyRolesFilter());
        shiroFilterFactoryBean.setFilters(mapFilter);

        // 添加一些拦截规则
//        1). anon 可以被匿名访问
//        2). authc 必须认证(即登录)后才可能访问的页面.
//        3). logout 登出.
//        4). roles 角色过滤器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put(LOGIN_IN_URL, "login");
        filterChainDefinitionMap.put(LOGIN_UNAUTH_URL, "anon");
        filterChainDefinitionMap.put(LOGIN_OUT_URL, "logout");
//        filterChainDefinitionMap.put("/acl-user/info/1", "roles[admin]");
//        filterChainDefinitionMap.put("/acl-user/info/2", "roles[user,admin]");

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

}
