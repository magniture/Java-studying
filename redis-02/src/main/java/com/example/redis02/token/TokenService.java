package com.example.redis02.token;

import io.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: redis-02
 * @BelongsPackage: com.example.redis02.token
 * @Author: zhangq
 * @CreateTime: 2022-11-14  16:49
 * @Description: TODO
 */
@Service
public class TokenService {

    @Autowired
    RedisService redisService;

    public String createToken() {
        String uuid = UUID.randomUUID().toString();
        redisService.setEx(uuid,uuid,1000L);
        return uuid;
    }

    public boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            token = request.getParameter("token");
            if (StringUtils.isEmpty(token)) {
                throw new RuntimeException("token不存在");
            }
        }
        if (!redisService.exists(token)){
            throw new RuntimeException("token不存在");
        }
        boolean remove = redisService.remove(token);
        if (!remove) {
            throw new RuntimeException("重复操作");
        }
        return true;
    }
}
