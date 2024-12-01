package com.x.autoselenium.hemi;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.analog.Vote;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.metamask.CleanHistory;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.*;

public class Test {


    public static void main(String[] args) throws InterruptedException, IOException {

        int[] browser = {95, 93, 97, 48};

        for (int i: browser) {
            System.out.println( i + " : " + JSONUtil.parseObj(HttpUtil.get("http://local.adspower.net:50325/api/v1/browser/start?serial_number="+i)));
            Thread.sleep(2000);
        }





    }
}
