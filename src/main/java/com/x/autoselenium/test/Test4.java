package com.x.autoselenium.test;

import java.util.ArrayList;
import java.util.List;

public class Test4 {
    public static List<String> log = new ArrayList<>();
    public static void main(String[] args) {

        log.add("哈哈哈");
        System.out.println(log.size());

        log.clear();
        System.out.println(log.size());

        log.add("heiheihei");
        log.add("heiheihei");
        System.out.println(log.size());
    }
}
