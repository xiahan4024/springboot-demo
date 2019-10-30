package com.xiahan.multiple.config;

import com.xiahan.multiple.util.MyDataSourceType;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2019年10月30日 下午8:25:29
 */
@Slf4j
public class DataSourceContextHolder {
	
	private static final ThreadLocal<String> CONTEXTHOLDER = new ThreadLocal<>();
	
	public static void setDatasource(MyDataSourceType type) {
		CONTEXTHOLDER.set(type.name());
		
	}
	
	public static String getDatasource() {
		return CONTEXTHOLDER.get();
	}
	
	public static void removeDatasource() {
		CONTEXTHOLDER.remove();
	}

}
