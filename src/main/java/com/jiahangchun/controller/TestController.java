package com.jiahangchun.controller;

import com.jiahangchun.dao.ResourceInfoRepository;
import com.jiahangchun.model.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.controller
 */
@RestController
public class TestController {

    @Autowired
    private ResourceInfoRepository resourceInfoRepository;

    @RequestMapping("queryAll")
    @ResponseBody
    public List<ResourceInfo> queryAll(){
        List<ResourceInfo> list = new ArrayList<ResourceInfo>();
        list = resourceInfoRepository.findAll();
        return list;
    }


}
