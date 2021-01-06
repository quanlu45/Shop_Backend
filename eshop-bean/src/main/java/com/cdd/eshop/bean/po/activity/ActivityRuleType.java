package com.cdd.eshop.bean.po.activity;

/**
 * 活动类型
 *
 * @author quan
 * @date 2021/01/01
 */
public enum  ActivityRuleType {

    DISCOUNT(1,"打折"),
    FULL_REDUCE(1<<1,"满减"),
    ATTACH(1<<2,"附送")
    ;

    private int code;
    private String desc;

    private ActivityRuleType(int code,String desc){
        this.code = code;
        this.desc=desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
