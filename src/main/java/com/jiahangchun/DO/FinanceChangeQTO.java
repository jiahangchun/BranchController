package com.jiahangchun.DO;

import lombok.Data;

import java.util.Date;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午4:06
 **/
@Data
public class FinanceChangeQTO {


    /**
     * 记账时间
     */
    private Date time;

    /**
     *微信支付业务单号
     */
    private String tradeSn;

    /**
     *资金流水单号
     */
    private String financeSn;

    /**
     *业务名称
     */
    private String name;

    /**
     * 1:交易
     * 2:扣除交易手续费
     * 3:未知类型
     */
    private Integer bsType;

    /**
     *1:收入
     * 2:支出
     */
    private Integer type;

    /**
     *金额，单位为分
     */
    private Long count;

    /**
     *账户结余
     */
    private Long leftCount;

    /**
     *资金变更提交申请
     */
    private String applyName;

    /**
     *备注
     */
    private String remark;

    /**
     *业务凭证
     */
    private String bsSn;
}
