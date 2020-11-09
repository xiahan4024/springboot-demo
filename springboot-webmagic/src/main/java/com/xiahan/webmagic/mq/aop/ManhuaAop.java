package com.xiahan.webmagic.mq.aop;

import com.rabbitmq.client.Channel;
import com.xiahan.webmagic.entity.MsgLog;
import com.xiahan.webmagic.mq.MqMsgStatusEnum;
import com.xiahan.webmagic.service.MsgLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:51:09
 * @Description: AOP 思想
 */
@Slf4j
@Aspect
@Component
public class ManhuaAop {

  @Autowired
  MsgLogService msgLogService;

  @Pointcut("execution(public * com.xiahan.webmagic.mq.cglib.CglibMqTestConsum.*(..))")
  public void webmagicPointcut(){
  }

//  @Before("webmagicPointcut()")
  public void webmagicBefore(JoinPoint joinPoint){
      // 下面两个数组中，参数值和参数名的个数和位置是一一对应的。
      log.info("========  before  ===============");
      Object[] args = joinPoint.getArgs(); // 参数值
      String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames(); // 参数名
  }

//  @After("webmagicPointcut()")
  public void webmagicAfter(JoinPoint joinPoint){
      log.info("========  after  ===============");
      // 下面两个数组中，参数值和参数名的个数和位置是一一对应的。
      Object[] args = joinPoint.getArgs(); // 参数值
      String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames(); // 参数名
      log.info("args = [{}]", args);
      log.info("argNames = [{}]", argNames);
  }

  @Around("webmagicPointcut()")
  public Object wenmagicAround(ProceedingJoinPoint joinPoint) throws Throwable {
      //获取方法参数值数组
      Object[] args = joinPoint.getArgs();
      //得到其方法签名
//      MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
      //获取方法参数类型数组
//      Class[] paramTypeArray = methodSignature.getParameterTypes();

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
          System.out.println("================  AOP 开始  =================");
          Object result = joinPoint.proceed(args);
          System.out.println("================  AOP 结束  =================");
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

  @Before("execution(public * com.xiahan.webmagic.controller.*.*(..))")
  public void doBefore(JoinPoint joinPoint){
      // 接收到请求，记录请求内容
      log.info("Controller Aspect.doBefore()");
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      HttpServletRequest request = attributes.getRequest();
      // 记录下请求内容
      log.info("URL : " + request.getRequestURL().toString());
      log.info("HTTP_METHOD : " + request.getMethod());
      // 获取不正确，可以找找工具类方法
      log.info("IP : " + request.getRemoteAddr());
      log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
      log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
      //获取所有参数方法一：
      Enumeration<String> enu=request.getParameterNames();
      while(enu.hasMoreElements()){
          String paraName=(String)enu.nextElement();
          System.out.println(paraName+": "+request.getParameter(paraName));
      }
  }
}
