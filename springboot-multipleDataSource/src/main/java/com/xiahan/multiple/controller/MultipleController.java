package com.xiahan.multiple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.xiahan.multiple.entity.Student;
import com.xiahan.multiple.service.MultipleService;

/**
 * 
 * @author xiahan
 * @dateTime 2019年10月30日 下午8:39:36
 */
@RestController
public class MultipleController {

	@Autowired
	MultipleService multipleService;

	@GetMapping("/multiple/{type}")
	public Object test(@PathVariable String type) {
		Student student = null;
		switch (type) {
		case "no":
			student = multipleService.no();
			break;
		case "primekeyNoValue":
			student = multipleService.primekeyNoValue();
			break;
		case "primekey":
			student = multipleService.primekey();
			break;
		case "datasource1":
			student = multipleService.datasource1();
			break;
		case "datasource2":
			student = multipleService.datasource2();
			break;

		default:
			student = multipleService.primekey();
			break;
		}
		return student;
	}

}
