package com.example.feign;

import com.example.domain.Order;
import lombok.extern.log4j.Log4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author HuaRunSheng
 * @date 2023/4/18 9:53
 * @description :
 */
// 服务提供者的应用名称,即application.name

/**
 * 接口是不能做事情的,如果想要做事情,就必须要有对象,
 * 那么这个接口肯定是被创建出代理的对象
 * 动态代理 jdk(Java interface 接口$proxy) cglib( 子类)
 */
@FeignClient(value = "order-server")
public interface UserOrderFeign {
    /**
     * 远程方法的方法名,复制过来
     * 需要调用哪个controller就写它的方法签名
     * @return
     */
    @GetMapping("doOrder")
    public String doOrder();

    @GetMapping("testUrl/{name}/and/{age}")
    public String testUrl(@PathVariable("name") String name, @PathVariable("age") Integer age);

    /**
     * @RequestParam(required = false)
     * 默认一定要传,不穿就会报错,设为false就可以不传
     * @param name
     * @return
     */
    @GetMapping("oneParam")
    public String oneParam(@RequestParam(value = "name", required = false) String name);
    @GetMapping("twoParam")
    public String twoParam(@RequestParam(required = false, value = "name") String name, @RequestParam(required = false, value = "age") Integer age);

    @PostMapping("oneObj")
    public String oneObj(@RequestBody Order order);


    @PostMapping("oneObjOneParam")
    public String oneObjOneParam(@RequestBody Order order,@RequestParam("name") String name);


    ////////////////// 单独传递时间对象

    @GetMapping("testTime")
    public String testTime(@RequestParam(value = "date") Date date);
}
