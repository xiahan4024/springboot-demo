package com.xiahan.multiple.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.xiahan.multiple.util.MyDataSourceType;

/**
 * 	配置三个数据源
 * @author xiahan
 * @dateTime 2019年10月30日 下午8:08:55
 */
@Configuration
public class DruiConfig {
	
	@Bean("primekeyDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.primekey")
	public DataSource primekeyDataSource() {
		return DruidDataSourceBuilder.create().build();
	}
	
	@Bean("dataSource1")
	@ConfigurationProperties(prefix = "spring.datasource.datasource1")
	public DataSource dataSource1() {
		return DruidDataSourceBuilder.create().build();
	}
	
	@Bean("dataSource2")
	@ConfigurationProperties(prefix = "spring.datasource.datasource2")
	public DataSource dataSource2() {
		return DruidDataSourceBuilder.create().build();
	}
	
	@Bean
	@Primary
	public DataSource multipleDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		/**
		 * 设置默认数据源
		 */
		dynamicDataSource.setDefaultTargetDataSource(primekeyDataSource());
		
		/**
		 * 设置多数据源
		 */
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>(16);
		targetDataSources.put(MyDataSourceType.primekey.name(), primekeyDataSource());
		targetDataSources.put(MyDataSourceType.datasourceOne.name(), dataSource1());
		targetDataSources.put(MyDataSourceType.datasourceTwo.name(), dataSource2());
		dynamicDataSource.setTargetDataSources(targetDataSources );
		return dynamicDataSource;
	}
	
	/**
	 * 配置事务
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(multipleDataSource());
		
	}
	
	

}
