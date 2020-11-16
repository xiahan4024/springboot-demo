package com.xiahan.miaosha.vo;

import com.xiahan.miaosha.domain.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:36
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;
}
