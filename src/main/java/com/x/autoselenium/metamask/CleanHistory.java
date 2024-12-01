package com.x.autoselenium.metamask;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CleanHistory {
    public static void cleanHistory(JSONObject jsonObject) throws InterruptedException, IOException {

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

        // 访问
        browser.get("chrome-extension://apfklcgfjacbjddimlfdkcipgmkclfco/home.html#settings/advanced");
        Util.RandomSleep(3,5);

        //清理活动选项卡数据
        browser.findElement(By.xpath("//button[@class='button btn--rounded btn-danger btn--large settings-tab__button--red']")).click();
        Util.RandomSleep(3,5);

        //清除
        browser.findElement(By.xpath("//button[@class='button btn--rounded btn-danger-primary modal-container__footer-button']")).click();
        Util.RandomSleep(3,5);

        //访问
        browser.get("chrome-extension://apfklcgfjacbjddimlfdkcipgmkclfco/home.html#");
        Util.RandomSleep(3,5);

        Log.logs.add(jsonObject.getStr("serial_number"));

        File src = browser.getScreenshotAs(OutputType.FILE);
        Util.takeScreenShot(src,"hemi clear history" + jsonObject.getStr("serial_number") + ".png");

        // 关闭
        browser.close();
        // 退出
        browser.quit();
    }
}
