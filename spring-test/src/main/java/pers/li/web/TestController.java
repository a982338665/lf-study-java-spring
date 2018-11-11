package pers.li.web;

import org.springframework.stereotype.Controller;
import pers.li.service.TestService;

import javax.annotation.Resource;

/**
 * create by lishengbo 2018/11/11
 */
@Controller
public class TestController {

    @Resource
    TestService testService;

    public TestController() {
        System.err.println("web-->instance");
    }

    public void test(){
        testService.test();
    }
}
