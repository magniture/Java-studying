package com.example.thread02threadexecutor.config;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {

    /**
     * 并发处理次数
     *
     *  @return
     */
    int limit() default 10;

    /**
     * 超时设置
     *
     *  @return
     */
    int seconds() default 3;

    /**
     * 限流场景的名字
     *
     *  @return
     */
    String name() default "";

}
