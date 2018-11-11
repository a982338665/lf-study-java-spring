package pers.li.custom.service;

import pers.li.custom.dao.TestDao;
import pers.li.custom.dao.TestDaoInterface;
import pers.li.custom.packages.annotation.Autowired;
import pers.li.custom.packages.annotation.Service;

/**
 * create by lishengbo 2018/11/11
 */
@Service
public class TestService {

    @Autowired
    private TestDaoInterface testDao;

    public TestService() {
        System.err.println("service-->instance");
    }

    public void test(){
        testDao.test();
    }

}
