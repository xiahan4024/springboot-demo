package com.xiahan.miaosha.redis;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:28
 * @Description:
 */
public class AccessKey extends BasePrefix{

    private AccessKey( int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }

}