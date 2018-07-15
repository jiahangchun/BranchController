package com.jiahangchun.manager.impl;

import com.jiahangchun.constant.CategotyEnum;
import com.jiahangchun.constant.ExceptionInfoTypeEnum;
import com.jiahangchun.constant.ResourceEnum;
import com.jiahangchun.dao.CatalogEntityRepository;
import com.jiahangchun.dao.ExceptionInfoRepository;
import com.jiahangchun.dao.ResourceInfoRepository;
import com.jiahangchun.manager.NovelManager;
import com.jiahangchun.model.CatalogEntity;
import com.jiahangchun.model.ExceptionInfo;
import com.jiahangchun.model.ResourceInfo;
import com.jiahangchun.util.JsonUtil;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.manager.impl
 */
@Log4j2
@Service
public class NovelManagerImpl implements NovelManager{

    @Autowired
    private ResourceInfoRepository resourceInfoRepository;
    @Autowired
    private ExceptionInfoRepository exceptionInfoRepository;
    @Autowired
    private CatalogEntityRepository catalogEntityRepository;

    private static final String novelListsUrl="http://www.biqugexsw.com/paihangbang/";

    @Override
    @Async
    public void download() {
        System.setProperty("webdriver.firefox.bin", "I:\\firfox\\install\\firefox.exe");
        System.setProperty("webdriver.gecko.driver", "H:\\springTemplate\\BranchController\\geckodriver.exe");
        //初始化一个火狐浏览器实例，实例名称叫driver
        WebDriver driver = new FirefoxDriver();
        try {
            //最大化窗口
            driver.manage().window().maximize();
            //设置隐性等待时间
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            // get()打开一个站点
            driver.get(novelListsUrl);

            WebElement listElement = driver.findElement(By.cssSelector(".wrap.rank"));
            List<WebElement> novelsElements = listElement.findElements(By.tagName("li"));
            List<String> novelsList = new ArrayList<>();
            for (WebElement novelElement : novelsElements) {
                WebElement novelHref = novelElement.findElement(By.tagName("a"));
                String href = novelHref.getAttribute("href");
                novelsList.add(href.replace("http://www.biqugexsw.com", ""));
            }

            for (String novelHrefStr : novelsList) {
                String novelXpathStr = "//a[@href='" + novelHrefStr + "']";
                try {
                    //设置等待时间
                    WebDriverWait wait=new WebDriverWait(driver,5);
                    wait.until(new ExpectedCondition<WebElement>() {
                        @Override
                        public WebElement apply(WebDriver webDriver) {
                            return driver.findElement(By.xpath(novelXpathStr));
                        }
                    });

                    WebElement webElement = driver.findElement(By.xpath(novelXpathStr));
                    //点击某本小说进入小说的章节列表页面
                    webElement.click();
                    List<WebElement> chapterElements = driver.findElements(By.tagName("dd"));
                    List<String> hrefLists = new ArrayList<>();
                    for (WebElement chapterElement : chapterElements) {
                        WebElement href = chapterElement.findElement(By.tagName("a"));
                        String hrefStr = href.getAttribute("href");
                        hrefLists.add(hrefStr.replace("http://www.biqugexsw.com", ""));
                    }

                    //小说名称
                    WebElement coverElement=driver.findElement(By.cssSelector(".info"));
                    WebElement novelTitleElement=coverElement.findElement(By.tagName("h2"));
                    String novelName=novelTitleElement.getText();

                    CatalogEntity catalogEntity=new CatalogEntity();
                    catalogEntity.setName(novelName);
                    catalogEntity.setSourceUrl(novelListsUrl);
                    catalogEntity.setType(CategotyEnum.NOVEL.getValue());
                    catalogEntityRepository.save(catalogEntity);
                    Long catalogEntityId=catalogEntity.getId();


                    //需要根据名称进行去重处理,因为他这里还进行了最新章节的处理
                    for (String href : hrefLists) {
                        String xpathStr = "//a[@href='" + href + "']";
                        try {
                            //设置等待时间
                            WebDriverWait waitContent=new WebDriverWait(driver,5);
                            wait.until(new ExpectedCondition<WebElement>() {
                                @Override
                                public WebElement apply(WebDriver webDriver) {
                                    return driver.findElement(By.xpath(xpathStr));
                                }
                            });

                            WebElement hrefElement = driver.findElement(By.xpath(xpathStr));
                            hrefElement.click();
                            WebElement contentElement = driver.findElement(By.id("content"));
                            String content = contentElement.getText();

                            WebElement allElement = driver.findElement(By.cssSelector(".content"));
                            WebElement titleElement = allElement.findElement(By.tagName("h1"));
                            String title = titleElement.getText();
                            //对内容的处理
                            ResourceInfo resourceInfo = new ResourceInfo();
                            resourceInfo.setContent(content);
                            resourceInfo.setTitle(title);
                            resourceInfo.setCatalogEntityId(catalogEntityId);
                            resourceInfo.setType(ResourceEnum.NOVEL_BIQUGE.getValue());
                            resourceInfoRepository.saveAndFlush(resourceInfo);
                        } catch (Exception e) {
                            log.error("查询小说内容出错 :{} 当前查询内容页： {} " , e.getMessage() , JsonUtil.toJson(xpathStr));
                            ExceptionInfo exceptionInfo=new ExceptionInfo();
                            exceptionInfo.setParam(xpathStr);
                            exceptionInfo.setReason(e.getMessage());
                            exceptionInfo.setType(ExceptionInfoTypeEnum.NOVEL_BIQUGE_CONTENT_EXCEPTION.getValue());
                            exceptionInfoRepository.save(exceptionInfo);
                        }finally {
                            driver.navigate().back();
                            driver.navigate().refresh();
                        }
                    }
                    driver.navigate().back();
                    driver.navigate().refresh();
                    //返回到排序页面
                    hrefLists.clear();
                }catch (Exception e){
                    log.error("查找某本小说的列表出错：{} 当前查询小说 ： {}",e.getMessage(),JsonUtil.toJson(novelXpathStr));
                    ExceptionInfo exceptionInfo=new ExceptionInfo();
                    exceptionInfo.setParam(novelXpathStr);
                    exceptionInfo.setReason(e.getMessage());
                    exceptionInfo.setType(ExceptionInfoTypeEnum.NOVEL_BIQUGE_CATEGORY_EXCEPTION.getValue());
                    exceptionInfoRepository.save(exceptionInfo);
                }
            }
            novelsList.clear();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            log.info("success query all novels ");
            driver.close();
            driver.quit();
        }
    }
}
