package com.xiahan.multiple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiahan.multiple.entity.Student;
import com.xiahan.multiple.mapper.MultipleMapper;

/**
 * 
 * @author xiahan
 * @dateTime 2019年10月30日 下午8:53:35
 */
@Service
public class MultipleService {
	
	@Autowired
	MultipleMapper multipleMapper;

	/**
	 * @description TODO
	 * @author xiahan
	 * @dateTime 2019年10月30日 下午9:05:46
	 * @param 
	 */
	public Student primekeyNoValue() {
		return multipleMapper.primekeyNoValue();
	}

	/**
	 * @description TODO
	 * @author xiahan
	 * @dateTime 2019年10月30日 下午9:05:53
	 * @param 
	 */
	public Student primekey() {
		return multipleMapper.primekey();
	}

	/**
	 * @description TODO
	 * @author xiahan
	 * @dateTime 2019年10月30日 下午9:05:57
	 * @param 
	 */
	public Student datasource1() {
		return multipleMapper.datasource1();
	}

	/**
	 * @description TODO
	 * @author xiahan
	 * @dateTime 2019年10月30日 下午9:06:11
	 * @param 
	 */
	public Student datasource2() {
		return multipleMapper.datasource2();
	}

	/**
	 * @description TODO
	 * @author xiahan
	 * @dateTime 2019年10月30日 下午10:00:25
	 * @param @return
	 */
	public Student no() {
		return multipleMapper.noDatasource();
	}

}
