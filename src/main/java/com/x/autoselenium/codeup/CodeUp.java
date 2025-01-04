package com.x.autoselenium.codeup;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class CodeUp {
    public static void doCodeUp(JSONObject jsonObject) throws InterruptedException {

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

        //登录小狐狸并切换到arb网络
        Util.loginMetaMask(browser, Constant.NETWORK_ARBITRUMONE);
        //这里等待个几秒钟
        Util.RandomSleep(3,5);

        //获取小狐狸页面handle
        String metamaskTabHandle = browser.getWindowHandle();

        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);
        // 访问
        browser.get("https://codeup.app/");
        Util.RandomSleep(15,20);
        //获取当前页面handle
        String mainHandle = browser.getWindowHandle();



        //获取当前收菜进度
        String progressRate = browser.findElement(By.xpath("//p[@class='_progressCount_xyb94_44']")).getText();
        System.out.println(serialNumber + " 当前进度 = " + progressRate);

        //收菜
        browser.findElement(By.xpath("//div[@class='_btn_xyb94_64']")).click();
        //这里等待个几秒钟
        Util.RandomSleep(3,5);

        browser.findElement(By.xpath("//div[@class='_wraActive_9h7jg_8 _wrap_9h7jg_1']")).click();
        //这里等待个几秒钟
        Util.RandomSleep(10,15);


        //切换到小狐狸页面
        browser.switchTo().window(metamaskTabHandle);
        Util.RandomSleep(2,5);
        //在小狐狸页面最多操作5次
        for (int i=0;i<5;i++){
            //刷新页面
            browser.navigate().refresh();
            //等待页面加载完成，最多30秒
            Util.RandomSleep(5,10);
            //判断当前页面是否有需要处理的操作
            String currentUrl = browser.getCurrentUrl();
            //url里面有这个字符串的时候就表示当前处于业务处理页面
            if (currentUrl.contains("confirm")){
                List<WebElement> list = browser.findElements(By.xpath("//button[text()='确认' or text()='下一步' or text()='批准' or text()='切换网络']"));
                if (list.isEmpty()){
                    System.out.println("====================小狐狸业务处理页面没有获取到前进按钮，请检查========================");
                    continue;
                }
                list.get(0).click();
                Util.RandomSleep(5,10);

                list = browser.findElements(By.xpath("//button[text()='确认' or text()='下一步' or text()='批准' or text()='切换网络']"));
                if (list.isEmpty()){
                    continue;
                }
                list.get(0).click();
                Util.RandomSleep(5,10);

                list = browser.findElements(By.xpath("//button[text()='确认' or text()='下一步' or text()='批准' or text()='切换网络']"));
                if (list.isEmpty()){
                    continue;
                }
                list.get(0).click();
                Util.RandomSleep(5,10);

                list = browser.findElements(By.xpath("//button[text()='确认' or text()='下一步' or text()='批准' or text()='切换网络']"));
                if (list.isEmpty()){
                    continue;
                }
                list.get(0).click();
                Util.RandomSleep(5,10);
            }else{
                break;
            }
        }

    }
}
