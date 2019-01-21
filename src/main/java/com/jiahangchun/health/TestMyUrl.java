package com.jiahangchun.health;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/17 下午6:01
 **/
public class TestMyUrl extends RequestMappingInfoHandlerMapping implements InitializingBean {
    @Override
    protected boolean isHandler(Class<?> beanType) {
        return false;
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        return null;
    }
}
