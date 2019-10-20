package com.xiahan.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiahan.email.entity.EmailEntity;
import com.xiahan.email.service.EmailService;

/**
 * @description：邮件
 * @author：xiahan
 * @date：2019年10月19日 下午3:36:57
 */
@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	EmailService emailService;
	
	private static final String CCTO = "抄送给谁，多个抄送分号分割";
	private static final String TO = "发送给谁，多个分好分割";
	
	@GetMapping("/simple/{message}")
	public boolean simpleEmail(@PathVariable String message) {
		EmailEntity emailEntity = new EmailEntity();
		emailEntity.setTitle(message);
		emailEntity.setTo(TO);
		emailEntity.setCcTo(CCTO);
		emailEntity.setContent(message);
		boolean result = emailService.simpleEmail(emailEntity);
		return result;
	}
	
	@GetMapping("/html/{message}")
	public boolean htmlEmail(@PathVariable String message) {
		EmailEntity emailEntity = new EmailEntity();
		emailEntity.setTitle(message);
		emailEntity.setTo(TO);
		emailEntity.setCcTo(CCTO);
		emailEntity.setContent(message);
		boolean result = emailService.htmlEmail(emailEntity);
		return result;
	}
	
	@GetMapping("/file/{message}")
	public boolean fileEmail(@PathVariable String message) {
		EmailEntity emailEntity = new EmailEntity();
		emailEntity.setTitle(message);
		emailEntity.setTo(TO);
		emailEntity.setCcTo(CCTO);
		emailEntity.setContent(message);
		boolean result = emailService.fileEmail(emailEntity);
		return result;
	}
	
	@GetMapping("/picture/{message}")
	public boolean pictureEmail(@PathVariable String message) {
		EmailEntity emailEntity = new EmailEntity();
		emailEntity.setTitle(message);
		emailEntity.setTo(TO);
		emailEntity.setCcTo(CCTO);
		emailEntity.setContent(message);
		boolean result = emailService.pictureEmail(emailEntity);
		return result;
	}

	
	@GetMapping("/template/{message}")
	public boolean templateEmail(@PathVariable String message) {
		EmailEntity emailEntity = new EmailEntity();
		emailEntity.setTitle(message);
		emailEntity.setTo(TO);
		emailEntity.setCcTo(CCTO);
		emailEntity.setContent(message);
		boolean result = emailService.templateEmail(emailEntity);
		return result;
	}
}
