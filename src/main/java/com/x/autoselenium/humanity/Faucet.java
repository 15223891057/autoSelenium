package com.x.autoselenium.humanity;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

public class Faucet {
    public static void receive1tHP(JSONObject jsonObject) throws InterruptedException {

        //先获取serialNumber，后面用到的地方多
        String serialNumber = jsonObject.getStr("serial_number");
        if (serialNumber==null || serialNumber.equals("")){
            System.out.println("*****************************获取serialNumber失败，请检查*****************************");
            return;
        }

        //打开浏览器
        ChromeDriver browser = Util.startBrowser(jsonObject);
        if (browser == null) {
            return;
        }

        //关闭所有标签页
        Util.CloseAllTabs(browser);

        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);
        browser.get("https://faucet.testnet.humanity.org/");
        Util.RandomSleep(10,20);

        String address = JSONUtil.parse(jsonObject.getStr("remark")).getByPath("metamaskAdd").toString();

        browser.findElement(By.xpath("//input[@class='input is-rounded']")).sendKeys(address);
        Util.RandomSleep(3,5);

        browser.findElement(By.xpath("//button[@class='button is-primary is-rounded']")).click();
        Util.RandomSleep(3,5);

        Log.success.add(serialNumber);

        // 关闭
        browser.close();
        // 退出
        browser.quit();

    }
}
