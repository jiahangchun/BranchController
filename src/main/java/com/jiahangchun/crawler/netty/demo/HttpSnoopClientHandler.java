package com.jiahangchun.crawler.netty.demo;

import com.alibaba.fastjson.JSON;
import com.jiahangchun.crawler.netty.controller.CrawlerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hzmk
 */
@Slf4j
public class HttpSnoopClientHandler extends SimpleChannelInboundHandler<HttpObject> {

    Pattern pattern = Pattern.compile(".*[0-9]+/[0-9]+/$");
    private static CrawlerContext crawlerContext;

    public HttpSnoopClientHandler(CrawlerContext crawlerContext) {
        this.crawlerContext = crawlerContext;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        crawlerContext.doneCount.incrementAndGet();

        StringBuilder stringBuilder = new StringBuilder();
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            stringBuilder.append("STATUS: " + response.status()).append("\r\n");
            stringBuilder.append("VERSION: " + response.protocolVersion()).append("\r\n");
            if (!response.headers().isEmpty()) {
                for (CharSequence name : response.headers().names()) {
                    for (CharSequence value : response.headers().getAll(name)) {
                        stringBuilder.append("HEADER: " + name + " = " + value).append("\r\n");
                    }
                }
            }

            if (HttpUtil.isTransferEncodingChunked(response)) {
                stringBuilder.append("CHUNKED CONTENT {").append("\r\n");
            } else {
                stringBuilder.append("CONTENT {").append("\r\n");
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            String detailContent = content.content().toString(CharsetUtil.UTF_8);
            detailContent(detailContent);
            if (content instanceof LastHttpContent) {
                stringBuilder.append("} END OF CONTENT").append("\r\n");
                ctx.close();
            }
        }
        System.out.println(stringBuilder.toString());
        System.out.println(JSON.toJSONString(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        crawlerContext.doneCount.incrementAndGet();
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 处理 首页的处理规则
     *
     * @param content
     */
    private void detailContent(String content) {
        Document document = Jsoup.parse(content);
        Elements links = document.select("a[href]");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            if ((linkHref.startsWith("http") || linkHref.startsWith("https"))) {
                if (!linkHref.contains("biquge")) {
                    continue;
                }
                if (linkText == null || linkText.equals("")) {
                    continue;
                }
                Matcher matcher = pattern.matcher(linkHref);
                if (!matcher.matches()) {
                    continue;
                }
                System.out.println(linkText + "-" + linkHref);
            }
        }
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        HttpSnoopClientHandler snoopClientHandler = new HttpSnoopClientHandler(new CrawlerContext());
        Matcher matcher = snoopClientHandler.pattern.matcher("http://www.xbiquge.la/13/13959/");
        System.out.println(matcher.matches());


        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<meta name=\"baidu-site-verification\" content=\"L8SAgW36wb\" />\n" +
                "<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />\n" +
                "<meta http-equiv=\"Cache-Control\" content=\"no-transform\" />\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<title>【笔趣阁】_笔趣阁小说网_笔趣阁小说阅读网_新笔趣阁</title>\n" +
                "<meta name=\"keywords\" content=\"新笔趣阁,笔趣阁小说阅读网，新笔趣阁无弹窗小说阅读网\" />\n" +
                "<meta name=\"description\" content=\"笔趣阁是广大书友最值得收藏的网络小说阅读网，新笔趣阁收录了当前最火热的网络小说，新笔趣阁免费提供高质量的小说最新章节，新笔趣阁是广大网络小说爱好者必备的小说阅读网。\" />\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/biquge.css\"/>\n" +
                "<script type=\"text/javascript\" src=\"http://libs.baidu.com/jquery/1.4.2/jquery.min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"/images/bqg.js\"></script>\n" +
                "<link rel=\"shortcut icon\" href=\"favicon.ico\" />\n" +
                "<meta name=\"baidu-site-verification\" content=\"cBwSh6gmX8\" />\n" +
                "\n" +
                "\n" +
                "<!--<script>QIHOO_UNION_F_SLOT={w:500, h:48, ls:\"s44755a2d8d\",position:7, display:2,origin:0};</script><script src=\"http://s.lianmeng.360.cn/so/f.js\" charset=\"utf-8\"></script> -->\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div id=\"wrapper\">\n" +
                "    <script>login();</script>\n" +
                "\n" +
                "    <div class=\"header\">\n" +
                "    \n" +
                "      <div class=\"header_logo\">\n" +
                "        <a href=\"http://www.xbiquge.la\" >新笔趣阁</a>\n" +
                "      </div>\n" +
                "      <script>bqg_panel();</script>\n" +
                "    \n" +
                "    </div>\n" +
                "    <div class=\"nav\">\n" +
                "      <ul>\n" +
                "        <li><a href=\"http://www.xbiquge.la/\">首页</a></li>\n" +
                "        <li><a href=\"/modules/article/bookcase.php\">我的书架</a></li>\n" +
                "        <li><a href=\"/xuanhuanxiaoshuo/\">玄幻小说</a></li>\n" +
                "        <li><a href=\"/xiuzhenxiaoshuo/\">修真小说</a></li>\n" +
                "        <li><a href=\"/dushixiaoshuo/\">都市小说</a></li>\n" +
                "        <li><a href=\"/chuanyuexiaoshuo/\">穿越小说</a></li>\n" +
                "        <li><a href=\"/wangyouxiaoshuo/\">网游小说</a></li>\n" +
                "        <li><a href=\"/kehuanxiaoshuo/\">科幻小说</a></li>\n" +
                "        <li><a href=\"/paihangbang/\">排行榜单</a></li>\n" +
                "        <li><a href=\"/xiaoshuodaquan/\">全部小说</a></li>\n" +
                "      </ul>\n" +
                "    </div>\n" +
                "<script type=\"text/javascript\">top_bar();</script>\n" +
                " \n" +
                "    <div class=\"dahengfu\" style=\"margin-bottom:0;\"><script>mainbanner();</script></div>\n" +
                "<div id=\"main\">\n" +
                "\n" +
                "<div id=\"hotcontent\">\n" +
                "\n" +
                "\n" +
                "<div class=\"l\">\n" +
                "\n" +
                "  \n" +
                "  <div class=\"item\">\n" +
                "        <div class=\"image\"><a href=\"http://www.xbiquge.la/10/10489/\"><img src=\"http://www.xbiquge.la/files/article/image/10/10489/10489s.jpg\" alt=\"三寸人间\"  width=\"120\" height=\"150\" /></a></div>\n" +
                "        <dl>\n" +
                "           <dt><span>耳根</span><a href=\"http://www.xbiquge.la/10/10489/\">三寸人间</a></dt>\n" +
                "           <dd>    举头三尺无神明，掌心三寸是人间。这是耳根继《仙逆》《求魔》《我欲封天》《一念永恒》后，创作的第五部长篇小说《三寸人间》。</dd>\n" +
                "        </dl>\n" +
                "        <div class=\"clear\"></div>\n" +
                "      </div>  \n" +
                "\n" +
                "  \n" +
                "  <div class=\"item\">\n" +
                "        <div class=\"image\"><a href=\"http://www.xbiquge.la/13/13959/\"><img src=\"http://www.xbiquge.la/files/article/image/13/13959/13959s.jpg\" alt=\"圣墟\"  width=\"120\" height=\"150\" /></a></div>\n" +
                "        <dl>\n" +
                "           <dt><span>辰东</span><a href=\"http://www.xbiquge.la/13/13959/\">圣墟</a></dt>\n" +
                "           <dd>    在破败中崛起，在寂灭中复苏。\n" +
                "    沧海成尘，雷电枯竭，那一缕幽雾又一次临近大地，世间的枷锁被打开了，一个全新的世界就此揭开神秘的一角……\n" +
                "</dd>\n" +
                "        </dl>\n" +
                "        <div class=\"clear\"></div>\n" +
                "      </div>  \n" +
                "\n" +
                "  \n" +
                "  <div class=\"item\">\n" +
                "        <div class=\"image\"><a href=\"http://www.xbiquge.la/0/419/\"><img src=\"http://www.xbiquge.la/files/article/image/0/419/419s.jpg\" alt=\"绝品邪少\"  width=\"120\" height=\"150\" /></a></div>\n" +
                "        <dl>\n" +
                "           <dt><span>陨落星辰</span><a href=\"http://www.xbiquge.la/0/419/\">绝品邪少</a></dt>\n" +
                "           <dd>    谭笑笑说：我是极品美女！ 彭莹诗说：我是极品御姐！ 尹宝儿说：我是极品萝莉！ 上官无道说：我还是极品公子呢…… 叶潇震撼登场：吵什么吵，少爷才是主角，少爷的口号是践踏一切极品，只做人间绝品！ 【PS：陨落星辰自《校园风流邪神》之后又一都市巅峰...</dd>\n" +
                "        </dl>\n" +
                "        <div class=\"clear\"></div>\n" +
                "      </div>  \n" +
                "\n" +
                "  \n" +
                "  <div class=\"item\">\n" +
                "        <div class=\"image\"><a href=\"http://www.xbiquge.la/15/15003/\"><img src=\"http://www.xbiquge.la/files/article/image/15/15003/15003s.jpg\" alt=\"道君\"  width=\"120\" height=\"150\" /></a></div>\n" +
                "        <dl>\n" +
                "           <dt><span>跃千愁</span><a href=\"http://www.xbiquge.la/15/15003/\">道君</a></dt>\n" +
                "           <dd>    一个地球神级盗墓宗师，闯入修真界的故事……\n" +
                "    桃花源里，有歌声。\n" +
                "    山外青山，白骨山。\n" +
                "    五花马，千金裘，倚天剑，应我多情，啾啾鬼鸣，美人薄嗔。\n" +
                "    天地无垠，谁家旗鼓，碧落黄泉，万古高楼。\n" +
                "    为义气争雄！\n" +
                "   ...</dd>\n" +
                "        </dl>\n" +
                "        <div class=\"clear\"></div>\n" +
                "      </div>  \n" +
                "\n" +
                "</div>\n" +
                "  \n" +
                "\n" +
                "    <div class=\"r\">\n" +
                "     <h2>公告牌</h2>\n" +
                "    <ul>\n" +
                "          <li><span class=\"s1\">[公告]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/newmessage.php?tosys=1\" target=\"_blank\" rel=\"nofollow\" >发现章节未及时更新请联系我们</a></span></li>  \n" +
                "    </ul> \n" +
                "   <h2>上期强推</h2>\n" +
                "    <ul>\n" +
                "             <li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/15/15409/\">牧神记</a></span><span class=\"s5\">宅猪</span></li> <li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/2/2210/\">全职法师</a></span><span class=\"s5\">乱</span></li> <li><span class=\"s1\">[网游]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/25/25044/\">全息海贼时代</a></span><span class=\"s5\">罗秦</span></li> <li><span class=\"s1\">[都市]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/7/7552/\">万古神帝</a></span><span class=\"s5\">飞天鱼</span></li> <li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/13/13959/\">圣墟</a></span><span class=\"s5\">辰东</span></li> <li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/7/7931/\">终极斗罗</a></span><span class=\"s5\">唐家三少</span></li>     </ul>\n" +
                "    </div>\n" +
                "    <div class=\"clear\"></div>\n" +
                "  </div>\n" +
                "  \n" +
                "\n" +
                "<div class=\"dahengfu\">\n" +
                "<script>bannerindex();</script>\n" +
                "</div>\n" +
                " \n" +
                "<script src=\"http://cpro.baidustatic.com/cpro/ui/c.js\" type=\"text/javascript\"></script>\n" +
                "\n" +
                "<div class=\"novelslist\">\n" +
                "\n" +
                "<div class=\"content\">\n" +
                "  <h2>玄幻小说</h2>\n" +
                "  <div class=\"top\">\n" +
                "<div class=\"image\"><img src=\"http://www.xbiquge.la/files/article/image/15/15409/15409s.jpg\" alt=\"牧神记\"  width=\"67\" height=\"82\" /></div>\n" +
                "<dl><dt><a href=\"http://www.xbiquge.la/15/15409/\">牧神记</a></dt><dd>    大墟的祖训说，天黑，别出门。大墟残老村的老弱病残们从江边捡到了一个婴儿，取名秦牧，含辛茹苦将他养大。    这一天夜幕降临，黑暗笼罩大墟，秦牧走出了家门……做个春风中荡漾的反派吧！    瞎子对他说。秦牧的反派之路，正在崛起！...</dd></dl>\n" +
                "<div class=\"clear\"></div></div>\n" +
                "<ul>\n" +
                "<li><a href=\"http://www.xbiquge.la/2/2699/\">妖神记</a>/发飙的蜗牛</li><li><a href=\"http://www.xbiquge.la/0/951/\">伏天氏</a>/净无痕</li><li><a href=\"http://www.xbiquge.la/2/2210/\">全职法师</a>/乱</li><li><a href=\"http://www.xbiquge.la/10/10512/\">人皇纪</a>/皇甫奇</li><li><a href=\"http://www.xbiquge.la/14/14930/\">元尊</a>/天蚕土豆</li><li><a href=\"http://www.xbiquge.la/15/15159/\">大道朝天</a>/猫腻</li><li><a href=\"http://www.xbiquge.la/22/22108/\">史上最强赘婿</a>/沉默的糕点</li><li><a href=\"http://www.xbiquge.la/14/14831/\">飞剑问道</a>/我吃西红柿</li><li><a href=\"http://www.xbiquge.la/16/16108/\">凌天战尊</a>/风轻扬</li><li><a href=\"http://www.xbiquge.la/2/2208/\">霸皇纪</a>/踏雪真人</li><li><a href=\"http://www.xbiquge.la/17/17941/\">天帝传</a>/飞天鱼</li><li><a href=\"http://www.xbiquge.la/13/13959/\">圣墟</a>/辰东</li></ul>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"content\">\n" +
                "   <h2>修真小说</h2>\n" +
                "  <div class=\"top\">\n" +
                "<div class=\"image\"><img src=\"http://www.xbiquge.la/files/article/image/7/7004/7004s.jpg\" alt=\"遮天\" alt=\"遮天\"  width=\"67\" height=\"82\" /></div>\n" +
                "<dl><dt><a href=\"http://www.xbiquge.la/7/7004/\">遮天</a></dt><dd>    冰冷与黑暗并存的宇宙深处，九具庞大的龙尸拉着一口青铜古棺，亘古长存。\n" +
                "    这是太空探测器在枯寂的宇宙中捕捉到的一幅极其震撼的画面。\n" +
                "    九龙拉棺，究竟是回到了上古，还是来到了星空的彼岸？\n" +
                "    一个浩大的仙侠世界，光怪陆离，神秘无尽...</dd></dl>\n" +
                "<div class=\"clear\"></div></div>\n" +
                "<ul>\n" +
                "<li><a href=\"http://www.xbiquge.la/10/10489/\">三寸人间</a>/耳根</li><li><a href=\"http://www.xbiquge.la/0/215/\">魔天记</a>/忘语</li><li><a href=\"http://www.xbiquge.la/6/6930/\">天下第九</a>/鹅是老五</li><li><a href=\"http://www.xbiquge.la/0/205/\">执魔</a>/我是墨水</li><li><a href=\"http://www.xbiquge.la/11/11319/\">九棺</a>/山河万朵</li><li><a href=\"http://www.xbiquge.la/5/5395/\">凡人修仙传</a>/忘语</li><li><a href=\"http://www.xbiquge.la/6/6818/\">真武世界</a>/蚕茧里的牛</li><li><a href=\"http://www.xbiquge.la/19/19677/\">凡人修仙传仙界篇</a>/忘语</li><li><a href=\"http://www.xbiquge.la/1/1882/\">道门法则</a>/八宝饭</li><li><a href=\"http://www.xbiquge.la/5/5545/\">独步天下</a>/宅猪</li><li><a href=\"http://www.xbiquge.la/28/28573/\">逆天红包神仙群系统</a>/狂热的茄子</li><li><a href=\"http://www.xbiquge.la/21/21631/\">都市最强修真学生</a>/林北留</li></ul>\n" +
                "  </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div class=\"content border\">\n" +
                "   <h2>都市小说</h2>\n" +
                "  <div class=\"top\">\n" +
                "<div class=\"image\"><img src=\"http://www.xbiquge.la/files/article/image/0/52/52s.jpg\" alt=\"校花的贴身高手\"  width=\"67\" height=\"82\" /></div>\n" +
                "<dl><dt><a href=\"http://www.xbiquge.la/0/52/\">校花的贴身高手</a></dt><dd>    林逸是一名普通学生，不过，他还身负另外一个重任，那就是追校花！而且还是奉校花老爸之命！\n" +
                "    虽然林逸很不想跟这位难伺候的大小姐打交道，但是长辈之命难违抗，他不得不千里迢迢的转学到了松山市，给大小姐鞍前马后的当跟班……于是，史上最牛B的跟班...</dd></dl>\n" +
                "<div class=\"clear\"></div></div>\n" +
                "<ul>\n" +
                "<li><a href=\"http://www.xbiquge.la/7/7552/\">万古神帝</a>/飞天鱼</li><li><a href=\"http://www.xbiquge.la/15/15273/\">合租医仙</a>/白纸一箱</li><li><a href=\"http://www.xbiquge.la/3/3120/\">极品全能学生</a>/花都大少</li><li><a href=\"http://www.xbiquge.la/3/3321/\">邪王追妻：废材逆天小姐</a>/苏小暖</li><li><a href=\"http://www.xbiquge.la/7/7246/\">神藏</a>/打眼</li><li><a href=\"http://www.xbiquge.la/0/419/\">绝品邪少</a>/陨落星辰</li><li><a href=\"http://www.xbiquge.la/26/26201/\">重生香江之豪门盛宴</a>/烈日孤魂</li><li><a href=\"http://www.xbiquge.la/2/2984/\">武神血脉</a>/刚大木</li><li><a href=\"http://www.xbiquge.la/3/3521/\">重生之最强剑神</a>/天运老猫</li><li><a href=\"http://www.xbiquge.la/10/10093/\">我的绝色总裁老婆</a>/李暮歌</li><li><a href=\"http://www.xbiquge.la/3/3305/\">大刁民</a>/仲星羽</li><li><a href=\"http://www.xbiquge.la/26/26154/\">天降巨富</a>/陆原居</li></ul>\n" +
                "  </div>\n" +
                "\n" +
                "\n" +
                "<div class=\"clear\"></div>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"novelslist\">\n" +
                "\n" +
                "<div class=\"content\">\n" +
                "    <h2>穿越小说</h2>\n" +
                "  <div class=\"top\">\n" +
                "<div class=\"image\"><img src=\"http://www.xbiquge.la/files/article/image/19/19620/19620s.jpg\" alt=\"明朝败家子\"  width=\"67\" height=\"82\" /></div>\n" +
                "<dl><dt><a href=\"http://www.xbiquge.la/19/19620/\">明朝败家子</a></dt><dd>    弘治十一年。这是一个美好的清晨。此时朱厚照初成年。此时王守仁和唐伯虎磨刀霍霍，预备科举。    此时小冰河期已经来临，绵长的严寒肆虐着大地。此时在南和伯府里，地主家的傻儿子，南和伯的嫡传继承人方继藩……开始了他没羞没躁的败家人生。...</dd></dl>\n" +
                "<div class=\"clear\"></div></div>\n" +
                "<ul>\n" +
                "<li><a href=\"http://www.xbiquge.la/3/3322/\">寒门崛起</a>/朱郎才尽</li><li><a href=\"http://www.xbiquge.la/3/3720/\">楚臣</a>/更俗</li><li><a href=\"http://www.xbiquge.la/1/1690/\">庆余年</a>/猫腻</li><li><a href=\"http://www.xbiquge.la/19/19588/\">如意小郎君</a>/荣小荣</li><li><a href=\"http://www.xbiquge.la/24/24402/\">绝世极品兵王</a>/不萌的熊猫</li><li><a href=\"http://www.xbiquge.la/0/999/\">大宋超级学霸</a>/高月</li><li><a href=\"http://www.xbiquge.la/2/2352/\">三国大驯兽师</a>/虎豹骑</li><li><a href=\"http://www.xbiquge.la/7/7380/\">灭世武修</a>/天上无鱼</li><li><a href=\"http://www.xbiquge.la/17/17502/\">狼牙兵王</a>/蝼蚁望天</li><li><a href=\"http://www.xbiquge.la/18/18245/\">秦吏</a>/七月新番</li><li><a href=\"http://www.xbiquge.la/30/30324/\">暗兵之王</a>/狼员外</li><li><a href=\"http://www.xbiquge.la/28/28117/\">抗日之全能兵王</a>/寂寞剑客</li></ul>\n" +
                "  </div>\n" +
                "\n" +
                "<div class=\"content\">\n" +
                "   <h2>网游小说</h2>\n" +
                "  <div class=\"top\">\n" +
                "<div class=\"image\"><img src=\"http://www.xbiquge.la/files/article/image/26/26872/26872s.jpg\" alt=\"蓝龙的无限之旅\"  width=\"67\" height=\"82\" /></div>\n" +
                "<dl><dt><a href=\"http://www.xbiquge.la/26/26872/\">蓝龙的无限之旅</a></dt><dd>    重生多元宇宙，    在龙之谷世界中寻找龙之宝玉的踪迹；    在霍比特人世界中吊打史矛革；    在魔兽世界中强占希尔瓦娜斯；    在不死之王世界中，登上了纳萨力克大坟墓王座...    ......    当你凝望深渊时，却发现早已身处...</dd></dl>\n" +
                "<div class=\"clear\"></div></div>\n" +
                "<ul>\n" +
                "<li><a href=\"http://www.xbiquge.la/16/16288/\">网游之末日剑仙</a>/头发掉了</li><li><a href=\"http://www.xbiquge.la/15/15993/\">奶爸的异界餐厅</a>/轻语江湖</li><li><a href=\"http://www.xbiquge.la/24/24191/\">篮坛之氪金无敌</a>/肉末大茄子</li><li><a href=\"http://www.xbiquge.la/15/15579/\">游戏之狩魔猎人</a>/阡之陌一</li><li><a href=\"http://www.xbiquge.la/25/25044/\">全息海贼时代</a>/罗秦</li><li><a href=\"http://www.xbiquge.la/24/24874/\">超神学院之万界商城</a>/失落的轻语</li><li><a href=\"http://www.xbiquge.la/16/16776/\">带刀禁卫</a>/快剑江湖</li><li><a href=\"http://www.xbiquge.la/23/23438/\">网游之九转轮回</a>/莫若梦兮</li><li><a href=\"http://www.xbiquge.la/1/1602/\">全职高手</a>/蝴蝶蓝</li><li><a href=\"http://www.xbiquge.la/24/24400/\">突然无敌了</a>/踏仙路的冰尘</li><li><a href=\"http://www.xbiquge.la/18/18254/\">落地一把98K</a>/Iced子夜</li><li><a href=\"http://www.xbiquge.la/0/931/\">得分之王</a>/华晓鸥</li></ul>\n" +
                "  </div>\n" +
                "\n" +
                "<div class=\"content border\">\n" +
                "   <h2>科幻小说</h2>\n" +
                "  <div class=\"top\">\n" +
                "<div class=\"image\"><img src=\"http://www.xbiquge.la/files/article/image/17/17179/17179s.jpg\" alt=\"重生最强穿越\"  width=\"67\" height=\"82\" /></div>\n" +
                "<dl><dt><a href=\"http://www.xbiquge.la/17/17179/\">重生最强穿越</a></dt><dd>    得系统林天踏上掠夺诸天之路，谋取机缘，铸成不朽！狐妖小红娘、神印王座、超神学院、瓦罗兰大陆、斗罗大陆、西游记，诸天万界皆等待着他的探索，然而这一切还得从古剑奇谭开始。    【书友群：588496239】</dd></dl>\n" +
                "<div class=\"clear\"></div></div>\n" +
                "<ul>\n" +
                "<li><a href=\"http://www.xbiquge.la/20/20059/\">诸天最强大佬</a>/七只跳蚤</li><li><a href=\"http://www.xbiquge.la/26/26461/\">穿梭诸天万域</a>/檐外细雨</li><li><a href=\"http://www.xbiquge.la/25/25035/\">九龙拉棺</a>/舞独魂灵</li><li><a href=\"http://www.xbiquge.la/25/25300/\">极品阎罗太子爷</a>/鬼哭老朽</li><li><a href=\"http://www.xbiquge.la/24/24034/\">纵横诸天的武者</a>/我叫排云掌</li><li><a href=\"http://www.xbiquge.la/2/2057/\">星级猎人</a>/陈词懒调</li><li><a href=\"http://www.xbiquge.la/26/26421/\">我是龙族老祖宗</a>/漂泊的黑猫</li><li><a href=\"http://www.xbiquge.la/15/15006/\">轮回乐园</a>/那一只蚊子</li><li><a href=\"http://www.xbiquge.la/26/26331/\">最强万界大穿越</a>/青铜峡</li><li><a href=\"http://www.xbiquge.la/18/18263/\">诸天投影</a>/裴屠狗</li><li><a href=\"http://www.xbiquge.la/20/20795/\">影视世界当神探</a>/冰原三雅</li><li><a href=\"http://www.xbiquge.la/26/26136/\">九星毒奶</a>/育</li></ul>  </div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div id=\"newscontent\">\n" +
                "\n" +
                "<div class=\"l\">\n" +
                "<h2>最近更新小说列表</h2>\n" +
                "<ul>\n" +
                "\n" +
                "<li><span class=\"s1\">[玄幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/19/19332/\" target=\"_blank\">神魔之上</a></span><span class=\"s3\"><a href=\"/19/19332/14856775.html\" target=\"_blank\">第923章 你羞辱我？</a></span><span class=\"s4\">被罚站的豆豆</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[玄幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/28/28918/\" target=\"_blank\">乡野村民</a></span><span class=\"s3\"><a href=\"/28/28918/14856785.html\" target=\"_blank\">第三千一百三十三章 弑神箭</a></span><span class=\"s4\">牙签弟</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[修真小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/20/20751/\" target=\"_blank\">修真必须死</a></span><span class=\"s3\"><a href=\"/20/20751/14856773.html\" target=\"_blank\">正文 第一百八十九章傀儡热</a></span><span class=\"s4\">落跑</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[其他小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/24/24654/\" target=\"_blank\">农门女将：娶个相公来生娃</a></span><span class=\"s3\"><a href=\"/24/24654/14856774.html\" target=\"_blank\">第一百二十四章 名声大噪</a></span><span class=\"s4\">梨子</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[玄幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/22/22123/\" target=\"_blank\">重生之城市修仙</a></span><span class=\"s3\"><a href=\"/22/22123/14856788.html\" target=\"_blank\">第371章 一脚踩爆！</a></span><span class=\"s4\">乘风破天</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[其他小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/22/22912/\" target=\"_blank\">若爱命中注定</a></span><span class=\"s3\"><a href=\"/22/22912/14856797.html\" target=\"_blank\">第163章 察觉</a></span><span class=\"s4\">水木耳</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[都市小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/26/26541/\" target=\"_blank\">绝世兵王</a></span><span class=\"s3\"><a href=\"/26/26541/14856770.html\" target=\"_blank\">654.一生让他毁了 五更</a></span><span class=\"s4\">那根</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[玄幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/29/29780/\" target=\"_blank\">玄傲帝尊</a></span><span class=\"s3\"><a href=\"/29/29780/14856801.html\" target=\"_blank\">第58章 信服</a></span><span class=\"s4\">关三度</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[其他小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/23/23467/\" target=\"_blank\">软萌甜心：首席恶魔来solo</a></span><span class=\"s3\"><a href=\"/23/23467/14856799.html\" target=\"_blank\">第374章 致幻成分的迷药</a></span><span class=\"s4\">时温暖</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[都市小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/29/29547/\" target=\"_blank\">我真是财神啊</a></span><span class=\"s3\"><a href=\"/29/29547/14856790.html\" target=\"_blank\">113、名媛</a></span><span class=\"s4\">财源广进啊</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[其他小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/24/24451/\" target=\"_blank\">高冷大叔，宠妻无度！</a></span><span class=\"s3\"><a href=\"/24/24451/14856779.html\" target=\"_blank\">第381章 我就取消他的继承权！【加更】</a></span><span class=\"s4\">公子如雪</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[修真小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/30/30412/\" target=\"_blank\">仙二代的生存法则</a></span><span class=\"s3\"><a href=\"/30/30412/14856798.html\" target=\"_blank\">第六十二章 该我的谁也抢不走，不该我的，给我我也不稀罕~~</a></span><span class=\"s4\">了了君安</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[玄幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/27/27090/\" target=\"_blank\">我家店里全是妖</a></span><span class=\"s3\"><a href=\"/27/27090/14856802.html\" target=\"_blank\">第151章 难道是情人？</a></span><span class=\"s4\">败叶君</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[玄幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/23/23956/\" target=\"_blank\">仙武寰宇</a></span><span class=\"s3\"><a href=\"/23/23956/14856782.html\" target=\"_blank\">第463章 出大事了</a></span><span class=\"s4\">风轩南冕</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[其他小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/20/20040/\" target=\"_blank\">甜蜜盛宠：99次心动</a></span><span class=\"s3\"><a href=\"/20/20040/14856777.html\" target=\"_blank\">第69章 发现了新大陆</a></span><span class=\"s4\">甜到入心</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[玄幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/30/30307/\" target=\"_blank\">妖界之门</a></span><span class=\"s3\"><a href=\"/30/30307/14856792.html\" target=\"_blank\">第一卷  北疆风云起 第七十五章   筑灵台  十层现</a></span><span class=\"s4\">换渔</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[其他小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31158/\" target=\"_blank\">快穿系统：女主她苏炸全世界</a></span><span class=\"s3\"><a href=\"/31/31158/14856803.html\" target=\"_blank\">第19章 末世病娇，太偏执（16）</a></span><span class=\"s4\">圈成团子</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[科幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/20/20180/\" target=\"_blank\">幻想娱乐帝国</a></span><span class=\"s3\"><a href=\"/20/20180/14856778.html\" target=\"_blank\">第三百三十四章 八大项目</a></span><span class=\"s4\">大飞艇</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[其他小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/19/19318/\" target=\"_blank\">霸道军少，有点坏</a></span><span class=\"s3\"><a href=\"/19/19318/14856794.html\" target=\"_blank\">第786章 猝不及防的一个吻</a></span><span class=\"s4\">沐家小狸</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[科幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/27/27814/\" target=\"_blank\">我要做阎罗</a></span><span class=\"s3\"><a href=\"/27/27814/14856789.html\" target=\"_blank\">正文 第96章：每天起床第一句</a></span><span class=\"s4\">厄夜怪客</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[都市小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/16/16882/\" target=\"_blank\">我的八零年代</a></span><span class=\"s3\"><a href=\"/16/16882/14856772.html\" target=\"_blank\">第二百七十二章 两地</a></span><span class=\"s4\">磨刀堂主</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[其他小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/22/22510/\" target=\"_blank\">农门有狂妻：公子，别矜持</a></span><span class=\"s3\"><a href=\"/22/22510/14856780.html\" target=\"_blank\">第409章 易老和席老</a></span><span class=\"s4\">织梦双鱼</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[网游小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/28/28958/\" target=\"_blank\">斗罗之异数</a></span><span class=\"s3\"><a href=\"/28/28958/14856781.html\" target=\"_blank\">第245章 星斗大森林</a></span><span class=\"s4\">碧空玄月</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[其他小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/25/25579/\" target=\"_blank\">大时代的梦</a></span><span class=\"s3\"><a href=\"/25/25579/14856791.html\" target=\"_blank\">第九十一章 唐三彩（8）</a></span><span class=\"s4\">莞简妤</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[玄幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/30/30671/\" target=\"_blank\">重生之都市尸神</a></span><span class=\"s3\"><a href=\"/30/30671/14856795.html\" target=\"_blank\">正文 第二十六章 治病二</a></span><span class=\"s4\">爱吃肉的疯子</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[网游小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/19/19090/\" target=\"_blank\">大棋圣</a></span><span class=\"s3\"><a href=\"/19/19090/14856800.html\" target=\"_blank\">第488章 开门揖盗</a></span><span class=\"s4\">空明音</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[其他小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/26/26898/\" target=\"_blank\">早安，少将大人</a></span><span class=\"s3\"><a href=\"/26/26898/14856787.html\" target=\"_blank\">第七十七章</a></span><span class=\"s4\">明月稻花</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[玄幻小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/18/18861/\" target=\"_blank\">最强天眼皇帝</a></span><span class=\"s3\"><a href=\"/18/18861/14856771.html\" target=\"_blank\">第866章 一脚踩破</a></span><span class=\"s4\">寒食西风</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[都市小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/27/27562/\" target=\"_blank\">偷爱</a></span><span class=\"s3\"><a href=\"/27/27562/14856776.html\" target=\"_blank\">第三百四十八章</a></span><span class=\"s4\">火烧风</span></li>\n" +
                "\n" +
                "<li><span class=\"s1\">[修真小说]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/29/29678/\" target=\"_blank\">一剑朝天</a></span><span class=\"s3\"><a href=\"/29/29678/14856783.html\" target=\"_blank\">北境风光 第四十三章 手里有瓢？</a></span><span class=\"s4\">青涩的叶</span></li>\n" +
                "\n" +
                " </ul>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div class=\"r\">\n" +
                "<h2>最新入库小说</h2>\n" +
                "<ul>\n" +
                "<li><span class=\"s1\">[都市]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31246/\">深情入腑，岁月且姑</a></span><span class=\"s5\">五木木</span></li>\n" +
                "<li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31235/\">魔战曲</a></span><span class=\"s5\">云清见月明</span></li>\n" +
                "<li><span class=\"s1\">[都市]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31258/\">隐婚挚爱总裁的冷妻</a></span><span class=\"s5\">无花小仙</span></li>\n" +
                "<li><span class=\"s1\">[都市]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31251/\">都市极品巫医</a></span><span class=\"s5\">为轻尘</span></li>\n" +
                "<li><span class=\"s1\">[网游]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31247/\">相思长恨歌</a></span><span class=\"s5\">红豆月月</span></li>\n" +
                "<li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31243/\">邪灵战神</a></span><span class=\"s5\">风羽飞扬</span></li>\n" +
                "<li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31255/\">格兰</a></span><span class=\"s5\">耳东居士</span></li>\n" +
                "<li><span class=\"s1\">[其他]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31241/\">情定两世：我是你的玲珑妃</a></span><span class=\"s5\">夜靥</span></li>\n" +
                "<li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31250/\">伊斯卡诺之主</a></span><span class=\"s5\">弓兴言</span></li>\n" +
                "<li><span class=\"s1\">[其他]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31249/\">俏妃逆天记</a></span><span class=\"s5\">苏贯贯</span></li>\n" +
                "<li><span class=\"s1\">[其他]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31240/\">倾天下</a></span><span class=\"s5\">疏烟淡月</span></li>\n" +
                "<li><span class=\"s1\">[穿越]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31239/\">医世荣华</a></span><span class=\"s5\">楼小安</span></li>\n" +
                "<li><span class=\"s1\">[修真]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31260/\">六界聊天群</a></span><span class=\"s5\">墨冥三千</span></li>\n" +
                "<li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31244/\">爵魂</a></span><span class=\"s5\">寻梦晓二</span></li>\n" +
                "<li><span class=\"s1\">[其他]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31254/\">捡个王爷去种田</a></span><span class=\"s5\">小丸子</span></li>\n" +
                "<li><span class=\"s1\">[穿越]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31237/\">读唐</a></span><span class=\"s5\">还是那个唐</span></li>\n" +
                "<li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31256/\">飞龙战神</a></span><span class=\"s5\">jiang师徒儿</span></li>\n" +
                "<li><span class=\"s1\">[其他]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31252/\">风筝与瞳孔</a></span><span class=\"s5\">胭脂豆</span></li>\n" +
                "<li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31245/\">最强红包皇帝</a></span><span class=\"s5\">侠扯蛋</span></li>\n" +
                "<li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31236/\">不死武皇</a></span><span class=\"s5\">妖月夜</span></li>\n" +
                "<li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31238/\">重生之再世毒妃</a></span><span class=\"s5\">桃花漓漓</span></li>\n" +
                "<li><span class=\"s1\">[修真]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31253/\">西游之暗黑世界</a></span><span class=\"s5\">八臂九纹龙</span></li>\n" +
                "<li><span class=\"s1\">[都市]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31261/\">闪婚神秘老公</a></span><span class=\"s5\">我是木木</span></li>\n" +
                "<li><span class=\"s1\">[其他]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31259/\">官配要放身边养</a></span><span class=\"s5\">馆君</span></li>\n" +
                "<li><span class=\"s1\">[玄幻]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31262/\">重生之我为人祖</a></span><span class=\"s5\">我恨这</span></li>\n" +
                "<li><span class=\"s1\">[都市]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31242/\">丧尸吃了你的脑子</a></span><span class=\"s5\">MUP96</span></li>\n" +
                "<li><span class=\"s1\">[其他]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31263/\">冷家二小姐</a></span><span class=\"s5\">兮若风</span></li>\n" +
                "<li><span class=\"s1\">[都市]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31248/\">重生归来当首富</a></span><span class=\"s5\">莫奈何</span></li>\n" +
                "<li><span class=\"s1\">[其他]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31264/\">二次初恋</a></span><span class=\"s5\">花儿度</span></li>\n" +
                "<li><span class=\"s1\">[都市]</span><span class=\"s2\"><a href=\"http://www.xbiquge.la/31/31257/\">影后打脸指南</a></span><span class=\"s5\">妃缱绻</span></li>\n" +
                "\n" +
                "</ul>\n" +
                "\n" +
                "</div><div class=\"clear\"></div>\n" +
                "\n" +
                "</div></div>\n" +
                "\n" +
                "    </div>\n" +
                "    \n" +
                "<div id=\"firendlink\">\n" +
                "友情连接：<a href=\"http://www.xbiquge.la/\" target=\"_blank\">新笔趣阁</a>\n" +
                "<a href=\"http://www.xbiquge.la/2/2210/\" target=\"_blank\">全职法师</a>\n" +
                "<a href=\"http://www.xbiquge.la/1/1089/\" target=\"_blank\">妖魔战神</a>\n" +
                "<a href=\"http://www.xbiquge.la/0/69/\" target=\"_blank\">帝霸</a>\n" +
                "\n" +
                "<a href=\"http://www.xbiquge.la/1/1064/\" target=\"_blank\">原始战记</a>\n" +
                "<a href=\"http://www.xbiquge.la/3/3844/\" target=\"_blank\">御天神帝</a>\n" +
                "\n" +
                "<a href=\"http://www.douluo123.com\" target=\"_blank\">斗罗大陆3龙王传说\n" +
                "</a>\n" +
                "<a href=\"http://www.bxwu.net\" target=\"_blank\">免费小说</a>\n" +
                "<a href=\"http://www.bage01.com/\" target=\"_blank\">八哥电影网</a>\n" +
                "<a href=\"http://www.abcsee.net\" target=\"_blank\">北辰文学网</a>\n" +
                "<a href=\"http://biqugedd.com/\" target=\"_blank\">寒门崛起</a>\n" +
                "<a href=\"http://www.hebaoge.com/\" target=\"_blank\"> 荷包网</a>\n" +
                "<a href=\"http://www.bxwu.net/\" target=\"_blank\"> 免费小说\n" +
                "</a>\n" +
                "<a href=\"http://www.zgls5000.net\" target=\"_blank\"> 中国历史网\n" +
                "</a>\n" +
                "<a href=\"http://www.txt456.cc\" target=\"_blank\">TXT小说下载</a>\n" +
                "<a href=\"http://www.lasmr.com\" target=\"_blank\">男人小说</a>\n" +
                "<a href=\"http://www.fuhao56.com\" target=\"_blank\">大富豪影院</a>\n" +
                "<a href=\"http://www.xinquanji.com\" target=\"_blank\">全集网</a>\n" +
                "<a href=\"http://www.52kaoyan.cn\" target=\"_blank\">考研资料下载</a>\n" +
                "<a href=\"http://www.jinguatv.com\" target=\"_blank\">金瓜影视</a>\n" +
                "<a href=\"http://www.jilupianzhijia.com\" target=\"_blank\">纪录片之家</a>\n" +
                "<a href=\"http://care.qm120.com\" target=\"_blank\">全民保健网</a>\n" +
                "<a href=\"http://sobo.me\" target=\"_blank\">神马电影</a>\n" +
                "<a href=\"http://www.xj917.com\" target=\"_blank\">新疆综合网</a>\n" +
                "<a href=\"http://85duc.alihuahua.com\" target=\"_blank\">85度C</a>\n" +
                "<a href=\"http://tecenet.com\" target=\"_blank\">医疗器械</a>\n" +
                "<a href=\"http://www.5200wx.com\" target=\"_blank\">我爱小说</a>\n" +
                "<a href=\"http://www.iface.com.cn\" target=\"_blank\">爱容网</a>\n" +
                "<a href=\"http://www.meiwen1314.com\" target=\"_blank\">星辰美文网</a>\n" +
                "<a href=\"http://liuliushe.com\" target=\"_blank\">福利视频</a>\n" +
                "<a href=\"http://www.zanxsq.com\" target=\"_blank\">真爱社区</a>\n" +
                "<a href=\"http://www.tenchong.com\" target=\"_blank\">高清视频会议系统</a>\n" +
                "<a href=\"http://wuluhe.com\" target=\"_blank\">翡翠</a>\n" +
                "<a href=\"http://www.315fangwei.com\" target=\"_blank\">防伪查询</a>\n" +
                "<a href=\"http://www.xs91.net/\" target=\"_blank\">约稿网</a>\n" +
                "<a href=\"http://www.zunvyou.cn/\" target=\"_blank\">租女友</a>\n" +
                "<a href=\"http://www.44pq.com\" target=\"_blank\">永生小说网</a>\n" +
                "<a href=\"http://www.fylunwen.com\" target=\"_blank\">代写毕业论文</a>\n" +
                "<a href=\"http://www.yy0608.com\" target=\"_blank\">yy4480</a>\n" +
                "<a href=\"http://www.ttmj.cc\" target=\"_blank\">美剧天堂</a>\n" +
                "<a href=\"http://www.4008808840.com\" target=\"_blank\">股票配资</a>\n" +
                "<a href=\"http://92kshu.com\" target=\"_blank\">就爱看书网</a>\n" +
                "<a href=\"http://www.xzdlq.com\" target=\"_blank\">传奇万能登陆器</a>\n" +
                "<a href=\"http://www.neihan.net\" target=\"_blank\">内涵图</a>\n" +
                "<a href=\"http://www.aqiwen.com\" target=\"_blank\">爱奇闻</a>\n" +
                "<a href=\"http://www.315fangwei.com\" target=\"_blank\">防伪标签制作</a>\n" +
                "<a href=\"http://dashi.aipai.com\" target=\"_blank\">视频剪辑</a>\n" +
                "<a href=\"http://www.zhuangjiyuan.com\" target=\"_blank\">系统</a>\n" +
                "<a href=\"http://www.pgygho.com\" target=\"_blank\">ghost win7</a>\n" +
                "<a href=\"http://www.jsgho.net\" target=\"_blank\">电脑技术</a>\n" +
                "<a href=\"http://www.smdyy.cc\" target=\"_blank\">神马电影网</a>(邮箱见顶端)</div>\n" +
                "        <div class=\"dahengfu\"><script type=\"text/javascript\">bottom();</script></div>\n" +
                "\n" +
                "<div class=\"footer\">\n" +
                "    <div class=\"footer_link\"></div>\n" +
                "    <div class=\"footer_cont\">\n" +
                "      \n" +
                "    <script>footer();right();dl();</script>\n" +
                "    <script charset=\"utf-8\" src=\"http://www.baidu.com/js/opensug.js\"></script>\n" +
                "  </div>\n" +
                "</div>\n" +
                "<script>\n" +
                "    (function(){\n" +
                "        var bp = document.createElement('script');\n" +
                "        bp.src = '//push.zhanzhang.baidu.com/push.js';\n" +
                "        var s = document.getElementsByTagName(\"script\")[0];\n" +
                "        s.parentNode.insertBefore(bp, s);\n" +
                "    })();\n" +
                "</script>\n" +
                "<script>\n" +
                "(function(){\n" +
                "    var bp = document.createElement('script');\n" +
                "    var curProtocol = window.location.protocol.split(':')[0];\n" +
                "    if (curProtocol === 'https') {\n" +
                "        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';        \n" +
                "    }\n" +
                "    else {\n" +
                "        bp.src = 'http://push.zhanzhang.baidu.com/push.js';\n" +
                "    }\n" +
                "    var s = document.getElementsByTagName(\"script\")[0];\n" +
                "    s.parentNode.insertBefore(bp, s);\n" +
                "})();\n" +
                "</script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "        \n" +
                "  </div>\n" +
                "</body>\n" + "</html>" +
                "\n";
        new HttpSnoopClientHandler(new CrawlerContext()).detailContent(content);

    }


}
