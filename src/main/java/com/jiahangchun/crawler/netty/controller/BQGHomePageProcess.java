package com.jiahangchun.crawler.netty.controller;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/28 上午11:10
 **/
@Service("BQGHomePageProcess")
public class BQGHomePageProcess implements CrawlerProcess {
    @Override
    public void send(Bootstrap bootstrap, URI uri) throws InterruptedException {
        String host=MyHttpClient.getHost(uri);
        Integer port=MyHttpClient.getPort(uri);
// Make the connection attempt.
        Channel ch = bootstrap.connect(host, port).sync().channel();
        // Prepare the HTTP request.
        HttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
        request.headers().set(HttpHeaderNames.HOST, host);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);

        // Set some example cookies.
        request.headers().set(
                HttpHeaderNames.COOKIE,
                io.netty.handler.codec.http.cookie.ClientCookieEncoder.STRICT.encode(
                        new io.netty.handler.codec.http.cookie.DefaultCookie("my-cookie", "foo"),
                        new DefaultCookie("another-cookie", "bar")));

        // Send the HTTP request.
        ch.writeAndFlush(request);
        // Wait for the server to close the connection.
        ch.closeFuture().sync();
    }
}
