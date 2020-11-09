package com.xiahan.webmagic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:48:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TargetUrl("http://www.tianxiabachang.cn/5_5851/*")
//@HelpUrl("http://www.tianxiabachang.cn/5_5851/")
public class ChapterWithAnnotation {

    @ExtractBy(value = "#wrapper > div.content_read > div > div.bookname > h1", type = ExtractBy.Type.Css, notNull = true)
    private String title;

    @ExtractBy(value = "#content", type = ExtractBy.Type.Css, notNull = true)
    private String body;

}
