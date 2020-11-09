package com.xiahan.webmagic.webmagic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xiahan.webmagic.entity.ChapterWithAnnotation;
import com.xiahan.webmagic.entity.ManhuaImg;
import com.xiahan.webmagic.mapper.ManhuaMapper;
import com.xiahan.webmagic.util.DownImg;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:57:27
 */
@Component
@Deprecated
public class WebmagicMain {

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

    @Deprecated
    public void getManhuaImgUrl() {
        Spider.create(manhuaPageProcessor).addUrl(url).setScheduler(new RedisScheduler(redishost)).thread(saveThread)
                .run();
        System.out.println("--------------------运行完毕--------------------");
    }

    @Deprecated
    public void downManhuaImgUrl() {
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
        System.out.println("--------------------运行完毕--------------------");
    }

    @Deprecated
    public void xiaoshuo() {
        OOSpider.create(Site.me().setUserAgent("Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0")
                .setSleepTime(1000 * 3), new TxtPipeline(savePath), ChapterWithAnnotation.class)
                .setScheduler(new RedisScheduler(redishost)).addUrl(url).thread(saveThread).run();
        System.out.println("--------------------运行完毕--------------------");
    }
}
