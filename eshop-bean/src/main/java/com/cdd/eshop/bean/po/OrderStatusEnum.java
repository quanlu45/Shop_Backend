package com.cdd.eshop.bean.po;

import java.util.HashMap;

/**
 * 订单状态枚举
 *
 * @author quan
 * @date 2021/01/07
 */
public enum OrderStatusEnum {
    WAIT_PAY(0,"待付款"),
    WAIT_SHIP(1,"待发货"),
    WAIT_RECEIPT(2,"待收货"),
    WAIT_REFUND(3,"待退款"),
    RECEIPTED(4,"已收货"),
    REFUNDED(4,"已退款"),
    CANCELED(5,"已关闭"),
    DELETED(5,"已删除"),
    ;

    /**
     * 代码
     */
    private final short code;
    /**
     * 描述
     */
    private final String desc;

    private static final HashMap<Short,OrderStatusEnum> codeMap= new HashMap<>();
    static {
        codeMap.put(WAIT_PAY.getCode(),WAIT_PAY);
        codeMap.put(WAIT_SHIP.getCode(),WAIT_SHIP);
        codeMap.put(WAIT_RECEIPT.getCode(),WAIT_RECEIPT);
        codeMap.put(WAIT_REFUND.getCode(),WAIT_REFUND);
        codeMap.put(RECEIPTED.getCode(),RECEIPTED);
        codeMap.put(REFUNDED.getCode(),REFUNDED);
        codeMap.put(CANCELED.getCode(),CANCELED);
        codeMap.put(DELETED.getCode(),DELETED);
    }

    OrderStatusEnum(Integer code, String desc){
        this.code = code.shortValue();
        this.desc = desc;
    }

    public Short getCode(){
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }

    public static OrderStatusEnum getStatusFromCode(Short code){
        return codeMap.get(code);
    }
}
