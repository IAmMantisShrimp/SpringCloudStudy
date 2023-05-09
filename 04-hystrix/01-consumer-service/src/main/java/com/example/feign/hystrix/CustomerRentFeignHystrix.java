package com.example.feign.hystrix;

import com.example.feign.CustomerRentFeign;
import org.springframework.stereotype.Component;

/**
 * @author HuaRunSheng
 * @date 2023/4/20 22:16
 * @description :
 */
@Component
public class CustomerRentFeignHystrix implements CustomerRentFeign {
    /**
     * 这个方法是备选方案
     * @return
     */
    @Override
    public String rent() {
        return "我是备胎";
    }
}
