package com.xiahan.webmagic.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiahan.webmagic.entity.MsgLog;
import com.xiahan.webmagic.mapper.MsgLogMaper;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:55:14
 */
@Service
public class MsgLogService {

    @Autowired
    MsgLogMaper msgLogMaper;

    public void updateStatus(String msgId, Integer status) {
        MsgLog msgLog = new MsgLog();
        msgLog.setMsgId(msgId);
        msgLog.setStatus(status);
        msgLog.setUpdateTime(LocalDateTime.now(ZoneOffset.of("+8")));
        msgLogMaper.updateStatus(msgLog);
    }

    public void insertMas(MsgLog msgLog) {
        msgLogMaper.insertMas(msgLog);
    }

    public MsgLog selectByMsgId(String msgId) {
        return msgLogMaper.selectByMsgId(msgId);
    }

    public List<MsgLog> selectTimeoutMsg() {
        return msgLogMaper.selectTimeoutMsg();
    }

    public void updateTryCount(String msgId, LocalDateTime nextTryTime) {
        msgLogMaper.updateTryCount(msgId, nextTryTime);
    }
}

