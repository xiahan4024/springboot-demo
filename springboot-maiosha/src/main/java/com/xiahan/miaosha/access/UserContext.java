package com.xiahan.miaosha.access;

import com.xiahan.miaosha.domain.MiaoshaUser;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:19
 * @Description: 线程存储用户信息
 */
public class UserContext {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user) {
        userHolder.set(user);
    }

    public static MiaoshaUser getUser() {
        return userHolder.get();
    }

}
