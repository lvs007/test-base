package com.liang.controller;

import com.liang.service.UserService;
import liang.mvc.commons.LogUtils;
import liang.mvc.commons.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by mc-050 on 2016/11/21.
 */
@Controller
public class TestAction {

    private static final LogUtils.Log LOG = LogUtils.getInstance(TestAction.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SpringContextHolder contextHolder;

    @ResponseBody
    public Object test() {
        return userService.getUserName();
    }

    @ResponseBody
    public Object getPerson() {
        String param = contextHolder.getRequest().getParameter("test");
        Enumeration<String> attributes = contextHolder.getContext().getAttributeNames();
        while (attributes.hasMoreElements()) {
            System.out.println("element:" + attributes.nextElement());
        }
        System.out.println("获取的参数是：" + param);
        return userService.getPerson();
    }

    @ResponseBody
    public Object getPersonList() {
        LOG.debugLog("[TestAction]请求getPersonList");
        return userService.getPersonList();
    }

    public Object getValue() {
        return "value hhhh";
    }

}
