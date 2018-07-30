package com.jiahangchun;

import com.jiahangchun.listener.MyStartListener;
import com.jiahangchun.utils.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ApplicationEventMulticaster;

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
        SpringApplication application=new SpringApplication(App.class);
        application.addListeners(new MyStartListener());
        application.run(args);
    }
}
