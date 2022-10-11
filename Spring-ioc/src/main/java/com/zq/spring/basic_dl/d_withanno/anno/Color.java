package com.zq.spring.basic_dl.d_withanno.anno;

import java.lang.annotation.*;

/**
 * @author zq
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Color {
}
