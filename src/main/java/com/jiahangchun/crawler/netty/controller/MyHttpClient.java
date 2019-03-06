//package com.jiahangchun.crawler.netty.controller;
//
//import com.jiahangchun.crawler.netty.demo.HttpSnoopClientInitializer;
//import com.jiahangchun.util.GetConfig;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.http.*;
//import io.netty.handler.codec.http.cookie.DefaultCookie;
//import io.netty.handler.ssl.SslContext;
//import io.netty.handler.ssl.SslContextBuilder;
//import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.net.URI;
//
//
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2019/1/27 上午10:36
// **/
//@Slf4j
//public class MyHttpClient {
//
//    /**
//     * http 和 https 的实现方式
//     */
//    private  Bootstrap httpBootstrap = new Bootstrap();
//    private  EventLoopGroup httpGroup = new NioEventLoopGroup();
//    private static SslContext sslCtx = null;
//    private final CrawlerContext crawlerContext;
//    private final CrawlerType type;
//
//    /**
//     * TODO Dubbo是怎么实现的？？？
//     */
//    static {
//        try {
//            sslCtx = SslContextBuilder.forClient()
//                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
//        } catch (Exception e) {
//            log.error("init sslCtx error", e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 每个类型建立一类
//     *
//     * @param type
//     */
//    public MyHttpClient(CrawlerType type, CrawlerContext crawlerContext) {
//        this.crawlerContext = crawlerContext;
//        this.type=type;
//        //这里可以根据url进行动态选择，甚至运用模版方法定制
//        httpBootstrap.group(httpGroup)
//                .channel(NioSocketChannel.class)
//                .handler(new HttpSnoopClientInitializer(null, crawlerContext, type));
//    }
//
//
//    /**
//     * 收集那种启动触发器
//     *
//     * @param url
//     * @return
//     */
//    public Bootstrap chooseBootstrap(String url) {
//        return httpBootstrap;
//    }
//
//    /**
//     * 进行Get请求
//     * finally
//     *
//     * @param
//     */
//    public Boolean doGet(URI uri) throws Exception {
//        while (null == uri) {
//            Thread.sleep(1000);
//            if (crawlerContext.ifFinished()) {
////                shutdownGracefully();
//                return false;
//            }
//        }
//
//        /**
//         * 实际处理
//         */
//        CrawlerProcess crawlerProcess= (CrawlerProcess) GetConfig.getApplicationContext().getBean(type.getProcessName());
//        crawlerProcess.send(chooseBootstrap(uri.getScheme()),uri);
//        return true;
//    }
//
//
//    /**
//     * 提供三个通用的方法
//     * @param uri
//     * @return
//     */
//    public static String getScheme(URI uri){
//       return uri.getScheme() == null ? "http" : uri.getScheme();
//    }
//
//    public static String getHost(URI uri){
//        return uri.getHost() == null ? "127.0.0.1" : uri.getHost();
//    }
//
//    public static Integer getPort(URI uri){
//        String scheme=getScheme(uri);
//        int port = uri.getPort();
//        if (port == -1) {
//            if ("http".equalsIgnoreCase(scheme)) {
//                port = 80;
//            } else if ("https".equalsIgnoreCase(scheme)) {
//                port = 443;
//            }
//        }
//        return port;
//    }
//
//
//    /**
//     * 优雅地关闭
//     * 一般不会被使用，因为我只是一个web应用
//     */
//    public void shutdownGracefully() {
//        httpGroup.shutdownGracefully();
//    }
//
//}
