package com.luban;

import com.luban.service.OrderService;
import com.spring.LubanApplicationContext;

public class Test {

    public static void main(String[] args) {
        LubanApplicationContext applicationContext = new LubanApplicationContext(AppConfig.class);
        System.out.println(applicationContext.getBean("orderService"));
        System.out.println(applicationContext.getBean("orderService"));
        System.out.println(applicationContext.getBean("orderService"));
    }
}
