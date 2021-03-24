package com.travelsky.controller;

import com.travelsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cctang
 * @version 1.0
 * @date 2019/12/12 21:43
 */
@RestController
public class MyBasticController {
    @Autowired
    private UserService userService;

    @RequestMapping("/addUser")
    public String addUser(String userName, Integer age) {
        return userService.addUser(userName, age) ? "success" : "fail";
    }
}
