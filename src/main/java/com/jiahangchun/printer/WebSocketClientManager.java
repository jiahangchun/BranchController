package com.jiahangchun.printer;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/5 下午3:29
 **/

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

@Slf4j
public class WebSocketClientManager extends WebSocketClient {

    static WebSocketClientManager webSocket = null;

    public static void main(String[] args) throws URISyntaxException {
        String uri = "ws://172.16.0.220:13528";
        webSocket = new WebSocketClientManager(new URI(uri), new Draft_17());
        //建立连接
        webSocket.connect();

    }

    public WebSocketClientManager(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        //获取打印机列表
        String getPrinterListCmd = "{\"requestID\":\"12345678901234567890\",\"verson\":\"1.0\",\"cmd\":\"getPrinters\"}";
        webSocket.send(getPrinterListCmd);
        String taskID = UUID.randomUUID().toString();
        String printCmd = "{\n" +
                "\t\"cmd\": \"print\",\n" +
                "\t\"requestID\": \"123458976\",\n" +
                "\t\"version\": \"1.0\",\n" +
                "\t\"task\": {\n" +
                "\t\t\"taskID\": \"" + taskID + "\",\n" +
                "\t\t\"preview\": true,\n" +
                "\t\t\"printer\": \"\",\n" +
                "\t\t\"previewType\": \"pdf\",\n" +
                "\t\t\"firstDocumentNumber\": 10,\n" +
                "\t\t\"totalDocumentCount\": 100,\n" +
                "\t\t\"documents\": [{\n" +
                "\t\t\t\"documentID\": \"0123456789\",\n" +
                "\t\t\t\"contents\": [{\n" +
                "\t\t\t\t\"data\": {\n" +
                "\t\t\t\t\t\"recipient\": {\n" +
                "\t\t\t\t\t\t\"address\": {\n" +
                "\t\t\t\t\t\t\t\"city\": \"杭州市\",\n" +
                "\t\t\t\t\t\t\t\"detail\": \"良睦路999号乐佳国际大厦2号楼小邮局\",\n" +
                "\t\t\t\t\t\t\t\"district\": \"余杭区\",\n" +
                "\t\t\t\t\t\t\t\"province\": \"浙江省\",\n" +
                "\t\t\t\t\t\t\t\"town\": \"\"\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\"mobile\": \"13012345678\",\n" +
                "\t\t\t\t\t\t\"name\": \"菜鸟网络\",\n" +
                "\t\t\t\t\t\t\"phone\": \"057112345678\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"routingInfo\": {\n" +
                "\t\t\t\t\t\t\"consolidation\": {\n" +
                "\t\t\t\t\t\t\t\"name\": \"杭州\",\n" +
                "\t\t\t\t\t\t\t\"code\": \"hangzhou\"\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\"origin\": {\n" +
                "\t\t\t\t\t\t\t\"name\": \"杭州\",\n" +
                "\t\t\t\t\t\t\t\"code\": \"POSTB\"\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\"sortation\": {\n" +
                "\t\t\t\t\t\t\t\"name\": \"杭州\"\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\"routeCode\": \"123A-456-789\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"sender\": {\n" +
                "\t\t\t\t\t\t\"address\": {\n" +
                "\t\t\t\t\t\t\t\"city\": \"杭州市\",\n" +
                "\t\t\t\t\t\t\t\"detail\": \"文一西路1001号阿里巴巴淘宝城5号小邮局\",\n" +
                "\t\t\t\t\t\t\t\"district\": \"余杭区\",\n" +
                "\t\t\t\t\t\t\t\"province\": \"浙江省\",\n" +
                "\t\t\t\t\t\t\t\"town\": \"\"\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\"mobile\": \"13012345678\",\n" +
                "\t\t\t\t\t\t\"name\": \"阿里巴巴\",\n" +
                "\t\t\t\t\t\t\"phone\": \"057112345678\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"shippingOption\": {\n" +
                "\t\t\t\t\t\t\"code\": \"COD\",\n" +
                "\t\t\t\t\t\t\"services\": {\n" +
                "\t\t\t\t\t\t\t\"SVC-COD\": {\n" +
                "\t\t\t\t\t\t\t\t\"value\": \"200\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t\"TIMED-DELIVERY\": {\n" +
                "\t\t\t\t\t\t\t\t\"value\": \"SEVERAL-DAYS\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t\"PAYMENT-TYPE\": {\n" +
                "\t\t\t\t\t\t\t\t\"value\": \"ON-DELIVERY\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t\"SVC-INSURE\": {\n" +
                "\t\t\t\t\t\t\t\t\"value\": \"1000000\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t\"SVC-PROMISE-DELIVERY\": {\n" +
                "\t\t\t\t\t\t\t\t\"promise-type\": \"SAMEDAY_DELIVERY\"\n" +
                "\t\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\"title\": \"代收货款\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"waybillCode\": \"0123456789\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"signature\": \"19d6f7759487e556ddcdd3d499af087080403277b7deed1a951cc3d9a93c42a7e22ccba94ff609976c5d3ceb069b641f541bc9906098438d362cae002dfd823a8654b2b4f655e96317d7f60eef1372bb983a4e3174cc8d321668c49068071eaea873071ed683dd24810e51afc0bc925b7a2445fdbc2034cdffb12cb4719ca6b7\",\n" +
                "\t\t\t\t\"templateURL\": \"http://cloudprint.cainiao.com/cloudprint/template/getStandardTemplate.json?template_id=101&version=4\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"data\": {\n" +
                "\t\t\t\t\t\"value\": \"测试字段值需要配合自定义区变量名\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"templateURL\": \"http://cloudprint.cainiao.com/template/customArea/440439\"\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}\n" +
                "}";
        webSocket.send(printCmd);

    }

    //WebSocket回调函数
    @Override
    public void onMessage(String message) {
        try {
            Response response = JSON.parseObject(message, Response.class);
            log.info("===" + response.getCmd());
            switch (response.getCmd()) {
                case "getPrinters":
                    GetPrintersResponse getPrintersDTO = JSON.parseObject(message, GetPrintersResponse.class);
                    System.out.println(message);
                    break;
                case "print":
                    GetPrintResponse getPrintResponse = JSON.parseObject(message, GetPrintResponse.class);
                    System.out.println(message);
                    break;
                case "notifyPrintResult":
                    GetNotifyPrintResultResponse getNotifyPrintResultResponse = JSON.parseObject(message, GetNotifyPrintResultResponse.class);
                    System.out.println(message);
                    break;
                default:
                    log.error("unknow cmd type");
                    break;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
