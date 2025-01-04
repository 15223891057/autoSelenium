package com.x.autoselenium.ads;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class UpdateProxySpecify {
    public static void main(String[] args) throws InterruptedException {
        List<JSONObject> list = Util.getAll(true);
        int[] targetA = {90, 57, 83, 103};
        List<String> targetL = new ArrayList<>();
        for (int i:targetA) {
            targetL.add(String.valueOf(i));
        }

//        for (JSONObject obj : list) {
//            if (targetL.contains(obj.getStr("serial_number"))){
//                //先关闭浏览器
//                Util.stopBrowser(obj);
//                Thread.sleep(1000);
//            }
//        }
//
//        for (JSONObject obj : list) {
//            if (targetL.contains(obj.getStr("serial_number"))){
//                //修改代理
//                Util.updateProxy(obj);
//                Thread.sleep(1000);
//            }
//        }

        for (JSONObject obj : list) {
            if (targetL.contains(obj.getStr("serial_number"))){
                //打开浏览器
                JSONObject resJson = JSONUtil.parseObj(HttpUtil.get("http://local.adspower.net:50325/api/v1/browser/start?serial_number="+obj.getStr("serial_number")));
                System.out.println(obj.getStr("serial_number") + " 启动浏览器请求的返回值为 " + resJson);
                Thread.sleep(1000);
            }
        }

    }
}
