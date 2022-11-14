package com.example.redis02.interceptor;

import com.example.redis02.anno.AutoIdempotent;
import com.example.redis02.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @BelongsProject: redis-02
 * @BelongsPackage: com.example.redis02.interceptor
 * @Author: zhangq
 * @CreateTime: 2022-11-14  17:23
 * @Description: TODO
 */
@Component
public class IdempoterInterceptor implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        AutoIdempotent annotation = method.getAnnotation(AutoIdempotent.class);
        if (annotation != null) {
            try {
                return tokenService.checkToken(request);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
