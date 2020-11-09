package com.xiahan.webmagic.mq.dynamic;

import java.io.IOException;

import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:52:12
 * @Description: 动态代理接口
 */
@Deprecated
public interface BaseConsumer {

    @Deprecated
    void consumer(Message message, Channel channel) throws IOException;

}
