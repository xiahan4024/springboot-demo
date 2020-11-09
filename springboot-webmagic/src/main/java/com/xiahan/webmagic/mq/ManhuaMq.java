package com.xiahan.webmagic.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.xiahan.webmagic.entity.MsgLog;
import com.xiahan.webmagic.mq.cglib.CglibMqTestConsum;
import com.xiahan.webmagic.mq.cglib.CglibProxyFactory;
import com.xiahan.webmagic.mq.dynamic.*;
import com.xiahan.webmagic.service.MsgLogService;
import com.xiahan.webmagic.webmagic.WebmagicMain;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:53:46
 * @Description: manhua mq处理
 * 解析网页、下载图片
 *        @RabbitListener 注解，处理mq 信息。但是有许多重复的东西，比如检查重复消费， 处理过程失败，发送ack、以及不发送ack等一模一样的代码
 *        只是核心处理不同，可以使用代理模式，将其相同的部分抽取出来即可。
 */
@Slf4j
@Component
public class ManhuaMq {

    @Autowired
    MsgLogService msgLogService;

    @Autowired
    WebmagicMain webmagicMain;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    XiaoshuoConsumer xiaoshuoConsumer;

    @Autowired
    ManhuaDownImgConsumer manhuaDownImgConsumer;

    @Autowired
    ManhuaImgUrlConsumer manhuaImgUrlConsumer;

    @Autowired
    CglibMqTestConsum cglibMqTestConsum;

    @RabbitListener(queues = "manhua")
    public void manhuaSaveimgAndDownAop(Message message, Channel channel) throws IOException{
        String body = new String(message.getBody());
        String msg = JSON.parseObject(body, String.class);
        if(Objects.equals(msg, "save img")){
            cglibMqTestConsum.manhuaGetImgUrl(message, channel);
        }else if(Objects.equals(msg, "down img")){
            cglibMqTestConsum.manhuaDownImg(message, channel);
        }else if(Objects.equals(msg, "xiaoshuo")){
            cglibMqTestConsum.xiaoshuo(message, channel);
        }
    }

    // cglib 代理
//    @RabbitListener(queues = "manhua")
    public void manhuaSaveimgAndDownCglib(Message message, Channel channel) throws IOException {
        CglibMqTestConsum proxyInstance = (CglibMqTestConsum) new CglibProxyFactory(cglibMqTestConsum, msgLogService).getProxyInstance();
        String body = new String(message.getBody());
        String msg = JSON.parseObject(body, String.class);
        if(Objects.equals(msg, "save img")){
            proxyInstance.manhuaGetImgUrl(message, channel);
        }else if(Objects.equals(msg, "down img")){
            proxyInstance.manhuaDownImg(message, channel);
        }else if(Objects.equals(msg, "xiaoshuo")){
            proxyInstance.xiaoshuo(message, channel);
        }
    }

    // 动态代理（JDK代理）
//    @RabbitListener(queues = "manhua")
    @Deprecated
    public void manhuaSaveimgAndDownDynamic(Message message, Channel channel) throws IOException {
        BaseConsumer baseConsumer = null;
        String body = new String(message.getBody());
        String msg = JSON.parseObject(body, String.class);
        if(Objects.equals(msg, "save img")){
            baseConsumer = manhuaImgUrlConsumer;
        }else if(Objects.equals(msg, "down img")){
            baseConsumer = manhuaDownImgConsumer;
        }else if(Objects.equals(msg, "xiaoshuo")){
            baseConsumer = xiaoshuoConsumer;
        }
        BaseConsumerProxy baseConsumerProxy = new BaseConsumerProxy(baseConsumer, msgLogService);
        BaseConsumer consumer = (BaseConsumer) baseConsumerProxy.getRabbitmqConsumerProxy();
        consumer.consumer(message, channel);
    }

//    动态代理替换
//    @RabbitListener(queues = "manhua")
    @Deprecated
    public void manhuaSaveimgAndDown(Message message, Channel channel) throws IOException {
        String body = new String(message.getBody());
        String msg = JSON.parseObject(body, String.class);
        if(StringUtils.isBlank(msg)){
            log.info("参数错误，请输入参数");
        }
        String msgId = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
        MsgLog msgLog = msgLogService.selectByMsgId(msgId);
        if(msgLog == null || msgLog.getStatus().equals(MqMsgStatusEnum.CONSUMED_SUCCESS.getCode())){
            log.info("重复消费， msg= [{}], msgId = [{}]", msg, msgId);
            return ;
        }
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        boolean flag = false;
        try {
            if(Objects.equals(msg, "save img")){
                webmagicMain.getManhuaImgUrl();
            }else if(Objects.equals(msg, "down img")){
                webmagicMain.downManhuaImgUrl();
            }else if(Objects.equals(msg, "xiaoshuo")){
                webmagicMain.xiaoshuo();
            }
            flag = true;
        }catch (Exception ex){
            log.info("处理其他动作 失败。message = 【{}】, ex = 【{}】", ex.getMessage(), ex);
        }

        if(flag){
            msgLogService.updateStatus(msgId, MqMsgStatusEnum.CONSUMED_SUCCESS.getCode());
            channel.basicAck(deliveryTag, false);
        }else {
            channel.basicNack(deliveryTag, false, true);
        }
    }

    @Transactional
    public void sentRabbitMq(String msgId, Object msg, String exchange, String bading){
        if(StringUtils.isBlank(msgId)){
            msgId = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() + "";
        }
        CorrelationData correlationData = new CorrelationData(msgId);
        MsgLog msgLog = new MsgLog(msgId, msg, exchange, bading);
        msgLogService.insertMas(msgLog);
        rabbitTemplate.convertAndSend(exchange, bading, msg, correlationData);
    }

}

