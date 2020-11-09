package com.xiahan.webmagic.mq.cglib;

import com.rabbitmq.client.Channel;
import com.xiahan.webmagic.entity.MsgLog;
import com.xiahan.webmagic.mq.MqMsgStatusEnum;
import com.xiahan.webmagic.service.MsgLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:51:52
 * @Description: Cglib 代理逻辑
 */
@Slf4j
public class CglibProxyFactory implements MethodInterceptor {

    private Object target;

    private MsgLogService msgLogService;

    public CglibProxyFactory(Object target, MsgLogService msgLogService){
        this.target = target;
        this.msgLogService = msgLogService;
    }

    public Object getProxyInstance(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
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
        Object invokeObject = null;
        try {
            System.out.println("================ Cglib  开始  =================");
            invokeObject = method.invoke(target, args);
            System.out.println("================ Cglib  结束  =================");
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
        return invokeObject;
    }
}

