package com.xiahan.shiro.finish.shiro;

import com.xiahan.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: xiahan
 * @Date: 2021/3/7 21:17
 * @Description: 自定义 Shiro cache
 */
@Slf4j
public class MyShiroRedisCache<K, V> implements Cache<K, V> {

    private RedisTemplate redisTemplate;

    private long expireTime;

    public MyShiroRedisCache(RedisTemplate redisTemplate, long expireTime) {
        this.redisTemplate = redisTemplate;
        this.expireTime = expireTime;
    }

    @Override
    public V get(K key) throws CacheException {
        if (key == null) {
            return null;
        }
        String str = (String) redisTemplate.opsForValue().get(getCacheKey(key));
        if(str == null ){
            return null;
        }
        return (V)MyShiroSessionSerializableUtils.deSerialize(str);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        redisTemplate.opsForValue().set(getCacheKey(key), MyShiroSessionSerializableUtils.serialize(value), this.expireTime, TimeUnit.SECONDS);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        String str = (String) redisTemplate.opsForValue().get(getCacheKey(key));
        V value = null;
        if(!StringUtils.isBlank(str)){
            value = (V)MyShiroSessionSerializableUtils.deSerialize(str);
        }
        redisTemplate.opsForValue().getOperations().delete(getCacheKey(key));
        return value;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    private String getCacheKey(K key){
        StringBuffer stringBuffer = new StringBuffer();
        String orderNo = RandomUtil.getOrderNo();
        stringBuffer.append("Shiro-cache:");
        String format = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        stringBuffer.append(format);
        stringBuffer.append(":");
        stringBuffer.append(key.toString());
        return stringBuffer.toString();
    }
}
