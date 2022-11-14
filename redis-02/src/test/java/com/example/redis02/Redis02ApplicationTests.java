package com.example.redis02;

import com.example.redis02.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class Redis02ApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        User user = new User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setAge("11");
        user.setName("www.52it.cc");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("user11",user,10000, TimeUnit.MICROSECONDS);
        User u = (User) valueOperations.get("user11");
        System.out.println(u);

    }

}
