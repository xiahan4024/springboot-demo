package com.xiahan.webmagic.config;

import com.xiahan.webmagic.mq.MqExchangeAndQueuesAndRoutingkey;
import com.xiahan.webmagic.mq.MqMsgStatusEnum;
import com.xiahan.webmagic.service.MsgLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:46:22
 * @Description: rabbitMQ设置， 以及exchange、queues、Binding.使用fastjson 序列化
 */
@Slf4j
@Configuration
public class RabbitmqConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private MsgLogService msgLogService;

    // 为rabbitmq 序列化 使用jackson
    @Bean
    public MessageConverter messageConverter(){
//        return new Jackson2JsonMessageConverter();
        return new SelfConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());

        // 消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)->{
            if(ack){
                log.info("消息发送成功到 exchange");
                String msgId = correlationData.getId();
                msgLogService.updateStatus(msgId, MqMsgStatusEnum.DELIVER_SUCCESS.getCode());
            }else {
                log.info("消息发送到exchange 失败{}， casue{}", correlationData, cause);
            }
        });

        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);
        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey)->{
            log.info("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", exchange, routingKey, replyCode, replyText, message);
        });

        return rabbitTemplate;
    }

    @Bean
    public DirectExchange manhuaExchange(){
        return new DirectExchange(MqExchangeAndQueuesAndRoutingkey.EXCHANGE_DIRECT_MANHUA.getMsg(), true, false);
    }

    @Bean
    public Queue manhuaQueue(){
        return new Queue(MqExchangeAndQueuesAndRoutingkey.QUEUES_MANHUA.getMsg(), true, false, false);
    }

    @Bean
    public Binding manhuaBinding(){
        return BindingBuilder.bind(manhuaQueue()).to(manhuaExchange()).with(MqExchangeAndQueuesAndRoutingkey.BANDINGKEY_MANHUA.getMsg());
    }

    @Bean
    public DirectExchange manhuaTestExchange(){
        return new DirectExchange(MqExchangeAndQueuesAndRoutingkey.EXCHANGE_DIRECT_TEST_MANHUA.getMsg(), true, false);
    }

    @Bean
    public Queue manhuaTestQueue(){
        return new Queue(MqExchangeAndQueuesAndRoutingkey.QUEUES_TEST_MANHUA.getMsg(), true, false, false);
    }

    @Bean
    public Binding manhuaTestBinding(){
        return BindingBuilder.bind(manhuaTestQueue()).to(manhuaTestExchange()).with(MqExchangeAndQueuesAndRoutingkey.BANDINGKEY_TEST_MANHUA.getMsg());
    }
}
