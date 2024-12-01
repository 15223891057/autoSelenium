package com.x.autoselenium.fission;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;

public class Business {

    public static void getEarlyAccess(JSONObject jsonObject) throws InterruptedException {
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
        browser.get("https://www.fission.xyz/");

        Util.RandomSleep(3,5);

        //点击按钮
        if (!browser.findElements(By.xpath("//button[@class='mantine-focus-auto mantine-active m_77c9d27d mantine-Button-root m_87cf2631 mantine-UnstyledButton-root'][1]")).isEmpty()){
            browser.findElement(By.xpath("//button[@class='mantine-focus-auto mantine-active m_77c9d27d mantine-Button-root m_87cf2631 mantine-UnstyledButton-root'][1]")).click();
            Util.RandomSleep(3,5);
        }else{
            System.out.println("===========================网络问题 " + jsonObject.getStr("serial_number") + "加载失败=================================");
            return;
        }


        //获取4个input
        List<WebElement> list = browser.findElements(By.xpath("//input[@class='_input_1e0tr_13 m_8fb7ebe7 mantine-Input-input mantine-TextInput-input']"));

        if (!list.isEmpty()){
            //name
            list.get(0).sendKeys((String) JSONUtil.parse(jsonObject.getByPath("remark")).getByPath("twAccount"));
            Util.RandomSleep(3,5);
            //email
            list.get(2).sendKeys((String) JSONUtil.parse(jsonObject.getByPath("remark")).getByPath("email"));
            Util.RandomSleep(3,5);
            list.get(3).sendKeys("wxe..5201314");
            Util.RandomSleep(3,5);

            //模拟enter键
            list.get(3).sendKeys(Keys.ENTER);
            Util.RandomSleep(10,15);

            //页面截图保存
            File src = browser.getScreenshotAs(OutputType.FILE);
            Util.takeScreenShot(src,"fission " + jsonObject.getStr("serial_number") + ".png");

            // 关闭
            browser.close();
            // 退出
            browser.quit();
        }



    }
}
