package com.jiahangchun.annotation;

import java.lang.annotation.*;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/26 下午4:58
 **/
@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityAnnotation {

    int level() default 5;
}