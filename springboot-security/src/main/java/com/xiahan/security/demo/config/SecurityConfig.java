package com.xiahan.security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

/**
 * @Auther: xiahan
 * @Date: 2021/2/25 20:37
 * @Description:
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    // 可以自定义这个加密方式  实现这个接口 PasswordEncoder
    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    // rememberMe 将token 存储在数据库中
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //退出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/swagger-ui.html").permitAll();

        //配置没有权限访问跳转自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");
        http.formLogin()   //自定义自己编写的登录页面
                .loginPage("/on.html")  //登录页面设置
                .loginProcessingUrl("/user/login")   //登录访问路径 前端 from 请求路径
                .defaultSuccessUrl("/success.html").permitAll()  //登录成功之后，跳转路径
                .failureUrl("/unauth.html")

                // 配置那个 URL 为登录页面
//                .loginPage("/on.html")
                // 登录失败后跳转到那个URL
//                .failureForwardUrl("")
                // 登录成功后 跳转到那个 URL
//                .successForwardUrl("")
                // 登录用户名 前端name自定义
//                .usernameParameter("")
                // 登录密码 前端password自定义
//                .passwordParameter("")


                .and().authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll() //设置哪些路径可以直接访问，不需要认证
                //当前登录用户，只有具有admins权限才可以访问这个路径
                //1 hasAuthority方法
                // .antMatchers("/test/index").hasAuthority("admins")
                //2 hasAnyAuthority方法
                // .antMatchers("/test/index").hasAnyAuthority("admins,manager")
                //3 hasRole方法   ROLE_sale
                .antMatchers("/test/index").hasRole("sale")

                .anyRequest().authenticated()

                // rememberMe 技术 默认 2 周时间。但是可以通过设置状态有效时间，即使项目重新启动下次也可以正常登录。
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60)//设置有效时长，单位秒
                .userDetailsService(userDetailsService)

//                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                .and().csrf().disable()  //关闭csrf防护
                ;

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/swagger-ui.html")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/webjars/**")
                ;
    }


}
