package com.xiahan.webmagic.mq.dynamic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.xiahan.webmagic.entity.ManhuaImg;
import com.xiahan.webmagic.mapper.ManhuaMapper;
import com.xiahan.webmagic.util.DownImg;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:52:40
 * @Description: 动态代理实现
 */
@Component
@Deprecated
public class ManhuaDownImgConsumer implements BaseConsumer{

    @Value("${webmagic.savePath}")
    private String savePath;

    @Value("${webmagic.cookie}")
    private String cookie;

    @Autowired
    ManhuaMapper manhuaMapper;

    @Autowired
    DownImg downImg;

    @Override
    @Deprecated
    public void consumer(Message message, Channel channel) throws IOException {
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
}

