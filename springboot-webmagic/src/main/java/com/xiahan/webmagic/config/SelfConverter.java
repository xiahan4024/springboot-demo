package com.xiahan.webmagic.config;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:47:25
 * @Description:  自定义 。rabbitMq 使用fasttjson 来序列化
 */
public class SelfConverter extends AbstractMessageConverter {
    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties) {
        messageProperties.setContentType("application/json");
        return new Message(JSON.toJSONBytes(object), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return JSON.parse(message.getBody());
    }
}
