package com.x.autoselenium.skygate;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.x.autoselenium.hemi.Capsule;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        List<JSONObject> list = Util.getAll();

        int totalTasks = list.size();  // 总任务数量
        int maxThreads = 5;    // 最大并发线程数量
        double permitsPerSecond = 0.5;  // 每2秒1个请求，相当于每秒0.5个请求

        // 创建一个具有固定线程数的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);

        // 使用信号量来控制最多3个线程同时执行
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
                    Business.skygateCheckIn(jsonObject);

                } catch (Exception e) {
                    // ANSI转义序列开启红色文本
                    System.out.print("\033[31m");
                    System.out.println(jsonObject.getStr("serial_number") + "的线程被中断了");
                    System.out.print("\033[0m");
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                } finally {

                    //关闭浏览器
                    if (Util.stopBrowser(jsonObject)) {
                        System.out.println(jsonObject.getStr("serial_number")+" 号浏览器已关闭");
                    }

                    // 释放信号量
                    semaphore.release();
                }
            });
        }

        // 关闭线程池，等待所有任务完成
        executorService.shutdown();

        System.out.println("成功"+ Log.logs.size()+"个 ：" + Log.logs);

        List<String> fails = new ArrayList<>();

        for (JSONObject jsonObject : list) {
            if (!Log.logs.contains(jsonObject.getStr("serial_number"))) {
                fails.add(jsonObject.getStr("serial_number"));
            }
        }

        System.out.println("失败"+fails.size()+"个 ：" + fails);
    }
}
