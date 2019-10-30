package com.xiahan.multiple.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xiahan.multiple.util.MyDataSourceType;

/**
 * 	多数据源注解
 * @author xiahan
 * @dateTime 2019年10月30日 下午8:39:06
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
	
	MyDataSourceType value() default MyDataSourceType.primekey;

}
