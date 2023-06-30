package com.juzi.common.annotation;


import java.lang.annotation.*;

/**
 * @author codejuzi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthCheck {

    /**
     * 必须有某个角色
     *
     * @return 角色
     */
    String mustRole() default "user";
}
