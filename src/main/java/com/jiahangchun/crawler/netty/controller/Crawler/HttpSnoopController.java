package com.jiahangchun.crawler.netty.controller.Crawler;

import com.jiahangchun.crawler.netty.controller.CrawlerContext;
import com.jiahangchun.crawler.netty.controller.CrawlerType;
import com.jiahangchun.crawler.netty.controller.MyHttpClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/27 上午10:35
 **/
@RestController
public class HttpSnoopController {

    /**
     * BQG
     */
    private static MyHttpClient client;
    private static CrawlerContext crawlerContext;

    /**
     * BQG json
     */
    private static MyHttpClient jsonClient;
    private static CrawlerContext jsonCrawlerContext;

    static {
        crawlerContext = new CrawlerContext();
        client = new MyHttpClient(CrawlerType.BI_QU_GE, crawlerContext);

        jsonCrawlerContext = new CrawlerContext();
        jsonClient = new MyHttpClient(CrawlerType.BI_QU_GE_JSON, jsonCrawlerContext);
    }

    /**
     * 探测json
     * @return
     * @throws Exception
     */
    @RequestMapping("/crawler/bqg/json")
    public Object BQGSnoop() throws Exception {

        jsonCrawlerContext.pushURI(new URI("http://www.xbiquge.la/"));
        while (!jsonClient.doGet(jsonCrawlerContext.pop())) {
            jsonCrawlerContext.clearData();
            break;
        }
        return "success";
    }

    /**
     * 笔趣阁首页上的所有文本链接
     *
     * @return
     */
    @RequestMapping("/crawler/xbiquge")
    public Object xbiquege() throws Exception {
        crawlerContext.pushURI(new URI("http://www.xbiquge.la/"));
        while (!client.doGet(crawlerContext.pop())) {
            crawlerContext.clearData();
            break;
        }
        return "first controller";
    }
}
