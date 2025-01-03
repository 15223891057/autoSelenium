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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Test {


    public static void main(String[] args) throws InterruptedException, IOException {

        // 定义原始的int数组
        int[] numbers = {89, 89, 89, 59, 108, 45, 45, 88, 88, 35, 35, 8, 8, 8};

        // 使用Set去重
        Set<Integer> set = new HashSet<>();
        for (int number : numbers) {
            set.add(number);
        }

        // 将去重后的元素转换回数组
        int[] uniqueNumbers = new int[set.size()];
        int index = 0;
        for (int number : set) {
            uniqueNumbers[index++] = number;
        }

        // 输出去重后的结果
        System.out.println("水不够了，需要领水: " + Arrays.toString(uniqueNumbers));
        System.out.println(uniqueNumbers.length);





    }
}
