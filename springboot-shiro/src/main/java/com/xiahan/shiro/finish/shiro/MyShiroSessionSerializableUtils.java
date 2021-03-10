package com.xiahan.shiro.finish.shiro;

import com.xiahan.util.ExceptionUtil;
import com.xiahan.util.ResultCode;
import com.xiahan.util.exceptionhandler.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;

import java.io.*;

/**
 * @Auther: xiahan
 * @Date: 2021/3/8 21:16
 * @Description:
 */
@Slf4j
public class MyShiroSessionSerializableUtils {

    public static String serialize(Object object){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            log.error("序列化出错 data= [{}], errorMsg = [{}]", object, ExceptionUtil.getMessage(e));
            throw  new GlobalException(ResultCode.SERIALIZABLE_ERROR, "序列化错误");
        }
    }

    public static Session deSerialize(String session){
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decode(session));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Session)ois.readObject();
        } catch (Exception e) {
            log.error("反序列化出错 data= [{}], errorMsg = [{}]", session, ExceptionUtil.getMessage(e));
            throw  new GlobalException(ResultCode.SERIALIZABLE_ERROR, "反序列化错误");
        }
    }

}
