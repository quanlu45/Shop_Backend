package com.cdd.eshop.bean.po;

import lombok.Data;

import java.util.HashMap;

/**
 * 商品状态
 *
 * @author quan
 * @date 2021/01/06
 */
public enum  GoodsStatus {

    WAIT_ON(0,"待上架"),
    ON(1,"已上架"),
    OFF(2,"已下架"),
    DELETE(3,"已删除")
    ;

    private short code;
    private String desc;

    private static HashMap<Short,GoodsStatus> codeMap= new HashMap<>();
    static {
        codeMap.put((short) 0,WAIT_ON);
        codeMap.put((short) 1,ON);
        codeMap.put((short) 2,OFF);
        codeMap.put((short) 3,DELETE);
    }

    private GoodsStatus(Integer code,String desc){
        this.code = code.shortValue();
        this.desc = desc;
    }

    public Short getCode(){
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }

    public static GoodsStatus getStatusFromCode(Short code){
        return codeMap.get(code);
    }
}
