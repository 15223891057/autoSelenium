package com.x.autoselenium.metamask;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class DoAddressAlone {
    public static void main(String[] args) throws InterruptedException {
//        List<JSONObject> list = Util.getAll();

        List<JSONObject> list = new ArrayList<>();
        list.add(new JSONObject().set("serial_number","107"));


        for (JSONObject jsonObject : list) {

            try {
                //业务
                Address.getAddress(jsonObject);

                Thread.sleep(2000);

            } catch (Exception e) {
                // ANSI转义序列开启红色文本
                System.out.print("\033[31m");
                System.out.println(jsonObject.getStr("serial_number") + "的线程被中断了");
                System.out.print("\033[0m");
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {

                //关闭浏览器
                Util.stopBrowser(jsonObject);
            }
        }

        System.out.println("成功"+Log.logs.size()+"个 ：" + Log.logs);

        List<String> fails = new ArrayList<>();

        for (JSONObject jsonObject : list) {
            if (!Log.logs.contains(jsonObject.getStr("serial_number"))) {
                fails.add(jsonObject.getStr("serial_number"));
            }
        }

        System.out.println("失败"+fails.size()+"个 ：" + fails);

        for (String s: Log.logs){
            System.out.println(s);
        }


        System.out.println(new JSONArray(Log.list));

    }
}
