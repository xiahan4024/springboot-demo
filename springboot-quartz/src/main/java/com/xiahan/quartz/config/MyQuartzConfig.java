package com.xiahan.quartz.config;

import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2019年11月1日 下午9:50:25
 */
@Slf4j
@Configuration
public class MyQuartzConfig {
	
	@Bean
	public AdaptableJobFactory myJobFactory() {
		return new MyJobFactory();
	}
	
	@Bean
    public Properties quartzProperties() throws Exception {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
	
	@Bean
	public SchedulerFactoryBean schedulerFactory(JobFactory jobFactory) {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean ();
		try {
			schedulerFactory.setQuartzProperties(quartzProperties());
		} catch (Exception e) {
			log.error("设置quartz.properties出错：" + e);
		}
		schedulerFactory.setJobFactory(jobFactory);
		return schedulerFactory;
	}

	/**
	 * 	quartz初始化监听器
	 * @author xiahan
	 * @dateTime 2019年11月2日 下午9:55:10
	 * @return
	 */
    @Bean
    public QuartzInitializerListener executorListener() {
       return new QuartzInitializerListener();
    }
	
//    @Bean
//    public Scheduler scheduler(SchedulerFactoryBean schedulerFactory){
//        return schedulerFactory.getScheduler();
//    }
	
	
	
	
	
	
}
