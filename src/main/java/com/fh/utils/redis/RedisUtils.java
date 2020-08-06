package com.fh.utils.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {

    //从静态块中初始化
    private static JedisPool jedisPool;

    private RedisUtils(){

    }

    //static  静态块  只会加载一次
    static {
        //创建redis池的配置
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);
        jedisPoolConfig.setMaxIdle(2);
        jedisPoolConfig.setMinIdle(1);
        jedisPoolConfig.setMaxWaitMillis(1000);
        //初始化redis池
        jedisPool=new JedisPool(jedisPoolConfig,"192.168.25.136",6379);
    }

    //从池中拿连接
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
    //连接之后，再还给池中
    public static void returnJedis(Jedis jedis){
        jedisPool.returnResource(jedis);
    }

}
