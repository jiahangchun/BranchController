package com.jiahangchun.application;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/29 上午10:30
 **/
@Service
public class TestEventListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof TestEvent){
            TestEvent testEvent= (TestEvent) event;
            System.out.println("listener......"+testEvent.getName());
        }
    }
}
