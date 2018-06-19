package com.jiahangchun;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Hello world!
 *
 * //现在还要知道如何获取OpenId
 * 如何保存
 *
 *
 */
public class App {
//    public static void main(String[] args) throws Exception {
//        String certPath="/Users/hzmk/Desktop/jiahangchun/cert/cert/apiclient_cert.p12";
//
//        //关于商城的支付信息全部存在了AppDB的bizProperty表中
//
//
//        Map packageParams = new TreeMap();
//        String appid="************";//WECHAT_MINA_APP_ID
////        这个接口是通过message来获取一些参数
//        packageParams.put("mch_appid", appid);
////        select * from biz_property
////        where biz_code in(SELECT biz_code FROM app_db.biz_property where value='wx02c6e502006da484' )
////        and (p_key like '%mina_app_id%' or p_key like '%partner_id%' or p_key like '%partner_key%')
////        order by gmt_created desc;
//        String mch_id="1334711001";//WECHAT_MINA_PARTNER_ID
//        packageParams.put("mchid", mch_id);
//        String nonce_str = "4232343765";// 随机字符串
//        packageParams.put("nonce_str", nonce_str);
//        String partner_trade_no="11112";//这个是自己编的
//        packageParams.put("partner_trade_no", partner_trade_no);
////        SELECT * FROM user_db.user_open_info where name='春春';
//        packageParams.put("openid", "oNP4C0TJkC_EOg6X-IIwBaiKggQE");
//        String check_name = "NO_CHECK"; //不验证真是姓名
//        packageParams.put("check_name", check_name);
//        packageParams.put("amount", "1");//一块钱
//        packageParams.put("desc", "贾杭春的测试：从wdzg拿钱到我的零钱");
//        String spbill_create_ip = "183.157.67.92";
//        packageParams.put("spbill_create_ip", spbill_create_ip);
//
//
//        String partnerkey="0c95b11e77974d6698b607a00we55a9c";
//        packageParams.put("sign", getWxParamSign(packageParams, partnerkey));
//
//        String reqUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
//        String xmlData = XmlUtil.map2XmlStr(packageParams);
//        String s = ClientCustomSSL.doProcess(reqUrl, xmlData, mch_id, certPath);
//        System.out.println("返回值"+s);
//        Map<String, String> respMap = XmlUtil.xmlStr2Map(s);
//        for(Map.Entry<String,String> entry:respMap.entrySet()){
//            System.out.println(entry.getKey()+"="+entry.getValue());
//        }
//
//        //err_code_des返回错误提示
//    }

    public static String getWxParamSign(Map<String, String> paramMap, String partnerKey) {
        StringBuilder signSb = new StringBuilder();
        for (Map.Entry entry : paramMap.entrySet()) {
            signSb.append((String) entry.getKey()).append("=").append((String) (entry.getValue())).append("&");
        }

        String toSignStr = new StringBuilder().append(signSb.toString()).append("key=").append(partnerKey).toString();
        System.out.println(new StringBuilder().append("signSb:").append(toSignStr).toString());

        String sign = DigestUtils.md5Hex(toSignStr).toUpperCase();
        return sign;
    }


    private String AuthSessionKey(String appId, String code)  {
        //这个从message_db.wechat_thirdparty获取
        Map<String, String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        params.put("component_appid", "wxbea552fe492b03e8");//之前设置的
        params.put("component_access_token", "10_ihhRqCCTWH1e6cZ-IRWWtejkgxgOfbUydw9UMzO4QM5ZNpk4mrTnaGo_jvPsW5fNG_N-buy1xD_kuL8Z5fvU_lVaNFRN4SY1K_QYQsXpUF_Lg0l9kjSaiUqB-iwBCJcABAXYF");
        String response = HttpUtil.sendHttpPost("https://api.weixin.qq.com/sns/component/jscode2session", params);
        Map<String, String> resMap = JsonUtil.parseJson(response, Map.class);
//        log.info("getThirdParty SessionKey >>>>>>>> response : {}", JsonUtil.toJson(resMap));
        return resMap.get("openid");
    }

    //获取opeNId
    public static void main(String[] args) {
        App app=new App();
        String openId=app.AuthSessionKey("*****************","001SEjX91Q61QP1OUpZ91tGyX91SEjXv");
        System.out.println(openId);
    }
}
