package com.xiahan.java8.demo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author xiahan
 * @dateTime 2019年11月21日 下午9:32:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	private Integer id;
	
	private String name;
	
	private LocalDate birthday;
	
}
