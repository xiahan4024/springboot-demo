package com.xiahan.webmagic.mq.dynamic;

import java.lang.reflect.Proxy;

import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;
import com.xiahan.webmagic.entity.MsgLog;
import com.xiahan.webmagic.mq.MqMsgStatusEnum;
import com.xiahan.webmagic.service.MsgLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:52:25
 */
@Slf4j
@Deprecated
public class BaseConsumerProxy {

    private Object target;

    private MsgLogService msgLogService;

    @Deprecated
    public BaseConsumerProxy(Object target, MsgLogService msgLogService){
        this.target = target;
        this.msgLogService = msgLogService;
    }

    @Deprecated
    public Object getRabbitmqConsumerProxy(){
        Object result = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
            (proxy, method, args) -> {
                Message message = (Message)args[0];
                Channel channel = (Channel)args[1];
                String msgId = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");

                MsgLog msgLog = msgLogService.selectByMsgId(msgId);
                if(msgLog == null || msgLog.getStatus().equals(MqMsgStatusEnum.CONSUMED_SUCCESS.getCode())){
                    log.info("重复消费， msgId = [{}]", msgId);
                    return null;
                }
                long deliveryTag = message.getMessageProperties().getDeliveryTag();
                // 其他动作。 flag 为核心逻辑处理错误标记
                boolean flag = false;
                Object invoke = null;
                try {
                    System.out.println("================  dynamic 开始  =================");
                    invoke = method.invoke(target, args);
                    System.out.println("================ dynamic  结束  =================");
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
                return invoke;
            }
        );
        return result;
    }
}

