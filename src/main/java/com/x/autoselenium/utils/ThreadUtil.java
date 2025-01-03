package com.x.autoselenium.utils;

import cn.hutool.json.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import com.x.autoselenium.humanity.Faucet;
import com.x.autoselenium.log.Log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
    public static void doThreadTasks(List<JSONObject> list,int maxThreads,double permitsPerSecond,ToRun toRun) throws InterruptedException {

        int totalTasks = list.size();  // 总任务数量
//        double permitsPerSecond = 0.25;  // 每2秒1个请求，相当于每秒0.5个请求
        // 创建一个具有固定线程数的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);
        // 使用信号量来控制最多几个线程同时执行
        Semaphore semaphore = new Semaphore(maxThreads);
        // 使用Guava RateLimiter来限制每2秒只能处理1个请求
        RateLimiter rateLimiter = RateLimiter.create(permitsPerSecond);
        for (int i = 0; i < totalTasks; i++) {
            final JSONObject jsonObject = list.get(i);
            String serialNumber = jsonObject.get("serial_number").toString();
            semaphore.acquire();  // 获取信号量
            executorService.submit(() -> {
                try {
                    // 通过RateLimiter来控制每2秒一个请求
                    rateLimiter.acquire();
                    //业务
                    toRun.execute(jsonObject);
                } catch (Exception e) {
                    Log.fails.add(serialNumber);
                    // ANSI转义序列开启红色文本
                    System.out.print("\033[31m");
                    System.out.println(serialNumber + "的线程被中断了");
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

        // 关闭线程池，不再接受新任务
        executorService.shutdown();

        // 等待所有任务完成
        if (!executorService.awaitTermination(120, TimeUnit.SECONDS)) {
            System.out.println("Timeout occurred before tasks could finish");
            executorService.shutdownNow();  // 如果超时，强制关闭任务
        }

    }
}
