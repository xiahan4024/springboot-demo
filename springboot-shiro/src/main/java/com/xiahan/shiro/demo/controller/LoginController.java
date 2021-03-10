package com.xiahan.shiro.demo.controller;

import com.xiahan.shiro.demo.entity.AclUser;
import com.xiahan.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: xiahan
 * @Date: 2021/3/7 13:45
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/acl")
public class LoginController {

    // 登录页面
    @GetMapping("/login/page")
    public String loginPage(){
        return "/demo/login";
    }

    // 登录逻辑处理
    @PostMapping("/login")
    public String login(AclUser aclUser, Model model){
        if(StringUtils.isBlank(aclUser.getUsername()) || StringUtils.isBlank(aclUser.getPassword())){
            model.addAttribute("msg", "请输入相关信息");
            return "/demo/login";
        }
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(aclUser.getUsername(), aclUser.getPassword());
            try {
                subject.login(token);
            } catch (Exception ex){
                log.info("登录出错。错误信息为 = 【{}】", ExceptionUtil.getMessage(ex));
                return "/demo/login";
            }
        }
        return "redirect:/acl/list";
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

}
