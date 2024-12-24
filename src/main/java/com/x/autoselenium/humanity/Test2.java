package com.x.autoselenium.humanity;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        List<JSONObject> list = Util.getAll(true);
        List<String> countries = new ArrayList<>();
        for (JSONObject obj : list) {
            if (countries.contains(obj.getStr("ip_country"))){
                continue;
            }
            countries.add(obj.getStr("ip_country"));
        }

        System.out.println(countries);

    }
}
