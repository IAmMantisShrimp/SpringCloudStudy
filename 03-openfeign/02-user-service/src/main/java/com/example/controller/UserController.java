package com.example.controller;

import com.example.domain.Order;
import com.example.feign.UserOrderFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author HuaRunSheng
 * @date 2023/4/18 9:42
 * @description :
 */
@RestController
public class UserController {
    @Resource
    private UserOrderFeign userOrderFeign;

    /**
     * 总结: 浏览器 --> user-service --> RPC(feign) --> order-service
     * feign默认等待时间为1s,超过1s就会报错超时
     * @return
     */
    @GetMapping("userDoOrder")
    public String userDoOrder(){
        System.out.println("有用户进来了");
        // 这里需要发起一个远程调用
        String result = userOrderFeign.doOrder();
        return result;
    }


    @GetMapping("testParam")
    public String testParam(){
        String cxs = userOrderFeign.testUrl("cxs", 18);
        System.out.println(cxs);

        String t = userOrderFeign.oneParam("老唐");
        System.out.println(t);

        String lg = userOrderFeign.twoParam("雷哥", 31);
        System.out.println(lg);

        Order order = Order.builder()
                .name("牛排")
                .price(188D)
                .time(new Date())
                .id(1)
                .build();

        String s = userOrderFeign.oneObj(order);
        System.out.println(s);

        String param = userOrderFeign.oneObjOneParam(order, "稽哥");
        System.out.println(param);
        return "ok";
    }

    /**
     * Sun Mar 20 10:24:13 CST 2022
     * Mon Mar 21 00:24:13 CST 2022  +- 14个小时
     * 1.不建议单独传递时间参数
     * 2.转成字符串   2022-03-20 10:25:55:213 因为字符串不会改变
     * 3.jdk LocalDate 年月日    LocalDateTime 会丢失s
     * 4.改feign的源码
     *
     * @return
     */
    @GetMapping("time")
    public String time(){
        Date date = new Date();
        System.out.println(date);
        String s = userOrderFeign.testTime(date);
        // 年月日
        LocalDate now = LocalDate.now();
        // 时分秒
        LocalDateTime now1 = LocalDateTime.now();

        return s;
    }
}
