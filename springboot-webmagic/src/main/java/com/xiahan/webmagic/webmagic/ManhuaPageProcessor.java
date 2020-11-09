package com.xiahan.webmagic.webmagic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiahan.webmagic.entity.ManhuaImg;
import com.xiahan.webmagic.service.ManhuaService;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:56:15
 * @Description: 非注解解析
 */
@Slf4j
public class ManhuaPageProcessor implements PageProcessor {

    @Autowired
    ManhuaService manhuaService;

    private List<String> titleError;

    private String titleRegex = "</?.+?>";
    private String imgSrc = "[a-zA-z]+://[^\\s]*\\.jpg";

    private Site site = null;

    public ManhuaPageProcessor(String cookie){
        site = Site
                .me()
                .setDomain("blog.sina.com.cn")
                .setSleepTime(1000 * 3)
                .setTimeOut(999999999)
                .addHeader("cookie", cookie)
                .setUserAgent("Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0");
    }

    @Override
    public void process(Page page) {
        Html html1 = page.getHtml();
        String html = html1.toString();
        Document document = Jsoup.parse(html);
        String href1 = document.select("body > div.c > a:nth-child(4)").first().attr("href");
        String href2 = document.select("body > div.c > a.n.zhangjie").first().attr("href");
        if(!href1.startsWith("javascript")){
            page.addTargetRequest(href1);
        }
        page.addTargetRequest(href2);
        String title = html1.css("head > title").toString();
        List<String> imgUrls = html1.css("body > div.e > img").all();
        if(title == null || imgUrls == null || StringUtils.isBlank(title)  || imgUrls.size() == 0){
            return ;
        }
        titleError = new ArrayList<>();
//        \ /  : *  ?  "  <  > |
        titleError.add("\\");
        titleError.add("/");
        titleError.add(":");
        titleError.add("*");
        titleError.add("?");
        titleError.add("'");
        titleError.add("<");
        titleError.add(">");
        titleError.add("|");
        for (String s : titleError) {
            title = title.replace(s, "");
        }

        for (String imgUrl : imgUrls) {
            Pattern r = Pattern.compile(imgSrc);
            Matcher m = r.matcher(imgUrl);
            if (m.find( )) {
                for (int i=0; i<= m.groupCount();i++){
                    try {
                        ManhuaImg manhuaUrl = new ManhuaImg();
                        manhuaUrl.setTitle(title.replaceAll(titleRegex, "").trim());
                        manhuaUrl.setImgUrl(m.group(i));
                        manhuaService.addManhuaImgUrl(manhuaUrl);
                    } catch (Exception ex) {
                        log.error("保存图片出错！error = [{}]errorMessage = 【{}】", ex, ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
