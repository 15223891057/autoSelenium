package com.x.autoselenium.hemi;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Capsule {

    public static void hemiCapsule(JSONObject jsonObject) throws InterruptedException {
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
        Util.RandomSleep(3,5);

        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);
        // 访问
        browser.get("https://app.capsulelabs.xyz/");
        Util.RandomSleep(3,5);
        //获取当前页面handle
        String hemiHandle = browser.getWindowHandle();
        //获取当前所有handles
        Set<String> windowHandles = browser.getWindowHandles();

        //检测当前页面是否连接了metamask
        String content = browser.findElement(By.xpath("//div[@class='flex items-center gap-2 md:gap-4 lg:gap-6']")).getText();
        //如果没有登录小狐狸
        if (content.contains("Connect Wallet") || content.contains("Connect")){
            //进行登录小狐狸操作
            //点击登录按钮
            browser.findElement(By.xpath("//button[@class='bg-capsule-gradient rounded-xl py-2 px-4 text-white']")).click();
            Util.RandomSleep(3,5);
            //选择小狐狸
            browser.findElement(By.xpath("//button[@class='flex w-full items-center gap-4 rounded-lg bg-neutral-6 p-4 font-medium text-white']")).click();
            Util.RandomSleep(3,5);

            //这里再次检测当前页面是否连接了metamask
            content = browser.findElement(By.xpath("//div[@class='flex items-center gap-2 md:gap-4 lg:gap-6']")).getText();
            //如果没有登录小狐狸
            if (content.contains("Connect Wallet") || content.contains("Connect")){
                //获取当前所有handles
                Set<String> windowHandles2 = browser.getWindowHandles();
                if (windowHandles.size() < windowHandles2.size()){
                    for (String handle : windowHandles2) {
                        if (!windowHandles.contains(handle)){
                            browser.switchTo().window(handle);
                        }
                    }

                    for (int i=0;i<5;i++){
                        try{
                            //只要有向下滚动的按钮，就一直点
                            browser.findElement(By.xpath("//button[@class='mm-box mm-button-icon mm-button-icon--size-md confirm-scroll-to-bottom__button mm-box--display-flex mm-box--justify-content-center mm-box--align-items-center mm-box--color-primary-default mm-box--background-color-background-default mm-box--rounded-full']")).click();
                            Util.RandomSleep(3,5);
                        } catch (Exception e) {
//                            //System.out.println("没有向下滚动的按钮了，到底了");
                            break;
                        }

                    }

                    for (int i=0;i<5;i++){
                        //前进
                        try{
                            //只要获取前进按钮不报异常，就一直点前进
//                            //System.out.println("第" + (i+1) + "次前进");
                            browser.findElement(By.xpath("//button[@class='button btn--rounded btn-primary page-container__footer-button' and @data-testid='page-container-footer-next']")).click();
                            Util.RandomSleep(3,5);
                        } catch (Exception e) {
                            //System.out.println("没有前进按钮了，循环结束，回到业务页面");
                            browser.switchTo().window(hemiHandle);
                            break;
                        }
                    }
                    //这里休息5-10秒等待小狐狸这边处理完成
                    Util.RandomSleep(5,10);
                }else{
                    //System.out.println("=========小狐狸没能弹出=============");
                }
            }

        }

        //这里再次检测当前页面是否连接了metamask
        content = browser.findElement(By.xpath("//div[@class='flex items-center gap-2 md:gap-4 lg:gap-6']")).getText();
        //如果没有登录小狐狸
        if (content.contains("Connect Wallet") || content.contains("Connect")){
            //System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX小狐狸登录失败，原因不明，请检查代码XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            //结束执行
            return;
        }

        //获取输入name的表单元素
        WebElement formElement = browser.findElement(By.xpath("//form[@class='flex flex-col gap-4']"));

        //输入Name
        browser.findElement(By.xpath("//input[@name='name']")).sendKeys((String)JSONUtil.parse(jsonObject.getByPath("remark")).getByPath("twAccount"));
        Util.RandomSleep(3,5);

        //点击continue
        formElement.findElement(By.xpath("//button[@type='submit']")).click();
        Util.RandomSleep(3,5);

        //重新获取element
        formElement = browser.findElement(By.xpath("//form[@class='flex flex-col gap-4']"));
        List<WebElement> buttons = formElement.findElement(By.xpath("//div[@class='flex gap-4 pt-4']")).findElements(By.tagName("button"));
        //点击continue
        buttons.get(1).click();
        Util.RandomSleep(3,5);


        //重新获取element
        formElement = browser.findElement(By.xpath("//form[@class='flex flex-col gap-4']"));
        //选中checkbox
        browser.findElement(By.xpath("//input[@name='privacyPolicy']")).click();
        Util.RandomSleep(3,5);
        //点击transfer
        buttons = formElement.findElement(By.xpath("//div[@class='flex gap-4']")).findElements(By.tagName("button"));
        //点击continue
        buttons.get(1).click();
        Util.RandomSleep(3,5);

        //点击continue
        browser.findElement(By.xpath("//button[@class='w-full rounded-lg py-2 bg-capsule-purple']")).click();
        //这里因为网络问题，需要多等一会，暂定等待30-60秒
        Util.RandomSleep(30,60);


        //获取当前所有handles
        Set<String> windowHandles2 = browser.getWindowHandles();

        if (windowHandles.size() < windowHandles2.size()){
            for (String handle : windowHandles2) {
                if (!windowHandles.contains(handle)){
                    browser.switchTo().window(handle);
                }
            }

            for (int i=0;i<5;i++){
                try{
                    //只要有向下滚动的按钮，就一直点
                    browser.findElement(By.xpath("//button[@class='mm-box mm-button-icon mm-button-icon--size-md confirm-scroll-to-bottom__button mm-box--display-flex mm-box--justify-content-center mm-box--align-items-center mm-box--color-primary-default mm-box--background-color-background-default mm-box--rounded-full']")).click();
                    Util.RandomSleep(3,5);
                } catch (Exception e) {
                    //System.out.println("没有向下滚动的按钮了，到底了");
//                //System.out.println(e.getMessage());
                    break;
                }

            }

            for (int i=0;i<5;i++){
                //前进
                try{
                    //只要获取前进按钮不报异常，就一直点前进
                    //System.out.println("第" + (i+1) + "次前进");
                    browser.findElement(By.xpath("//button[@class='mm-box mm-text mm-button-base mm-button-base--size-lg mm-button-base--block mm-button-primary mm-text--body-md-medium mm-box--padding-0 mm-box--padding-right-4 mm-box--padding-left-4 mm-box--display-inline-flex mm-box--justify-content-center mm-box--align-items-center mm-box--color-primary-inverse mm-box--background-color-primary-default mm-box--rounded-pill']")).click();
                    Util.RandomSleep(3,5);
                } catch (Exception e) {
                    //System.out.println("没有前进按钮了，循环结束，回到业务页面");
//                //System.out.println(e.getMessage());
                    browser.switchTo().window(hemiHandle);
                    break;
                }
            }
        }else{
            //System.out.println("=========小狐狸没能弹出=============");
        }

        Log.logs.add(jsonObject.getStr("serial_number"));

        //页面截图保存
        File src = browser.getScreenshotAs(OutputType.FILE);
        Util.takeScreenShot(src,"hemi capsule " + jsonObject.getStr("serial_number") + ".png");

        // 关闭
        browser.close();
        // 退出
        browser.quit();

    }
}
