package com.xiahan.miaosha.rabbitmq;

import com.xiahan.miaosha.domain.MiaoshaUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:26
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;
}
