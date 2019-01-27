//package com.jiahangchun.hystrix;
//
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2019/1/24 下午2:02
// **/
//@Configuration
//public class HystrixConfig {
//    @Bean
//    public ServletRegistrationBean getServlet() {
//        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
//        ServletRegistrationBean regBean = new ServletRegistrationBean(streamServlet);
//        regBean.setLoadOnStartup(1);
//        List mappingList = new ArrayList();
//        mappingList.add("/hystrix.stream");
//        regBean.setUrlMappings(mappingList);
//        regBean.setName("hystrixServlet");
//        return regBean;
//    }
//
//}
