package com.x.autoselenium.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tools {
    public static String getTime() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 定义格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        // 格式化时间
        String formattedDateTime = now.format(formatter);

        return formattedDateTime;
    }
}
