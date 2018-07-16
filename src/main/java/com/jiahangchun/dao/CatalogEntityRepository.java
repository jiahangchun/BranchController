package com.jiahangchun.dao;

import com.jiahangchun.model.CatalogEntity;
import com.jiahangchun.model.ResourceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.dao
 */
public interface CatalogEntityRepository extends JpaRepository<CatalogEntity,Long> {

     public List<CatalogEntity> findByNameIsLikeAndSourceUrlEquals(String name,String sourceUrl);
}
