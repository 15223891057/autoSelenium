package com.x.autoselenium.ads;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.*;

public class ReGroup {
    public static void main(String[] args) throws InterruptedException {

        //获取所有分组信息
        String groups = HttpUtil.get("http://local.adspower.net:50325/api/v1/group/list?page_size=1000");
        System.out.println(groups);
        JSON groupsJson = JSONUtil.parse(groups);
        System.out.println(groupsJson.getByPath("data"));
        JSONArray groupsJsonArray = JSONUtil.parseArray(JSONUtil.parse(groupsJson.getByPath("data")).getByPath("list"));
//        List<JSONObject> tempList = JSONUtil.toList(groupsJsonArray, JSONObject.class);
//        Collections.reverse(tempList);
//        groupsJsonArray = JSONUtil.parseArray(tempList);
        for (int i = 0; i < groupsJsonArray.size(); i++) {
            JSONObject groupJson = groupsJsonArray.getJSONObject(i);
            System.out.println(groupJson.get("group_id"));
        }

        //获取所有浏览器信息
        String users = HttpUtil.get("local.adspower.net:50325/api/v1/user/list?page_size=1000");
        System.out.println(users);
        JSON usersJson = JSONUtil.parse(users);
        System.out.println(usersJson.getByPath("data"));
        JSONArray usersJsonArray = JSONUtil.parseArray(JSONUtil.parse(usersJson.getByPath("data")).getByPath("list"));
        System.out.println(usersJsonArray.size());
        JSONArray userIds = new JSONArray();
        for (int i = 0; i < usersJsonArray.size(); i++) {
            JSONObject groupJson = usersJsonArray.getJSONObject(i);
            System.out.println(groupJson.get("user_id"));
            userIds.add(groupJson.get("user_id"));
        }
        System.out.println(userIds);
        System.out.println(userIds.size());
        List<String> list = JSONUtil.toList(userIds, String.class);
        Collections.addAll(list);
        //打乱顺序
        Collections.shuffle(list);
        System.out.println(list);

        List<List<String>> groups_u = new ArrayList<>();
        int groupSize = 10;
        int totalSize = list.size();

        for (int i = 0; i < totalSize; i += groupSize) {
            List<String> group = new ArrayList<>(list.subList(i, Math.min(totalSize, i + groupSize)));
            groups_u.add(group);
        }

        System.out.println("一共" +  groups_u.size() + " 个分组，详情如下：");
        for (List<String> l:groups_u) {
            System.out.println(l);
        }

        // 打印结果
        for (int i = 0; i < groups_u.size(); i++) {
            //System.out.println("Group " + (i + 1) + ": " + groups_u.get(i));
            Map<String,Object> pm = new HashMap<>();
            pm.put("group_id",groupsJsonArray.getJSONObject(i).get("group_id").toString());
            pm.put("user_ids",groups_u.get(i));
//            System.out.println(JSONUtil.parse(pm).toString());
            String res = HttpUtil.post("local.adspower.net:50325/api/v1/user/regroup",JSONUtil.parse(pm).toString());
            System.out.println("修改结果为：" + res);
            Thread.sleep(1000);
        }


        System.out.println("随机分组完成");

    }
}
