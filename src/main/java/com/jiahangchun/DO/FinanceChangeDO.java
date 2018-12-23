package com.jiahangchun.DO;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午2:28
 **/
@Data
@Entity(name = "finance_change")
public class FinanceChangeDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 记账时间
     */
    @Column
    private Date time;

    /**
     * 微信支付业务单号
     */
    @Column
    private String tradeSn;

    /**
     * 资金流水单号
     */
    @Column
    private String financeSn;

    /**
     * 业务名称
     */
    @Column
    private String name;

    /**
     * 1:交易
     * 2:扣除交易手续费
     * 3:未知类型
     */
    @Column
    private Integer bsType;

    /**
     * 1:收入
     * 2:支出
     */
    @Column
    private Integer type;

    /**
     * 金额，单位为分
     */
    @Column
    private Long count;

    /**
     * 账户结余
     */
    @Column
    private Long leftCount;

    /**
     * 资金变更提交申请
     */
    @Column
    private String applyName;

    /**
     * 备注
     */
    @Column
    private String remark;

    /**
     * 业务凭证
     */
    @Column
    private String bsSn;

    /**
     * 导入文件的id
     */
    @Column
    private Long inputFileId;

}
