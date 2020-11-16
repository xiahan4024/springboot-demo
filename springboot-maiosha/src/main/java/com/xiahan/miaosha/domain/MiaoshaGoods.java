package com.xiahan.miaosha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Auther: xiahan
 * @Date: 2020/11/10 21:23
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaGoods {

    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
