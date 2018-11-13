package pers.li.custom.packages.mapping;

import java.lang.reflect.Method;

/**
 * 映射类
 * @author:luofeng
 * @createTime : 2018/11/13 15:14
 */
public class HandlerMapping {

    private String pattern;
    private Method method;
    private Object controller;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return "HandlerMapping{" +
                "pattern='" + pattern + '\'' +
                ", method=" + method +
                ", controller=" + controller +
                '}';
    }
}
