package com.jiahangchun.controller.test;

import com.google.common.collect.Maps;
import com.google.protobuf.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final static Map<HandlerMethod, RequestMappingInfo> HANDLER_METHOD_REQUEST_MAPPING_INFO_MAP = Maps.newHashMap();

    // 用于保存处理方法和RequestMappingInfo的映射关系(这个方法在解析@RequestMapping时就会被调用, 达达科技中这个地方可能写的有问题, 文中提到覆写AbstractHandlerMethodMapping#registerMapping方法, 但是经过实验之后覆写这个方法不能生效)
    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        HandlerMethod handlerMethod = super.createHandlerMethod(handler, method);
        HANDLER_METHOD_REQUEST_MAPPING_INFO_MAP.put(handlerMethod, mapping);
        super.registerHandlerMethod(handler, method, mapping);
    }

    @Override
    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {

        // 判断请求参数中是否带了event字段
         String event = request.getParameter("event");

        // 如果没有带则说明这次的请求不带路径参数, 则使用默认的处理
        if(StringUtils.isEmpty(event)) {
            return super.lookupHandlerMethod(lookupPath, request);
        }

        // 如果带了, 则从Map(这个Map中的entry在后面介绍)中获取处理当前url的方法
        List<HandlerMethod> handlerMethods = super.getHandlerMethodsForMappingName(event);
        if(CollectionUtils.isEmpty(handlerMethods)) throw new ServiceException("没有找到指定的方法");
        if(handlerMethods.size() > 1) throw new ServiceException("存在多个匹配的方法");

        HandlerMethod handlerMethod = handlerMethods.get(0);

        // 根据处理方法查找RequestMappingInfo, 用于解析路径url中的参数
        RequestMappingInfo requestMappingInfo = HANDLER_METHOD_REQUEST_MAPPING_INFO_MAP.get(handlerMethod);
        if(requestMappingInfo == null) throw new ServiceException("没有对应的匹配方法");
        super.handleMatch(requestMappingInfo, lookupPath, request);
        return handlerMethod;
    }
}