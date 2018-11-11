package pers.li.custom.dao;


import pers.li.custom.packages.annotation.Repository;

/**
 * create by lishengbo 2018/11/11
 */
@Repository
public class TestDao implements  TestDaoInterface{

    public TestDao() {
        System.err.println("dao-->instance");
    }

    public void test(){
        System.out.println("dao---->test");
    }
}
