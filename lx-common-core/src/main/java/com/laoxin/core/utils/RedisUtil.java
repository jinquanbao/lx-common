package com.laoxin.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@ConditionalOnClass(RedisTemplate.class)
@Component
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate){
        RedisUtil.redisTemplate = redisTemplate;
    }

    public static void set(String key, Object value, long expire){
        redisTemplate.opsForValue().set(key, value ,expire, TimeUnit.SECONDS);
    }

    public static void leftPushAll(String key, Object value){
        redisTemplate.opsForList().leftPushAll(key, value);
    }

    public static void add(String key, Object value){
        redisTemplate.opsForSet().add(key, value);
    }
    public static void hPut(String key,Object var2, Object var3){
        redisTemplate.opsForHash().put(key, var2,var3);
    }
    public static Object hashGet(String key,Object var2){
        if(var2==null){
            return null;
        }
        return redisTemplate.opsForHash().get(key, var2);
    }

    public static Boolean hasKey(String key,Object var2){
        return redisTemplate.opsForHash().hasKey(key, var2);
    }

    public static Boolean isMember(String key, Object value){
        return redisTemplate.opsForSet().isMember(key, value);
    }


    public static void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    //存入的必须是对象字符串
    public static <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? null : JsonUtil.fromJson(value.toString(), clazz);
    }

    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static String getStr(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? null : value.toString();
    }

    public static void delete(String key) {
        redisTemplate.delete(key);
    }

    public static void hSet(String key,String filed, Object value){
        redisTemplate.opsForHash().put(key,filed,value);
    }

    public static Object hGet(String key,String filed){
        return redisTemplate.opsForHash().get(key,filed);
    }

    public static void expire(String key,long seconds){
        redisTemplate.expire(key,seconds,TimeUnit.SECONDS);
    }

    public static Long increment(String key,long value){
        return redisTemplate.opsForValue().increment(key,value);
    }

    /**
     *
     * @param key redisKey
     */
    public static Boolean zAdd(String key,Object value,double score){
        return redisTemplate.opsForZSet().add(key,value,score);
    }

    /**
     * zSet返回指定位置的指定数量的数据
     */
    public static Set<Object> zRange(String key, Long start, Long size){
        long end = start + size - 1;
        return redisTemplate.opsForZSet().range(key,start,end);
    }

    /**
     * zSet移除元素
     */
    public static void zRemove(String key, Object value){
        redisTemplate.opsForZSet().remove(key,value);
    }
}
