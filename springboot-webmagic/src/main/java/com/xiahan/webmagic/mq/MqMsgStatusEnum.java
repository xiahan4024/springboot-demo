package com.xiahan.webmagic.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:54:17
 */
@Getter
@AllArgsConstructor
public enum MqMsgStatusEnum {

    DELIVERING(0, "消息投递中"),

    DELIVER_SUCCESS(1, "投递成功"),
    DELIVER_FAIL(2, "投递失败"),
    CONSUMED_SUCCESS(3, "已消费");

    private int code;
    private String msg;
}
