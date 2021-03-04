package com.xiahan.util.MyBatisHandler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Auther: xiahan
 * @Date: 2021/2/23 21:35
 * @Description: Mybaits_plus 创建和修改 自动填充
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    private String createName;
    private String modifyName;

    public MyMetaObjectHandler(String createName, String modifyName){
        this.createName = createName;
        this.modifyName = modifyName;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(createName, LocalDateTime.now(), metaObject);
        this.setFieldValByName(modifyName, LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(modifyName, LocalDateTime.now(), metaObject);
    }
}
