package com.xiahan.security.demo.service.impl;

import com.xiahan.security.demo.entity.AclUser;
import com.xiahan.security.demo.service.AclUserService;
import com.xiahan.util.ResultCode;
import com.xiahan.util.exceptionhandler.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: xiahan
 * @Date: 2021/2/25 22:22
 * @Description:
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    AclUserService aclUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AclUser aclUser = aclUserService.loadUserByUsername(username);
        if(aclUser == null){
            throw new GlobalException(ResultCode.USERNAME_PASSWORD_ERROR, "用户名/密码错误");
        }
        // 角色 必须加前缀 ROLE_
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_sale");
        // org.springframework.security.core.userdetails.User; 可以自定义这个user。实现UserDetails 这个接口即可
        return new User(aclUser.getUsername(), new BCryptPasswordEncoder().encode(aclUser.getPassword()),auths);
    }
}
