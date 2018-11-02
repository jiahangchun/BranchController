package com.jiahangchun.pool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/1 下午2:41
 **/
public class TestObject {

    private AtomicInteger count = new AtomicInteger(0);

    public void add() {
        System.out.println("Tread "+Thread.currentThread().getName()+" : "+count.getAndIncrement());
    }

}
