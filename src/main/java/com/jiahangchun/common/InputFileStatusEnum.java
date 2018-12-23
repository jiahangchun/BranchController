package com.jiahangchun.common;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/23 下午1:24
 **/
public enum InputFileStatusEnum {

    INPUT(1, "导入中"),

    FAILED(2, "失败"),

    SUCCESS(3, "成功"),;

    private int value;

    private String desc;

    InputFileStatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
