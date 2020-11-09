package com.xiahan.webmagic.entity;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:49:02
 */
@Data
public class ManhuaImg {

    private Long id;

    private String title;

    private String imgUrl;

    private Boolean del;

    private LocalDateTime creatTime;
}
