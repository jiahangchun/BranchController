package com.jiahangchun.common;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午3:31
 **/
public enum FinanceChangeBsTypeEnum {
    TRADE(1, "交易"),

    DEDUCT(2, "扣除交易手续费"),

    UNKNOW(3, "未知类型"),

    ;

    private int value;

    private String desc;

    public static FinanceChangeBsTypeEnum getEnumByValue(String appTypeValue) {
        if (appTypeValue == null) {
            return UNKNOW;
        }

        for (FinanceChangeBsTypeEnum appTypeEnum : FinanceChangeBsTypeEnum.values()) {
            if (appTypeValue.contains(appTypeEnum.getDesc()) ) {
                return appTypeEnum;
            }
        }
        return UNKNOW;
    }

    public static FinanceChangeBsTypeEnum getEnumByValue(Integer appTypeValue) {
        if (appTypeValue == null) {
            return UNKNOW;
        }

        for (FinanceChangeBsTypeEnum appTypeEnum : FinanceChangeBsTypeEnum.values()) {
            if (appTypeValue.intValue()== appTypeEnum.getValue()) {
                return appTypeEnum;
            }
        }
        return UNKNOW;
    }



    FinanceChangeBsTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
