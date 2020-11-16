package com.xiahan.miaosha.controller;

import com.xiahan.miaosha.domain.MiaoshaUser;
import com.xiahan.miaosha.redis.RedisService;
import com.xiahan.miaosha.result.Result;
import com.xiahan.miaosha.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:21
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser user) {
        return Result.success(user);
    }

}

