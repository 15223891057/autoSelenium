package com.x.autoselenium.humanity;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.utils.Util;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<JSONObject> list = Util.getAll();
        int hk = 0;
        int tw = 0;
        int sg = 0;
        int my = 0;
        int other = 0;
        for (JSONObject obj : list) {
            String country = obj.getStr("ip_country");
            if (country.contains("hk")){
                hk++;
            }else if (country.contains("tw")){
                tw++;
            }else if (country.contains("sg")){
                sg++;
            }else if (country.contains("my")){
                my++;
            }else{
                other++;
            }
        }

        System.out.println("hk = " + hk);
        System.out.println("tw = " + tw);
        System.out.println("sg = " + sg);
        System.out.println("my = " + my);
        System.out.println("ot = " + other);
    }
}
