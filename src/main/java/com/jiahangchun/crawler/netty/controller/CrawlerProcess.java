package com.jiahangchun.crawler.netty.controller;

import io.netty.bootstrap.Bootstrap;

import java.net.URI;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/28 上午11:07
 **/
public interface CrawlerProcess {

    public void send(Bootstrap bootstrap,URI uri) throws InterruptedException;
}
