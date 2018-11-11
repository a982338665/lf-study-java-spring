package pers.li.bean;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * create by lishengbo 2018/11/11
 * deprecation 表示 忽略弃用方法不加中划线
 */
@SuppressWarnings("deprecation")
public class BeanFactoryTest {


    @Test
    /**
     *  bean容器的基本用法测试
     * {@link pers.li.bean.MyTestBean}
     * {@link spring-bean.xml}
     */

    public void testSimpleLoad(){
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("spring-bean.xml"));
        MyTestBean myTestBean = (MyTestBean)xmlBeanFactory.getBean("myTestBean");
        Assert.assertEquals("testStr",myTestBean.getTestStr());
    }
}
