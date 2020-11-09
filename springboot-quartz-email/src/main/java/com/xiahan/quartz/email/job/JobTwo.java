package com.xiahan.quartz.email.job;

import org.quartz.DisallowConcurrentExecution;
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
@DisallowConcurrentExecution
public class JobTwo extends QuartzJobBean{
	
	@Autowired
	QuartzEmailService quartzEmailService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	    EmailEntity emailEntity = new EmailEntity();
        emailEntity.setTitle("测试自动邮件--任务二");
        emailEntity.setTo(QuartzEmailController.TO);
        emailEntity.setContent("测试自动邮件--任务二");
        quartzEmailService.simpleEmail(emailEntity);
	}

}
