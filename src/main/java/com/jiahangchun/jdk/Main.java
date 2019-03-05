package com.jiahangchun.jdk;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/2/27 上午10:04
 **/
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.Throughput)
public class Main {
    private static AtomicLong count = new AtomicLong();
    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(Main.class.getName()).forks(1).build();
        new Runner(options).run();
    }

//    @Benchmark
//    @Threads(10)
//    public void run0() {
//        count.getAndIncrement();
//    }

    @Benchmark
    @Threads(100)
    public void run1() {
        for(int i=0;i<1000;i++) {
            longAdder.increment();
        }
        System.out.println(longAdder.longValue());
    }


}