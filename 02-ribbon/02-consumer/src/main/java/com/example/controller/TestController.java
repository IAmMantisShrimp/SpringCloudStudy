package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author HuaRunSheng
 * @date 2023/4/17 22:31
 * @description :
 */
@RestController
public class TestController {
    @Resource
    RestTemplate restTemplate;
    @GetMapping("test")
    public String testRibbon(String serviceName){
        // 正常来讲需要拿到ip和端口才能发送请求
        return restTemplate.getForObject("http://"+serviceName+"/test", String.class);
    }
}
