package pers.li.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pers.li.dao.TestDao;
import pers.li.dao.TestDaoInterface;

import javax.annotation.Resource;

/**
 * create by lishengbo 2018/11/11
 */
@Service
public class TestService {

    /**
     * @Resurce非spring注解，属J2ee注解，默认按名称装配，其次是类型
     * @Autowired注解按照类型装配：-->spring提供的注解
     *  -->若直接注入实现类则无问题,因为类型只有一种，
     *  所以不会去匹配名称
     *  1.当Atuowired注入的是一个接口时，若这个接口有两个实现类，则仅使用注解@Autowired会报错，
     *      因为按照类型装配，则会匹配到两个实现类，无法确定
     *      expected single matching bean but found 2: testDao,testDao2
     * @Qualifier("testDao")按照名称装配：
     *  1.为解决以上问题，所以需要再加此注解，指定名称，确定注入哪个实现类
     *  2.若不想写此注解，则需要保证注入的属性名称为指定的bean，即：
     *    @Autowired
     *    @Qualifier("testDao")
     *    TestDaoInterface testDao888  -->名称可随意因为已经注解指定了
     *    或
     *    @Autowired
     *    TestDaoInterface testDao  --名称不可变必须是testDao
     */
    /*@Autowired
    @Qualifier("testDao")
    TestDaoInterface testDao22;*/

   /* @Autowired
    TestDao testDao2;*/

    @Resource(name = "testDao2")
    TestDaoInterface testDao;

    public TestService() {
        System.err.println("service-->instance");
    }

    public void test(){
        testDao.test();
    }

}
