package com.xiahan.security.finish.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiahan.security.finish.entity.AclUser;
import com.xiahan.util.ResponseUtil;
import com.xiahan.util.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: xiahan
 * @Date: 2021/3/1 22:05
 * @Description: 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private TokenManager tokenManager;

    private RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager,
                            TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        // 前端post 提交链接 /admin/acl/login
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
    }

    // 认证处理
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            ServletInputStream inputStream = req.getInputStream();
            // TODO 如果有其他的参数如何处理？？？？ 具体写法可参考 UsernamePasswordAuthenticationFilter
//            HashMap<Object, Object> aclUser1 = (HashMap<Object, Object>) new ObjectMapper().readValue(req.getInputStream(), HashMap.class);

            String username = req.getParameter("username");
            String password = req.getParameter("password");
            AclUser aclUser = new AclUser();
            aclUser.setUsername(username);
            aclUser.setPassword(password);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(aclUser.getUsername(), aclUser.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 认证成功
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        String token =tokenManager.createToken(user.getCurrentUserInfo().getUsername());

        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(),
                user.getPermissionValueList(), 60*60, TimeUnit.SECONDS);
        ResponseUtil.out(res, Result.ok().data("token", token));
    }

    // 认证失败
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(response, Result.error());
    }
}
