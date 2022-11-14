package com.example.redis02.exception;

import com.example.redis02.interceptor.IdempoterInterceptor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @BelongsProject: redis-02
 * @BelongsPackage: com.example.redis02.exception
 * @Author: zhangq
 * @CreateTime: 2022-11-14  17:33
 * @Description: TODO
 */
@RestControllerAdvice
public class GlobaException {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
