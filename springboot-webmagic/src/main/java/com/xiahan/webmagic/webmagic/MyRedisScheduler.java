package com.xiahan.webmagic.webmagic;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

/**
 * 
 * @author xiahan
 * @dateTime 2020年11月9日 下午8:56:35
 */
public class MyRedisScheduler extends DuplicateRemovedScheduler implements MonitorableScheduler, DuplicateRemover {

    protected JedisPool pool;

    public MyRedisScheduler(String host){
        this.pool = new JedisPool(host);
        this.setDuplicateRemover(this);
    }

    @Override
    protected void pushWhenNoDuplicate(Request request, Task task) {
        Jedis jedis = this.pool.getResource();

        try {
            jedis.rpush(this.getQueueKey(task), new String[]{request.getUrl()});
            if (request.getExtras() != null) {
                String field = DigestUtils.shaHex(request.getUrl());
                String value = JSON.toJSONString(request);
                jedis.hset(this.getItemKey(task), field, value);
            }
        } finally {
            jedis.close();
        }
    }

    /**
     * @Author xiahan
     * @Description 计数
     * @Date 17:10 2020/10/18
     * @Param [task]
     * @return int
     **/
    @Override
    public int getLeftRequestsCount(Task task) {
        Jedis jedis = this.pool.getResource();
        int num;
        try {
            // Redis Llen 命令用于返回列表的长度。 如果列表 key 不存在，则 key 被解释为一个空列表，返回 0 。 如果 key 不是列表类型，返回一个错误
            Long llen = jedis.llen(this.getQueueKey(task));
            num = llen.intValue();
        }finally {
            jedis.close();
        }
        return num;
    }

    /**
     * @Author xiahan
     * @Description 判断是否重复，不重复添加队列
     * @Date 17:10 2020/10/18
     * @Param [request, task]
     * @return boolean
     **/
    @Override
    public boolean isDuplicate(Request request, Task task) {
        Jedis jedis = this.pool.getResource();
        boolean result;
        try {
            /*
             * Redis Sadd 命令将一个或多个成员元素加入到集合中，已经存在于集合的成员元素将被忽略。
             * 假如集合 key 不存在，则创建一个只包含添加的元素作成员的集合。
             * 当集合 key 不是集合类型时，返回一个错误。
             */
            result = jedis.sadd(this.getSetKey(task), request.getUrl()) == 0L;
        }finally {
            jedis.close();
        }
        return result;
    }

    /**
     * @Author xiahan
     * @Description 删除
     * @Date 17:12 2020/10/18
     * @Param [task]
     * @return void
     **/
    @Override
    public void resetDuplicateCheck(Task task) {
        Jedis jedis = this.pool.getResource();
        try {
            jedis.del(this.getSetKey(task));
        }finally {
            jedis.close();
        }
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        Jedis jedis = this.pool.getResource();

        int var4;
        try {
            // Redis Scard 命令返回集合中元素的数量。
            Long size = jedis.scard(this.getSetKey(task));
            var4 = size.intValue();
        } finally {
            this.pool.returnResource(jedis);
        }

        return var4;
    }

    @Override
    public synchronized Request poll(Task task) {
        Jedis jedis = this.pool.getResource();

        Request var8;
        try {
            // 移除并返回 列表 key 的头元素。
            String url = jedis.lpop(this.getQueueKey(task));
            String key;
            if (url == null) {
                key = null;
                return null;
            }

            key = this.getItemKey(task);
            String field = DigestUtils.shaHex(url);
            // Redis Hget 命令用于返回哈希表中指定字段的值。
            byte[] bytes = jedis.hget(key.getBytes(), field.getBytes());
            Request request;
            if (bytes != null) {
                request = (Request) JSON.parseObject(new String(bytes), Request.class);
                var8 = request;
                return var8;
            }

            request = new Request(url);
            var8 = request;
        } finally {
            this.pool.returnResource(jedis);
        }

        return var8;
    }

    protected String getSetKey(Task task) {
        return "webmagic:set_" + task.getUUID();
    }

    protected String getQueueKey(Task task) {
        return "webmagic:queue_" + task.getUUID();
    }

    protected String getItemKey(Task task) {
        return "webmagic:item_" + task.getUUID();
    }

}

