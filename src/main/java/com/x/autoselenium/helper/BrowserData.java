package com.x.autoselenium.helper;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BrowserData {
    public static void main(String[] args) {
        List<JSONObject> list = Util.getAll(false);
        System.out.println(list);

        int[] sns = {47,69,34,42,104,48,59,64,44,101,110,74,52,95,56,60};
        // 将 int[] 转换为 List<Integer>
        List<Integer> listS = Arrays.stream(sns)
                .boxed()  // 将每个 int 转换为 Integer
                .collect(Collectors.toList());

        List<JSONObject> listT = new ArrayList<>();

        for (JSONObject json : list) {
            int serianNumber = json.getInt("serian_number");
            if (listS.contains(serianNumber)){
                listT.add(json);
            }
        }

        List<String> userIds = new ArrayList<>();

        for (JSONObject obj : list) {
            userIds.add(obj.getStr("user_id"));
            System.out.println(obj.toString());
        }

        List<JSONObject> actives = new ArrayList<>();

        List<String> emails = new ArrayList<>();
        List<String> twNames = new ArrayList<>();
        List<String> twAccounts = new ArrayList<>();
        List<String> metamaskAdds = new ArrayList<>();
        List<String> serialNumbers = new ArrayList<>();

        List<JSONObject> list2 = Util.getAll(false);
        for (JSONObject obj : list2) {
            String userId = obj.getStr("user_id");
            if (userIds.contains(userId)) {
                actives.add(obj);

                JSONObject remark = obj.getJSONObject("remark");

                String email = remark.getStr("email");
                String twName = remark.getStr("twName");
                String twAccount = remark.getStr("twAccount");
                String metamaskAdd = remark.getStr("metamaskAdd");
                String serialnumber = obj.getStr("serial_number");

                emails.add(email);
                twNames.add(twName);
                twAccounts.add(twAccount);
                metamaskAdds.add(metamaskAdd);
                serialNumbers.add(serialnumber);

            }
        }


        System.out.println("\n\n=================emails=================");
        for (String str:emails){
            System.out.println(str);
        }

        System.out.println("\n\n=================twNames=================");
        for (String str:twNames){
            System.out.println(str);
        }

        System.out.println("\n\n=================twAccounts=================");
        for (String str:twAccounts){
            System.out.println(str);
        }

        System.out.println("\n\n=================metamaskAdds=================");
        for (String str:metamaskAdds){
            System.out.println(str);
        }

        System.out.println("\n\n=================serialNumbers=================");
        for (String str:serialNumbers){
            System.out.println(str);
        }





    }
}
