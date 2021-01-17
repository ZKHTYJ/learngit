package com.example.service;

import com.example.framework.Component;
import com.example.framework.Lazy;
import com.example.framework.Scope;

@Component("orderService")
  // 原型bean
@Scope("singleton")
 //懒加载的单例bean
//@Lazy
public class OrderService {

}
