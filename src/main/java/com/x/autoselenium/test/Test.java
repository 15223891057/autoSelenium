package com.x.autoselenium.test;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.x.autoselenium.hemi.Capsule;
import com.x.autoselenium.metamask.Balance;
import com.x.autoselenium.skygate.Business;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


@Slf4j
public class Test {

    public static void main(String[] args) throws InterruptedException, IOException {

        List<JSONObject> list = new ArrayList<>();
        list.add(new JSONObject().set("serial_number","12"));
        list.add(new JSONObject().set("serial_number","44"));
        list.add(new JSONObject().set("serial_number","45"));
        list.add(new JSONObject().set("serial_number","52"));
        list.add(new JSONObject().set("serial_number","56"));
        list.add(new JSONObject().set("serial_number","74"));
        list.add(new JSONObject().set("serial_number","91"));
        list.add(new JSONObject().set("serial_number","99"));
        list.add(new JSONObject().set("serial_number","104"));
        list.add(new JSONObject().set("serial_number","109"));


        int totalTasks = list.size();  // 总任务数量
        int maxThreads = 3;    // 最大并发线程数量
        // 创建一个具有固定线程数的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);

        // 使用信号量来控制最多3个线程同时执行
        Semaphore semaphore = new Semaphore(maxThreads);


        for (int i = 0; i < totalTasks; i++) {
            semaphore.acquire();  // 获取信号量
            System.out.println("semaphore = "+semaphore.getQueueLength());

            final JSONObject jsonObject = list.get(i);
            executorService.submit(() -> {
                try {
                    //业务
                    Util.stopBrowser(jsonObject);
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
                }
            });
        }

        // 关闭线程池，等待所有任务完成
        executorService.shutdown();


    }

}
