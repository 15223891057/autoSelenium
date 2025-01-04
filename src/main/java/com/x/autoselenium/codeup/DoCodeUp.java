package com.x.autoselenium.codeup;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class DoCodeUp {
    public static void main(String[] args) throws InterruptedException {

        int[] targetA = {3,5,6,7,8,9,10,11,12,18};
        List<JSONObject> list = Util.getAll(true);

        List<String> targetL = new ArrayList<>();
        for (int i:targetA) {
            targetL.add(String.valueOf(i));
        }

        while (true){
            System.out.println("开始收菜");
            for (JSONObject obj : list) {
                if (targetL.contains(obj.getStr("serial_number"))){

                    CodeUp.doCodeUp(obj);
                    //关闭浏览器
                    Util.stopBrowser(obj);
                }
            }
            System.out.println("完成收菜，进入休眠");
            //每执行完一批就休眠1500秒
            Util.RandomSleep(1500,1501);
            System.out.println("休眠结束");
        }

    }
}
