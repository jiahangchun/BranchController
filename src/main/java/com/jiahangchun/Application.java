package com.jiahangchun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/5/30 上午9:42
 **/
@SpringBootApplication
@EnableScheduling
@EnableAsync //异步的情况
//调用的时候能直接使用@Async进行异步的执行
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
