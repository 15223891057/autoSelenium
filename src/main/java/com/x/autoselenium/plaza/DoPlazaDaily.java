package com.x.autoselenium.plaza;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.hemi.Capsule;
import com.x.autoselenium.humanity.Faucet;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.ThreadUtil;
import com.x.autoselenium.utils.Util;
import io.swagger.util.Json;

import java.util.ArrayList;
import java.util.List;

public class DoPlazaDaily {
    public static void main(String[] args) throws InterruptedException {
        List<JSONObject> list = Util.getAll(true);

        ThreadUtil.doThreadTasks(list, 3, 0.25, jsonObject -> PlazaDaily.daily(jsonObject));

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

        ThreadUtil.doThreadTasks(failedList, 3, 0.25, jsonObject -> PlazaDaily.daily(jsonObject));

        System.out.println("成功 "+Log.success.size()+" 个：" + Log.success);
        System.out.println("失败 "+Log.fails.size()+" ：" + Log.fails);
        System.out.println("需要领水 "+Log.insufficientBalance.size()+" ：" + Log.insufficientBalance);
    }
}
