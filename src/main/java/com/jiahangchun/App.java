package com.jiahangchun;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Hello world!
 * @author  s
 */

@CrossOrigin//允许跨越访问
@DubboComponentScan(basePackages = "com.jiahangchun.dubbo")
@SpringBootApplication(exclude=SolrAutoConfiguration.class)
@EnableTransactionManagement
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
