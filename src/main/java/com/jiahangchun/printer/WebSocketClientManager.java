package com.jiahangchun.printer;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/5 下午3:29
 **/

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

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




        //发送打印任务
        String printCmd = "打印任务报文，内容过长此处不粘贴";
        webSocket.send(printCmd);
    }

    //WebSocket回调函数
    @Override
    public void onMessage(String message) {
        //TODO 对打印服务返回的数据进行处理
        System.out.println(message);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
