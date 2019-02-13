package com.jiahangchun;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Hello world!
 *
 * @author s
 */
@CrossOrigin//允许跨越访问
@DubboComponentScan(basePackages = "com.jiahangchun.dubbo")
@SpringBootApplication(exclude = SolrAutoConfiguration.class)
@EnableTransactionManagement
//@EnableEurekaServer
//@EnableEurekaClient
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
