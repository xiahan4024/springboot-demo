package com.xiahan.email.mapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.xiahan.email.entity.EmailEntity;

/**
 * @description：
 * @author：xiahan
 * @date：2019年10月19日 下午3:40:46
 */
@Component
public class EmailMapper {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailMapper.class);
	
	@Value("${spring.mail.username}")
	private String from;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	TemplateEngine templateEngine;
	

	/**
	 * @describe：@Async不能再同一个类中使用。
	 * 				同一个类中使用就相当于：this.simpleEmail().没有经过spring容器，也就不存在代理
	 * 			  Async注解失效和事务的注解失效原因差不多
	 * @date：2019年10月19日 下午5:11:31
	 * @param emailEntity
	 */
	@Async
	public void simpleEmail(EmailEntity emailEntity) throws Exception {
		logger.info("开始success");
		SimpleMailMessage simpleMail = new SimpleMailMessage();
		simpleMail.setFrom(from);
		simpleMail.setTo(emailEntity.getTo());
		/*抄送*/
		if(emailEntity.isCcToFlag()) {
			simpleMail.setCc(emailEntity.getCcTo());  
		}
		simpleMail.setSubject(emailEntity.getTitle());
		simpleMail.setText(emailEntity.getContent());
		mailSender.send(simpleMail);
		logger.info("结束success");
	}
	
	@Async
	public void htmlEmail(EmailEntity emailEntity) throws Exception {
		logger.info("开始success");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		// 有附件、图片、html为true
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(from);
		mimeMessageHelper.setTo(emailEntity.getTo());
		/* 抄送 */
		if(emailEntity.isCcToFlag()) {
			mimeMessageHelper.setCc(emailEntity.getCcTo());  
		}
		String html = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "    <h1>hello world ! 这是一封Html邮件!</h1>\n" +
                "</body>\n" +
                "</html>";

		//发送html：true	源码：false
		mimeMessageHelper.setText(html, true);
		mimeMessageHelper.setSubject(emailEntity.getTitle());
		mailSender.send(mimeMessage);
		logger.info("结束success");
	}

	@Async
	public void fileEmail(EmailEntity emailEntity) throws Exception {
		logger.info("开始success");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		String html = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "    <h1>hello world ! 这是一封Html邮件!</h1>\n" +
                "</body>\n" +
                "</html>";
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(from);
		mimeMessageHelper.setTo(emailEntity.getTo());
		if(emailEntity.isCcToFlag()) {
			mimeMessageHelper.setCc(emailEntity.getCcTo());  
		}
		mimeMessageHelper.setSubject(emailEntity.getTitle());
		mimeMessageHelper.setText(html, true);
		FileSystemResource  file = new FileSystemResource(new File("E:\\Temp\\测试文档.txt"));
		String attachmentFilename = file.getFilename();
		System.out.println(attachmentFilename);
		//附件名	附件
		mimeMessageHelper.addAttachment(attachmentFilename, file);
		mailSender.send(mimeMessage);
		logger.info("结束success");
		
	}

	@Async
	public void pictureEmail(EmailEntity emailEntity) throws Exception {
		logger.info("开始success");
		String rscId = "test";
		String html = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "    <h1>hello world ! 这是一封Html邮件!</h1>\n" +
                "<img src=\'cid:" + rscId + "\'> \n "+
                "<img src='cid:rscId'> \n "+
                "</body>\n" +
                "</html>";
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(from);
		mimeMessageHelper.setTo(emailEntity.getTo());
		if(emailEntity.isCcToFlag()) {
			mimeMessageHelper.setCc(emailEntity.getCcTo());  
		}
		mimeMessageHelper.setSubject(emailEntity.getTitle());
		mimeMessageHelper.setText(html, true);
		FileSystemResource  file = new FileSystemResource(new File("E:\\Temp\\测试文档.txt"));
		String attachmentFilename = file.getFilename();
		System.out.println(attachmentFilename);
		mimeMessageHelper.addAttachment(attachmentFilename, file);
		FileSystemResource picFileSystemResource  = new FileSystemResource(new File("E:\\Temp\\timg.jfif"));
		//内嵌图片
		mimeMessageHelper.addInline(rscId , picFileSystemResource);
		mimeMessageHelper.addInline("rscId" , picFileSystemResource);
		mailSender.send(mimeMessage);
		logger.info("结束success");
		
	}

	@Async
	public void templateEmail(EmailEntity emailEntity) throws Exception {
		logger.info("开始success");
		 MimeMessage mimeMessage = mailSender.createMimeMessage();
         MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
         messageHelper.setFrom(from);
         messageHelper.setTo(emailEntity.getTo());
         messageHelper.setSubject(emailEntity.getTitle());
         
         // 使用模板thymeleaf Context
         //这个包import org.thymeleaf.context.Context
         Context context = new Context();
         Map<String,Object> map = new HashMap<>(16);
         map.put("username",emailEntity.getTitle());
         //定义模板数据
         context.setVariables(map);
         //获取thymeleaf的html模板:/springboot-email/src/main/resources/templates/emailTemplate.html
         String emailContent = templateEngine.process("/emailTemplate",context);
         messageHelper.setText(emailContent,true);
         //发送邮件
         mailSender.send(mimeMessage);
		
		
		
		logger.info("结束success");
		
	}

	@Async
	public void chooseTypeEmail(EmailEntity emailEntity) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, emailEntity.isHtmlFlag());
			
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setTo(emailEntity.getTo().split(","));
			if(emailEntity.isCcToFlag()) {
				mimeMessageHelper.setCc(emailEntity.getCcTo().split(","));
			}
			mimeMessageHelper.setSubject(emailEntity.getTitle());
			if(emailEntity.isTemplateFlag()) {
				Context contentContext = new Context();
				contentContext.setVariables(emailEntity.getVariables());
				String process = templateEngine.process(emailEntity.getTemplate(), contentContext);
				emailEntity.setContent(process);
			}
			/** 设置为html时必须放在前面，不然图片没效果 */
			mimeMessageHelper.setText(emailEntity.getContent(), emailEntity.isHtmlFlag());
			
			if(emailEntity.isPictureFlag()) {
				emailEntity.getPictureMap().entrySet().stream().forEach(item->{
					FileSystemResource file = new FileSystemResource(new File(item.getValue()));
					if(!file.exists()) {
						logger.error("邮件图片不存在[{}][{}]",item.getKey(),item.getValue());
						throw new RuntimeException("邮件图片不存在");
					}
					try {
						mimeMessageHelper.addInline(item.getKey(), file );
					} catch (Exception e) {
						logger.error("邮件图片出错，邮件发送失败。" + e);
						throw new RuntimeException(e);
					}
				});
				
			}
			if(emailEntity.isExtraFlag()) {
				emailEntity.getExtraList().forEach(item->{
					FileSystemResource file = new FileSystemResource(new File(item));
					if(!file.exists()) {
						logger.error("邮件文件不存在[{}]",item.toString());
						throw new RuntimeException("邮件文件不存在");
					}
					String attachmentFilename = file.getFilename();
					try {
						mimeMessageHelper.addAttachment(attachmentFilename, file);
						System.out.println(attachmentFilename);
					} catch (MessagingException e) {
						logger.error("邮件附件出错，邮件发送失败。" + e);
						throw new RuntimeException(e);
					}
				});
			}
			mailSender.send(mimeMessage);
			logger.error("邮件发送成功。");
		} catch (Exception e) {
			logger.error("邮件发送失败。" + e);
		}
	}

}
