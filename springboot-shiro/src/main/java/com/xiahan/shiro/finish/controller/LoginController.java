package com.xiahan.shiro.finish.controller;

import com.xiahan.shiro.finish.config.ShiroConfig;
import com.xiahan.shiro.finish.entity.AclUser;
import com.xiahan.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: xiahan
 * @Date: 2021/3/7 13:45
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/acl")
public class LoginController {

    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;

    // 登录页面
    @GetMapping("/login/page")
    public String loginPage(){
        return "/demo/login";
    }

    @GetMapping("list")
    public String list(){
        return "/demo/list";
    }

    // 没有权限 页面提示
    @GetMapping("/unauthorize")
    public String unauthorize(){
        return "/demo/unauthorize";
    }

    @ResponseBody
    @GetMapping("/change/{type}")
    public void changeLinkedHashMap(@PathVariable String type){
        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            log.error("改变 权限对应关系出错。msg = 【{}】", ExceptionUtil.getMessage(e));
        }

        // 获取过滤管理器
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

        // 清空初始权限配置
        manager.getFilterChains().clear();
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

        // 重新构建生成
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put(ShiroConfig.LOGIN_IN_URL, "login");
        filterChainDefinitionMap.put(ShiroConfig.LOGIN_UNAUTH_URL, "anon");
        filterChainDefinitionMap.put(ShiroConfig.LOGIN_OUT_URL, "logout");
        filterChainDefinitionMap.put("/acl-user/info/1", "roles[role_admin]");
        filterChainDefinitionMap.put("/acl-user/info/2", "roles[role_user,role_admin]");

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();

        for (Map.Entry<String, String> entry : chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue().trim().replace(" ", "");
            manager.createChain(url, chainDefinition);
        }
    }

}
