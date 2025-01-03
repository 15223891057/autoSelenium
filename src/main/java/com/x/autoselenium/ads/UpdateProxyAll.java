package com.x.autoselenium.ads;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class UpdateProxyAll {
    public static void main(String[] args) throws InterruptedException {
        List<JSONObject> list = Util.getAll(true);
        for (JSONObject obj : list) {
            //修改代理
            Util.updateProxy(obj);
            Thread.sleep(1000);
        }
    }
}
