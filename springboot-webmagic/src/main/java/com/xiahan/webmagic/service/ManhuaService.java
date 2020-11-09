package com.xiahan.webmagic.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiahan.webmagic.entity.ManhuaImg;
import com.xiahan.webmagic.mapper.ManhuaMapper;
import com.xiahan.webmagic.mq.ManhuaMq;
import com.xiahan.webmagic.mq.MqExchangeAndQueuesAndRoutingkey;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:54:58
 */
@Slf4j
@Service
public class ManhuaService {

    @Autowired
    ManhuaMq manhuaMq;

    @Autowired
    ManhuaMapper manhuaMapper;

    public void manhuaSaveimgAndDown(String info) {
        manhuaMq.sentRabbitMq(null, info, MqExchangeAndQueuesAndRoutingkey.EXCHANGE_DIRECT_MANHUA.getMsg(), MqExchangeAndQueuesAndRoutingkey.BANDINGKEY_MANHUA.getMsg());
    }

    public void addManhuaImgUrl(ManhuaImg manhuaImg){
        manhuaImg.setCreatTime(LocalDateTime.now());
        manhuaImg.setDel(false);
        int i = manhuaMapper.selectByImgUrl(manhuaImg.getImgUrl());
        if( i == 0 ){
            manhuaMapper.addOne(manhuaImg);
            log.info("插入一条数据 【{}】", LocalDateTime.now());
        }
    }
}

