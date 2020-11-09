package com.xiahan.webmagic.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xiahan.webmagic.entity.MsgLog;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:49:45
 */
@Mapper
public interface MsgLogMaper {
    @Update("update msg_log set status = #{status}, update_time = #{updateTime} where msg_id = #{msgId}")
    void updateStatus(MsgLog msgLog);

    @Insert("INSERT INTO msg_log(msg_id, msg, exchange, routing_key, status, try_count, next_try_time, create_time, update_time) VALUES (#{msgId}, #{msg}, #{exchange}, #{routingKey}, #{status}, #{tryCount}, #{nextTryTime}, #{createTime}, #{updateTime})")
    void insertMas(MsgLog msgLog);

    @Select("select * from msg_log where msg_id = #{msgId}")
    MsgLog selectByMsgId(String msgId);

    @Select("select * from msg_log where status = 0")
    List<MsgLog> selectTimeoutMsg();

    @Update("update msg_log set try_count = try_count + 1, next_try_time = #{nextTryTime}, update_time = now()  where msg_id = #{msgId}")
    void updateTryCount(String msgId, LocalDateTime nextTryTime);
}
