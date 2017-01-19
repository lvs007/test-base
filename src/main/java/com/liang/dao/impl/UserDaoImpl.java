package com.liang.dao.impl;

import com.liang.dao.UserDao;
import com.liang.entity.PersonEntity;
import liang.dao.common.EntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mc-050 on 2016/11/22.
 */
@Repository
public class UserDaoImpl extends EntityDao<PersonEntity> implements UserDao {

    public UserDaoImpl() {
        super(PersonEntity.class);
    }

    public String getUserName() {
        String sqlStr = "select * from t_person limit 0,10";
        List<PersonEntity> personEntityList = sqlQuery(sqlStr);
        return personEntityList.get(0).getName();
    }

    public PersonEntity getPerson() {
        return get(2L);
    }

    @Override
    public List<PersonEntity> getPersonList() {
        String sqlStr = "select * from t_person limit 0,10";
        List<PersonEntity> personEntityList = sqlQuery(sqlStr);
        return personEntityList;
    }
}
