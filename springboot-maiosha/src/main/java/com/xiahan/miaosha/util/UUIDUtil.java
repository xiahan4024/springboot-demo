package com.xiahan.miaosha.util;

import java.util.UUID;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:34
 * @Description:
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
