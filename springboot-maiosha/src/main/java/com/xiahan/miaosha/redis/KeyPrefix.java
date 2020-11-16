package com.xiahan.miaosha.redis;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:29
 * @Description:
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

}
