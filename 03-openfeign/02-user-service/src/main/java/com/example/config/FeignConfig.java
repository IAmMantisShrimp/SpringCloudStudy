package com.example.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuaRunSheng
 * @date 2023/4/18 15:15
 * @description :
 */
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLogger(){
        return Logger.Level.FULL;
    }
}
