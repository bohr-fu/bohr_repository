package com.bohr.blogmvn.common.cache;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Redis缓存类
 * @author Bohr Fu
 * @Date 2019/12/10 14:03
 * @Version 1.0
 */
public class RedisManager {

    /**
     * 默认过期时间 单位 秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 30;

    /**
     * 不设置过期时间
     */
    public final static long NOT_EXPIRE = -1;

    private RedisTemplate redisTemplate;

    /**
     * 缓存 set方法
     * @param key
     * @param value
     * @param expire
     */
    public void set(String key, Object value, long expire) {
        try {
            if (NOT_EXPIRE == expire) {
                redisTemplate.opsForValue().set(key, value);
            }else {
                redisTemplate.opsForValue().set(key,value,expire);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 重载方法 采用默认过期时间
     * @param key
     * @param value
     */
    public void set(String key, Object value){set(key,value,DEFAULT_EXPIRE);}

    /**
     * redis 缓存get方法
     * @param key
     * @return
     */
    public Object get(String key){return redisTemplate.opsForValue().get(key);}

    /**
     * 指定返回类型的 redis缓存get方法
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> T get(String key,Class<T> value){
        ValueOperations<String ,T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public void delete(String key){redisTemplate.delete(key);}

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
