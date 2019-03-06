//package com.jiahangchun.printer;
//
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2018/11/5 下午3:29
// **/
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.UUID;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.drafts.Draft;
//import org.java_websocket.drafts.Draft_17;
//import org.java_websocket.handshake.ServerHandshake;
//
//@Slf4j
//public class WebSocketClientManager extends WebSocketClient {
//
//    static WebSocketClientManager webSocket = null;
//
//    public static void main(String[] args) throws URISyntaxException {
//        String uri = "ws://172.16.0.220:13528";
//        webSocket = new WebSocketClientManager(new URI(uri), new Draft_17());
//        //建立连接
//        webSocket.connect();
//
//    }
//
//    public WebSocketClientManager(URI serverUri, Draft draft) {
//        super(serverUri, draft);
//    }
//
//    @Override
//    public void onOpen(ServerHandshake serverHandshake) {
//    }
//
//
//    /**
//     * WebSocket回调函数
//     * @param message
//     */
//    @Override
//    public void onMessage(String message) {
//        try {
//            Response response = JSON.parseObject(message, Response.class);
//            log.info("===" + response.getCmd());
//            switch (response.getCmd()) {
//                case "getPrinters":
//                    GetPrintersResponse getPrintersDTO = JSON.parseObject(message, GetPrintersResponse.class);
//                    System.out.println(message);
//                    break;
//                case "print":
//                    GetPrintResponse getPrintResponse = JSON.parseObject(message, GetPrintResponse.class);
//                    System.out.println(message);
//                    break;
//                case "notifyPrintResult":
//                    GetNotifyPrintResultResponse getNotifyPrintResultResponse = JSON.parseObject(message, GetNotifyPrintResultResponse.class);
//                    System.out.println(message);
//                    break;
//                default:
//                    log.error("unknow cmd type");
//                    break;
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    @Override
//    public void onClose(int i, String s, boolean b) {
//
//    }
//
//    @Override
//    public void onError(Exception e) {
//
//    }
//}
