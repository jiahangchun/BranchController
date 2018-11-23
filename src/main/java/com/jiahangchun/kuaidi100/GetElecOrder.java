package com.jiahangchun.kuaidi100;

import com.alibaba.fastjson.JSON;
import com.jiahangchun.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/12 下午2:45
 **/
@Slf4j
public class GetElecOrder {

    private final static String key = "cdSvSaFj8527";

    private final static String secret = "0ec3bf8e1eda4c86b2ea00bcc06f2bfd";

    private final static String url = "http://api.kuaidi100.com/eorderapi.do?method=getElecOrder";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Map<String, String> content = new HashMap<>();
        content.put("t", System.currentTimeMillis() + "");
        content.put("key", key);
        content.put("param", genParamContent());
        content.put("sign", sign(content.get("param"), Long.valueOf(content.get("t"))));
        System.out.println(JSON.toJSON(content));
        String response = HttpUtils.post(url, content);
        System.out.println(response);
    }

    /**
     * 加密
     *
     * @param paramStr
     * @param t
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String sign(String paramStr, Long t) throws NoSuchAlgorithmException {
        return sign(paramStr, t, Optional.of(key), Optional.of(secret));
    }

    private static String sign(String paramStr, Long t, Optional<String> keyOptional, Optional<String> secretOptional) throws NoSuchAlgorithmException {
        String currentKey = keyOptional.orElse(key);
        String secretKey = secretOptional.orElse(secret);


        String con = new StringBuilder().append(paramStr).append(t).append(currentKey).append(secretKey).toString();
        String MD5 = DigestUtils.md5Hex(con);
//         MessageDigest md5 = MessageDigest.getInstance("MD5");
//        String MD5 = md5.digest(con.getBytes(Charset.forName("UTF-8"))).toString();
        return MD5.toUpperCase();
    }

    public static String genParamContent() {
        return "aaa";
    }


}
