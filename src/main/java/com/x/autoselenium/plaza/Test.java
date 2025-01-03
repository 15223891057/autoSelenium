package com.x.autoselenium.plaza;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.utils.Util;

import java.util.List;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        List<JSONObject> list = Util.getAll(true);
        PlazaDaily.daily(list.get(0));
    }
}
