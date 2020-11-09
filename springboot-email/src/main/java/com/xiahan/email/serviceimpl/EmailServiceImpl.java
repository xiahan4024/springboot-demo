package com.xiahan.email.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiahan.email.entity.EmailEntity;
import com.xiahan.email.mapper.EmailMapper;
import com.xiahan.email.service.EmailService;
/**
 * @description：
 * @author：xiahan
 * @date：2019年10月19日 下午3:51:49
 */
@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	EmailMapper emailMapper;
	
	@Override
	public boolean chooseTypeEmail(EmailEntity emailEntity) {
		System.out.println("--------------");
		emailMapper.chooseTypeEmail(emailEntity);
		System.out.println("--------------");
		return true;
	}
	
	@Override
	public boolean templateEmail(EmailEntity emailEntity) {
		System.out.println("--------------");
		try {
			emailMapper.templateEmail(emailEntity);
			System.out.println("--------------");
			return true;
		} catch (Exception e) {
			System.out.println("--------------");
			return true;
		}
	}
	
	@Override
	public boolean pictureEmail(EmailEntity emailEntity) {
		System.out.println("--------------");
		try {
			emailMapper.pictureEmail(emailEntity);
			System.out.println("--------------");
			return true;
		} catch (Exception e) {
			System.out.println("--------------");
			return true;
		}
	}
	
	@Override
	public boolean fileEmail(EmailEntity emailEntity) {
		System.out.println("--------------");
		try {
			emailMapper.fileEmail(emailEntity);
			System.out.println("--------------");
			return true;
		} catch (Exception e) {
			System.out.println("--------------");
			return true;
		}
	}
	
	@Override
	public boolean htmlEmail(EmailEntity emailEntity) {
		System.out.println("--------------");
		try {
			emailMapper.htmlEmail(emailEntity);
			System.out.println("--------------");
			return true;
		} catch (Exception e) {
			System.out.println("--------------");
			return true;
		}
	}
	
	@Override
	public boolean simpleEmail(EmailEntity emailEntity) {
		System.out.println("--------------");
		try {
			emailMapper.simpleEmail(emailEntity);
			System.out.println("--------------");
			return true;
		} catch (Exception e) {
			System.out.println("--------------");
			return false;
		}
	}

}
