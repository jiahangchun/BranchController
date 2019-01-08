package com.jiahangchun.mybatis;

import com.alibaba.fastjson.JSON;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/7 下午4:46
 **/
@RestController
public class VCController {

    @Resource
    private VCTestMapper vcTestMapper;

    @RequestMapping(value = "/vc/get", method={ RequestMethod.GET})
    public Object first(@RequestParam(required = false,name = "a",defaultValue = "aaa")String a) {
        System.out.println(a);
        return JSON.toJSONString(vcTestMapper.findByState("ab") );
    }

}
