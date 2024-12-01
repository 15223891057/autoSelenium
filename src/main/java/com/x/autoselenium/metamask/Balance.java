package com.x.autoselenium.metamask;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Balance {
    public static void checkBalance(JSONObject jsonObject) throws InterruptedException, IOException {

        //打开浏览器
        ChromeDriver browser = Util.startBrowser(jsonObject);
        if (browser == null) {
            return;
        }

        //关闭所有标签页
        Util.CloseAllTabs(browser);

        //登录小狐狸并切换到hemi网络
        Util.loginMetaMask(browser, Constant.NETWORK_HEMISEPOLIA);
        //这里等待个几秒钟
        Util.RandomSleep(5,10);

        //获取余额
        String balance = browser.findElement(By.xpath("//div[@class='eth-overview__primary-container']")).getText().replace("\n", " ");
        String sn = jsonObject.getStr("serial_number");
        if (sn.length()==1){
            sn = "  " + sn;
        }else if (sn.length()==2){
            sn = " " + sn;
        }
        Log.logs.add(sn + " 余额为： "  + balance);

        // 关闭
        browser.close();
        // 退出
        browser.quit();
    }
}
