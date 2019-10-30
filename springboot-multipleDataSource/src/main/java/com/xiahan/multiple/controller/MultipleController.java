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
		Student Student = null;
		switch (type) {
		case "no":
			Student = multipleService.no();
			break;
		case "primekeyNoValue":
			Student = multipleService.primekeyNoValue();
			break;
		case "primekey":
			Student = multipleService.primekey();
			break;
		case "datasource1":
			Student = multipleService.datasource1();
			break;
		case "datasource2":
			Student = multipleService.datasource2();
			break;

		default:
			Student = multipleService.primekey();
			break;
		}
		return Student;
	}

}
