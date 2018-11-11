package pers.li.ioc;

import org.junit.Test;
import pers.li.custom.packages.context.ApplicationContext;
import pers.li.custom.packages.context.ClassPathXmlApplicationContext;

/**
 * create by lishengbo 2018/11/11
 */
public class IOCTest {

    @Test
    public void test(){
        ApplicationContext context=new ClassPathXmlApplicationContext("spring-context.xml");

    }
}
