package com.x.autoselenium.analog;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;
import java.util.Random;

public class Vote {

    public static void vote(JSONObject jsonObject) throws InterruptedException {
        //打开浏览器
        ChromeDriver browser = Util.startBrowser(jsonObject);
        if (browser == null) {
            return;
        }

        //关闭所有标签页
        Util.CloseAllTabs(browser);

        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);
        // 访问
        browser.get("https://testnet.analog.one/");

        Util.RandomSleep(3,5);

        //点击start voting元素
        browser.findElement(By.xpath("//button[@class='btn relative max-h-full primary h-[unset] !py-3 w-full !rounded-t-none font-text-bold']")).click();
        Util.RandomSleep(10,15);

        //获取所有可投票的按钮
        List<WebElement> votes = browser.findElements(By.xpath("//button[@class='btn relative h-10 max-h-full primary w-full cursor-pointer xl:order-1 order-2']"));
        if (!votes.isEmpty()){
            //点击vote
            votes.get(0).click();
            Util.RandomSleep(3,5);
        }

        //页面截图保存
        File src = browser.getScreenshotAs(OutputType.FILE);
        Util.takeScreenShot(src,"analog vote " + jsonObject.getStr("serial_number") + ".png");

        Log.logs.add(jsonObject.getStr("serial_number"));

        // 关闭
        browser.close();
        // 退出
        browser.quit();
    }
}
