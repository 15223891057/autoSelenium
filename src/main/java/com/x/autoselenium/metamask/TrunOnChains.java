package com.x.autoselenium.metamask;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class TrunOnChains {
    public static void trunOn(JSONObject jsonObject) throws InterruptedException {
        //打开浏览器
        ChromeDriver browser = Util.startBrowser(jsonObject);
        if (browser == null) {
            return;
        }

        //关闭所有标签页
        Util.CloseAllTabs(browser);

        //登录小狐狸
        Util.loginMetaMask(browser, null);
        //这里等待个几秒钟
        Util.RandomSleep(5,10);

        // 访问
        browser.get("chrome-extension://apfklcgfjacbjddimlfdkcipgmkclfco/home.html#settings/security");
        Util.RandomSleep(3,5);

        List<WebElement> list = browser.findElements(By.xpath("//div[@class='mm-box network-toggle-wrapper mm-box--margin-top-6 mm-box--margin-bottom-6 mm-box--display-flex mm-box--gap-4 mm-box--flex-direction-row mm-box--justify-content-space-between']"));



        for (WebElement webElement : list) {
            String isChecked = webElement.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("value");
            System.out.println(isChecked);
            if (isChecked.equals("false")) {
                webElement.findElement(By.xpath(".//div[@class='mm-box mm-box--margin-left-auto']")).click();
            }
            System.out.println("==================================================================");
//            System.out.println(webElement.findElement(By.xpath("//p[@class='mm-box mm-text mm-text--body-md mm-text--ellipsis mm-box--color-text-default mm-box--background-color-transparent']")).getText());
//            System.out.println(webElement.findElement(By.xpath("//input[@type='checkbox']")).getAttribute("value"));
        }
    }
}
