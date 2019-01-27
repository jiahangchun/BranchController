/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiahangchun.crawler.imagecrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yasser Ganjisaffar
 */
@RestController
public class ImageCrawlController {
    private static final Logger logger = LoggerFactory.getLogger(ImageCrawlController.class);

    /**
     * logger.info("Needed parameters: ");
     * //            logger.info("\t rootFolder (it will contain intermediate crawl data)");
     * //            logger.info("\t numberOfCrawlers (number of concurrent threads)");
     * //            logger.info("\t storageFolder (a folder for storing downloaded images)");
     *
     * @param rootFolder
     */
    @RequestMapping("/crawler")
    public void crawler(@RequestParam(required = false, defaultValue = "/Users/hzmk/Desktop/crawler/rootFolder", name = "rootFolder") String rootFolder,
                        @RequestParam(required = false, defaultValue = "1", name = "numberOfCrawlers") Integer numberOfCrawlers,
                        @RequestParam(required = false, defaultValue = "/Users/hzmk/Desktop/crawler/storageFolder", name = "storageFolder") String storageFolder) throws Exception {
        DateTime dateTime = new DateTime();
        String dateTimeStr = dateTime.toString("yyyyMMddHHmmss");
        rootFolder += "/" + dateTimeStr;
        storageFolder += "/" + dateTimeStr;
        doCrawler(rootFolder, numberOfCrawlers, storageFolder);
    }

    /**
     * 真实启动
     *
     * @param rootFolder
     * @param numberOfCrawlers
     * @param storageFolder
     */
    @Async
    public void doCrawler(String rootFolder, Integer numberOfCrawlers, String storageFolder) throws Exception {
        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(rootFolder);

        /*
         * Since images are binary content, we need to set this parameter to
         * true to make sure they are included in the crawl.
         */
        config.setIncludeBinaryContentInCrawling(true);

        String[] crawlDomains = {"http://www.xbiquge.la","http://m.xbiquge.la"};

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        for (String domain : crawlDomains) {
            controller.addSeed(domain);
        }

        ImageCrawler.configure(crawlDomains, storageFolder);
        controller.start(ImageCrawler.class, numberOfCrawlers);
    }
}
