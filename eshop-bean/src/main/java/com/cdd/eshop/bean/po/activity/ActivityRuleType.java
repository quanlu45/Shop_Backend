package com.cdd.eshop.bean.po.activity;

import java.util.HashMap;

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
    private static final HashMap<Integer, ActivityRuleType> codeMap= new HashMap<>();
    static {
        codeMap.put(DISCOUNT.code,DISCOUNT);
        codeMap.put(FULL_REDUCE.code,FULL_REDUCE);
        codeMap.put(ATTACH.code,ATTACH);
    }

    private final int code;
    private final String desc;

    private ActivityRuleType(int code,String desc){
        this.code = code;
        this.desc=desc;
    }

    public int getCode() {
        return code;
    }

    public static ActivityRuleType getTypeFromCode(Integer code){
        return codeMap.get(code);
    }

    public String getDesc() {
        return desc;
    }
}
