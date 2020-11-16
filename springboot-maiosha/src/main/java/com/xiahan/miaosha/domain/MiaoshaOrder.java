package com.xiahan.miaosha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:24
 * @Description: 秒杀的订单和普通的订单是分开的（业务决定）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaOrder {

    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;
}
