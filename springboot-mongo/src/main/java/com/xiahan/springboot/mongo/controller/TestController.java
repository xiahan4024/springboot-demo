package com.xiahan.springboot.mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiahan.springboot.mongo.dao.CmsPageRepository;
import com.xiahan.springboot.mongo.entity.CmsPage;

/**
 * 
 * @author xiahan
 * @dateTime 2020年2月20日 下午10:37:27
 */
@RestController
public class TestController {
    
    @Autowired
    CmsPageRepository cmsPageRepository;
    
    
    @GetMapping("/test")
    public Object test01() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        return all;
    }

}
