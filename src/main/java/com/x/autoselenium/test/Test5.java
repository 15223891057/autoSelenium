package com.x.autoselenium.test;

import cn.hutool.json.JSONObject;
import com.x.autoselenium.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class Test5 {
    public static void main(String[] args) {
        List<JSONObject> list = Util.getAllActive();

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
