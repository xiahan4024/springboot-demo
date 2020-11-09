package com.xiahan.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
/**
 * @description：
 * @author：xiahan
 * @date：2019年10月19日 下午3:51:39
 */
@EnableAsync
@SpringBootApplication
public class EmailApplication {

	public static void main(String[] args) throws Exception {
		 //java mail发邮件时附件名过长默认会被截断，附件名显示【tcmime.29121.29517.50430.bin】，主动设为false可正常显示附件名
        System.setProperty("mail.mime.splitlongparameters", "false");
		SpringApplication.run(EmailApplication.class, args);
	}

	
}
