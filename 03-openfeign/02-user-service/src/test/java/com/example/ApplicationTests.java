package com.example;

import com.example.controller.UserController;
import com.example.feign.UserOrderFeign;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SpringBootTest
class ApplicationTests {
    @Resource
    RestTemplate restTemplate;
    @Test
    void contextLoads() {
        /**
         * 代理方法需要用到的类,即接口多对应的具体实现方法所在类
         * ClassLoader loader, --> UserController.class.getClassLoader()
         *
         * 具体代理的接口
         * Class<?>[] interfaces, --> new Class[]{UserOrderFeign.class}
         *
         * 代理的具体实现,可以使用Lombardi表达式,也可以懒汉式
         * InvocationHandler h --> new InvocationHandler() {}
         */
        UserOrderFeign userOrderFeign = (UserOrderFeign)Proxy.newProxyInstance(UserController.class.getClassLoader(), new Class[]{UserOrderFeign.class}, new InvocationHandler() {
            /**
             * Method method: 就是传入接口的方法,这里传入的是UserOrderFeign,
             * 当代理对象调用方法时,就会经过invoke方法
             * 通过invoke方法可以获取该方法(包括注解),和类对象
             * @param proxy
             * @param method
             * @param args
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();
                System.out.println(name + "方法调用了动态代理方法");
                // 获取方法上的getmapping注解
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                String path = getMapping.value()[0];
                // 获取该方法所在类对象
                Class<?> aClass = method.getDeclaringClass();
                // 获取类对象上的Feign注解
                FeignClient feignClient = aClass.getAnnotation(FeignClient.class);
                String applicationName = feignClient.value();
                String url = "http://" + applicationName + "/" + path;
                System.out.println("远程获取地址: " + url);
                String result = restTemplate.getForObject(url, String.class);
                System.out.println("返回结果为: "+result);
                return result;
            }
        });
        userOrderFeign.doOrder();
    }

}
