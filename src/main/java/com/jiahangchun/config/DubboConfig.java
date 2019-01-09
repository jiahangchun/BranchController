//package com.jiahangchun.config;
//
//import com.alibaba.dubbo.config.ApplicationConfig;
//import com.alibaba.dubbo.config.ConsumerConfig;
//import com.alibaba.dubbo.config.RegistryConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2019/1/9 上午11:09
// **/
//@Configuration
//public class DubboConfig {
//    @Bean
//    public ApplicationConfig applicationConfig() {
//        ApplicationConfig applicationConfig = new ApplicationConfig();
//        applicationConfig.setName("provider-test");
//        return applicationConfig;
//    }
//
//    @Bean
//    public RegistryConfig registryConfig() {
//        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setAddress("zookeeper://daily.zk.mockuai.com:2181");
//        return registryConfig;
//    }
//
//    @Bean
//    public ConsumerConfig consumerConfig() {
//        ConsumerConfig consumerConfig = new ConsumerConfig();
//        consumerConfig.setTimeout(3000);
//        return consumerConfig;
//    }
//
//}
