package com.xiahan.email.service;

import com.xiahan.email.entity.EmailEntity;

/**
 * @description：邮箱接口
 * @author：xiahan
 * @date：2019年10月19日 下午3:39:03
 */
public interface EmailService {

	/**
	 * @description： 发送文本邮箱
	 * @date：2019年10月19日 下午3:47:50
	 * @param emailEntity  
	 * @return 
	 */
	boolean simpleEmail(EmailEntity emailEntity);

	/**
	 * @describe：发送html邮件
	 * @date：2019年10月19日 下午5:31:52
	 * @param emailEntity
	 * @return
	 */
	boolean htmlEmail(EmailEntity emailEntity);

	/**
	 * @describe：发送文件邮箱
	 * @date：2019年10月19日 下午5:47:53
	 * @param emailEntity
	 * @return
	 */
	boolean fileEmail(EmailEntity emailEntity);

	/**
	 * @describe：发送图片邮件
	 * @date：2019年10月19日 下午6:12:26
	 * @param emailEntity
	 * @return
	 */
	boolean pictureEmail(EmailEntity emailEntity);

	/**
	 * @describe：发送模板邮箱
	 * @date：2019年10月19日 下午7:01:44
	 * @param emailEntity
	 * @return
	 */
	boolean templateEmail(EmailEntity emailEntity);

	/**
	 * @describe：发送邮件，根据emailEntity里面的设置自动选择普通邮件、图片、html邮件、模板邮件
	 * @date：2019年10月19日 下午10:33:25
	 * @param emailEntity
	 * @return
	 */
	boolean chooseTypeEmail(EmailEntity emailEntity);

}
