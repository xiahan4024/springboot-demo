package com.xiahan.multiple.config;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import lombok.extern.slf4j.Slf4j;


/**
 * 	自定义路由数据源
 * @author xiahan
 * @dateTime 2019年10月30日 下午8:23:20
 */
@Slf4j
public class DynamicDataSource  extends AbstractRoutingDataSource{
	
	@Override
	protected Object determineCurrentLookupKey() {
		log.info("--DynamicDataSource---切换到[{}]",DataSourceContextHolder.getDatasource());
		return DataSourceContextHolder.getDatasource();
	}


}
