package com.liang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.liang.service.UserService;

/**
 * Created by mc-050 on 2016/11/21.
 */
@Controller
public class TestAction {

    private UserService userService;

    @ResponseBody
    @RequestMapping("/test")
    public Object test() {
        return userService.getUserName();
    }

    @ResponseBody
    @RequestMapping("/person")
    public Object getPerson() {
        return userService.getPerson();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
