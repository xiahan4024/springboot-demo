package com.xiahan.quartz.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiahan.quartz.email.entity.EmailEntity;
import com.xiahan.quartz.email.job.JobOne;
import com.xiahan.quartz.email.job.JobTwo;
import com.xiahan.quartz.email.service.QuartzEmailService;
import com.xiahan.quartz.email.util.ScheduledManagerUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2019年12月30日 下午8:05:00
 */
@Slf4j
@RestController
@RequestMapping("/quartz/email")
public class QuartzEmailController {

    public static final String TO = "867327587@qq.com";
    
    @Autowired
    QuartzEmailService quartzEmailService;
    
    @Autowired
    ScheduledManagerUtil scheduledManagerUtil;
    
    @GetMapping("/{message}")
    public boolean simpleEmail(@PathVariable String message) {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setTitle(message);
        emailEntity.setTo(TO);
        emailEntity.setContent(message);
        boolean result = quartzEmailService.simpleEmail(emailEntity);
        return result;
    }
    
    @GetMapping("/job/{jobID}")
    public boolean addJob(@PathVariable String jobID) {
        try {
            if(jobID.equals("one")) {
                scheduledManagerUtil.addJob("xiahanJob", "xiahanJobGroup", "xiahanTrigger", 
                        "xiahanTriggerGroup", JobOne.class, "/1 * * * * ?");
            }else{
                scheduledManagerUtil.addJob("xiahanJob1", "xiahanJobGroup1", "xiahanTrigger1", 
                        "xiahanTriggerGroup1", JobTwo.class, "/2 * * * * ?");
            }
            return true;
        } catch (Exception e) {
            log.error("添加任务出错"+ e);
            return false;
        }
    }
    
    @GetMapping("/job/{jobID}/{corn}")
    public boolean modifyJob(@PathVariable String jobID) {
        try {
            if("one".equals(jobID)) {
                scheduledManagerUtil.modifyJobTime("xiahanJob", "xiahanJobGroup", "xiahanTrigger", 
                        "xiahanTriggerGroup", "0 0 * * * ?");
            }else{
                scheduledManagerUtil.modifyJobTime("xiahanJob1", "xiahanJobGroup1", "xiahanTrigger1", 
                        "xiahanTriggerGroup1", "/10 * * * * ?");
            }
            return true;
        } catch (Exception e) {
            log.error("修改任务出错"+ e);
            return false;
        }
    }
}
