package com.xiahan.webmagic.mq.dynamic;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.xiahan.webmagic.webmagic.ManhuaPageProcessor;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:52:56
 */
//@Slf4j
@Component
@Deprecated
public class ManhuaImgUrlConsumer implements BaseConsumer{

    @Value("${webmagic.redishost}")
    private String redishost;

    @Value("${webmagic.url}")
    private String url;

    @Value("${webmagic.saveThread}")
    private Integer saveThread;

    @Autowired
    ManhuaPageProcessor manhuaPageProcessor;


    @Override
    @Deprecated
    public void consumer(Message message, Channel channel) throws IOException {
        Spider.create(manhuaPageProcessor)
                .addUrl(url)
                .setScheduler(new RedisScheduler(redishost))
                .thread(saveThread)
                .run();
        System.out.println("--------------------运行完毕--------------------");
    }
}

