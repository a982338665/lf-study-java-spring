package pers.li.custom.packages.context;

import pers.li.custom.dao.TestDaoInterface;
import pers.li.custom.packages.annotation.Autowired;
import pers.li.custom.packages.annotation.Repository;
import pers.li.custom.packages.annotation.Service;
import pers.li.custom.packages.utils.StringUtils;
import pers.li.custom.packages.utils.analysisDOM4J;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by lishengbo 2018/11/11
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {

    /**
     * 存放添加有注解类的集合--类类型
     */
    List<Class<?>> classCache=new ArrayList<>();

    /**
     * 保存对象实例的集合
     */
    Map<String,Object> beanMap=new ConcurrentHashMap<>();

    public ClassPathXmlApplicationContext() {
    }

    public ClassPathXmlApplicationContext(String name) {
        //获取扫描包路径
        String aPackage = analysisDOM4J.getPackage(name);
        //确定扫描类-->递归查找使用注解的类
        getScanAnnotationClass(aPackage);
        //获取class对象进行IOC
        doIOC();
        //通过ioc操作的实例集合进行实例中对应的依赖装配DI
        doDI();
        System.err.println(classCache);
        System.err.println(beanMap  );
    }

    private void doDI() {
        if(beanMap.size()>0){
            for (Map.Entry<String,Object> entry:beanMap.entrySet()
                 ) {
                String key = entry.getKey();
                System.out.println("------>"+key);
                Object instance = entry.getValue();
                //反射获取当前实例对应类中所有字段
                Field[] declaredFields = instance.getClass().getDeclaredFields();
                for (Field fie:declaredFields
                     ) {
                    //含有Autowired注解的属性
                    if(fie.isAnnotationPresent(Autowired.class)){
//                      //根据类型装配对象
//                        Class<?> type = fie.getType();
                        //按名称装配-->属性名testDao
                        String name = fie.getName();
                        System.out.println("=======》"+name);
                        //@Autowired
                        //private TestDaoInterface testDao;-->即属性名
                        //获取需要装配对象的实例
                        Object o = beanMap.get(name);
                        //反射设置.调用set方法注入
                        fie.setAccessible(true);
                        try {
                            fie.set(instance,o);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void doIOC() {
        if(classCache.size()>0){
            for (Class<?> aclass:classCache
                 ) {
                try {
                    //创建实例对象
                    Object o = aclass.newInstance();
                    //别名默认为类名的首字母小写
                    String simpleName = aclass.getSimpleName();
                    String ali= StringUtils.toLowerCaseFirstOne(simpleName);
                    beanMap.put(ali,o);
                    //保存对应class实现的所有接口
                    Class<?>[] interfaces = aclass.getInterfaces();
                    if(interfaces!=null&&interfaces.length>0){
                        for (Class<?> instr:interfaces
                             ) {
//                            System.out.println(instr.getTypeName());
                            beanMap.put(simpleName+"."+instr.getName(),o);
                        }
                    }

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void getScanAnnotationClass(String aPackage) {
        //获取class文件路径
//        URL resource1 = this.getClass().getClassLoader().getResource("pers/li");
//        System.err.println(resource1.getPath());
        String sss = aPackage.replace(".", "/");
//        System.err.println(sss+"-------->");
        URL resource = this.getClass().getClassLoader().getResource(sss);
        File file = new File(resource.getFile());
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File childFile) {
                if(childFile.isDirectory()){
                    getScanAnnotationClass(aPackage+"."+childFile.getName());
                }else{
                    //获取文件名称
                    String name = childFile.getName();
                    //判断class文件
                    if(name.endsWith(".class")){
                        //构建一个类路径
                        String classPath=aPackage+"."+name.replace(".class","");
                        //创建路径对应的class对象
                        try {
                            Class<?> aClass = this.getClass().getClassLoader().loadClass(classPath);
                            //判断存储
                            if(aClass.isAnnotationPresent(Repository.class)
                                    || aClass.isAnnotationPresent(Service.class)){
                                classCache.add(aClass);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }



                }
                return false;
            }
        });


    }

    @Override
    public Object getBean(String beanId) {
        return this.beanMap.get(beanId);
    }


}
