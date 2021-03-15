package com.xiahan.quartz.controller;

import com.xiahan.quartz.job.JobOne;
import com.xiahan.quartz.job.JobTwo;
import com.xiahan.quartz.util.ScheduledManagerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xiahan
 * @dateTime 2019年11月1日 下午10:50:57
 */
@Slf4j
@RestController
public class TestQuartzController {

    @Autowired
    ScheduledManagerUtil scheduledManagerUtil;

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

    @GetMapping("/job/change/{jobID}")
    public boolean modifyJob(@PathVariable String jobID) {
        try {
            if("one".equals(jobID)) {
                scheduledManagerUtil.modifyJobTime("xiahanJob", "xiahanJobGroup", "xiahanTrigger",
                        "xiahanTriggerGroup", "/5 * * * * ?");
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