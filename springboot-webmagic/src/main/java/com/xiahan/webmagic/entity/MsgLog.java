package com.xiahan.webmagic.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.alibaba.fastjson.JSON;
import com.xiahan.webmagic.mq.MqMsgStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:49:15
 * @Description: mq 落库
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgLog {

    private String msgId;
    private String msg;
    private String exchange;
    private String routingKey;
    private Integer status;
    private Integer tryCount;
    private LocalDateTime nextTryTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public MsgLog(String msgId, Object msg, String exchange, String routingKey) {
        this.msgId = msgId;
        this.msg = JSON.toJSONString(msg);
        this.exchange = exchange;
        this.routingKey = routingKey;

        this.status = MqMsgStatusEnum.DELIVERING.getCode();
        this.tryCount = 0;

        LocalDateTime date = LocalDateTime.now(ZoneOffset.of("+8"));
        this.createTime = date;
        this.updateTime = date;
        this.nextTryTime = date.plusMinutes(1L);
    }
}

