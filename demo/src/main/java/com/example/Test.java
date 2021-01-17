package com.example;

import com.example.framework.AnnotationConfigApplicationContext;
import com.example.service.UserService;


public class Test {
    public static void main(String[] args) {
        // 启动 扫描 创建bean （非懒加载的单例bean）
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) applicationContext.getBean("userService");

//        Object userService = applicationContext.getBean("userService");
//        Object userService1 = applicationContext.getBean("userService");
//        Object userService2 = applicationContext.getBean("userService");
//
//        System.out.println(userService);
//        System.out.println(userService1);
//        System.out.println(userService2);

        /**
         * 上面打印结果为：
         * com.example.service.UserService@5caf905d
         * com.example.service.UserService@5caf905d
         * com.example.service.UserService@5caf905d
         * */

        /**
         * 当在userService上加Scope注解
         * 打印结果为：
         *com.example.service.UserService@27716f4
         * com.example.service.UserService@8efb846
         * com.example.service.UserService@2a84aee7
         *
         * */
        userService.test();
    }
}
