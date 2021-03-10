package com.xiahan.shiro.finish.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @Auther: xiahan
 * @Date: 2021/3/7 17:15
 * @Description: 自定义 shiro 的 token
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyShiroToken implements RememberMeAuthenticationToken {

    private String username;

    private String password;

    private boolean rememberMe;


    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    // 账号
    @Override
    public Object getPrincipal() {
        return username;
    }

    // 密码
    @Override
    public Object getCredentials() {
        return password;
    }

}
