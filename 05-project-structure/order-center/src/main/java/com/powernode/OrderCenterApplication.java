package com.powernode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author HuaRunSheng
 * @date 2023/4/24 23:31
 * @description :
 */
@SpringBootApplication
@EnableEurekaClient
public class OrderCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderCenterApplication.class, args);
    }
}
