package com.powernode.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HuaRunSheng
 * @date 2023/4/17 15:58
 * @description :
 */
@RestController
public class DiscoveryController {
    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 通过应用名称,找到服务的ip和port
     * @param serverName
     * @return
     */
    @GetMapping("test")
    public String doDiscovery(String serverName){
        // 服务发现,通过服务的应用名称找到服务的具体信息
        List<ServiceInstance> instances = discoveryClient.getInstances(serverName);
        instances.forEach(System.out::println);
        ServiceInstance serviceInstance = instances.get(0);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        System.out.println(host + ":" + port);
        return instances.get(0).toString();
    }
}
