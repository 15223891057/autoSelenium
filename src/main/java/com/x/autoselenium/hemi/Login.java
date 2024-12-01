package com.x.autoselenium.hemi;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Set;

public class Login {

    public static void main(String[] args) throws InterruptedException {
        hemiDaily("3");
    }

    public static void hemiDaily(String serialNumber) throws InterruptedException {
        //打开浏览器
        JSON resJson = JSONUtil.parse(HttpUtil.get("http://local.adspower.net:50325/api/v1/browser/start?serial_number="+serialNumber));

        System.setProperty("webdriver.chrome.driver", (String) resJson.getByPath("data.webdriver"));
        System.out.println((String) resJson.getByPath("data.webdriver"));
        // 使用ChromeOptions设置调试地址
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", resJson.getByPath("data.ws.selenium"));

        // 创建远程ChromeDriver实例
        ChromeDriver browser = new ChromeDriver(options);
        // 最大化浏览器窗口
        browser.manage().window().maximize();

        //关闭所有标签页
        Util.CloseAllTabs(browser);

        //登录小狐狸
        Util.loginMetaMask(browser,null);
        //获取小狐狸页面handle
        String metamaskHandle = browser.getWindowHandle();

        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);
        // 访问
        browser.get("https://points.absinthe.network/hemi/start");
        Util.RandomSleep(3,5);
        //获取当前页面handle
        String hemiHandle = browser.getWindowHandle();

        //获取当前所有handles
        Set<String> windowHandles = browser.getWindowHandles();
        System.out.println(windowHandles);

        //点击connect wallet
        browser.findElement(By.xpath("//button[@class='text-sm font-semibold max-w-full justify-center transition duration-150 ease-in-out disabled:cursor-not-allowed __className_36bd41 css-ds9w7p'][1]")).click();
        Util.RandomSleep(3,5);

        //点击EVM wallets
        browser.findElement(By.xpath("//button[@class='text-sm font-semibold max-w-full transition duration-150 ease-in-out disabled:cursor-not-allowed __className_36bd41 flex items-center p-4 justify-start gap-4 w-full css-1hyeu5s']")).click();
        Util.RandomSleep(3,5);


        //点击metamask
        browser.findElement(By.xpath("//button[@class='text-sm font-semibold max-w-full transition duration-150 ease-in-out disabled:cursor-not-allowed __className_36bd41 flex items-center p-4 justify-start gap-4 w-full css-1hyeu5s'][2]")).click();
        Util.RandomSleep(3,5);

        //获取当前所有handles
        Set<String> windowHandles2 = browser.getWindowHandles();
        System.out.println(windowHandles2);

        for (String handle : windowHandles2) {
            if (!windowHandles.contains(handle)){
                browser.switchTo().window(handle);
                System.out.println(handle);
            }
        }

        for (int i=0;i<5;i++){
            try{
                //只要有向下滚动的按钮，就一直点
                browser.findElement(By.xpath("//button[@class='mm-box mm-button-icon mm-button-icon--size-md confirm-scroll-to-bottom__button mm-box--display-flex mm-box--justify-content-center mm-box--align-items-center mm-box--color-primary-default mm-box--background-color-background-default mm-box--rounded-full']")).click();
                Util.RandomSleep(3,5);
            } catch (Exception e) {
                System.out.println("没有向下滚动的按钮了，到底了");
//                System.out.println(e.getMessage());
                break;
            }

        }

        for (int i=0;i<5;i++){
            //前进
            try{
                //只要获取前进按钮不报异常，就一直点前进
                browser.findElement(By.xpath("//button[@class='mm-box mm-text mm-button-base mm-button-base--size-lg mm-button-base--block mm-button-primary mm-text--body-md-medium mm-box--padding-0 mm-box--padding-right-4 mm-box--padding-left-4 mm-box--display-inline-flex mm-box--justify-content-center mm-box--align-items-center mm-box--color-primary-inverse mm-box--background-color-primary-default mm-box--rounded-pill']")).click();
                Util.RandomSleep(3,5);
            } catch (Exception e) {
                System.out.println("没有前进按钮了，循环结束，回到业务页面");
//                System.out.println(e.getMessage());
                browser.switchTo().window(hemiHandle);
                break;
            }
        }
        // 访问
        browser.get("https://points.absinthe.network/hemi/d/dashboard");
        Util.RandomSleep(3,5);



    }
}
