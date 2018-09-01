package com.jiahangchun.config;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/8/31 下午3:56
 **/
@Service
public class MyHolder {

    @Autowired
    private WebApplicationContext applicationContext;

    private static Map<String, String/*方法的请求地址 <--> 所需要的权限 */> ALL_METHOD_AND_AUTH_MAP = Maps.newHashMapWithExpectedSize(50);

    /**
     * 用于初始化调用
     * TODO 改成PostIn...
     * @return
     */
    public Map<String, String> queryMethodAndAuth() {
        if (ALL_METHOD_AND_AUTH_MAP.size() <= 0) {
            RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
            handlerMethods.entrySet()
                    .stream()
                    .filter(map -> map.getKey().getName() != null)
                    .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()))
                    .forEach((k, v) -> {
                        String name = k.getName();
                        Set<String> patterns = k.getPatternsCondition().getPatterns();
                        patterns.forEach(url -> ALL_METHOD_AND_AUTH_MAP.put(url, generatorAuth(name)));
                    });
        }
        return ALL_METHOD_AND_AUTH_MAP;
    }

    /**
     * 生成对应的权限名称
     *
     * @param
     * @return
     */
    private String generatorAuth(String name) {
        return name;
    }

}
