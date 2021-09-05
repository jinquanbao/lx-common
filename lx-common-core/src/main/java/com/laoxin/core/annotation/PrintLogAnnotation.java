package com.laoxin.core.annotation;

import java.lang.annotation.*;

/**
 * 日志切面注解
 * @author jinquanbao
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface PrintLogAnnotation {
    //是否打印响应数据
    boolean printResponseData() default true;
}
