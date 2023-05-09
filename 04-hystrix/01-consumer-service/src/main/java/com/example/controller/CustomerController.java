package com.example.controller;

import com.example.feign.CustomerRentFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author HuaRunSheng
 * @date 2023/4/18 23:27
 * @description :
 */
@RestController
public class CustomerController {
    @Resource
    CustomerRentFeign customerRentFeign;
    @GetMapping("customerRent")
    public String CustomerRent(){
        System.out.println("客户来租车了");
        String result = customerRentFeign.rent();
        return result;
    }
}
