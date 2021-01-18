package com.example.service;

import com.example.framework.*;

import java.lang.reflect.Field;

@Component
public class CommonAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public void autowired(Class beanClass, Object instance, AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        for (Field field : beanClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Scope.class)) {
                // 满足条件 添加属性
                // 给这个instance赋值  给他一个orderService值
                // byType byName (@resource 就是byName)
                //这里只写了byName
                Object bean = annotationConfigApplicationContext.getBean(field.getName());

                field.setAccessible(true);
                try {
                    field.set(instance, bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("处理Resource注解");
    }
}
