package com.laoxin.core.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface IgnoreResponseAdviceAnnotation {
}
