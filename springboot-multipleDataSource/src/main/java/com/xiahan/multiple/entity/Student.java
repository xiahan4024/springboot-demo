package com.xiahan.multiple.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author xiahan
 * @dateTime 2019年10月30日 下午8:59:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	
	private Integer id;
	
	private String name;
	
	private Integer age;
	
	private String address;
	
//	@JsonFormat(pattern = "yyyy-MM-dd ")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private LocalDateTime birthday;

	/**
	 * 时间返回为空。
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birthday;

}
