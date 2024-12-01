package com.x.autoselenium.metamask;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class Address {
    public static void getAddress(JSONObject jsonObject) throws InterruptedException, IOException {

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

        //点击账户选项
        browser.findElement(By.xpath("//button[@class='mm-box mm-button-icon mm-button-icon--size-sm mm-box--display-inline-flex mm-box--justify-content-center mm-box--align-items-center mm-box--color-icon-default mm-box--background-color-transparent mm-box--rounded-lg']")).click();
        Util.RandomSleep(3,5);

        //点击账户详情
        browser.findElement(By.xpath("//button[@class='menu-item' and @data-testid='account-list-menu-details']")).click();
        Util.RandomSleep(3,5);

        String address = browser.findElement(By.xpath("//p[@class='mm-box mm-text qr-code__address-segments mm-text--body-md mm-box--margin-bottom-4 mm-box--color-text-default']")).getText().replace("\n", " ");

        String sn = jsonObject.getStr("serial_number");
        if (sn.length()==1){
            sn = "  " + sn;
        }else if (sn.length()==2){
            sn = " " + sn;
        }
        Log.logs.add(sn + " 地址为： "  + address);

        // 关闭
        browser.close();
        // 退出
        browser.quit();
    }
}
