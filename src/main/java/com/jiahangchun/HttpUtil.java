package com.jiahangchun;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/6/15 上午10:12
 **/
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.*;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 4/29/15.
 */
public class HttpUtil {
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .setRedirectsEnabled(true)
            .build();

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     */
    public static String sendHttpPost(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttp(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param params  参数(格式:key1=value1&key2=value2)
     */
    public static String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("text/xml;charset=UTF-8");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttp(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param params  参数(格式:key1=value1&key2=value2)
     */
    public static String sendHttpPostWithXML(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttp(httpPost);
    }

    /**
     * 发送 post请求（带文件）
     *
     * @param httpUrl   地址
     * @param maps      参数
     * @param fileLists 附件
     */
//    public static String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists) {
//        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
//        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
//        for (String key : maps.keySet()) {
//            meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
//        }
//        for (File file : fileLists) {
//            FileBody fileBody = new FileBody(file);
//            meBuilder.addPart("files", fileBody);
//        }
//        HttpEntity reqEntity = meBuilder.build();
//        httpPost.setEntity(reqEntity);
//        return sendHttp(httpPost);
//    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param maps    参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttp(httpPost);
    }


    /**
     * 发送 https post 请求
     *
     * @param httpUrl
     * @param maps    参数
     * @return
     */
    public static String sendHttpsPost(String httpUrl, Map<String, String> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttps(httpPost);
    }


    /**
     * 发送 get请求
     *
     * @param httpUrl
     */
    public static String sendHttpGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttp(httpGet);
    }

    /**
     * 发送 get 请求
     *
     * @param httpUrl
     * @param paramMap 参数
     * @return
     */
    public static String sendHttpGet(String httpUrl, Map<String, String> paramMap) {
        HttpGet httpGet = new HttpGet(paramForHttpGet(httpUrl, paramMap));// 创建get请求
        return sendHttp(httpGet);
    }

    /**
     * 发送 get 设置 cookie
     *
     * @param httpUrl
     * @param cookie
     * @return
     */
    public static String sendHttpGet(String httpUrl, String cookie) {
        HttpGet httpGet = new HttpGet(httpUrl);
        httpGet.setHeader("cookie", cookie);
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
        return sendHttp(httpGet);
    }

    /**
     * 发送Get请求
     *
     * @param requestBase
     * @return
     */
    private static String sendHttp(HttpRequestBase requestBase) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            requestBase.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(requestBase);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    private static String sendHttps(HttpRequestBase requestBase) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(requestBase.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            requestBase.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(requestBase);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送 https get请求
     *
     * @param httpUrl
     */
    public static String sendHttpsGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttps(httpGet);
    }

    /**
     * 发送 https post 请求
     *
     * @param httpUrl
     * @param maps    参数
     * @return
     */
    public static String sendHttpsGet(String httpUrl, Map<String, String> maps) {
        HttpGet httpGet = new HttpGet(paramForHttpGet(httpUrl, maps));// 创建httpGet
        return sendHttps(httpGet);
    }

    private static String paramForHttpGet(String httpUrl, Map<String, String> maps) {
        if (maps != null && maps.isEmpty() == false) {
            StringBuilder paramBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                paramBuilder.append("&");
                paramBuilder.append(entry.getKey());
                paramBuilder.append("=");
                paramBuilder.append(entry.getValue());
            }
            return httpUrl + "?" + paramBuilder.toString().substring(1);
        }
        return httpUrl;
    }

    public static void main(String[] args) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("url", "https://item.taobao.com/item.htm?spm=a230r.1.14.47.ebb2eb2UWhDn3&id=548413780208&ns=1&abbucket=16#detail");
        String resp = HttpUtil.sendHttpPost("http://www.wadao.com/conversion/mix2wxUrl", paramMap);
        System.out.println("resp:" + resp);
    }
}

