package com.xiahan.email.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @description：邮件实体
 * @author：xiahan
 * @date：2019年10月19日 下午3:43:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuppressWarnings("unused")
public class EmailEntity {
	
	/** 多名 接收者 彼此之间用","分割 */
	private String to;
	
	/** 抄送标记   */
	private boolean ccToFlag = false;
	
	/** 多名 抄送接者 彼此之间用","分割   */
	private String ccTo;

	/** 邮件标题 */
	private String title;

	/** 邮件正文   */
	private String content;
	
	/** 普通邮件、HTML邮件 */
	private boolean htmlFlag = true;
	
	/** 图片标记 */
	private boolean pictureFlag = false;;
	
	/** 图片位置 */
	private Map<String,String> pictureMap;
	
	/** 附件标记 */
	private boolean extraFlag = false;;
	
	/** 附件位置 */
	private List<String> extraList;

	/** 模板标记 */
	private boolean templateFlag = false;
	
	/** 模板位置 */
	private String template;
	
	/** 模板参数 */
	private Map<String, Object> variables;
	
}
