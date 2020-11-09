package com.xiahan.webmagic.mq;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rabbitmq.client.Channel;
import com.xiahan.webmagic.entity.ManhuaImg;
import com.xiahan.webmagic.mq.cglib.CglibMqTestConsum;
import com.xiahan.webmagic.mq.cglib.CglibProxyFactory;
import com.xiahan.webmagic.mq.dynamic.MqTestConsumer;
import com.xiahan.webmagic.service.MsgLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:54:36
 */
@Slf4j
@Component
public class MqTest {

    @Autowired
    ManhuaMq manhuaMq;

    @Autowired
    MsgLogService msgLogService;

    @Autowired
    MqTestConsumer mqTestConsumer;

    @Autowired
    CglibMqTestConsum cglibMqTestConsum;

    @Transactional
    public void testMq(String msg) {
        ManhuaImg manhuaImg = new ManhuaImg();
        manhuaImg.setImgUrl(msg);
        manhuaImg.setTitle("测试mq");
        manhuaImg.setCreatTime(LocalDateTime.now(ZoneOffset.of("+8")));
        manhuaImg.setId(10L);
        manhuaImg.setDel(true);

        manhuaMq.sentRabbitMq(null, manhuaImg, MqExchangeAndQueuesAndRoutingkey.EXCHANGE_DIRECT_TEST_MANHUA.getMsg(), MqExchangeAndQueuesAndRoutingkey.BANDINGKEY_TEST_MANHUA.getMsg());
    }

//    使用代理模式替换
    /*
    @RabbitListener(queues = "manhuatest")
    public void testReviceMq(Message message, Channel channel) throws IOException {
        String body = new String(message.getBody());
        ManhuaImg manhuaImg = JSON.parseObject(body, ManhuaImg.class);
        log.info("收到信息 message = 【{}】", manhuaImg);
        String msgId = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");

        MsgLog msgLog = msgLogService.selectByMsgId(msgId);
        if(msgLog == null || msgLog.getStatus().equals(MqMsgStatusEnum.CONSUMED_SUCCESS.getCode())){
            log.info("重复消费， msg= [{}], msgId = [{}]", body, msgId);
            return ;
        }
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        // 其他动作。 flag 为其他动作标记
        boolean flag = false;
        try {
            System.out.println(" 收到的信息为：" + manhuaImg);
            flag = true;
        }catch (Exception ex){
            log.info("处理其他动作 失败。");
        }

        if(flag){
            msgLogService.updateStatus(msgId, MqMsgStatusEnum.CONSUMED_SUCCESS.getCode());
            channel.basicAck(deliveryTag, false);
        }else {
            channel.basicNack(deliveryTag, false, true);
        }
    }
    */

//    动态代理
    /*
    @RabbitListener(queues = "manhuatest")
    public void testReviceMq(Message message, Channel channel) throws IOException {
        BaseConsumerProxy baseConsumerProxy = new BaseConsumerProxy(mqTestConsumer, msgLogService);
        BaseConsumer consumer = (BaseConsumer)baseConsumerProxy.getRabbitmqConsumerProxy();
        consumer.consumer(message, channel);
    }
    */

//    cglib
    @RabbitListener(queues = "manhuatest")
    public void testReviceMq(Message message, Channel channel) throws IOException {
        CglibProxyFactory cglibProxyFactory = new CglibProxyFactory(cglibMqTestConsum, msgLogService);
        CglibMqTestConsum proxyInstance = (CglibMqTestConsum) cglibProxyFactory.getProxyInstance();
        proxyInstance.mqTest(message, channel);
    }
}

