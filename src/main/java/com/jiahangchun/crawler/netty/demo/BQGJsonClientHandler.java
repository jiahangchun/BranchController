package com.jiahangchun.crawler.netty.demo;

import com.jiahangchun.crawler.netty.controller.CrawlerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/28 上午11:42
 **/
@Slf4j
public class BQGJsonClientHandler extends SimpleChannelInboundHandler<HttpObject> {
    private static CrawlerContext crawlerContext;

    public BQGJsonClientHandler(CrawlerContext crawlerContext) {
        this.crawlerContext = crawlerContext;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            HttpResponseStatus status = response.status();
            if (status.equals(HttpResponseStatus.OK)) {
                //有效的链接
                if (msg instanceof HttpContent) {
                    HttpContent content = (HttpContent) msg;
                    String detailContent = content.content().toString(CharsetUtil.UTF_8);
                    //收集其中的url
                    try {
                        detailContent(detailContent);
                    } catch (URISyntaxException e) {
                        log.error(e.getMessage(),e);
                    }
                }
            }
            ctx.close();
        }
    }

    /**
     * 处理
     * @param content
     * @return
     */
    private Set<String> detailContent(String content) throws URISyntaxException {
        crawlerContext.doneCount.incrementAndGet();
        Set<String> set=new HashSet<>();
        Document document = Jsoup.parse(content);
        Elements links = document.select("a[href]");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            if ((linkHref.startsWith("http") || linkHref.startsWith("https"))) {
                if (!linkHref.contains("biquge")) {
                    continue;
                }
                if(linkHref.contains("query") || linkHref.contains("count")){
                    System.out.println(linkText + "-" + linkHref);
                }
            }
        }
        return set;
    }
}
