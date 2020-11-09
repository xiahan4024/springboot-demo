package com.xiahan.webmagic.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:54:04
 * @Description: 枚举 mq exchange、queues、banding
 */
@Getter
@AllArgsConstructor
public enum MqExchangeAndQueuesAndRoutingkey {

    EXCHANGE_DIRECT_MANHUA(1, "manhua"),
    EXCHANGE_DIRECT_TEST_MANHUA(2, "manhuatest"),

    QUEUES_MANHUA(3, "manhua"),
    QUEUES_TEST_MANHUA(4, "manhuatest"),

    BANDINGKEY_MANHUA(5, "manhua"),
    BANDINGKEY_TEST_MANHUA(6, "manhuatest");

    private int code;
    private String msg;
}
