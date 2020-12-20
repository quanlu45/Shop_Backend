package com.cdd.eshop.common;

import java.io.Serializable;

public enum StatusEnum implements Serializable {
    SUCCESS(0),
    SERVER_ERROR(500),
    PARAM_ERROR(501),
    UKNOW_ERROR(502),

    AUTH_FAILED(300),
    TOKEN_INVALID(301);



    private Integer code = null;
    StatusEnum(int code){
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}