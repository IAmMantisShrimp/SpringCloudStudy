package com.example.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

@Configuration
public class RouteConfig {

    /**
     * 代码的路由  和yml不冲突  都可以用
     * 如果你的uri后面给了一个访问地址 和匹配地址相同 那么就不会再凭借
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        /**
         * id: guochuang-id 路由的标识,保证每个id不一样即可
         * path: 路径,后面可以加路径,也可以不加路径
         *      如/guochuang则会匹配 -> http://127.0.0.1:80/guochuang/*
         * uri: 转发的路径, 当匹配到path时,就会转发到uri中
         */
        return  builder.routes()
                .route("guochuang-id",r->r.path("/guochuang").uri("https://www.bilibili.com/guochuang"))
                .route("dance-id",r->r.path("/v/dance").uri("https://www.bilibili.com"))
                .route("kichiku-id",r->r.path("/v/kichiku").uri("https://www.bilibili.com"))
                .build();
    }

    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }


}
