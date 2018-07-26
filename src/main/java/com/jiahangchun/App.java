package com.jiahangchun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {

    static{
        System.out.println("test class.forName App");
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
