package com.controller;

import com.hx.mapper.UserMapper;
import com.member.MemberMapper;
import com.order.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController

public class MyBatisController {
    // 上一期代码
    //   @Autowired
    //    private UserMapper userMapper;
    //    @RequestMapping("/addUser")
    //    public String addUser(String name, int age){
    //        return userMapper.insert(name,age)>0 ? "success":"fail";
    //    }
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;
    @RequestMapping("/addUserMember")
    public String addUserMember(String name, Integer age){
                return memberMapper.insert(name,age)>0 ? "success":"fail";
            }
    @RequestMapping("/addOrder")
    public String addOrder(String name, Integer age){
         return orderMapper.insertOrder(name,age)>0 ? "success":"fail";
    }
}
