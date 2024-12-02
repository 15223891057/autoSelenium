package com.x.autoselenium.hemi;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Swap {
    public static void hemiSwap(JSONObject jsonObject) throws InterruptedException {
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
        browser.get("https://swap.hemi.xyz/");
        Util.RandomSleep(3,5);
        //获取当前页面handle
        String hemiHandle = browser.getWindowHandle();
        //获取当前所有handles
        Set<String> windowHandles = browser.getWindowHandles();

        //检测当前页面是否连接了metamask
        //这里获取不到登录按钮所在的元素，说明小狐狸已经自动登录了
        if (!browser.findElements(By.xpath("//div[@class='Web3Status__Web3StatusConnectWrapper-sc-25fdb140-1 fxTYOx']")).isEmpty()){
            //进行登录小狐狸操作
            //点击登录按钮
            browser.findElement(By.xpath("//button[@class='Web3Status__StyledConnectButton-sc-25fdb140-6 bInzNY']")).click();
            Util.RandomSleep(3,5);
            //选择小狐狸
            List<WebElement> wallets = browser.findElements(By.xpath("//button[@class='Option__OptionCardClickable-sc-c27486cb-1 icYbrv']"));
            if (wallets.isEmpty()){
                System.out.println(jsonObject.getStr("serial_number") + "点击Connect之后没有获取到钱包列表");
                return;
            }else{
                for (WebElement wallet : wallets){
                    if (wallet.getText().contains("MetaMask")){
                        //点击选择小狐狸
                        wallet.click();
                        Util.RandomSleep(3,5);
                        break;
                    }
                }
            }

            //这里再次检测当前页面是否连接了metamask
            //如果没有登录小狐狸
            if (!browser.findElements(By.xpath("//div[@class='Web3Status__Web3StatusConnectWrapper-sc-25fdb140-1 fxTYOx']")).isEmpty()){
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
        //如果没有登录小狐狸
        if (!browser.findElements(By.xpath("//div[@class='Web3Status__Web3StatusConnectWrapper-sc-25fdb140-1 fxTYOx']")).isEmpty()){
            //System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX小狐狸登录失败，原因不明，请检查代码XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            //结束执行
            return;
        }

        //输入you pay
        //获取一个1-9的随机数

        browser.findElement(By.xpath("//input[@id='swap-currency-input']")).sendKeys("0.0000"+(new Random().nextInt(9)+1));
        Util.RandomSleep(3,5);

        //点击select token
        browser.findElement(By.xpath("//button[@class='sc-aXZVg bbWEFp Button__BaseButton-sc-4a2dca96-1 Button__ButtonGray-sc-4a2dca96-5 SwapCurrencyInputPanel__CurrencySelect-sc-16a29330-3 kVRhnV hLDSvd hVJiaM open-currency-select-button']")).click();
        Util.RandomSleep(3,5);

        //获取token列表
        List<WebElement> tokens = browser.findElements(By.xpath("//div[@class='sc-aXZVg Row-sc-34df4f97-0 CurrencyList__RowWrapper-sc-1dd0cc7b-6 jqxqw cPCYrp czRsqa']"));
        if (tokens.isEmpty()){
            System.out.println(jsonObject.getStr("serial_number") + "点击Select Token之后没有获取到token列表");
            return;
        }
        //随机选择一个token
//        int tokenIndex = new Random().nextInt(3);
        int tokenIndex = 1;
        tokens.get(tokenIndex).click();
        Util.RandomSleep(10,15);

        //检测是否出现了Warning
        List<WebElement> warnings = browser.findElements(By.xpath("//button[@class='sc-aXZVg bbWEFp Button__BaseButton-sc-4a2dca96-1 Button__ButtonPrimary-sc-4a2dca96-2 TokenSafety__StyledButton-sc-6bd8e5ca-5 kVRhnV Dsaqa Tfrxx']"));
        if (!warnings.isEmpty()){
            //点击I understand
            warnings.get(0).click();
            Util.RandomSleep(3,5);
        }

        //这里休眠10-20秒
        Util.RandomSleep(10,20);

        //如果是选择的WETH
        if (tokenIndex == 1){
            browser.findElement(By.xpath("//button[@class='sc-aXZVg hCFFB Button__BaseButton-sc-4a2dca96-1 Button__ButtonPrimary-sc-4a2dca96-2 kVRhnV Dsaqa']")).click();
        }else{
            //点击swap
            browser.findElement(By.id("swap-button")).click();
            Util.RandomSleep(3,5);

            //检测是否出现了price update警告
            List<WebElement> priceUpdates = browser.findElements(By.xpath("//button[@class='sc-aXZVg bbWEFp Button__BaseButton-sc-4a2dca96-1 Button__ButtonPrimary-sc-4a2dca96-2 Button__SmallButtonPrimary-sc-4a2dca96-3 kVRhnV Dsaqa cGfmIY']"));
            if (!priceUpdates.isEmpty()){
                //点击accept
                priceUpdates.get(0).click();
                //这里等待1秒钟
                Util.RandomSleep(1,1);
            }

            //点击confirm swap
            browser.findElement(By.id("confirm-swap-or-send")).click();
            Util.RandomSleep(3,5);

            //检测是否出现了try again警告
            List<WebElement> tryAgains = browser.findElements(By.xpath("//button[@class='sc-aXZVg bbWEFp Button__BaseButton-sc-4a2dca96-1 Button__ButtonPrimary-sc-4a2dca96-2 kVRhnV Dsaqa']"));
            //如果出现了try again警告
            if (tryAgains.size()==2){
                tryAgains.get(1).click();
                Util.RandomSleep(3,5);
                //点击confirm swap
                browser.findElement(By.id("confirm-swap-or-send")).click();
            }
        }


        //这里因为网络问题，需要多等一会，暂定等待30-60秒
        Util.RandomSleep(10,20);


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


        //页面截图保存
        File src = browser.getScreenshotAs(OutputType.FILE);
        Util.takeScreenShot(src,"hemi swap " + jsonObject.getStr("serial_number") + ".png");

        Log.logs.add(jsonObject.getStr("serial_number"));

        // 关闭
        browser.close();
        // 退出
        browser.quit();

    }
}
