package com.xiahan.springboot.mongo.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author xiahan
 * @dateTime 2020年2月20日 下午10:40:42
 */
@Data
@ToString
public class CmsPageParam {
   //参数名称
    private String pageParamName;
    //参数值
    private String pageParamValue;

}
