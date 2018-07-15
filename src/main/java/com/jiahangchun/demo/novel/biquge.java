package com.jiahangchun.demo.novel;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: jiahangchun
 * @Description: http://www.biqugexsw.com/paihangbang/ 排行榜
 * @Date: 2018/7/14 缺少进度显示 & 多线程 & 代码结构调整 & mysql & 异常处理 & 定时任务 & 异常记录表
 * @Location: BranchController com.jiahangchun.demo.novel
 */
@Log4j2
public class biquge {
    public static void main(String[] args) {
        System.setProperty("webdriver.firefox.bin", "I:\\firfox\\install\\firefox.exe");
        System.setProperty("webdriver.gecko.driver", "H:\\springTemplate\\BranchController\\geckodriver.exe");

        //初始化一个火狐浏览器实例，实例名称叫driver
        WebDriver driver = new FirefoxDriver();
        try {
            //最大化窗口
            driver.manage().window().maximize();
            //设置隐性等待时间
            driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

            // get()打开一个站点
            driver.get("http://www.biqugexsw.com/paihangbang/");

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

                //需要根据名称进行去重处理,因为他这里还进行了最新章节的处理
                for (String href : hrefLists) {
                    String xpathStr = "//a[@href='" + href + "']";
                    WebElement hrefElement = driver.findElement(By.xpath(xpathStr));
                    hrefElement.click();
                    WebElement contentElement = driver.findElement(By.id("content"));
                    String content = contentElement.getText();

                    WebElement allElement = driver.findElement(By.cssSelector(".content"));
                    WebElement titleElement = allElement.findElement(By.tagName("h1"));
                    String title = titleElement.getText();

                    //对内容的处理
                    System.out.println(content);
                    driver.navigate().back();
                }
                driver.navigate().back();
                //返回到排序页面
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            driver.close();
            driver.quit();
        }

    }
}
