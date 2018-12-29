package com.jiahangchun.application;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/29 上午10:29
 **/
@Data
public class TestEvent extends ApplicationEvent {

    /**
     *
     */
    private String name;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TestEvent(Object source) {
        super(source);
    }

    public TestEvent(Object source,String name) {
        super(source);
        this.name=name;
    }
}
