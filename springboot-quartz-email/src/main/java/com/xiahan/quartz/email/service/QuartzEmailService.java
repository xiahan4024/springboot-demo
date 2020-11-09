package com.xiahan.quartz.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiahan.quartz.email.entity.EmailEntity;
import com.xiahan.quartz.email.mapper.QuartzEmailMapper;

/**
 * 
 * @author xiahan
 * @dateTime 2019年12月30日 下午8:06:51
 */
@Service
public class QuartzEmailService {
    
    @Autowired
    QuartzEmailMapper quartzEmailMapper;

    /**
     * 
     * @author xiahan
     * @dateTime 2019年12月30日 下午8:14:11
     * @param emailEntity
     * @return
     */
    public boolean simpleEmail(EmailEntity emailEntity) {
        System.out.println("--------------");
        try {
            quartzEmailMapper.simpleEmail(emailEntity);
            System.out.println("--------------");
            return true;
        } catch (Exception e) {
            System.out.println("--------------");
            return false;
        }
    }

}
