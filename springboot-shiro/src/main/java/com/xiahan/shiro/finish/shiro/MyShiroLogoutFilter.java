package com.xiahan.shiro.finish.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Locale;

/**
 * @Auther: xiahan
 * @Date: 2021/3/7 17:26
 * @Description: 自定义登出 过滤器
 */
@Slf4j
public class MyShiroLogoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);
        if (this.isPostOnlyLogout() && !WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH).equals("POST")) {
            return this.onLogoutRequestNotAPost(request, response);
        } else {
            String redirectUrl = this.getRedirectUrl(request, response, subject);

            try {
                subject.logout();
            } catch (SessionException var6) {
                log.debug("Encountered session exception during logout.  This can generally safely be ignored.", var6);
            }
            this.issueRedirect(request, response, redirectUrl);
            return false;
        }
    }


}
