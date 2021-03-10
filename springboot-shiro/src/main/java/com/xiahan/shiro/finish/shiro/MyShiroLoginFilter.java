package com.xiahan.shiro.finish.shiro;

import com.xiahan.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Auther: xiahan
 * @Date: 2021/3/7 17:23
 * @Description: 自定义登录 过滤器
 */
@Slf4j
public class MyShiroLoginFilter extends AuthenticatingFilter {

    private String login_fail_url;

    private String login_success_url;

    public MyShiroLoginFilter(String login_fail_url, String login_success_url){
        this.login_fail_url = login_fail_url;
        this.login_success_url = login_success_url;
    }


    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String username = getparamnameValue(servletRequest, "username");
        String password = getparamnameValue(servletRequest, "password");
        String rememberme = getparamnameValue(servletRequest, "rememberme");
        log.info("rememberme  = 【{}】", rememberme);
        MyShiroToken myToken = new MyShiroToken();
        myToken.setUsername(username);
        myToken.setPassword(password);
        log.info("myToken = 【{}】", myToken);
        return myToken;
    }

    // 根据请求，拒绝通过处理，如果返回false，则不再去访问web控制器或action
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return executeLogin(servletRequest, servletResponse);
    }

    // 登录成功处理
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        try {
            WebUtils.issueRedirect(request, response, login_success_url);
        } catch (Exception ex) {
            log.info("onLoginSuccess 处理失败.mes = [{}]", ExceptionUtil.getMessage(ex));
        }
        return false;
    }

    // 登录失败处理
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        try {
            WebUtils.issueRedirect(request, response, login_fail_url);
        } catch (Exception ex) {
            log.info("onLoginFailure 处理失败.mes = [{}]", ExceptionUtil.getMessage(ex));
        }
        return false;
    }

    // 是否启用 记住我
    @Override
    protected boolean isRememberMe(ServletRequest request) {
        String rememberme = getparamnameValue(request, "rememberme");
        return super.isRememberMe(request);
    }

    // 根据请求信息，参数等信息判断是否允许通过，如果返回false，则是不通过。最终是否去访问web处理，有isAccessAllowed，onAccessDenied方法共同或运算决定，也就是只要有一个是true就会访问web控制器或action。
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    protected String getparamnameValue(ServletRequest request, String paramname) {
        return WebUtils.getCleanParam(request, paramname);
    }
}
