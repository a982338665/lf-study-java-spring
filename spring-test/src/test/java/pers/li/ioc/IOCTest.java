package pers.li.ioc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import pers.li.bean.MyTestBean;
import pers.li.service.TestService;

/**
 * create by lishengbo 2018/11/11
 * deprecation 表示 忽略弃用方法不加中划线
 */
@SuppressWarnings("deprecation")
public class IOCTest {


    @Test
    /**
     * {@link pers.li.dao.TestDao}
     * {@link pers.li.service.TestService}
     * {@link pers.li.web.TestController}
     * {@link spring-bean.xml}
     */
    public void testSimpleLoad(){
        //执行对象，dao，service，web被实例化，构造函数被调用
        //dao-->instance
        //service-->instance
        //web-->instance
        //IOC控制反转：对象实例化交给spring创建管理
        //DI依赖注入:  程序在运行时，spring动态注入实例化对象
        //由spring管理的对象均为单例模式
        ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        TestService testService = (TestService) classPathXmlApplicationContext.getBean("testService");
        TestService testService2 = (TestService) classPathXmlApplicationContext.getBean("testService");
        testService.test();
        System.out.println(testService==testService2);

    }
}
