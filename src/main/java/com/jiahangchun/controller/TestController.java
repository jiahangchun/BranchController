package com.jiahangchun.controller;

import com.jiahangchun.dao.ResourceInfoRepository;
import com.jiahangchun.manager.NovelManager;
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
    @Autowired
    private NovelManager novelManager;

    @RequestMapping("queryAll")
    @ResponseBody
    public List<ResourceInfo> queryAll(){
        List<ResourceInfo> list = new ArrayList<ResourceInfo>();
        list = resourceInfoRepository.findAll();
        return list;
    }

    @RequestMapping("save")
    @ResponseBody
    public Boolean save(){
        ResourceInfo resourceInfo =new ResourceInfo();
        resourceInfo.setContent("中文乱码问题");
        resourceInfo.setTitle("title");
        ResourceInfo responseResult= resourceInfoRepository.save(resourceInfo);
        System.out.println(responseResult.getId());
        return Boolean.TRUE;
    }


    @RequestMapping("/novels")
    @ResponseBody
    public Boolean downloadNovel(){
        novelManager.download();
        return true;
    }


}
