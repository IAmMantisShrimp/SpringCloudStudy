package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuaRunSheng
 * @date 2023/5/6 11:13
 * @description :
 */
@RestController
public class BController {
    @GetMapping("info")
    public String info(){
        return "hhh";
    }
}
