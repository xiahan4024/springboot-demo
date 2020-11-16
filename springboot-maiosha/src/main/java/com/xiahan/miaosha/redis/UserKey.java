package com.xiahan.miaosha.redis;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:30
 * @Description:
 */
public class UserKey extends BasePrefix{

    private UserKey(String prefix) {
        super(prefix);
    }
    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
