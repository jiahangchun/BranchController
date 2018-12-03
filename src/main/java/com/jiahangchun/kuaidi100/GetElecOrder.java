package com.jiahangchun.kuaidi100;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

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

    private final static String KEY = "cdSvSaFj8527";

    private final static String SECRET = "0ec3bf8e1eda4c86b2ea00bcc06f2bfd";

    private final static String URL = "http://api.kuaidi100.com/eorderapi.do?method=getElecOrder";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Map<String, String> content = new HashMap<>(8);
        content.put("t", System.currentTimeMillis() + "");
        content.put("key", KEY);
        content.put("param", genParamContent());
        content.put("sign", sign(content.get("param"), Long.valueOf(content.get("t"))));
        System.out.println(JSON.toJSON(content));
//        String response = HttpUtils.post(URL, content);
//        System.out.println(response);
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
        return sign(paramStr, t, Optional.of(KEY), Optional.of(SECRET));
    }

    private static String sign(String paramStr, Long t, Optional<String> keyOptional, Optional<String> secretOptional) throws NoSuchAlgorithmException {
        String currentKey = keyOptional.orElse(KEY);
        String secretKey = secretOptional.orElse(SECRET);


        String con = new StringBuilder().append(paramStr).append(t).append(currentKey).append(secretKey).toString();
        String md5 = DigestUtils.md5Hex(con);
        return md5.toUpperCase();
    }

    public static String genParamContent() {
        return "aaa";
    }


}
