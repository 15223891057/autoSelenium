package com.x.autoselenium.utils;

import cn.hutool.json.JSONObject;

public interface ToRun {
    void execute(JSONObject jsonObject) throws InterruptedException;
}
