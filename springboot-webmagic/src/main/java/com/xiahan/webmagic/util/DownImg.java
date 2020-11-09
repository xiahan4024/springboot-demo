package com.xiahan.webmagic.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.xiahan.webmagic.mapper.ManhuaMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:55:53
 */
@Slf4j
@Component
public class DownImg {

    @Autowired
    ManhuaMapper manhuaMapper;

    private boolean filePathExist = false;

    @Async("myTaskAsyncPool")
    public void dowlImg(Long id, String urlStr, String fileName, String savePath, String cookie) {
        if (!filePathExist) {
            try {
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs(); // 创建文件夹
//                file.createNewFile(); // 创建文件
                }
            } catch (Exception ex) {
                log.error("创建新文件夹错误。ex = [{}], exMessage = [{}]", ex, ex.getMessage());
            }
            filePathExist = true;
        }


        boolean result = false;
        int byteread = 0;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(300000);
            conn.setReadTimeout(300000);
//            conn.setRequestProperty("Host", "paper.cnstock.com");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");
//            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//            conn.setRequestProperty("Accept-Encoding", "utf8, deflate");//注意编码，gzip可能会乱码
//            conn.setRequestProperty("Content-Encoding", "utf8");
//            conn.setRequestProperty("Connection", "keep-alive");
//            conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
            conn.setRequestProperty("Cookie", cookie);
//            conn.setRequestProperty("Cache-Control", "max-age=0");
            conn.setRequestProperty("Content-Type", "application/fore-download");

            InputStream inStream = conn.getInputStream();
            String filename = savePath + fileName + LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")) + ".jpg";
            log.info("下载的文件名称为：[{}]", filename);
            FileOutputStream fs = new FileOutputStream(filename);
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            fs.flush();
            inStream.close();
            fs.close();
            result = true;
        } catch (Exception e) {
            result = false;
            System.out.println("失败了。urlStr = " + urlStr);
            log.info("失败了。【{}】", urlStr);
            e.printStackTrace();
        }
        if (result) {
            manhuaMapper.updateManhuaDel(id);
        }
    }
}

