package com.xiahan.webmagic.mq.cglib;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.xiahan.webmagic.entity.ChapterWithAnnotation;
import com.xiahan.webmagic.entity.ManhuaImg;
import com.xiahan.webmagic.mapper.ManhuaMapper;
import com.xiahan.webmagic.util.DownImg;
import com.xiahan.webmagic.webmagic.ManhuaPageProcessor;
import com.xiahan.webmagic.webmagic.TxtPipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:51:33
 */
@Slf4j
@Component
public class CglibMqTestConsum {

    @Value("${webmagic.savePath}")
    private String savePath;

    @Value("${webmagic.cookie}")
    private String cookie;

    @Value("${webmagic.redishost}")
    private String redishost;

    @Value("${webmagic.url}")
    private String url;

    @Value("${webmagic.saveThread}")
    private Integer saveThread;

    @Autowired
    ManhuaPageProcessor manhuaPageProcessor;

    @Autowired
    ManhuaMapper manhuaMapper;

    @Autowired
    DownImg downImg;

    public void mqTest(Message message, Channel channel) throws IOException {
        String body = new String(message.getBody());
        ManhuaImg manhuaImg = JSON.parseObject(body, ManhuaImg.class);
        System.out.println(" 收到的信息为：" + manhuaImg);
        log.info("收到信息 message = 【{}】", manhuaImg);
    }

    public void manhuaGetImgUrl(Message message, Channel channel) throws IOException {
        /*
        Spider.create(manhuaPageProcessor)
                .addUrl(url)
                .setScheduler(new RedisScheduler(redishost))
                .thread(saveThread)
                .run();
                */
        System.out.println("-------------------- save img 运行完毕--------------------");
    }

    public void manhuaDownImg(Message message, Channel channel) throws IOException {
        /*
        List<ManhuaImg> list = manhuaMapper.getManhua();
        for (ManhuaImg manhua : list) {
            List<String> titleError = new ArrayList<>();
            titleError.add("\\");
            titleError.add("/");
            titleError.add(":");
            titleError.add("*");
            titleError.add("?");
            titleError.add("'");
            titleError.add("<");
            titleError.add(">");
            titleError.add("|");
            titleError.add("?");
            String title = manhua.getTitle();
            for (String s : titleError) {
                title = title.replace(s, "");
            }
            downImg.dowlImg(manhua.getId(), manhua.getImgUrl(), title, savePath, cookie);
        }
        */
        System.out.println("-------------------- down img  运行完毕--------------------");
    }

    public void xiaoshuo(Message message, Channel channel) throws IOException {
        /*
        OOSpider.create(Site.me()
                        .setUserAgent("Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0")
                        .setSleepTime(1000 * 3)
                , new TxtPipeline(savePath), BiqugeChapterWithAnnotation.class)
                .setScheduler(new RedisScheduler(redishost))
                .addUrl(url)
                .thread(saveThread)
                .run();
         */
        System.out.println("--------------------  xiaoshuo  运行完毕--------------------");
    }


}

