package com.xiahan.util;

/**
 * @Auther: xiahan
 * @Date: 2021/2/23 21:30
 * @Description: 返回状态码
 */
public final class ResultCode {

    public static final Integer SUCCESS = 20000; //成功

    public static final Integer ERROR = 20001; //失败

    public static final Integer USERNAME_PASSWORD_ERROR = 20002; //登录账号用户名/密码错误

    public static final Integer SERIALIZABLE_ERROR = 20003; //序列化错误
}
