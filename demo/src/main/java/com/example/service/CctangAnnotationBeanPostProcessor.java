package com.example.service;

import com.example.framework.AnnotationConfigApplicationContext;
import com.example.framework.Autowired;
import com.example.framework.BeanPostProcessor;
import com.example.framework.Component;

import java.lang.reflect.Field;

@Component
public class CctangAnnotationBeanPostProcessor {
//    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//    @Override
//    public void autowired(Class beanClass, Object instance) {
//        //PublicFunction.checkAnnotationType(beanClass, instance);
//        for (Field field : beanClass.getDeclaredFields()) {
//            if (field.isAnnotationPresent(Autowired.class)) {
//                // 满足条件 添加属性
//                // 给这个instance赋值  给他一个orderService值
//                // byType byName (@resource 就是byName)
//                //这里只写了byName
//                Object bean = applicationContext.getBean(field.getName());
//
//                field.setAccessible(true);
//                try {
//                    field.set(instance, bean);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("处理程序员自定义的注解");
//    }
}
