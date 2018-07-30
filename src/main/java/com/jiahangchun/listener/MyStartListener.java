package com.jiahangchun.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/30 下午1:39
 **/
public class MyStartListener implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("MyStartListener.ApplicationStartedEvent started");
    }
}
