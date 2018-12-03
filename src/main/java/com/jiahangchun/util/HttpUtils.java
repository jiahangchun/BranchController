//package com.jiahangchun.util;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.params.ConnManagerParams;
//import org.apache.http.conn.params.ConnPerRouteBean;
//import org.apache.http.conn.scheme.PlainSocketFactory;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.entity.InputStreamEntity;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.util.EntityUtils;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.io.*;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.nio.charset.Charset;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2018/6/22 下午3:30
// **/
//@Slf4j
//public class HttpUtils {
//    private static HttpClient httpClient = new DefaultHttpClient();
//
//    private static HttpParams httpParams;
//    private static ClientConnectionManager connectionManager;
//
//    /**
//     * 最大连接数
//     */
//    public final static int MAX_TOTAL_CONNECTIONS = 800;
//    /**
//     * 获取连接的最大等待时间
//     */
//    public final static int WAIT_TIMEOUT = 60000;
//    /**
//     * 每个路由最大连接数
//     */
//    public final static int MAX_ROUTE_CONNECTIONS = 400;
//    /**
//     * 连接超时时间
//     */
//    public final static int CONNECT_TIMEOUT = 30000;
//    /**
//     * 读取超时时间
//     */
//    public final static int READ_TIMEOUT = 30000;
//
//    static {
//        try{
//            httpParams = new BasicHttpParams();
//            // 设置最大连接数
//            ConnManagerParams.setMaxTotalConnections(httpParams, MAX_TOTAL_CONNECTIONS);
//            // 设置获取连接的最大等待时间
//            ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);
//            // 设置每个路由最大连接数
//            ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);
//            ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);
//            // 设置连接超时时间
//            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
//            // 设置读取超时时间
//            HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
//
//            SSLContext ctx = SSLContext.getInstance("TLS");
//            X509TrustManager tm = new X509TrustManager() {
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//                @Override
//                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
//                @Override
//                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
//            };
//            ctx.init(null, new TrustManager[] { tm }, null);
//            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            SchemeRegistry registry = new SchemeRegistry();
//            registry.register(new Scheme("https", 443, ssf));
//            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//
//            connectionManager = new ThreadSafeClientConnManager(httpParams, registry);
//            //TODO httpClient调优
//            //TODO httpClient设置以上初始化参数后，https://api.weixin.qq.com/sns/userinfo 访问不了，该问题待排查
//            httpClient = new DefaultHttpClient(connectionManager);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static String getWithApiSign(String url, Map<String, String> params) {
//        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
//            pairs.add(nameValuePair);
//        }
//
//        return getWithApiSign(url, pairs);
//    }
//
//    /**
//     * Get请求
//     * @param url
//     * @param params
//     * @return
//     */
//    public static String getWithApiSign(String url, List<NameValuePair> params) {
////        params = fillApiSign(params);
//        System.out.println(JSON.toJSON(params));
//        String body = null;
//        try {
//            // Get请求
//            HttpGet httpget = new HttpGet(url);
//            // 设置参数
//            String str = EntityUtils.toString(new UrlEncodedFormEntity(params, "utf-8"));
//            String requestUrl = httpget.getURI().toString() + "?" + str;
//            System.out.println("requestUrl:"+requestUrl);
//            httpget.setURI(new URI(requestUrl));
//            // 发送请求
//            HttpResponse httpresponse = httpClient.execute(httpget);
//            // 获取返回数据
//            HttpEntity entity = httpresponse.getEntity();
//            body = EntityUtils.toString(entity);
//            if (entity != null) {
//                entity.consumeContent();
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        return body;
//    }
//
//    public static String post(String url, Map<String,String> params){
//        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//        for(Map.Entry<String,String> entry: params.entrySet()){
//            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
//            pairs.add(nameValuePair);
//        }
//
//        return postWithApiSign(url, pairs);
//    }
//
//    public static String postFileWithSign(String url, List<NameValuePair> params, File file) {
////        params = fillApiSign(params);
//        String body = null;
//        try {
//            //post请求
//            HttpPost httpPost = new HttpPost(url);
//
//            // 把文件转换成流对象FileBody
//            FileBody fileBody = new FileBody(file);
//
//            MultipartEntity multipartEntity = new MultipartEntity();
//            //设置需要上传的文件
//            multipartEntity.addPart("file", fileBody);//相当于<input type="file" name="file"/>
//            //设置其他参数
//            if (params != null && params.isEmpty() == false) {
//                for (NameValuePair nameValuePair : params) {
//                    StringBody paramValue = new StringBody(nameValuePair.getValue(), Charset.forName("UTF-8"));
//                    multipartEntity.addPart(nameValuePair.getName(), paramValue);
//                }
//            }
//            httpPost.setEntity(multipartEntity);
//            //发送请求
//            HttpResponse httpresponse = httpClient.execute(httpPost);
//            // 获取返回数据
//            HttpEntity entity = httpresponse.getEntity();
//            body = EntityUtils.toString(entity);
//            if (entity != null) {
//                entity.consumeContent();
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return body;
//    }
//
//    /**
//     * // Post请求
//     * @param url
//     * @param params
//     * @return
//     */
//    public static String postWithApiSign(String url, List<NameValuePair> params) {
////        params = fillApiSign(params);
//
//        System.out.println("params:"+ JSON.toJSONString(params));
//
//        String body = null;
//        try {
//            // Post请求
//            HttpPost httppost = new HttpPost(url);
//            // 设置参数
//            httppost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
//            // 发送请求
//            HttpResponse httpresponse = httpClient.execute(httppost);
//            // 获取返回数据
//            HttpEntity entity = httpresponse.getEntity();
//
//
//            body = EntityUtils.toString(entity);
//            if (entity != null) {
//                entity.consumeContent();
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return body;
//    }
//
//    public static String postWithData(String url, List<NameValuePair> params, byte[] postData) {
//        String body = null;
//        try {
//            // Post请求
//            HttpPost httpPost = new HttpPost(url);
//            // 设置参数
//            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//
//            //设置post数据
//            if (postData != null && postData.length > 0) {
//                InputStream inputStream = new ByteArrayInputStream(postData);
//                httpPost.setEntity(new InputStreamEntity(inputStream, postData.length));
//            }
//
//            // 发送请求
//            HttpResponse httpresponse = httpClient.execute(httpPost);
//            // 获取返回数据
//            HttpEntity entity = httpresponse.getEntity();
//            body = EntityUtils.toString(entity);
//            if (entity != null) {
//                entity.consumeContent();
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return body;
//    }
//
////    private static List<NameValuePair> fillApiSign(List<NameValuePair> nameValuePairs){
////        if(nameValuePairs == null){
////            return null;
////        }
////
//////        nameValuePairs.add(new BasicNameValuePair("app_key", Config.appKey));
////
////        Map<String,String> signMap = new TreeMap<String,String>();
////        for(NameValuePair nameValuePair: nameValuePairs){
////            signMap.put(nameValuePair.getName(), (String)nameValuePair.getValue());
////        }
////
////        StringBuilder paramSb = new StringBuilder();
////        for (Map.Entry<String, String> entry : signMap.entrySet()) {
////            paramSb.append(entry.getKey());
////            paramSb.append("=");
////            paramSb.append(entry.getValue());
////            paramSb.append("&");
////        }
////        paramSb.deleteCharAt(paramSb.length() - 1);
////
////
//////        String appPwd = Config.appPwd;
//////        String signStr = appPwd+paramSb.toString()+appPwd;
////
////        System.out.println("signStr:"+signStr);
////        String sign = DigestUtils.md5Hex(signStr);
////
////
////        nameValuePairs.add(new BasicNameValuePair("api_sign", sign));
////
////        return nameValuePairs;
////    }
//}
//
