package com.xiahan.quartz.job;

import com.xiahan.quartz.service.MyQuartzService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author xiahan
 * @dateTime 2019年10月31日 下午10:49:17
 */
@DisallowConcurrentExecution
public class JobTwo extends QuartzJobBean {

    @Autowired
    MyQuartzService myQuartzService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        myQuartzService.testQuartz("qiuhan");
    }

}
