package com.x.autoselenium.controller;

import cn.hutool.json.JSONUtil;
import com.x.autoselenium.log.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    @RequestMapping("/gbl")
    private String getBalanceLog(){
        return "";
    }

}
