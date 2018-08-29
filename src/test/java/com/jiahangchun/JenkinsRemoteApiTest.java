//package com.jiahangchun;
//
//import com.cdancy.jenkins.rest.JenkinsClient;
//import com.cdancy.jenkins.rest.domain.system.SystemInfo;
//import com.google.common.collect.Maps;
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2018/8/27 下午3:30
// **/
//public class JenkinsRemoteApiTest {
//
//    public static void main(String[] args) {
//        JenkinsClient client = JenkinsClient.builder()
//                .endPoint("http://127.0.0.1:8080") // Optional. Defaults to http://127.0.0.1:8080
////                .credentials("test:test") // Optional.
//                .build();
//
//        SystemInfo systemInfo = client.api().systemApi().systemInfo();
//        System.out.println(systemInfo.jenkinsVersion());
//
//
//    }
//}
