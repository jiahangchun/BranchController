//package com.jiahangchun.healthJMX;
//
//import javax.management.*;
//import java.lang.management.ManagementFactory;
//
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2019/1/18 下午3:20
// **/
//public class HelloAgent {
//    public static void main(String[] args) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
//
//        //create mbean server
//        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
//
//        //create object name
//        ObjectName helloName = new ObjectName("jmx:name=dynamicBeanHello");
//
//        //create mbean and register mbean
//        server.registerMBean(new HelloDynamic(), helloName);
//
//        //create adaptor, adaptor is just a form as show mbean. It has no relation to specific mbean.
//        HtmlAdaptorServer adaptor  = new HtmlAdaptorServer();
//        //create adaptor name
//        ObjectName adaptorName = new ObjectName("jmxAdaptor:name=adaptor,port=5050");
//        //register adaptor and adaptor name
//        server.registerMBean(adaptor, adaptorName);
//
//        adaptor.setPort(9999);
//        adaptor.start();
//        System.out.println("....................jmx server start....................");
//    }
//
//}
