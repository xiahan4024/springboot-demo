package com.xiahan.security.finish.security;

import com.baomidou.mybatisplus.extension.api.R;
import com.xiahan.util.ResponseUtil;
import com.xiahan.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: xiahan
 * @Date: 2021/3/1 21:50
 * @Description: security 未授权统一处理
 */
public class UnauthorizedEntryPoint  implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseUtil.out(response, Result.error().data("msg", "未拥有权限"));
    }

}
