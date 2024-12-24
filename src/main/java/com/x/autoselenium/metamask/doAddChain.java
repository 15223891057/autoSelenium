package com.x.autoselenium.metamask;

import cn.hutool.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class doAddChain {
    public static void main(String[] args) throws InterruptedException {
        AddChain.addChain(new JSONObject().set("serial_number","3"));
    }
}
