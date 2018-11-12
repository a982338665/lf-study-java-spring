package pers.li.custom.test;

import pers.li.custom.packages.context.ApplicationContext;
import pers.li.custom.packages.context.ClassPathXmlApplicationContext;
import pers.li.custom.service.TestService;

/**
 * create by lishengbo 2018/11/11
 */
public class IOCTest {

    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("spring-context.xml");
        TestService testService = (TestService) context.getBean(    "testService");
        testService.test();
    }

    /*@Test
    public void test(){

    }*/
}
