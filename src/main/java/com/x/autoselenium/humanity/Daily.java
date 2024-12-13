package com.x.autoselenium.humanity;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.text.Element;
import java.io.File;
import java.util.List;
import java.util.Set;

public class Daily {
    public static void claimDailyReward(JSONObject jsonObject) throws InterruptedException {

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

        //登录小狐狸并切换到Humanity网络
        Util.loginMetaMask(browser, Constant.NETWORK_HUMANITY);
        //这里等待个几秒钟
        Util.RandomSleep(3,5);

        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);
        // 访问
        browser.get("https://testnet.humanity.org/dashboard");
        Util.RandomSleep(10,15);

        //获取当前页面handle
        String mainHandle = browser.getWindowHandle();
        //获取当前所有handles
        Set<String> windowHandles = browser.getWindowHandles();

        //检测当前页面是否连接了metamask

        //检测当前是否可以签到
        List<WebElement> timeLefts = browser.findElements(By.xpath("//div[@class='tiem']"));
        //是否可以签到
        int isPossible = 0;
        for (WebElement ele:timeLefts){
            //如果Until your next reward = 00:00:00，就可以签到

            System.out.println(ele.getText());

            if (ele.getText().contains("00:00:00")){
                isPossible = 1;
            }
        }

        if (isPossible == 0){
            System.out.println(serialNumber + " 未到签到时间");
            return;
        }


        //获取claim daily reward

        List<WebElement> bottoms = browser.findElements(By.xpath("//div[@class='bottom']"));

        System.out.println(bottoms.size());

        if (bottoms.isEmpty()){
            System.out.println(serialNumber + " 获取claim daily reward按钮失败");
            return;
        }

        for (WebElement ele:bottoms){

            System.out.println(ele.getText());

            if (ele.getText().contains("CLAIM DAILY REWARD")){
                ele.click();
                Util.RandomSleep(10,15);
                //获取当前所有handles
                Set<String> windowHandles2 = browser.getWindowHandles();
                if (windowHandles.size() < windowHandles2.size()){
                    for (String handle : windowHandles2) {
                        if (!windowHandles.contains(handle)){
                            browser.switchTo().window(handle);
                            break;
                        }
                    }

                    System.out.println("当前小狐狸页面的URL = " + browser.getCurrentUrl());
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
                            browser.findElement(By.xpath("//button[@data-testid='confirm-footer-button']")).click();
                            Util.RandomSleep(3,5);
                        } catch (Exception e) {
                            //System.out.println("没有前进按钮了，循环结束，回到业务页面");
                            browser.switchTo().window(mainHandle);
                            break;
                        }
                    }
                    //这里休息5-10秒等待小狐狸这边处理完成
                    Util.RandomSleep(10,15);
                }else{
                    //System.out.println("=========小狐狸没能弹出=============");
                }

                Log.logs.add(serialNumber);

                //页面截图保存
                File src = browser.getScreenshotAs(OutputType.FILE);
                Util.takeScreenShot(src,"Humanity claim daily reward " + serialNumber + ".png");

                // 关闭
                browser.close();
                // 退出
                browser.quit();
            }
        }
    }
}
