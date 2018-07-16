package com.jiahangchun.controller;

import com.jiahangchun.dao.CatalogEntityRepository;
import com.jiahangchun.dao.ExceptionInfoRepository;
import com.jiahangchun.dao.ResourceInfoRepository;
import com.jiahangchun.manager.NovelManager;
import com.jiahangchun.model.CatalogEntity;
import com.jiahangchun.model.ResourceInfo;
import com.jiahangchun.util.JsonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Log4j2
public class TestController {

    @Autowired
    private ResourceInfoRepository resourceInfoRepository;
    @Autowired
    private CatalogEntityRepository catalogEntityRepository;
    @Autowired
    private ExceptionInfoRepository exceptionInfoRepository;
    @Autowired
    private NovelManager novelManager;

    @RequestMapping("queryAll")
    @ResponseBody
    public List<ResourceInfo> queryAll() {
        List<ResourceInfo> list = new ArrayList<ResourceInfo>();
        list = resourceInfoRepository.findAll();
        return list;
    }

    @RequestMapping("queryCatalog")
    @ResponseBody
    public String queryCatalogEntity(@RequestParam("name") String name, @RequestParam("url") String url) {
//        http://localhost:8080/queryCatalog?name=%25%E4%B8%BB%25&url=http://www.biqugexsw.com/paihangbang/
        log.info("param.name:{} ; url:{}", name, url);
        List<CatalogEntity> catalogEntity = catalogEntityRepository.findByNameIsLikeAndSourceUrlEquals(name, url);
        return JsonUtil.toJson(catalogEntity);
    }

    @RequestMapping("deleteExceptionInfo")
    @ResponseBody
    public String deleteExceptionInfo(@RequestParam("param") String param) {
        log.info("param.param:{}:", param);
        Integer integer = exceptionInfoRepository.deleteAllByParam(param);
        return JsonUtil.toJson(integer);
    }


    @RequestMapping("save")
    @ResponseBody
    public Boolean save() {
        ResourceInfo resourceInfo = new ResourceInfo();
        resourceInfo.setContent("中文乱码问题");
        resourceInfo.setTitle("title");
        ResourceInfo responseResult = resourceInfoRepository.save(resourceInfo);
        System.out.println(responseResult.getId());
        return Boolean.TRUE;
    }


    @RequestMapping("/novels")
    @ResponseBody
    public Boolean downloadNovel() {
        novelManager.download();
        return true;
    }


}
