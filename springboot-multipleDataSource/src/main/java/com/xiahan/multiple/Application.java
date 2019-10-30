package com.xiahan.multiple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 	mybatis多数据源：基于注解方式
 * 		问题待解决：事件类型返回为空
 * @author xiahan
 * @dateTime 2019年10月30日 下午8:06:25
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
