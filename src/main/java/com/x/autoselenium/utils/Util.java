package com.x.autoselenium.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Util {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String findNumbers(String str){
        // 正则表达式匹配数字
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static List<String> checkFolder(){
        // 指定文件夹路径
        File folder = new File(Constant.SCREENSHOT_FOLDER);
        List<String> result = new ArrayList<>();

        // 判断文件夹是否存在并且是目录
        if (folder.exists() && folder.isDirectory()) {
            // 获取文件夹中的所有文件和子文件夹
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    // 打印文件或文件夹的名称
//                    System.out.println(findNumbers(file.getName()));
                    result.add(findNumbers(file.getName()));
                }
                return result;
            } else {
                System.out.println("文件夹为空或无法访问！");
            }
        } else {
            System.out.println("文件夹不存在或不是目录！");
        }
        return result;
    }

    /**
     * 更新代理信息
     * @param jsonObject
     */
    public static void updateProxy(JSONObject jsonObject){

        String user_id = jsonObject.getStr("user_id");
        String ip_country = jsonObject.getStr("ip_country");
//        String proxy_user = "23095475-zone-custom-region-"+ip_country+"-sessid-"+Util.generateRandomString(8)+"-sessTime-60";
        String proxy_user = "23095475-zone-custom-region-"+ip_country;
//        System.out.println(proxy_user);

        JSONObject user_proxy_config = new JSONObject();
        user_proxy_config.set("proxy_soft","other");
        user_proxy_config.set("proxy_type","socks5");
        user_proxy_config.set("proxy_host","f.proxys5.net");
        user_proxy_config.set("proxy_port","6200");
        user_proxy_config.set("proxy_user",proxy_user);
        user_proxy_config.set("proxy_password","8gGR4EgK");

        Map<String,Object> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("user_proxy_config",user_proxy_config);

        System.out.println(params);

        JSONObject resJson = JSONUtil.parseObj(HttpUtil.post("http://local.adspower.net:50325/api/v1/user/update",JSONUtil.parse(params).toString()));

        if (!resJson.getStr("code").equals("0")){
            System.out.println(jsonObject.getStr("serial_number")+" 更新代理信息失败");
            System.out.println("更新代理信息返回值 = " + resJson);
        }

        System.out.println(jsonObject.getStr("serial_number")+" 更新代理信息成功");
    }

    /**
     * 获取字符串中第n到m之间的“ - ”的字符
     * @param input
     * @param n
     * @param m
     * @return
     */
    public static String getSubstringBetweenHyphens(String input, int n, int m) {
        int startIndex = -1;
        int endIndex = -1;

        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '-') {
                count++;
                if (count == n) {
                    startIndex = i + 1; // 获取第 n 个 '-' 后的位置
                } else if (count == m) {
                    endIndex = i; // 获取第 m 个 '-' 的位置
                    break;
                }
            }
        }

        // 检查是否找到了有效的 n 和 m
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return input.substring(startIndex, endIndex);
        } else {
            return "未找到指定的 - 或者输入参数不正确";
        }
    }

    /**
     * 随机生成指定长度的字符串，包含字母大小写，数字
     * @param length
     * @return
     */
    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(index));
        }

        return stringBuilder.toString();
    }

    /**
     * 启动浏览器
     * @param jsonObject
     * @return
     */
    public static ChromeDriver startBrowser(JSONObject jsonObject) throws InterruptedException {
        //打开浏览器
        JSONObject resJson = JSONUtil.parseObj(HttpUtil.get("http://local.adspower.net:50325/api/v1/browser/start?serial_number="+jsonObject.getStr("serial_number")));
        System.out.println(jsonObject.getStr("serial_number") + " 启动浏览器请求的返回值为 " + resJson);
        //如果启动浏览器的请求失败了
        if (!resJson.getStr("code").equals("0")){
            //判断是否因为server问题而导致的启动失败
            if (resJson.getStr("msg").contains("The server is not working well, please check the network or try again later")){
                Util.RandomSleep(1,2);
                //重新尝试启动浏览器
                startBrowser(jsonObject);
            }else if (resJson.getStr("msg").contains("Check Proxy Fail")){
                Util.RandomSleep(1,2);
                //更新代理信息
                updateProxy(jsonObject);
                Util.RandomSleep(3,5);
//                System.out.println("===========================浏览器 " + jsonObject.getStr("serial_number") + " 启动失败 ： " + resJson.getByPath("msg") + "已自动修改代理信息，尝试再次启动===============================");
                //重新尝试启动浏览器
                startBrowser(jsonObject);
            }else{
                // ANSI转义序列开启红色文本
                System.out.print("\033[31m");
                System.out.println("===========================浏览器 " + jsonObject.getStr("serial_number") + " 启动失败 ： " + resJson.getByPath("msg") + " ===============================");
                System.out.print("\033[0m");
                return null;
            }
        }

//        System.out.println("===========================浏览器 " + jsonObject.getStr("serial_number") + " 启动成功");

//        System.setProperty("webdriver.chrome.driver", (String) resJson.getByPath("data.webdriver"));
        System.setProperty("webdriver.chrome.driver", "chromedriver129.exe");
        // 使用ChromeOptions设置调试地址
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", resJson.getByPath("data.ws.selenium"));

        ChromeDriver browser = null;


        try{
            // 创建远程ChromeDriver实例
            browser = new ChromeDriver(options);
            // 最大化浏览器窗口
            browser.manage().window().maximize();
        }catch (SessionNotCreatedException e){
            System.out.println("创建远程ChromeDriver实例时发生SessionNotCreatedException，正在进行重试");
            Util.RandomSleep(2,4);
            browser = new ChromeDriver(options);
            // 最大化浏览器窗口
            browser.manage().window().maximize();
            e.printStackTrace();
        }

        return browser;

    }

    public static boolean stopBrowser(JSONObject jsonObject) {
        //关闭浏览器
        String res = HttpUtil.get("http://local.adspower.net:50325/api/v1/browser/stop?serial_number="+jsonObject.getStr("serial_number"));
        System.out.println(jsonObject.getStr("serial_number")+ " 号浏览器的关闭浏览器请求返回值 = " + res);
        if (res==null || res.equals("")){
            System.out.println(jsonObject.getStr("serial_number")+"关闭浏览器请求失败，请检查网络");
            return false;
        }
        JSONObject resJson = JSONUtil.parseObj(res);
        //关闭成功
        if (resJson.getStr("code") .equals("0")){
//            System.out.println(jsonObject.getStr("serial_number")+" 浏览器关闭成功");
            return true;
        }
        if (resJson.getStr("code") .equals("-1")){
            //分析失败原因
            if (resJson.getStr("msg").contains("Too many")){
                System.out.println(jsonObject.getStr("serial_number")+" 因请求频率过高导致浏览器关闭失败，重新请求关闭浏览器");
                //请求频率过高导致的关闭失败，重新请求即可
                Util.RandomSleep(1,2);
                stopBrowser(jsonObject);
            }
            if (resJson.getStr("msg").contains("not working well")){
                System.out.println(jsonObject.getStr("serial_number")+" 因网络问题关闭失败，重新请求关闭浏览器");
                //请求频率过高导致的关闭失败，重新请求即可
                Util.RandomSleep(1,2);
                stopBrowser(jsonObject);
            }

        }
        return true;
    }

    /**
     * 随机睡眠一段时间
     */
    public static void RandomSleep(int min,int max) {
        // 创建Random实例
        Random random = new Random();
        // 获取两数之间的随机整数
        int randomInteger = random.nextInt(max - min + 1) + min;
        // 休眠指定的随机秒数
        try{
            Thread.sleep(randomInteger * 1000);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("Sleep异常-----------------------------------------------------------------");
        }

    }

    /**
     * 切换网络
     * @param browser
     * @param network
     * @throws InterruptedException
     */
    public static void switchNetwork(ChromeDriver browser,String network) throws InterruptedException {
        // 打开选择网络窗口
        browser.findElement(By.cssSelector(".button.btn--rounded.btn-default")).click();
        Util.RandomSleep(3,5);
        // 选择网络
        browser.findElement(By.xpath("//div[@class='mm-box multichain-network-list-item__network-name mm-box--display-flex mm-box--align-items-center'][data-testid='" + network + "']"));
        Util.RandomSleep(3,5);
    }

    /**
     * 登录小狐狸并且切换到指定网络
     * @param browser
     * @throws InterruptedException
     */
    public static void loginMetaMask(ChromeDriver browser, String network) throws InterruptedException {
        //创建新tab
        browser.switchTo().newWindow(WindowType.TAB);
        // 2、访问小狐狸网址
        //普通小狐狸
        //browser.get("chrome-extension://nkbihfbeogaeaoehlefnkodbefgpgknn/home.html");
        //禁用了LavaMoat的小狐狸地址
        browser.get("chrome-extension://apfklcgfjacbjddimlfdkcipgmkclfco/home.html#");
        // 休息3秒
        Util.RandomSleep(3,5);
        //检测是否有输入密码框，这里如果不需要再输入密码进行登录了，会发生异常
        try{
            // 3、输入密码
            browser.findElement(By.cssSelector(".MuiInputBase-input.MuiInput-input")).sendKeys("wxe..5201314");
            // 4、点击登录按钮
            browser.findElement(By.cssSelector(".button.btn--rounded.btn-default")).click();
        } catch (Exception e) {
            System.out.println("当前MetaMask已登录，无需再次登录");
            //throw new RuntimeException(e);
        }

        // 休息3秒
        Util.RandomSleep(3,5);

        //判断是否有弹窗
        List<WebElement> elements = browser.findElements(By.xpath("//section[@class='popover-wrap']"));
        //有弹窗
        if (elements.size() > 0) {
            try {
                browser.findElement(By.xpath("//section[@class='popover-wrap']")).findElement(By.tagName("button")).click();
                Util.RandomSleep(1,2);
                System.out.println("小狐狸页面有弹窗并且已经被点击消除");
            }catch (Exception e){
                System.out.println("小狐狸页面没有弹窗");
            }
        }

        String js = "const customNetwork = {\n" +
                "                    chainId: '0xB56C7', // 网络的链ID（16进制字符串）\n" +
                "                    chainName: 'Hemi Sepolia', // 自定义网络的名称\n" +
                "                    nativeCurrency: {\n" +
                "                        name: 'ETH', // 自定义代币的名称\n" +
                "                        symbol: 'ETH', // 自定义代币的符号\n" +
                "                        decimals: 18, // 代币的小数位数\n" +
                "                    },\n" +
                "                    rpcUrls: ['https://testnet.rpc.hemi.network/rpc'], // RPC URL数组\n" +
                "                    blockExplorerUrls: ['testnet.explorer.hemi.xyz'], // 区块浏览器URL数组\n" +
                "                };\n" +
                "\n" +
                "                try {\n" +
                "                    // 尝试向 MetaMask 添加自定义网络\n" +
                "                    await ethereum.request({\n" +
                "                        method: 'wallet_addEthereumChain',\n" +
                "                        params: [customNetwork],\n" +
                "                    });\n" +
                "                    console.log('Hemi Sepolia 网络已成功添加！');\n" +
                "                } catch (error) {\n" +
                "                    console.error('添加网络失败', error);\n" +
                "                }";
        //添加网络
        browser.executeScript(js);

        //获取当前网络
        String currentNetwork = browser.findElement(By.xpath("//span[@class='mm-box mm-text mm-text--body-sm mm-text--ellipsis mm-box--color-text-default']")).getText();

        //判断是否需要切换网络
        if (network==null||network.equals("")||currentNetwork.equals(network)){
//            System.out.println("当前网络 = " + currentNetwork +"，目标网络 = " + network + "，无需切换网络");
            //什么也不用干
        }else{
            // 打开选择网络窗口
            browser.findElement(By.xpath("//button[@class='mm-box mm-picker-network multichain-app-header__contents__network-picker mm-box--margin-2 mm-box--padding-right-4 mm-box--padding-left-2 mm-box--display-none mm-box--sm:display-flex mm-box--gap-2 mm-box--align-items-center mm-box--background-color-background-alternative mm-box--rounded-pill']")).click();
            Util.RandomSleep(3,5);
            // 选择网络
            browser.findElement(By.xpath("//div[@class='mm-box multichain-network-list-item__network-name mm-box--display-flex mm-box--align-items-center' and @data-testid='" + network + "']")).click();
            Util.RandomSleep(3,5);
        }
    }

    /**
     * 关闭所有标签页
     * @param driver
     */
    public static void CloseAllTabs(ChromeDriver driver) {

        // 获取所有打开的窗口的句柄
        Set<String> windowHandles = driver.getWindowHandles();
        String adsHandles = "";

        // 关闭所有标签页
        for (String handle : windowHandles) {
            driver.switchTo().window(handle);
            //不关闭ads信息页面
            if (driver.getCurrentUrl().contains("start.adspower.net")){
                adsHandles = handle;
                continue;
            }
            driver.close();
        }
        //切换到剩下的一个tab页面，不然容易出现NoSuchWindowException异常
        driver.switchTo().window(adsHandles);
    }

    /**
     * 获取所有浏览器信息并打乱顺序
     */
    public static List<JSONObject> getAll(boolean shuffle){
        //获取所有浏览器信息
        String users = HttpUtil.get("local.adspower.net:50325/api/v1/user/list?page_size=1000&user_sort={\"serial_number\":\"asc\"}");
        JSON usersJson = JSONUtil.parse(users);
        JSONArray usersJsonArray = JSONUtil.parseArray(JSONUtil.parse(usersJson.getByPath("data.list")));
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < usersJsonArray.size(); i++) {
            list.add(usersJsonArray.getJSONObject(i));
            //list.add(groupJson.get("serial_number"));
        }
        if (shuffle){
            //打乱顺序
            Collections.shuffle(list);
        }
        return list;
    }

    /**
     * 获取所有已打开的浏览器信息并按照serial_number进行排序
     */
    public static List<JSONObject> getAllActive(){
        //获取所有浏览器信息
        String users = HttpUtil.get("local.adspower.net:50325/api/v1/browser/local-active");
        JSON usersJson = JSONUtil.parse(users);
        JSONArray usersJsonArray = JSONUtil.parseArray(JSONUtil.parse(usersJson.getByPath("data.list")));
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < usersJsonArray.size(); i++) {
            list.add(usersJsonArray.getJSONObject(i));
        }
        return list;
    }

    /**
     * 保存截图
     * @param src
     */
    public static void takeScreenShot(File src,String fileName){
        //页面截图保存
        try {
            // 拷贝截图文件到我们项目./Screenshots
            FileUtils.copyFile(src, new File(Constant.SCREENSHOT_FOLDER, fileName));
            Util.RandomSleep(3,5);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
