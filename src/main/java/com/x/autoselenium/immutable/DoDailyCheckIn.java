package com.x.autoselenium.immutable;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.hemi.Swap;
import com.x.autoselenium.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class DoDailyCheckIn {
    public static void main(String[] args) throws InterruptedException {

        List<JSONObject> list = new ArrayList<>();
        list.add(new JSONObject().set("serial_number","5"));
        //业务
        DailyCheckIn.doCheckIn(list.get(0));

    }
}
