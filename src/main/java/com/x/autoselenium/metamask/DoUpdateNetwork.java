package com.x.autoselenium.metamask;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.ThreadUtil;
import com.x.autoselenium.utils.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoUpdateNetwork {
    public static void main(String[] args) throws InterruptedException {
        List<JSONObject> list = Util.getAll(true);

        ThreadUtil.doThreadTasks(list, 1, 0.25, jsonObject -> {
            UpdateNetwork.updateNetwork(jsonObject);
        });

        System.out.println("成功 "+ Log.success.size()+" 个：" + Log.success);
        System.out.println("失败 "+Log.fails.size()+" ：" + Log.fails);

        if (Log.fails.size()==0){
            return;
        }

        //将失败的任务修改代理重新再做一次
        List<JSONObject> failedList = new ArrayList<>();
        for (JSONObject obj : list) {
            if (Log.fails.contains(obj.getStr("serial_number"))){
                //修改代理
                Util.updateProxy(obj);
                Thread.sleep(2000);
                failedList.add(obj);
            }
        }

        System.out.println("===========================开始重试失败的任务==========================");

        Log.success.clear();
        Log.fails.clear();


        ThreadUtil.doThreadTasks(failedList, 1, 0.25, jsonObject -> {
            UpdateNetwork.updateNetwork(jsonObject);
        });

        System.out.println("成功 "+Log.success.size()+" 个：" + Log.success);
        System.out.println("失败 "+Log.fails.size()+" ：" + Log.fails);
    }
}
