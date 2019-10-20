package com.xiahan.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xiahan.email.entity.EmailEntity;
import com.xiahan.email.service.EmailService;

/**
 *	测试邮件通用类
 * @author xiahan
 * @dateTime 2019年10月19日 下午11:37:07
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailTest {
	
	private static final String CCTO = "抄送给谁，多个抄送分号分割";
	private static final String TO = "发送给谁，多个分好分割";
	
	@Autowired
	EmailService emailService;
	
	EmailEntity email = new EmailEntity();
	
	@Before
	public void before() {
		email = new EmailEntity();
		email.setTo(TO);
//		email.setCcTo(CCTO);
	}
	
	/**
	 * @description 发送普通邮件 即：纯文本
	 * @author xiahan
	 * @dateTime 2019年10月19日 下午11:59:32
	 * @param @throws Exception
	 */
	@Test
	public void simple() throws Exception {
		String message = "测试文本";
		email.setTitle(message );
		email.setContent(message);
		emailService.chooseTypeEmail(email);
		System.in.read();
	}

	/**
	 * @description 测试 html邮件
	 * @author xiahan
	 * @dateTime 2019年10月20日 上午12:00:09
	 * @param @throws Exception
	 */
	@Test
	public void html() throws Exception {
		String html = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "    <h1>hello world ! 这是一封Html邮件!</h1>\n" +
                "</body>\n" +
                "</html>";
		String message = "测试---html";
		email.setTitle(message );
		email.setContent(html);
		email.setHtmlFlag(true);
		emailService.chooseTypeEmail(email);
		System.in.read();
	}
	
	/**
	 * @description 测试 图片 邮件
	 * @author xiahan
	 * @dateTime 2019年10月20日 上午12:06:58
	 * @param @throws Exception
	 */
	@Test
	public void picture() throws Exception {
		String html = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "    <h1>hello world ! 这是一封Html邮件!</h1>\n" +
                "<img src='cid:rscId'> \n "+
                "</body>\n" +
                "</html>";
		String message = "测试---html 内嵌图片";
		email.setTitle(message );
		email.setContent(html);
		email.setHtmlFlag(true);
		email.setPictureFlag(true);
		HashMap<String, String> pictureMap = new HashMap<>(16);
		pictureMap.put("rscId", "E:\\\\Temp\\\\timg.png");
		email.setPictureMap(pictureMap );
		emailService.chooseTypeEmail(email);
		System.in.read();
	}
	
	@Test
	public void file() throws Exception{
		String html = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "    <h1>hello world ! 这是一封Html邮件!</h1>\n" +
                "<img src='cid:rscId'> \n "+
                "</body>\n" +
                "</html>";
		String message = "测试---html 附件";
		email.setTitle(message );
		email.setContent(html);
		email.setHtmlFlag(true);
		email.setPictureFlag(true);
		email.setExtraFlag(true);
		HashMap<String, String> pictureMap = new HashMap<>(16);
		pictureMap.put("rscId", "E:\\Temp\\timg.png");
		email.setPictureMap(pictureMap );

		List<String> fileList = new ArrayList<String>();
		fileList.add("E:\\Temp\\timg.png");
		fileList.add("E:\\Temp\\测试文档.txt");
		email.setExtraList(fileList);
		
		emailService.chooseTypeEmail(email);
		System.in.read();
	}
	
	/**
	 * 
	 * @description 发送模板邮件
	 * @author xiahan
	 * @dateTime 2019年10月20日 下午12:29:00
	 * @param @throws Exception
	 */
	@Test
	public void templates() throws Exception{
		String message = "测试---html 模板";
		email.setTitle(message );
		email.setContent("");
		email.setHtmlFlag(true);
		email.setExtraFlag(true);
		email.setTemplateFlag(true);

		List<String> fileList = new ArrayList<String>();
		fileList.add("E:\\Temp\\timg.png");
		fileList.add("E:\\Temp\\测试文档.txt");
		email.setExtraList(fileList);
		
		email.setTemplate("/emailTemplate");
		Map<String,Object> map = new HashMap<>(16);
        map.put("username","世界，你好！");
        email.setVariables(map);
		
		emailService.chooseTypeEmail(email);
		System.in.read();
	}
}
