package com.xiahan.quartz.email.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import com.xiahan.quartz.email.entity.EmailEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2019年12月30日 下午8:07:20
 */
@Slf4j
@Component
public class QuartzEmailMapper {

    @Value("${spring.mail.username}")
    private String from;
    
    @Autowired
    JavaMailSender mailSender;
    
    @Autowired
    TemplateEngine templateEngine;
    

    /**
     * @describe：@Async不能再同一个类中使用。
     *              同一个类中使用就相当于：this.simpleEmail().没有经过spring容器，也就不存在代理
     *            Async注解失效和事务的注解失效原因差不多
     * @date：2019年10月19日 下午5:11:31
     * @param emailEntity
     */
    @Async
    public void simpleEmail(EmailEntity emailEntity) throws Exception {
        log.info("开始success");
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setFrom(from);
        simpleMail.setTo(emailEntity.getTo());
        /*抄送*/
        if(emailEntity.isCcToFlag()) {
            simpleMail.setCc(emailEntity.getCcTo());  
        }
        simpleMail.setSubject(emailEntity.getTitle());
        simpleMail.setText(emailEntity.getContent());
        mailSender.send(simpleMail);
        log.info("结束success");
    }

}
