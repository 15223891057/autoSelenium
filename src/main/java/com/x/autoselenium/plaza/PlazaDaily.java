package com.x.autoselenium.plaza;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.log.Log;
import com.x.autoselenium.utils.Constant;
import com.x.autoselenium.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PlazaDaily {

    public static void daily(JSONObject jsonObject) throws InterruptedException {

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

        //登录小狐狸并切换到base sepolia网络
        Util.loginMetaMask(browser, Constant.NETWORK_BASESEPOLIA);
        //这里等待个几秒钟
        Util.RandomSleep(3,5);

        //获取小狐狸页面handle
        String metamaskTabHandle = browser.getWindowHandle();

        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);

        List<String> urls = new ArrayList<>();
        urls.add("https://testnet.plaza.finance/market/0x47129e886b44b5b8815e6471fcd7b31515d83242/checkout/buy/bond");
        urls.add("https://testnet.plaza.finance/market/0x47129e886b44b5b8815e6471fcd7b31515d83242/checkout/sell/bond");
        urls.add("https://testnet.plaza.finance/market/0x47129e886b44b5b8815e6471fcd7b31515d83242/checkout/buy/leverage");
        urls.add("https://testnet.plaza.finance/market/0x47129e886b44b5b8815e6471fcd7b31515d83242/checkout/sell/leverage");
        //打乱顺序
        Collections.shuffle(urls);

        for (String url : urls) {
            // 访问
            browser.get(url);
            doBusiness(browser,metamaskTabHandle,serialNumber);
        }

        Log.success.add(serialNumber);

        // 关闭
        browser.close();
        // 退出
        browser.quit();

    }



    //有4个交互要做，但动作基本全都一样，复用代码
    public static void doBusiness(ChromeDriver browser,String metamaskHandle,String serialNumber) throws InterruptedException {
        //等待页面加载完成，最多30秒
        new WebDriverWait(browser, Duration.ofSeconds(30)).until(driver1 -> {
            JavascriptExecutor js = (JavascriptExecutor) driver1;
            // 检查 document.readyState 是否为 "complete"
            return js.executeScript("return document.readyState").toString().equals("complete");
        });

        Util.RandomSleep(5,10);

        //获取当前页面handle
        String currentHandle = browser.getWindowHandle();


        //获取余额
        List<WebElement> balances = browser.findElements(By.xpath("//span[@class='text-muted-foreground text-sm']"));
        if (balances.isEmpty()){
            System.out.println( "找不到余额，可能是因为页面没加载出来");
            return;
        }
        if (balances.get(0).getText().contains("Balance: 0")){
            System.out.println(serialNumber + " 余额为零，请领水-----------------------------------------------------------------");
            Log.insufficientBalance.add(serialNumber);
            return;
        }


        //输入数量
        List<WebElement> inputs = browser.findElements(By.xpath("//input[@class='text-2xl font-bold bg-transparent outline-none w-2/3']"));
        if (inputs.isEmpty()){
            System.out.println("找不到输入框，可能是因为页面没加载出来");
            return;
        }

        //获取币种
        List<WebElement> tokenType = browser.findElements(By.xpath("//span[@class='font-semibold text-sm sm:text-base']"));
        String token = tokenType.get(0).getText();

        String amount = "0.001";

        if (token.equals("wstETH")){
            //定义范围
            double min = 0.00101;
            double max = 0.00202;
            // 生成一个范围在 [min, max] 的随机数
            double randomValue = min + (max - min) * Math.random();
            // 使用 BigDecimal 保留 5 位小数
            BigDecimal randomValueRounded = new BigDecimal(randomValue).setScale(5, RoundingMode.HALF_UP);
            amount = randomValueRounded.toString();
        }
        if (token.equals("USDC")){
            // 定义范围
            int min = 10;
            int max = 20;

            // 使用 ThreadLocalRandom 生成范围在 [min, max] 之间的随机整数
            int randomValue = ThreadLocalRandom.current().nextInt(min, max + 1);
            amount = randomValue+"";
        }
        if (token.equals("bondETH")){
            // 定义范围
            double min = 0.11;
            double max = 0.33;

            // 生成一个范围在 [min, max] 的随机数
            double randomValue = min + (max - min) * Math.random();

            // 使用 BigDecimal 保留 1 位小数
            BigDecimal randomValueRounded = new BigDecimal(randomValue).setScale(2, RoundingMode.HALF_UP);
            amount = randomValueRounded.toString();
        }
        if (token.equals("levETH")){
            //定义范围
            double min = 0.00101;
            double max = 0.00202;
            // 生成一个范围在 [min, max] 的随机数
            double randomValue = min + (max - min) * Math.random();
            // 使用 BigDecimal 保留 5 位小数
            BigDecimal randomValueRounded = new BigDecimal(randomValue).setScale(5, RoundingMode.HALF_UP);
            amount = randomValueRounded.toString();
        }

        //输入
        inputs.get(0).sendKeys(amount);
        Util.RandomSleep(2,4);


        //点击按钮
        browser.findElement(By.xpath("//button[@data-sentry-component='CheckoutConfirmButton']")).click();

        //这里等待小狐狸弹出
        Util.RandomSleep(3,5);

        //切换到小狐狸页面
        browser.switchTo().window(metamaskHandle);
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
        //回到业务页面
        browser.switchTo().window(currentHandle);
    }

}
