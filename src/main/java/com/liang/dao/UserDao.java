package com.liang.dao;


import com.liang.entity.PersonEntity;

import java.util.List;

/**
 * Created by mc-050 on 2016/11/22.
 */
public interface UserDao {
    String getUserName();
    PersonEntity getPerson();
    List<PersonEntity> getPersonList();
}
