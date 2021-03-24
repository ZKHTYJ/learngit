package com.example.framework;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationConfigApplicationContext {

    //spring 容器配置类
    private Class configClass;

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    // 单例池
    private Map<String, Object> singletonsMap = new HashMap<>();
    // 存放实现了BeanPostProcessor接口的类
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<BeanPostProcessor>();

    public AnnotationConfigApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 扫描  得到beanDefinition对象
        // 是否存在ComponentScan 注解
        scan(configClass);

        // 创建非懒加载的单例bean
        // 找到哪些bean为懒加载的bean
        creatNoisLazySingleton();
    }

    public AnnotationConfigApplicationContext() {

    }

    private void creatNoisLazySingleton() {
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            // 单例非懒加载的
            if(beanDefinition.getScope().equals("singleton") && !beanDefinition.isLazy()){
                Object bean = creatrBean(beanDefinition, beanName);
                // 存在单例池中
                singletonsMap.put(beanName, bean);

            }
        }
    }



    /**
     * 创建一个bean都是按照beanDefinition来的
     * beanDefinition
     * */
    private  Object creatrBean(BeanDefinition beanDefinition,String beanName) {
        // bean为一个对象 需要一个类
        //拿到beanClass
        Class beanClass = beanDefinition.getBeanClass();
        // 调用无参的构造方法
        try {
            Object instance = beanClass.getDeclaredConstructor().newInstance();
            for (BeanPostProcessor postProcessor : beanPostProcessorList) {
                postProcessor.autowired(beanClass, instance, this);
            }
            // 填充这个对象的属性  依赖注入
            //遍历当前类所以的字段属性

            //这是针对实现Autowired注解做的操作  移动到autowired方法(见上方代码) 先注释保留
//            for (Field field : beanClass.getDeclaredFields()) {
//                if (field.isAnnotationPresent(Autowired.class)) {
//                    // 满足条件 添加属性
//                    // 给这个instance赋值  给他一个orderService值
//                    // byType byName (@resource 就是byName)
//                    //这里只写了byName
//                    String cc = field.getName();
//                    Object bean = getBean(cc);
//                    field.setAccessible(true);
//                    field.set(instance, bean);
//                }
//            }

            // 判断这个类是否实现了BeanNameAware接口
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            // 判断这个类是否实现了InitializingBean接口
            // bean创建好后 初始化 验证过程  看看spring创建的bean是否符合我的需求
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }
            // aop等操作

            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scan(Class configClass) {
        if(configClass.isAnnotationPresent(ComponentScan.class)){
            // 所有的注解都实现了Annotation接口 所以强制转换为ComponentScan类型
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            //扫描路径 com.example.service  注意 ：这里扫描的是target下面的路径
            String path = componentScanAnnotation.value();
            path = path.replace(".","/");
            // 再将path传入getResource方法
            System.out.println("这是扫描的路径："+path);

            // 以下代码（到遍历结束）证明扫描的com.example.service为target下面的路径
            // 打印结果为：文件夹有这些文件：E:\testProject\handspring\demo\target\classes\com\example\service\UserService.class
            // 所以我们将path进行修改
            ClassLoader classLoader = AnnotationConfigApplicationContext.class.getClassLoader();
            // 传一个相对路径
            URL resource = classLoader.getResource(path);
            //file为一个文件夹   E:\testProject\handspring\demo\target\classes\com\example\service
            File file = new File(resource.getFile());

            //遍历
//           for (File listFile : file.listFiles()) {
//
//               System.out.println("文件夹有这些文件："+listFile);
//           }
            for (File f : file.listFiles()) {
                // 需要将类先加载进来  得到绝对路径
                String s = f.getAbsolutePath();
                // E:\testProject\handspring\demo\target\classes\com\example\service\UserService.class
                System.out.println("绝对路径S为：" +s);
                System.out.println("文件夹有这些文件："+f);
                // 只关心s下面.class结尾的文件
                if (s.endsWith(".class")) {
                    // 截取
                    // 此时 s= com\\example\\service
                    s = s.substring(s.indexOf("com"), s.indexOf(".class"));
                    // com\example\service\UserService
                    System.out.println("第一次截取s:"+s);
                    // 将\\替换成.
                    s = s.replace("\\", ".");
                    // 替换后
                    System.out.println("替换后s:"+s);
                }
                // 加载类
                try {
                    Class aclass = classLoader.loadClass(s);
                    // 判断这个类上有没有Component注解
                    if (aclass.isAnnotationPresent(Component.class)) {
                        System.out.println("aclass对象含有Component注解："+aclass);
                        // 获取Component注解上的名字
                        //这是bean

                        // 判断这个类是否实现了BeanPostProcessor接口，不能用instanceof 对象才用这个， 现在aclass是一个类
                        //  所以这么判断---判断某一个类是否是BeanPostProcessor派生出来的
                        if (BeanPostProcessor.class.isAssignableFrom(aclass)) {
                            // 将类实例化
                            BeanPostProcessor o = (BeanPostProcessor) aclass.getDeclaredConstructor().newInstance();
                            beanPostProcessorList.add(o);
                        }
                        //生成一个BeanDefinition对象
                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setBeanClass(aclass);
                        Component componentAnnotation = (Component) aclass.getAnnotation(Component.class);
                        String beanName = componentAnnotation.value();
                        // 判断这个类上是否为懒加载
                        if (aclass.isAnnotationPresent(Lazy.class)) {
                            beanDefinition.setLazy(true);
                            System.out.println("这是aclass懒加载对象："+aclass);
                        }
                        // 判断这个类上是否存在Scope注解  ，没有的话就是单例的
                        if (aclass.isAnnotationPresent(Scope.class)) {
                            Scope scopeAnnotation = (Scope) aclass.getAnnotation(Scope.class);
                            String value = scopeAnnotation.value();
                            beanDefinition.setScope(value);
                        }else {
                            beanDefinition.setScope("singleton");
                            System.out.println("aclass对象为单例的："+aclass);
                        }
                        beanDefinitionMap.put(beanName, beanDefinition);
                    }

                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                System.out.println("----------------------分割线--------------------------------");
            }
        }
    }

    public  Object getBean(String beanName){
        // 肯定需要一个map
        if(beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = (BeanDefinition) beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                // 单例池
                Object o = singletonsMap.get(beanName);
                if (o == null) {
                    Object bean = creatrBean(beanDefinition, beanName);
                    singletonsMap.put(beanName, bean);
                }
                return o;

            } else if (beanDefinition.getScope().equals("prototype")) {
                // 如果是一个原型的  就去创建一个bean
                Object bean = creatrBean(beanDefinition, beanName);
                return bean;
            }

        }else {
            throw new NullPointerException("没有这个bean的名字");
        }

        //拿到这个bean后判断他到底是单例的还是原型的
        //但是spring对类只会解析一次 所以引入了BeanDefinition(bean的定义  把类解析的结果缓存起来)

        return null;
    }
}
