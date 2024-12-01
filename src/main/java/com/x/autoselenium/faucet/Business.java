package com.x.autoselenium.faucet;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * 领水
 */
public class Business {

    /**
     * 领取
     * @param serialNumber
     */
    public static void getEthereumSepolia(String serialNumber) throws InterruptedException {
        //打开浏览器
        JSON resJson = JSONUtil.parse(HttpUtil.get("http://local.adspower.net:50325/api/v1/browser/start?serial_number="+serialNumber));
//        // 浏览器驱动
//        System.setProperty("webdriver.chrome.driver", (String) resJson.getByPath("data.webdriver"));
//        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
//        System.out.println(System.getProperty("webdriver.chrome.driver"));
        // 使用ChromeOptions设置调试地址
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", resJson.getByPath("data.ws.selenium"));

        // 创建远程ChromeDriver实例
        ChromeDriver browser = new ChromeDriver(options);
        // 最大化浏览器窗口
        browser.manage().window().maximize();

        //关闭所有标签页
        //Util.CloseAllTabs(browser);

        //登录小狐狸
        //Util.loginMetaMask(browser,null);

        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);
        // 访问，访问这个网站之前只要是登录了小狐狸的，就会保持登录状态
        browser.get("https://faucets.chain.link/");
        Util.RandomSleep(3,5);

        //选择测试币
        browser.findElement(By.xpath("//h3[contains(text(), 'Ethereum Sepolia')]")).click();
        Util.RandomSleep(3,5);

        //continue
        browser.findElement(By.xpath("//button[@class='primary selectedFaucets_continue__GGhnV']")).click();
        Util.RandomSleep(3,5);

        //真人验证

        /**
         * Cloudflare Turnstile真人验证绕不过去，宣告失败
         */
        WebElement iframe = browser.findElement(By.id("faucets-turnstile")).findElement(By.xpath("//iframe"));
        System.out.println(iframe.getAttribute("src"));
        System.out.println(iframe.getAttribute("title"));
        browser.switchTo().frame(iframe);
        System.out.println(browser.getCurrentUrl());
        System.out.println(browser.findElement(By.xpath("//div[@id='content']")).getText());
        Util.RandomSleep(3,5);
        // 切换回主文档
        browser.switchTo().defaultContent();

    }
}
