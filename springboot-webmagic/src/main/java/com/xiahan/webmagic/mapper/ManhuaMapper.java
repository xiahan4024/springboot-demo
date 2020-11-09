package com.xiahan.webmagic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xiahan.webmagic.entity.ManhuaImg;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:49:33
 * @Description: img 操作，方便记录哪些已经下载。支持断点下载这类
 */
@Mapper
public interface ManhuaMapper {

    @Insert("insert into manhua_url(title, img_url, del, creat_time ) value(#{title}, #{imgUrl}, 0, NOW())")
    void addOne(ManhuaImg Manhua);

    @Select("select id, title, img_url from manhua_url where del = 0")
    List<ManhuaImg> getManhua();

    @Update("update manhua_url set del = 1 where id = #{id}")
    void updateManhuaDel(Long id);

    @Select("select count(1) from manhua_url where img_url = #{url}")
    int selectByImgUrl(String url);
}
