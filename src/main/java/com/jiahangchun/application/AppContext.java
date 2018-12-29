package com.jiahangchun.application;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/29 上午10:33
 **/
@Service
public class AppContext implements ApplicationContextAware {
    private static final long serialVersionUID = 6830942667813319841L;
    private ApplicationContext applicationContext;

    /**
     * 获取指定id的bean对象
     *
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

    public <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
}