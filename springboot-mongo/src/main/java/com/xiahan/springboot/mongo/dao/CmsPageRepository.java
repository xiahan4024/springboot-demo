package com.xiahan.springboot.mongo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.xiahan.springboot.mongo.entity.CmsPage;

/**
 * 
 * @author xiahan
 * @dateTime 2020年2月20日 下午10:39:42
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    //根据页面名称查询
    CmsPage findByPageName(String pageName);
}
