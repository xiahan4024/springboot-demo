package com.xiahan.webmagic.task;

import com.xiahan.webmagic.entity.MsgLog;
import com.xiahan.webmagic.mq.MqMsgStatusEnum;
import com.xiahan.webmagic.service.MsgLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:55:36
 */
@Slf4j
@Component
public class ManhuaTask {

    // 最大投递次数
    private static final int MAX_TRY_COUNT = 3;

    @Autowired
    MsgLogService msgLogService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/30 * * * * ?")
    public void resend(){
        log.info("开始执行定时任务(重新投递消息)");

        List<MsgLog> msgLogs = msgLogService.selectTimeoutMsg();
        msgLogs.forEach(msgLog -> {
            String msgId = msgLog.getMsgId();
            if (msgLog.getTryCount() >= MAX_TRY_COUNT) {
                msgLogService.updateStatus(msgId, MqMsgStatusEnum.DELIVER_FAIL.getCode());
                log.error("超过最大重试次数, 消息投递失败, msgId: {}", msgId);
            } else {
                msgLogService.updateTryCount(msgId, LocalDateTime.now(ZoneOffset.of("+8")).plusMinutes(1L));// 投递次数+1

                CorrelationData correlationData = new CorrelationData(msgId);
                rabbitTemplate.convertAndSend(msgLog.getExchange(), msgLog.getRoutingKey(), msgLog, correlationData);// 重新投递

                log.info("第 " + (msgLog.getTryCount() + 1) + " 次重新投递消息");
            }
        });

        log.info("定时任务执行结束(重新投递消息)");

    }
}

