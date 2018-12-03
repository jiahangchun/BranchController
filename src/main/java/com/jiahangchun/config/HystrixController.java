//package com.jiahangchun.config;
//
//import UserDO;
//import com.jiahangchun.repository.UserRepository;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Iterator;
//
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2018/10/16 下午3:17
// **/
//
//@RestController
//public class HystrixController {
//
//    @RequestMapping("/hystrix/test")
//    @HystrixCommand(
//            fallbackMethod = "error",
//            threadPoolProperties = {  //10个核心线程池,超过20个的队列外的请求被拒绝; 当一切都是正常的时候，线程池一般仅会有1到2个线程激活来提供服务
//                    @HystrixProperty(name = "coreSize", value = "10"),
//                    @HystrixProperty(name = "maxQueueSize", value = "100"),
//                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "20")},
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"), //命令执行超时时间
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"), //若干10s一个窗口内失败三次, 则达到触发熔断的最少请求量
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000") //断路30s后尝试执行, 默认为5s
//            })
//    public String get() {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("tax");
//        return stringBuilder.toString();
//    }
//
//    public String error() {
//        return "error";
//    }
//}
