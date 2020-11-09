package com.xiahan.webmagic.mq.dynamic;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.xiahan.webmagic.entity.ChapterWithAnnotation;
import com.xiahan.webmagic.webmagic.TxtPipeline;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:53:26
 * @Description: 动态代理实现
 */
//@Slf4j
@Component
@Deprecated
public class XiaoshuoConsumer implements BaseConsumer {

    @Value("${webmagic.savePath}")
    private String savePath;

    @Value("${webmagic.redishost}")
    private String redishost;

    @Value("${webmagic.url}")
    private String url;

    @Value("${webmagic.saveThread}")
    private Integer saveThread;

    @Override
    @Deprecated
    public void consumer(Message message, Channel channel) throws IOException {
        OOSpider.create(Site.me()
                        .setUserAgent("Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0")
                        .setSleepTime(1000 * 3)
                , new TxtPipeline(savePath), ChapterWithAnnotation.class)
                .setScheduler(new RedisScheduler(redishost))
                .addUrl(url)
                .thread(saveThread)
                .run();
        System.out.println("--------------------运行完毕--------------------");
    }
}

