package com.xiahan.shiro.demo.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: xiahan
 * @Date: 2021/3/7 13:42
 * @Description:
 */
public class MyAuthorizingRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = principalCollection.getPrimaryPrincipal().toString();
        Set<String> roles = new HashSet<>();
        if(username.equals("admin")){
            roles.add("admin");
        }else if(username.equals("user")){
            roles.add("user");
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if(authenticationToken == null){
            return null;
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getPrincipal().toString();
        if(username.equals("admin")){
            return new SimpleAuthenticationInfo(username, "admin", getName());
        }else if(username.equals("user")){
            return new SimpleAuthenticationInfo(username, "user", getName());
        }
        return null;
    }
}
