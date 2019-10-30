package com.xiahan.multiple.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.xiahan.multiple.annotation.DataSource;
import com.xiahan.multiple.config.DataSourceContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2019年10月30日 下午8:42:47
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class DataSourceAnnotationAspect {
	
	@Before(value = "@annotation(dataSource)")
	public void before(JoinPoint joinPoint,DataSource dataSource) {
		try {
			DataSourceContextHolder.setDatasource(dataSource.value());
			log.info("--DataSourceAnnotationAspect---切换到[{}]数据源",dataSource.value());
		} catch (Exception e) {
			log.info("注解切换数据源出错[{}]:" + e,dataSource);
		}
	}
	
	@After(value = "@annotation(dataSource)")
	public void after(JoinPoint joinPoint,DataSource dataSource) {
		DataSourceContextHolder.removeDatasource();
	}

}
