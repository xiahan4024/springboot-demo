package com.xiahan.security.finish.config;

import com.xiahan.security.finish.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @Auther: xiahan
 * @Date: 2021/3/1 21:30
 * @Description:
 */
@Configuration
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    //自定义查询数据库用户名密码和权限信息
    private UserDetailsService userDetailsService;

    //token 管理工具类（生成 token）
    private TokenManager tokenManager;

    //密码管理工具类
    private MyPasswordEncoder myPasswordEncoder;

    //redis 操作工具类
    private RedisTemplate redisTemplate;

    private CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    private CustomAccessDecisionManager customAccessDecisionManager;

    @Autowired
    public CustomSecurityConfig(UserDetailsService userDetailsService, MyPasswordEncoder defaultPasswordEncoder, TokenManager tokenManager, RedisTemplate redisTemplate,
                                CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource, CustomAccessDecisionManager customAccessDecisionManager) {
        this.userDetailsService = userDetailsService;
        this.myPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.customAccessDecisionManager = customAccessDecisionManager;
        this.customFilterInvocationSecurityMetadataSource = customFilterInvocationSecurityMetadataSource;
    }

    /**
     * 配置设置
     */
    //设置退出的地址和 token，redis 操作地址
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                // 未授权处理
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                // 关闭 csrf
                .and().csrf().disable()
                // 所有请求需要认证
                .authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object){
                        object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        object.setAccessDecisionManager(customAccessDecisionManager);
                        return object;
                    }
                })
                // 登出 URL
                .and().logout().logoutUrl("/admin/acl/index/logout")
                // 登出处理逻辑
                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
                // 登录过滤器
                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                // 授权过滤器
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
    }

    /**
     * 密码处理
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(myPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/swagger-ui.html/**")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/webjars/**")
        ;
    }

}

