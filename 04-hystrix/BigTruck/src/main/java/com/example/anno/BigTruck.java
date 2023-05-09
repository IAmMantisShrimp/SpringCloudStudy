package com.example.anno;

import java.lang.annotation.*;

/**
 * @author HuaRunSheng
 * @date 2023/4/23 22:06
 * @description : 熔断器aop注解
 */
// 作用域,这里是方法,也可以是类
@Target(ElementType.METHOD)
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
// 是否生成文档
@Documented
// 可集成的
@Inherited
public @interface BigTruck {

}
