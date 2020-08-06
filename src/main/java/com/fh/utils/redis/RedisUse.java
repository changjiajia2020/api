package com.fh.utils.redis;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.po.Area;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisUse {

    public static void set(String key,String value){
        Jedis jedis = RedisUtils.getJedis();
        jedis.set(key,value);
        RedisUtils.returnJedis(jedis);
    }

    // 续命   seconds：秒
    public static void set(String key,String value,int seconds){
        Jedis jedis = RedisUtils.getJedis();
        jedis.setex(key,seconds,value);
        RedisUtils.returnJedis(jedis);
    }

    public static String get(String key){
        Jedis jedis = RedisUtils.getJedis();
        String value=jedis.get(key);
        RedisUtils.returnJedis(jedis);
        return value;
    }

    public static void hset(String key,String filed,String value){
        Jedis jedis = RedisUtils.getJedis();
        Long hset = jedis.hset(key, filed, value);
        RedisUtils.returnJedis(jedis);
    }

    public static String hget(String key,String filed){
        Jedis jedis = RedisUtils.getJedis();
        String hget = jedis.hget(key, filed);
        RedisUtils.returnJedis(jedis);
        return hget;
    }

    // 可以把hash看成一个数组  判断hash中key的长度  即个数
    public static long hlen(String key){
        Jedis jedis = RedisUtils.getJedis();
        Long hlen = jedis.hlen(key);
        RedisUtils.returnJedis(jedis);
        return hlen;
    }

    // 获取key中所有的信息  应用场景   获取购物车中所有数据   例如：hvals cart_13453511561_cjj
    public static List<String> hvals(String key){
        Jedis jedis = RedisUtils.getJedis();
        List<String> hvals = jedis.hvals(key);
        RedisUtils.returnJedis(jedis);
        return hvals;
    }

    // ids  id，id，id        咋执行一下啊  在下面写main测试一下
    // static静态方法 预先加载好的  普通方法是调用它的时候才加载
    public static String getAreaNames(String areaIds){
        Jedis jedis = RedisUtils.getJedis();
        StringBuffer sb = new StringBuffer();   // 获取中文
        String[] split = areaIds.split(",");
        for (int i = 0; i < split.length; i++) {
            String areaId = split[i];
            /**
             * hgetall area_cjj
             *      "1"
             *       2) "{\"id\":1,\"name\":\"\xe4\xb8\xad\xe5\x9b\xbd\",\"pid\":0}"
             *       3) "2"
             *       4) "{\"id\":2,\"name\":\"\xe5\xb1\xb1\xe8\xa5\xbf\",\"pid\":1}"
             *     其中 area_cjj是key   "1" "2"是field
             *      "{\"id\":1,\"name\":\"\xe4\xb8\xad\xe5\x9b\xbd\",\"pid\":0}" 是value
             */
            String areaStr = jedis.hget("area_cjj", areaId);
            // 将json字符串转为json对象     Area.class 获取类的类对象
            // JSONObject jsonObject = JSONObject.parseObject(areaStr);
            // 后面写Area.class  因为：要将JSONObject转换为Area对象
            Area area = JSONObject.parseObject(areaStr,Area.class);
            sb.append(area.getName()).append(",");
        }
        RedisUtils.returnJedis(jedis);
        // 咋还toString()
        return sb.toString();
    }

    // hdel  登录人  商品id  删除
    public static void hdel(String key,String filed){
        Jedis jedis = RedisUtils.getJedis();
        jedis.hdel(key,filed);
        RedisUtils.returnJedis(jedis);
    }

    // 判断key是否存在
    public static boolean exists(String key){
        Jedis jedis = RedisUtils.getJedis();
        Boolean exists = jedis.exists(key);
        RedisUtils.returnJedis(jedis);
        return exists;
    }

    public static void main(String[] args) {
        String areaNames = getAreaNames("1,2,3");
        System.out.println(areaNames);
    }
}
