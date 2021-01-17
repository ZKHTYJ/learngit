package com.example.service;

import com.example.framework.*;

@Component("userService")
//  原型bean
//@Scope("prototype")
//@Lazy 懒加载的单例bean
public class UserService implements BeanNameAware, InitializingBean {
    private String name;
    @Autowired
    private OrderService orderService;
    public void setName(String name) {

        this.name = name;
    }

    //实现BeanNameAware接口  将beanName复制给name
    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    // bean创建好后 初始化 验证过程  看看spring创建的bean是否符合我的需求
    @Override
    public void afterPropertiesSet() {
        if(orderService == null){
            throw new NullPointerException();
        }
    }

    public void test() {

        System.out.println("实验对象："+orderService);
        System.out.println("打印出这个beanName："+name);
    }



}
