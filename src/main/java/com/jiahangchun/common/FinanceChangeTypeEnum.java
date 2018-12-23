package com.jiahangchun.common;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午3:31
 **/
public enum FinanceChangeTypeEnum {
    INCOME(1, "收入"),

    OUT(2, "支出"),

    UNKNOW(3, "未知类型"),;

    private int value;

    private String desc;

    public static FinanceChangeTypeEnum getEnumByValue(String appTypeDesc) {
        if (appTypeDesc == null) {
            return UNKNOW;
        }

        for (FinanceChangeTypeEnum appTypeEnum : FinanceChangeTypeEnum.values()) {
            if (appTypeDesc.contains(appTypeEnum.desc)) {
                return appTypeEnum;
            }
        }
        return UNKNOW;
    }

    public static FinanceChangeTypeEnum getEnumByValue(Integer appTypeValue) {
        if (appTypeValue == null) {
            return UNKNOW;
        }

        for (FinanceChangeTypeEnum appTypeEnum : FinanceChangeTypeEnum.values()) {
            if (appTypeValue.intValue() == appTypeEnum.getValue()) {
                return appTypeEnum;
            }
        }
        return UNKNOW;
    }


    FinanceChangeTypeEnum(int value, String desc) {
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