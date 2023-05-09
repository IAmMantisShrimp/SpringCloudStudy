package com.example.feign;

import com.example.feign.hystrix.CustomerRentFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author HuaRunSheng
 * @date 2023/4/20 22:04
 * @description :
 */
@FeignClient(value = "rent-car-servive", fallback = CustomerRentFeignHystrix.class)
public interface CustomerRentFeign {
    @GetMapping("rent")
    public String rent();
}
