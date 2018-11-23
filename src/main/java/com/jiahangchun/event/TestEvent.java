package com.jiahangchun.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/23 上午10:49
 **/
public class TestEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TestEvent(Object source) {
        super(source);
    }
}
