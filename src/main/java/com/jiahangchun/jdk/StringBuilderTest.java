package com.jiahangchun.jdk;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;
import io.netty.channel.DefaultEventLoopGroup;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/2/27 上午8:56
 **/
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 1)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(16)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StringBuilderTest {

    @Benchmark
    public void testStringAdd() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;
        }
        print(a);
    }

    @Benchmark
    public void testStringBuilderAdd() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        print(sb.toString());
    }

    private void print(String a) {
    }

    public static void main(String[] args) throws RunnerException {
//        ScheduledExecutorService delayExector =new DefaultEventLoopGroup()ThreadPoolManager();


        Options options = new OptionsBuilder().include(StringBuilderTest.class.getSimpleName()).forks(1).build();
        new Runner(options).run();
    }
}