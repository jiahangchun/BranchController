package com.jiahangchun.repository;

import com.jiahangchun.DO.FinanceChangeDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午2:45
 **/
public interface FinanceChangeRepository  extends CrudRepository<FinanceChangeDO, Long> {

    /**
     * 更具bsSn模糊查询
     * @param likeBsSn
     * @return
     */
    List<FinanceChangeDO> findAllByBsSnLike(String likeBsSn);
}
