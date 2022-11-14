package com.example.redis02.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: redis-02
 * @BelongsPackage: com.example.redis02.token
 * @Author: zhangq
 * @CreateTime: 2022-11-14  16:49
 * @Description: TODO
 */
@Service
public class RedisService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public boolean setEx(String key,String value,Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String,String> ops = stringRedisTemplate.opsForValue();
            ops.set(key, value);
            stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public boolean remove(String key) {
        if (exists(key)){
            return stringRedisTemplate.delete(key);
        }
        return false;
    }
}
