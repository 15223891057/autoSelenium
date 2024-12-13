package com.x.autoselenium.immutable;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.Set;

public class DailyCheckIn {
    public static void doCheckIn(JSONObject jsonObject) throws InterruptedException {

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
        browser.get("https://play.immutable.com/quests/");
        Util.RandomSleep(5,10);

        //判断是否登录
        if (!browser.findElements(By.xpath("//button[@class='Button Button--primary Button--medium BaseClickable css-sj11m4']")).isEmpty()){

            String text = browser.findElement(By.xpath("//button[@class='Button Button--primary Button--medium BaseClickable css-sj11m4']")).getText();
            if (text.contains("Sign")){
                //未登录
                System.out.println(jsonObject.getStr("serial_number") + " 未登录，直接开始下一个");
                return;
            }

        }

        //获取当前页面handle
        String mainHandle = browser.getWindowHandle();
        //获取当前所有handles
        Set<String> windowHandles = browser.getWindowHandles();

        //点击claim 3 gems
        browser.findElement(By.xpath("//button[@class='Button Button--primary Button--large BaseClickable css-178y9ur']")).click();
        Util.RandomSleep(30,35);

        //获取当前所有handles
        Set<String> windowHandles2 = browser.getWindowHandles();

        if (windowHandles.size() < windowHandles2.size()) {
            for (String handle : windowHandles2) {
                if (handle.contains("zkevm")) {
                    //切换到accept页面
                    browser.switchTo().window(handle);
                    break;
                }
            }
        }

        System.out.println(browser.getCurrentUrl());

        if (!browser.getCurrentUrl().contains("zkevm")) {
            System.out.println("签到确认页面没能加载出来，直接结束");
            return;
        }

        //点击Accept
        browser.findElement(By.xpath("//button[@class='Button Button--primary Button--large BaseClickable css-j1v98w' and @data-testid='zkevm__footer__approveButton']")).click();
        Util.RandomSleep(3,5);

        //回到主要页面
        browser.switchTo().window(mainHandle);

        //页面截图保存
        File src = browser.getScreenshotAs(OutputType.FILE);
        Util.takeScreenShot(src,"immutable play check in " + jsonObject.getStr("serial_number") + ".png");

        Log.logs.add(jsonObject.getStr("serial_number"));

        // 关闭
        browser.close();
        // 退出
        browser.quit();



    }
}
