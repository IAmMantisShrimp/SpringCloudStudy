package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RestController
@CrossOrigin // 加上这个注解之后 这个controller里面的方法就可以直接被访问了
public class LoginController {



    @GetMapping("doLogin")
    @CrossOrigin
    public String doLogin(String name, String pwd) {
        System.out.println(name);
        System.out.println(pwd);
        // 这里假设去做了登录
        String token = UUID.randomUUID().toString();
        return token;
    }

}
