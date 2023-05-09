package com.example.controller;

import com.example.anno.BigTruck;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author HuaRunSheng
 * @date 2023/4/23 21:44
 * @description :
 */
@RestController
public class RpcController {
    @Resource
    private RestTemplate restTemplate;
    @BigTruck
    @GetMapping("doRpc")
    public String doRpc(){
        String result = restTemplate.getForObject("http://localhost:9090/test", String.class);
        return result;
    }
}
