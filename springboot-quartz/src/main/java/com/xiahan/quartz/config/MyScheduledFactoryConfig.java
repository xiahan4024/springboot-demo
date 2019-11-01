package com.xiahan.quartz.config;

import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 
 * @author xiahan
 * @dateTime 2019年11月1日 下午9:50:25
 */
@Configuration
public class MyScheduledFactoryConfig {
	
	@Bean
	public AdaptableJobFactory MyJobFactory() {
		return new MyJobFactory();
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactory(JobFactory jobFactory) {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean ();
		
		schedulerFactory.setJobFactory(jobFactory);
		return schedulerFactory;
	}

}
