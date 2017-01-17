package com.liang.service;

import com.liang.dao.UserDao;
import com.liang.entity.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mc-050 on 2016/11/22.
 */
@Service
public class UserService {

    private UserDao userDao;

    public String getUserName() {
        return userDao.getUserName();
    }

    public PersonEntity getPerson() {
        return userDao.getPerson();
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
