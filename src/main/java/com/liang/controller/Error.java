package com.liang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mc-050 on 2017/1/18 15:57.
 * KIVEN will tell you life,send email to xxx@163.com
 */
@Controller
public class Error {

    @ResponseBody
    public Object getStatus(){
        throw new RuntimeException("test error");
    }
}
