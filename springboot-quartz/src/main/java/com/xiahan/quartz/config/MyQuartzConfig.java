package com.xiahan.quartz.config;

import javax.sql.DataSource;

import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.spi.JobFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

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
	
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean("dataSource")
	@Primary
	public DataSource DruidDataSource() {
		return DruidDataSourceBuilder.create().build();
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactory(DataSource dataSource,JobFactory jobFactory) {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean ();
		try {
			//设置覆盖已存在的任务
			schedulerFactory.setOverwriteExistingJobs(true);
	        //项目启动完成后，等待2秒后开始执行调度器初始化
			schedulerFactory.setStartupDelay(2);
	        //设置调度器自动运行
			schedulerFactory.setAutoStartup(true);
			schedulerFactory.setDataSource(dataSource);
			//设置配置文件位置
			schedulerFactory.setConfigLocation(new ClassPathResource("/quartz.properties"));
			schedulerFactory.setJobFactory(jobFactory);
		} catch (Exception e) {
			log.error("设置[SchedulerFactoryBean]出错：" + e);
		}
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
	
}
