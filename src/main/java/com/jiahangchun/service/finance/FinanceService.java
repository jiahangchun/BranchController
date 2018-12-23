package com.jiahangchun.service.finance;

import com.jiahangchun.DO.FinanceChangeDO;
import com.jiahangchun.DO.FinanceChangeQTO;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午2:56
 **/

public interface FinanceService {

    /**
     * 保存文件入库
     *
     * @param fileName
     */
    public Boolean saveFile(String fileName);

    /**
     * 查询
     * @param
     * @return
     */
    public List<FinanceChangeDO> query(FinanceChangeQTO financeChangeQTO);

}
