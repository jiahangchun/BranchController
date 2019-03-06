///*
// * Copyright 2012 The Netty Project
// *
// * The Netty Project licenses this file to you under the Apache License,
// * version 2.0 (the "License"); you may not use this file except in compliance
// * with the License. You may obtain a copy of the License at:
// *
// *   http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations
// * under the License.
// */
//package com.jiahangchun.crawler.netty.demo;
//
//import com.jiahangchun.crawler.netty.controller.CrawlerContext;
//import com.jiahangchun.crawler.netty.controller.CrawlerType;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.codec.http.HttpClientCodec;
//import io.netty.handler.codec.http.HttpContentDecompressor;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.ssl.SslContext;
//
//public class HttpSnoopClientInitializer extends ChannelInitializer<SocketChannel> {
//
//    private final SslContext sslCtx;
//    private final CrawlerContext crawlerContext;
//    private final CrawlerType type;
//
//    public HttpSnoopClientInitializer(SslContext sslCtx, CrawlerContext crawlerContext, CrawlerType type) {
//        this.sslCtx = sslCtx;
//        this.crawlerContext = crawlerContext;
//        this.type = type;
//    }
//
//    @Override
//    public void initChannel(SocketChannel ch) {
//        ChannelPipeline p = ch.pipeline();
//
//        // Enable HTTPS if necessary.
//        if (sslCtx != null) {
//            p.addLast(sslCtx.newHandler(ch.alloc()));
//        }
//
//        p.addLast(new HttpClientCodec());
//
//        // Remove the following line if you don't want automatic content decompression.
//        p.addLast(new HttpContentDecompressor());
//
//        // Uncomment the following line if you don't want to handle HttpContents.
//        p.addLast(new HttpObjectAggregator(1048576));
//
//        switch (type) {
//            case BI_QU_GE:
//                p.addLast(new HttpSnoopClientHandler(crawlerContext));
//                break;
//            case BI_QU_GE_JSON:
//                p.addLast(new BQGJsonClientHandler(crawlerContext));
//                break;
//            default:
//                throw new RuntimeException("未找到合适的pipeline");
//        }
//
//    }
//}
