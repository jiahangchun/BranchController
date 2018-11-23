package com.jiahangchun.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/23 上午10:51
 **/
@Service
public class TestListener implements ApplicationListener<TestEvent> {
    @Override
    public void onApplicationEvent(TestEvent event) {
        System.out.println(event.getSource()+"==== "+event.getTimestamp());
    }
}
