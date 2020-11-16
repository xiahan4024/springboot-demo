package com.xiahan.miaosha.vo;

import com.xiahan.miaosha.domain.MiaoshaUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:35
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVo {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods ;
    private MiaoshaUser user;
}
