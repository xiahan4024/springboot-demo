package com.xiahan.shiro.finish.shiro;

import com.xiahan.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: xiahan
 * @Date: 2021/3/7 20:55
 * @Description: redis 接管session
 */
public class MyShiroSessionDaoConfig extends EnterpriseCacheSessionDAO {

    private RedisTemplate redisTemplate;

    private long expireTime;

    public MyShiroSessionDaoConfig(RedisTemplate redisTemplate, long expireTime) {
        this.expireTime = expireTime;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        SimpleSession session1 = (SimpleSession) session;
        session1.setId(sessionId);
//        redisTemplate.opsForValue().set(getCacheKey(sessionId), MyShiroSessionSerializableUtils.serialize(session1));
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String object = (String)redisTemplate.opsForValue().get(getCacheKey(sessionId));
        if(StringUtils.isBlank(object)){
            return null;
        }
        return MyShiroSessionSerializableUtils.deSerialize(object);
    }

    @Override
    protected void doUpdate(Session session) {
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            redisTemplate.delete(getCacheKey(session.getId()));
            return; //如果会话过期/停止 没必要再更新了
        }
        redisTemplate.opsForValue().set(getCacheKey(session.getId()), MyShiroSessionSerializableUtils.serialize(session), this.expireTime, TimeUnit.SECONDS);
    }

    @Override
    protected void doDelete(Session session) {
        redisTemplate.delete(getCacheKey(session.getId()));
    }

    private String getCacheKey(Serializable key){
        StringBuffer stringBuffer = new StringBuffer();
        String orderNo = RandomUtil.getOrderNo();
        stringBuffer.append("Shiro-Session:");
        String format = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        stringBuffer.append(format);
        stringBuffer.append(":");
        stringBuffer.append(key.toString());
        return stringBuffer.toString();
    }
}
