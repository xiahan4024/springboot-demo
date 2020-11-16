package com.xiahan.miaosha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:23
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
