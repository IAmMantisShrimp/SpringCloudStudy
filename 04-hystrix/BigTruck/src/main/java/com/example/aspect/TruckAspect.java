package com.example.aspect;

import com.example.entiry.Truck;
import com.example.entiry.TruckStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.swing.event.SwingPropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author HuaRunSheng
 * @date 2023/4/23 21:58
 * @description :
 */
@Component
@Aspect
public class TruckAspect {
    /**
     * com.example.controller.RpcController.doRpc(..) : doRpc(..) 表示方法中的所有参数
     * com.example.controller.RpcController.*(..) 表示这个包下的所有方法
     */
    //public static final String Point_Cut = "execution (* com.example.controller.RpcController.doRpc(..))";

    // 切点页可以用注解的方式实现,注解方式实现还要方便一点,直接在需要的方法上加入注解,
    // 加入注解后,环绕通知就可以根据注解找到加了注解的方法,使用方法见下面的环绕通知

    /**
     * 因为一个雄安飞者可以去调用多个提供者,每个停供者都有自己的断路器
     * 在消费者里创建一个断路器容器,并在进行初始化
      */
    public static Map<String, Truck> truckMap=new HashMap<>();
    static {
        // 假设 需要调用order-service的服务,真实场景中这里要通过eureka来完成
        truckMap.put("order-service", new Truck());
    }


    private Random random;

    /**
     * 环绕通知
     * 判断当前断路器状态,根据状态决定是否调用方法
     * @param joinPoint
     * @return
     */
    @Around(value = "@annotation(com.example.anno.BigTruck)")
    public Object truckAround(ProceedingJoinPoint joinPoint){
        Object result = null;
        Truck truck=truckMap.get("order-service");
        TruckStatus status=truck.getStatus();
        switch (status){
            // 正常,直接执行目标方法
            case CLOSE:
                try {
                    result = joinPoint.proceed();
                    return result;
                } catch (Throwable throwable) {
                    // 调用失败,记录次数
                    truck.addFailCount();
                    return "我是备胎";
                }

            //    不能调用,直接执行备用方案
            case OPEN:
                return "我是备胎";

            //    可以少许流量进入
            case HALF_OPEN:
                if (random.nextInt(5) == 1){
                    // 20% 的几率调用
                    try {
                        result=joinPoint.proceed();
                        // 说明成功了
                        truck.setStatus(TruckStatus.CLOSE);
                        // 唤醒等待线程
                        truck.getLock().notifyAll();
                        return result;
                    } catch (Throwable throwable) {
                        return "我是备胎";
                    }
                }

        }
        return result;
    }
}
