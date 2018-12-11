package com.jiahangchun;

/**
 * @author chunchun
 * @descritpion https://github.com/alibaba/arthas/blob/master/README_CN.md
 * @date Created at 2018/10/19 上午10:32
 **/

import org.assertj.core.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo {
    static class Counter {
        private static AtomicInteger count = new AtomicInteger(0);

        public static void increment() {
            count.incrementAndGet();
        }

        public static int value() {
            return count.get();
        }
    }

//    public static void main(String[] args) throws InterruptedException {
//        while (true) {
//            Counter.increment();
//            System.out.println("counter: " + Counter.value());
//            TimeUnit.SECONDS.sleep(1);
//        }sssss
//    }

    public static void main(String[] args) {
    }
}

