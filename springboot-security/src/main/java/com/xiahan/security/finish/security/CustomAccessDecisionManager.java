package com.xiahan.security.finish.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Auther: xiahan
 * @Date: 2021/3/2 21:24
 * @Description: 根据URL资源权限和用户角色，进行鉴权
 */
@Slf4j
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    /**
     * 权限鉴定
     *
     * @param authentication   from SecurityContextHolder.getContext() => userDetails.getAuthorities()
     * @param object           就是FilterInvocation对象，可以得到request等web资源。
     * @param collection from MetaDataSource.getAttributes()，已经被框架做了非空判断
     * @throws AccessDeniedException   如果由于身份验证不具有所需的权限或ACL特权而拒绝访问
     * @throws InsufficientAuthenticationException 如果由于身份验证没有提供足够的信任级别而拒绝访问
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
//        // configattributes里存放着CustomFilterInvocationSecurityMetadataSource过滤出来的角色
//        for (ConfigAttribute configAttribute : collection) {
//            //如果你请求的url在数据库中不具备角色
//            if ("ROLE_def".equals(configAttribute.getAttribute())) {
//                //在判断是不是匿名用户(也就是未登录)
//                if (authentication instanceof AnonymousAuthenticationToken) {
//                    System.out.println(">>>>>>>>>>>>>>>>匿名用户>>>>>>>>>>>>>>");
//                    throw new AccessDeniedException("权限不足，无法访问");
//                }else{
//                    //这里面就是已经登录的其他类型用户，直接放行
//                    System.out.println(">>>>>>>>>>>其他类型用户>>>>>>>>>>>");
//                    return;
//                }
//            }
//            //如果你访问的路径在数据库中具有角色就会来到这里
//            //Autherntication这里面存放着登录后的用户所有信息
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                System.out.println(">>>>>>>authority(账户所拥有的权限)："+authority.getAuthority());
//                System.out.println(">>>>>>>configAttribute(路径需要的角色):"+configAttribute.getAttribute());
//                //路径需要的角色和账户所拥有的角色作比较
//                if (authority.getAuthority().equals(configAttribute.getAttribute())) {
//                    System.out.println(">>>>>>>>>>>>>>>>>>进来>>>>>>>>>>>>>>>>>");
//                    return;
//                }
//            }
//
//        }

        log.info("****************************************权限鉴定********************************************");
        /*FilterInvocation filterInvocation = (FilterInvocation) object; // object 是一个URL
        log.info("[当前路径[{}]需要的资源权限]：{}",filterInvocation.getRequestUrl(),configAttributes);*/
        log.info("[登录用户[{}]权限]：{}",authentication.getName(),authentication.getAuthorities());

        if(collection == null){
            return;
        }

        for (ConfigAttribute configAttribute : collection) {
            /* 资源的权限 */
            String attribute = configAttribute.getAttribute();
            /* 用户的权限 */
            for (GrantedAuthority authority : authentication.getAuthorities()) { // 当前用户的权限
                if(authority.getAuthority().trim().equals("ROLE_ANONYMOUS"))return;
                log.info("[资源角色==用户角色] ？ {} == {}", attribute.trim(), authority.getAuthority().trim());
                if (attribute.trim().equals(authority.getAuthority().trim())) {
                    log.info("[鉴权决策管理器]：登录用户[{}]权限匹配",authentication.getName());
                    return;
                }
            }
        }
        log.info("[鉴权决策管理器]：登录用户[{}]权限不足",authentication.getName());
        throw new AccessDeniedException("权限不足");
    }

    /**
     * AbstractSecurityInterceptor 调用，遍历ConfigAttribute集合，筛选出不支持的attribute
     *
     * @param configAttribute a configuration attribute that has been configured against the
     *                  <code>AbstractSecurityInterceptor</code>
     * @return true if this <code>AccessDecisionManager</code> can support the passed
     * configuration attribute
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /**
     * AbstractSecurityInterceptor 调用,验证AccessDecisionManager是否支持这个安全对象的类型。
     * supports(Class)方法被安全拦截器实现调用，
     * 包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型。
     *
     * @param aClass the class that is being queried
     * @return <code>true</code> if the implementation can process the indicated class
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
