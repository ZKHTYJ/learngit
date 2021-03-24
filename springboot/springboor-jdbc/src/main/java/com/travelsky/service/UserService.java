package com.travelsky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author cctang
 * @version 1.0
 * @date 2019/12/12 21:26
 */
@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addUser(String userName, Integer age) {
        //int update = jdbcTemplate.update("insert into users values(null,?,?)",userName,age);
        int update = jdbcTemplate.update("insert into users values(null,?,?);", userName, age);
        return update > 0 ? true : false;
    }


}
