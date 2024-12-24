package com.x.autoselenium.metamask;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Util;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class DoBalance {
    public static void main(String[] args) throws InterruptedException {
        List<JSONObject> list = Util.getAll(true);

        int totalTasks = list.size();  // 总任务数量
        int maxThreads = 3;    // 最大并发线程数量
        double permitsPerSecond = 0.25;  // 每2秒1个请求，相当于每秒0.5个请求

        // 创建一个具有固定线程数的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);

        // 使用信号量来控制最多几个线程同时执行
        Semaphore semaphore = new Semaphore(maxThreads);

        // 使用Guava RateLimiter来限制每2秒只能处理1个请求
        RateLimiter rateLimiter = RateLimiter.create(permitsPerSecond);

        for (int i = 0; i < totalTasks; i++) {
            semaphore.acquire();  // 获取信号量


            final JSONObject jsonObject = list.get(i);
            executorService.submit(() -> {
                try {

                    // 通过RateLimiter来控制每2秒一个请求
                    rateLimiter.acquire();
                    //业务
                    Balance.checkBalance(jsonObject);

                } catch (Exception e) {
                    // ANSI转义序列开启红色文本
                    System.out.print("\033[31m");
                    System.out.println(jsonObject.getStr("serial_number") + "的线程被中断了");
                    System.out.print("\033[0m");
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                } finally {
                    // 释放信号量
                    semaphore.release();

                    //关闭浏览器
                    Util.stopBrowser(jsonObject);
                }
            });
        }

        // 关闭线程池，等待所有任务完成
        executorService.shutdown();

        System.out.println("成功"+Log.logs.size()+"个 ：" + Log.logs);

        for (String s: Log.logs){
            System.out.println(s);
        }

    }
}
