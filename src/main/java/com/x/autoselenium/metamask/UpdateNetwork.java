package com.x.autoselenium.metamask;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.lang.model.element.Element;
import java.time.Duration;

public class UpdateNetwork {
    public static void updateNetwork(JSONObject jsonObject) throws InterruptedException {
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
        browser.get("chrome-extension://apfklcgfjacbjddimlfdkcipgmkclfco/home.html#settings/networks");
        Util.RandomSleep(5,10);

        WebElement input = browser.findElement(By.xpath("//input[@class='form-field__input']"));
        input.clear();
        Util.RandomSleep(2,3);
        input.sendKeys("https:/int02.testnet.rpc.hemi.network/rpc");
        Util.RandomSleep(2,3);

        for (int i=0;i<5;i++){
            // 定位到按钮元素
            WebElement button = browser.findElement(By.xpath("//button[@class='button btn--rounded btn-primary' and @datatestid='network-form-network-save-button']"));
            Util.RandomSleep(3,5);
            // 检查按钮是否可见且启用
            if (button.isDisplayed() && button.isEnabled()) {
                System.out.println("按钮是可点击的！");
                button.click();  // 执行点击操作
                break;
            }else{
                input.sendKeys(" ");
                Util.RandomSleep(3,5);
            }
        }
    }
}
