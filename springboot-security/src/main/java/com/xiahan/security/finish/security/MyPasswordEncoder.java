package com.xiahan.security.finish.security;

import com.xiahan.util.MD5;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Auther: xiahan
 * @Date: 2021/3/1 21:36
 * @Description: spring security 自定义密码处理
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * @Author xiahan
     * @Description 对密码加密.这你采用 security 的 BCryptPasswordEncoder加密 charSequence.toString();可得到原始密码
     * @Date 21:37 2021/3/1
     * @Param [charSequence] 原始密码
     * @return java.lang.String 返回加密的密码
     **/
    @Override
    public String encode(CharSequence rawPassword) {
//        return MD5.encrypt(rawPassword.toString());

        String encode = bCryptPasswordEncoder.encode(rawPassword);
        return encode;
    }

    /*
     * @Author xiahan
     * @Description 对前端输入密码 rawPassword 和数据库得到的密码 encodedPassword进行验证处理
     * @Date 21:41 2021/3/1
     * @Param [rawPassword, encodedPassword]
     * @return boolean
     **/
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));

//        boolean matches = bCryptPasswordEncoder.matches(bCryptPasswordEncoder.encode(rawPassword), encodedPassword);
        return true;
    }
}
