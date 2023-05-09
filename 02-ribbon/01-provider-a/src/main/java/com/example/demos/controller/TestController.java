package com.example.demos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuaRunSheng
 * @date 2023/4/17 22:05
 * @description :
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String Hello(){
        return "我是Provider A 提供的接口.";
    }
}
