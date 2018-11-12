package pers.li.custom.web;


import pers.li.custom.packages.annotation.Controller;
import pers.li.custom.packages.annotation.RequestMapping;
import pers.li.custom.service.TestService;

import javax.annotation.Resource;

/**
 * create by lishengbo 2018/11/11
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    TestService testService;

    public TestController() {
        System.err.println("web-->instance");
    }

    public void test(){
        testService.test();
    }

    /**
     * 不配置任何解析器直接转发
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "/WEB-INF/views/index.jsp";
    }
}
