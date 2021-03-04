package com.xiahan.security.finish.security;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiahan.security.finish.entity.AclRole;
import com.xiahan.security.finish.entity.dto.RolePermission;
import com.xiahan.security.finish.service.AclPermissionService;
import com.xiahan.util.ExceptionUtil;
import com.xiahan.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: xiahan
 * @Date: 2021/3/2 20:37
 * @Description: 重写并实现了基于数据库的权限数据源
 */
@Slf4j
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private AclPermissionService aclPermissionService;

    @Autowired
    private RedisTemplate redisTemplate;

    //ant风格的路径匹配器
//    AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final String ROLE_PERMISSION = "CustomFilterInvocationSecurityMetadataSource:rolePermission";



    // TODO 后面涉及 role 和 RolePermission 改变都需要来重置这个 Map  放在redis中？？
    @PostConstruct
    public void init(){
        Map<String, Collection<String>> requestMap = new LinkedHashMap<>();
        log.info("[自定义权限资源数据源]：{}","初始化权限资源");
        List<RolePermission> rolePermissions = aclPermissionService.getRolePermission();
        for (RolePermission rolePermission: rolePermissions) {
            if(CollectionUtils.isEmpty(rolePermission.getRoles())){
                continue;
            }
            List<String> configAttributes = new ArrayList<>();
            for (AclRole roleItem: rolePermission.getRoles()) {
                configAttributes.add(roleItem.getRoleCode());
            }
            requestMap.put(rolePermission.getUrl(),configAttributes);
        }
        redisTemplate.opsForValue().set(ROLE_PERMISSION, JSON.toJSONString(requestMap));
        log.info("[自定义权限资源数据源]：{}", requestMap);
    }

    /**
     * getAttributes方法返回本次访问需要的权限，可以有多个权限。
     * 在上面的实现中如果没有匹配的url直接返回null，
     * 也就是没有配置权限的url默认都为白名单，想要换成默认是黑名单只要修改这里即可。
     *
     * 访问配置属性（ConfigAttribute）用于给定安全对象（通过的验证）
     *
     * @param object 安全的对象
     * @return 用于传入的安全对象的属性。 如果没有适用的属性，则应返回空集合。
     * @throws IllegalArgumentException 如果传递的对象不是SecurityDatasource实现支持的类型，则抛出异常
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

//        //获取当前用户请求的url
//        String requestUrl=((FilterInvocation) object).getRequestUrl();
//        //数据库中查询出所有的路径
//        List<RolePermission> rolePermissions  =aclPermissionService.getRolePermission();
//        for (RolePermission rolePermission : rolePermissions) {
//            //判断用户请求的url和数据库的url是否能匹配的上
//            if (pathMatcher.match(rolePermission.getUrl(), requestUrl)) {
//                List<AclRole> roles =rolePermission.getRoles();
//                String[] roleStr = new String[roles.size()];
//                for (int i = 0; i < roles.size(); i++) {
//                    roleStr[i]=roles.get(i).getRoleName();
//                }
//                //将筛选的url路径所具备的角色返回回去
//                return SecurityConfig.createList(roleStr);
//            }
//
//        }
//        //如果没有匹配上就返回一个默认的角色，作用好比作一个标记
//        return SecurityConfig.createList("ROLE_def");
//
        log.info("[自定义权限资源数据源]：{}","获取本次访问需要的权限");
        Map<String, Collection<String>> requestMap = null;
        Object redisResult = redisTemplate.opsForValue().get(ROLE_PERMISSION);
        if(redisResult == null){
            init();
            redisResult = redisTemplate.opsForValue().get(ROLE_PERMISSION).toString();
        }
        String str = redisResult.toString();
        requestMap = JSON.parseObject(str, LinkedHashMap.class);

//        final HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        // 获取当前用户请求的url
        String requestUrl=((FilterInvocation) object).getRequestUrl();
        AntPathMatcher pathMatcher = new AntPathMatcher();

        List<ConfigAttribute> result = new ArrayList<>();
        for (Map.Entry<String, Collection<String>> entry : requestMap.entrySet()) {
            String key = entry.getKey();
//            AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(key);
            if (pathMatcher.match(key, requestUrl)) {
//            if (antPathRequestMatcher.matches(request)) {
                log.info("[自定义权限资源数据源]：当前路径[{}]需要的资源权限:[{}] ==> 触发鉴权决策管理器",entry.getKey(),entry.getValue().toString());
                for (String item: entry.getValue()) {
                    SecurityConfig securityConfig = new SecurityConfig(item);
                    result.add(securityConfig);
                }
                return result;
            }
        }
        log.info("[自定义权限资源数据源]：{}==> {}","白名单路径", requestUrl);
        return null;
    }

    /**
     * getAllConfigAttributes方法如果返回了所有定义的权限资源，
     * Spring Security会在启动时校验每个ConfigAttribute是否配置正确，不需要校验直接返回null。
     *
     * 如果可用，则返回由实现类定义的所有ConfigAttribute。
     *
     * AbstractSecurityInterceptor使用它对针对它ConfigAttribute的每个配置属性执行启动时验证。
     *
     * @return ConfigAttribute，如果没有适用的，就返回null
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Map<String, Collection<String>> requestMap;
        String str = redisTemplate.opsForValue().get(ROLE_PERMISSION).toString();
        requestMap = JSON.parseObject(str, LinkedHashMap.class);
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        for (Map.Entry<String, Collection<String>> entry : requestMap.entrySet()) {
            List<ConfigAttribute> result = new ArrayList<>();
            for (String item: entry.getValue()) {
                SecurityConfig securityConfig = new SecurityConfig(item);
                result.add(securityConfig);
            }
            allAttributes.addAll(result);
        }
        log.info("[自定义权限资源数据源]：获取所有的角色==> {}",allAttributes.toString());
        return allAttributes;
    }

    /**
     * AbstractSecurityInterceptor 调用
     * supports方法返回类对象是否支持校验，web项目一般使用FilterInvocation来判断，或者直接返回true。
     *
     * @param aClass 正在查询的类
     * @return 如果实现可以处理指定的类，则为true
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

}
