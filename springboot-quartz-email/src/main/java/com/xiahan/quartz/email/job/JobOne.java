package com.xiahan.quartz.email.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.xiahan.quartz.email.controller.QuartzEmailController;
import com.xiahan.quartz.email.entity.EmailEntity;
import com.xiahan.quartz.email.service.QuartzEmailService;

/**
 * 
 * @author xiahan
 * @dateTime 2019年10月31日 下午10:49:17
 */
//@DisallowConcurrentExecution
public class JobOne extends QuartzJobBean{

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	QuartzEmailService quartzEmailService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	    EmailEntity emailEntity = new EmailEntity();
        emailEntity.setTitle("测试自动邮件--任务一");
        emailEntity.setTo(QuartzEmailController.TO);
        String content = "当前时间为：" + LocalDateTime.now().format(FORMATTER);
        emailEntity.setContent(content);
//        emailEntity.setContent("测试自动邮件--任务一");
        quartzEmailService.simpleEmail(emailEntity);
	}
	
}
