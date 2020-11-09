package com.xiahan.webmagic.webmagic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.xiahan.webmagic.entity.ChapterWithAnnotation;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:57:07
 * @Description: 小说解析并保存到本地
 */
@Slf4j
public class TxtPipeline implements PageModelPipeline<ChapterWithAnnotation> {

    private String titleRegex = "</?.+?>";
    private String bodyRegex = "</?.+?>|&nbsp;";
    private String filePath = null;
    private boolean filePathExist = false;


    public TxtPipeline(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void process(ChapterWithAnnotation chapter, Task task) {
        chapter.setTitle(chapter.getTitle().replaceAll(titleRegex, "").trim());
        chapter.setBody(chapter.getBody().replaceAll(bodyRegex, "").trim());

        if (!filePathExist) {
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    file.mkdirs(); // 创建文件夹
//                file.createNewFile(); // 创建文件
                }
            }catch (Exception ex){
                log.error("创建新文件夹错误。ex = [{}], exMessage = [{}]", ex, ex.getMessage());
            }

            filePathExist = true;
        }
        try {

            PrintWriter printWriter = new PrintWriter(new FileWriter(filePath + chapter.getTitle() + ".txt"));
            printWriter.write(chapter.getBody());
            printWriter.close();
        } catch (IOException ex) {
            log.error("保存数据失败！error = [{}]errorMessage = 【{}】", ex, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        String title = "<h1> 第三章 双生武魂(三)</h1>";
        String body = "<div id='content'>" +
                " &nbsp;&nbsp;&nbsp;&nbsp;三轰飞的同时，比比东身体也是一阵晃动，哇的一一口紫黑色的鲜血。但是，嘉陵关城头上观战的众人却都明白，这场战斗的结局已经注定了。" +
                " <br> " +
                " <br> ";

        String titleRegex = "<\\S*>";
        String bodyRegex = "</?.+?>|&nbsp;";


        String titleResult = title.replaceAll(titleRegex, "").trim();
        System.out.println(titleResult);

        String bodyResult = body.replaceAll(bodyRegex, "").trim();
        System.out.println(bodyResult);
    }
}


