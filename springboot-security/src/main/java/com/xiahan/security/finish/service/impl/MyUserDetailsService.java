package com.xiahan.security.finish.service.impl;

import com.xiahan.security.finish.entity.AclUser;
import com.xiahan.security.finish.security.SecurityUser;
import com.xiahan.security.finish.service.AclUserService;
import com.xiahan.util.ResultCode;
import com.xiahan.util.exceptionhandler.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
//        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_sale");
        // org.springframework.security.core.userdetails.User; 可以自定义这个user。实现UserDetails 这个接口即可

        List<String> list = new ArrayList<>();
        list.add("ROLE_admin");
        list.add("ROLE_sale");
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(aclUser);
        securityUser.setPermissionValueList(list);
        return securityUser;
    }
}
