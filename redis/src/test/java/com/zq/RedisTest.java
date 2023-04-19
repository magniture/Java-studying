package com.zq;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class RedisTest {
    @Test
    public void testCacheProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Product product = new Product(); // 创建Product对象
        product.setName("杯子");
        product.setPrice(100d);
        product.setDesc("这是一个杯子");
        String json = objectMapper.writeValueAsString(product);

        RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisAsyncCommands<String, String> asyncCommands = connection.async();
        asyncCommands.set("product", json).get(1, TimeUnit.SECONDS);

        // RedisCommands<String, String> syncCommands = connection.sync();
        // syncCommands.set("product", json);

        connection.close();
        redisClient.shutdown();
    }
}
