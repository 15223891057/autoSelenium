package com.x.autoselenium.skygate;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Util;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;

public class Business {

    public static void skygateCheckIn(JSONObject jsonObject) throws InterruptedException {
        //打开浏览器
        ChromeDriver browser = Util.startBrowser(jsonObject);
        if (browser == null) {
            return;
        }

        //关闭所有标签页
        Util.CloseAllTabs(browser);

        //登录小狐狸
        Util.loginMetaMask(browser,null);

        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);
        // 访问
        browser.get("https://skygate.skyarkchronicles.com/farming");

        Util.RandomSleep(3,5);
        //点击地址元素
        browser.findElement(By.className("styles_account__LfCmI")).click();
        Util.RandomSleep(3,5);
        //点击member
        browser.findElement(By.xpath("//a[@href='/member']")).click();
        Util.RandomSleep(3,5);

        for (int i=0;i<5;i++){
            //点击空白处消除弹窗
            Actions action = new Actions(browser);
            action.moveByOffset(0,0).click().build().perform();
            //直接点击check-in
            browser.findElement(By.xpath("//div[@class='relative left-[-3px] mt-[10px] w-fit cursor-pointer transition duration-200 ease-in-out hover:scale-105 min-[550px]:mt-[10px]']")).click();
            Util.RandomSleep(3,5);
            //判断是否已经签到过了
            try{
                if (browser.findElement(By.xpath("//div[@class='my-8 text-center text-[20px] md:text-[30px] font-bold leading-none text-white']")).isDisplayed()){
                    System.out.println("今日已签到，无需重复签到");
                    break;
                }
            }catch (NoSuchElementException e){

            }

            //获取点击check-in的结果
            String checkInRes = browser.findElement(By.xpath("//div[@class='flex justify-between']")).getText();
//            System.out.println("checkInRes = " + checkInRes);
            if (checkInRes.contains("50")){
//                System.out.println("签到完成，获取积分成功");
                Log.logs.add(jsonObject.getStr("serial_number"));
                break;
            }


        }


        //页面截图保存
        File src = browser.getScreenshotAs(OutputType.FILE);
        Util.takeScreenShot(src,"skygate check in " + jsonObject.getStr("serial_number") + ".png");

        // 关闭
        browser.close();
        // 退出
        browser.quit();
    }
}
