package com.xiahan.quartz.util;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * 	解决quartz @Autowired 不能注入
 * @author xiahan
 * @dateTime 2019年11月1日 下午9:08:51
 */
public class MyJobFactory extends AdaptableJobFactory {

    @Autowired
    AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object createJobInstance = super.createJobInstance(bundle);
        autowireCapableBeanFactory.autowireBean(createJobInstance);
        return createJobInstance;
    }
}
