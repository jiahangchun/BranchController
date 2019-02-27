package com.jiahangchun.jdk;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/2/27 下午4:17
 **/
public class AtomicTest {
    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) {
        for(int i=0;i<1000;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    longAdder.increment();
                    longAdder.increment();
                    System.out.println(longAdder.longValue());
                }
            }).start();
        }
    }
}
