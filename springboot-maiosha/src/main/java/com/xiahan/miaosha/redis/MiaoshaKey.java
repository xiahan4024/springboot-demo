package com.xiahan.miaosha.redis;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:29
 * @Description:
 */
public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey( int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0, "go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60, "mp");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300, "vc");
}
