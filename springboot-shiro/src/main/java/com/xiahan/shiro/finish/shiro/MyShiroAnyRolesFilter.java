package com.xiahan.shiro.finish.shiro;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Auther: xiahan
 * @Date: 2021/3/7 17:55
 * @Description: 重写方法，使其有一个角色即可有权限访问 roles[admin,user]
 */
public class MyShiroAnyRolesFilter  extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        String[] rolesArray = (String[]) mappedValue;
        if(rolesArray == null || rolesArray.length == 0){
            return true;
        }
        for (String item:rolesArray) {
            if(subject.hasRole(item)){
                return true;
            }
        }
        return false;
    }
}
