package com.jiahangchun.dao;

import com.jiahangchun.model.ResourceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.dao
 */
public interface ResourceInfoRepository extends JpaRepository<ResourceInfo,Long> {
}
