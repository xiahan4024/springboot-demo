package com.xiahan.miaosha.controller;

import com.xiahan.miaosha.domain.MiaoshaUser;
import com.xiahan.miaosha.domain.OrderInfo;
import com.xiahan.miaosha.redis.RedisService;
import com.xiahan.miaosha.result.CodeMsg;
import com.xiahan.miaosha.result.Result;
import com.xiahan.miaosha.service.GoodsService;
import com.xiahan.miaosha.service.MiaoshaUserService;
import com.xiahan.miaosha.service.OrderService;
import com.xiahan.miaosha.vo.GoodsVo;
import com.xiahan.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:21
 * @Description: 订单详情
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
                                      @RequestParam("orderId") long orderId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }

}

