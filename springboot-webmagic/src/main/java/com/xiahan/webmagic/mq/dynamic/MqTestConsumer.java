package com.xiahan.webmagic.mq.dynamic;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.xiahan.webmagic.entity.ManhuaImg;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:53:14
 * @Description: 动态代理实现
 */
@Slf4j
@Component
@Deprecated
public class MqTestConsumer implements BaseConsumer {

    @Override
    @Deprecated
    public void consumer(Message message, Channel channel) throws IOException {
        String body = new String(message.getBody());
        ManhuaImg manhuaImg = JSON.parseObject(body, ManhuaImg.class);
        System.out.println(" 收到的信息为：" + manhuaImg);
        log.info("收到信息 message = 【{}】", manhuaImg);
    }
}
