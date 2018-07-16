package com.jiahangchun.dao;

import com.jiahangchun.model.ExceptionInfo;
import com.jiahangchun.model.ResourceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.dao
 */
@Transactional
public interface ExceptionInfoRepository  extends JpaRepository<ExceptionInfo,String> {

    public Integer deleteAllByParam(String param);

}
