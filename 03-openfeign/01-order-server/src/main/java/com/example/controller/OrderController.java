package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuaRunSheng
 * @date 2023/4/18 9:30
 * @description :
 */
@RestController
public class OrderController {
    @GetMapping("doOrder")
    public String doOrder(){
        return "下单成功";
    }
}
