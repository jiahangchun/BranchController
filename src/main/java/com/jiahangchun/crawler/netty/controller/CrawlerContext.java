//package com.jiahangchun.crawler.netty.controller;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2019/1/27 上午10:53
// **/
//@Data
//@Slf4j
//public class CrawlerContext {
//
//    /**
//     * 维持内存表，里面维护了需要继续处理的URL
//     */
//    private ConcurrentLinkedQueue<URI> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
//    private List<URI> doingList = new ArrayList<>();
//    public AtomicInteger doneCount = new AtomicInteger(0);
//
//
//    /**
//     * 入站
//     *
//     * @param uri
//     */
//    public void pushURI(URI uri) {
//        if (doingList.contains(uri)) {
//            return;
//        }
//        concurrentLinkedQueue.add(uri);
//    }
//
//    /**
//     * 推出最新的
//     *
//     * @return
//     */
//    public URI pop() {
//        URI uri = concurrentLinkedQueue.poll();
//        if (uri != null) {
//            log.info("正在处理 [" + uri.getScheme() + "/" + uri.getHost() + ":" + uri.getPort() + "/" + uri.getPath()
//                    + "] 当前剩余 " + concurrentLinkedQueue.size() + "个,"
//                    + "已处理 " + doingList.size() + "个。");
//            doingList.add(uri);
//        }
//        return uri;
//    }
//
//    /**
//     * 是否已经处理完了
//     *
//     * @return
//     */
//    public Boolean ifFinished() {
//        return concurrentLinkedQueue.isEmpty() && (doneCount.get() == doingList.size());
//    }
//
//    /**
//     * 清除数据
//     */
//    public void clearData(){
//        concurrentLinkedQueue.clear();
//        doneCount.set(0);
//        doingList.clear();
//    }
//}
