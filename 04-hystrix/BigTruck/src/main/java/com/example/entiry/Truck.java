package com.example.entiry;

import lombok.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author HuaRunSheng
 * @date 2023/4/23 22:24
 * @description :
 */
@Data
public class Truck {
    // 时间窗口,等待时间
    public static final Integer WINDOW_TIME = 20;
    // 最大失败次数
    public static final Integer MAX_FAIL_COUNT = 3;
    //断路器状态
    private TruckStatus status = TruckStatus.CLOSE;
    /*
    AtomicInteger 线程安全的Integer
     */
    private AtomicInteger currentFailCount = new AtomicInteger(0);

    /**
     *  第一个参数:线程池核心线程的个数
     *
     *    第二个参数:线程池中的最大线程数，超过最大线程会把其他线程任务放进第四个参数也就是队列里等待调度
     *
     *    第三个和第四个结合起来使用，第三个是线程空闲等待的时间 long 类型，第四个是时间的单位，TimeUnit里有各种时间单位。
     *
     * 当线程空闲后会等待这里设置的时间数，如果到达任然没有调用就进行销毁，销毁至核心线程的个数，
     */
    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            4,
            8,
            30,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2000),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    Object lock = new Object();
    // 定期清零 ,一直让currentFailCount累加,会出现误判,在一段时间内多次失败才打开断路器
    {
        poolExecutor.execute(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(WINDOW_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 清零
                if (this.getStatus().equals(TruckStatus.CLOSE)){
                    this.currentFailCount.set(0);
                }else{
                    // 断路器开启或半开,这个线程就可以不工作
                    synchronized (lock){
                        try {
                            // 当断路器关闭时,再唤醒线程
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
    }

    // 记录失败次数
    public void addFailCount(){
        int i=currentFailCount.incrementAndGet(); // ++i
        if (i >= MAX_FAIL_COUNT){
            // 失败次数已经到达了阈值了,修改当前状态为OPEN
            this.setStatus(TruckStatus.OPEN);

            // 当前断路器打开以后,就不能去访问了,等待一段时间后,让断路器变成半开
            poolExecutor.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(WINDOW_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 状态变成半开
                this.setStatus(TruckStatus.HALF_OPEN);
                // 重置次数
                this.currentFailCount.set(0);
            });
            //new Thread(()->{
            //    try {
            //        TimeUnit.SECONDS.sleep(WINDOW_TIME);
            //    } catch (InterruptedException e) {
            //        e.printStackTrace();
            //    }
            //    // 状态变成半开
            //    this.setStatus(TruckStatus.HALF_OPEN);
            //    // 重置次数
            //    this.currentFailCount.set(0);
            //}).start();

        }
    }
}
