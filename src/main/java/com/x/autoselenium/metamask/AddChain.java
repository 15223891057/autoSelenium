package com.x.autoselenium.metamask;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Set;

public class AddChain {
    public static void addChain(JSONObject jsonObject) throws InterruptedException {
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

        //检测当前是否已添加hemi网络

        // 打开选择网络窗口
        browser.findElement(By.xpath("//button[@class='mm-box mm-picker-network multichain-app-header__contents__network-picker mm-box--margin-2 mm-box--padding-right-4 mm-box--padding-left-2 mm-box--display-none mm-box--sm:display-flex mm-box--gap-2 mm-box--align-items-center mm-box--background-color-background-alternative mm-box--rounded-pill']")).click();
        Util.RandomSleep(3,5);

        //获取所有网络
        List<WebElement> list = browser.findElements(By.xpath("//p[@class='mm-box mm-text mm-text--body-md mm-text--ellipsis mm-box--color-text-default mm-box--background-color-transparent']"));
        for (WebElement webElement : list) {
            if (webElement.getText().equals("Hemi Sepolia")) {
                System.out.println("已有hemi网络，无需添加");
                return;
            }
        }

        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);
        // 访问
        browser.get("https://15223891057.github.io/autoSelenium/");
        Util.RandomSleep(3,5);

        //获取当前页面handle
        String mainHandle = browser.getWindowHandle();
        //获取当前所有handles
        Set<String> windowHandles = browser.getWindowHandles();

        browser.findElement(By.id("hemi")).click();
        Thread.sleep(3000);

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
                    browser.findElement(By.xpath("//button[@class='button btn--rounded btn-primary']")).click();
                    Util.RandomSleep(3,5);
                } catch (Exception e) {
                    //System.out.println("没有前进按钮了，循环结束，回到业务页面");
                    browser.switchTo().window(mainHandle);
                    break;
                }
            }
        }else{
            //System.out.println("=========小狐狸没能弹出=============");
        }

        Log.success.add(jsonObject.getStr("serial_number"));
    }
}
