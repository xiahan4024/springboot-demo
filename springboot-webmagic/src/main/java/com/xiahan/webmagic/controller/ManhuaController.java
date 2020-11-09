package com.xiahan.webmagic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiahan.webmagic.mq.MqTest;
import com.xiahan.webmagic.service.ManhuaService;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:48:08
 */
@RestController
@RequestMapping("/manhua")
public class ManhuaController {

    @Autowired
    ManhuaService manhuaService;

    @Autowired
    MqTest mqTest;

    @GetMapping("/get")
    public void getManhuaImgUrl(){
        manhuaService.manhuaSaveimgAndDown("save img");
    }

    @GetMapping("/down")
    public void downManhuaImgUrl(){
        manhuaService.manhuaSaveimgAndDown("down img");
    }

    @GetMapping("/xiaoshuo")
    public void downXiaoShuo(){
        manhuaService.manhuaSaveimgAndDown("xiaoshuo");
    }

    @GetMapping("/testmq/{msg}")
    public void testMq(@PathVariable("msg") String msg){
        mqTest.testMq(msg);
    }

}

