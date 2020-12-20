package com.cdd.eshop.bean.dto;

import com.cdd.eshop.common.StatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 数据传输对象
 */
@Data
public class ResponseDTO implements Serializable {

    private Object data = null;
    private String msg = "";
    private Integer code = null;

    private ResponseDTO(){};

    public static ResponseDTO success(){
        ResponseDTO dto = new ResponseDTO();
        dto.code = StatusEnum.SUCCESS.getCode();
        dto.msg = "请求成功!";
        return dto;
    };

    public static ResponseDTO success(String msg){
        ResponseDTO dto = new ResponseDTO();
        dto.code = StatusEnum.SUCCESS.getCode();
        dto.msg = "请求成功!";
        return dto;
    }

    public ResponseDTO data(Object data){
        this.data = data;
        return this;
    }

    public ResponseDTO data(){
        this.data = new HashMap<>();
        return this;
    }

    public ResponseDTO msg(String msg){
        this.msg = msg;
        return this;
    }
    public static ResponseDTO error(){
        ResponseDTO dto = new ResponseDTO();
        dto.code = StatusEnum.SERVER_ERROR.getCode();
        dto.msg = "请求错误!";
        return dto;
    }

    public static ResponseDTO error(StatusEnum status,String msg){
        ResponseDTO dto = new ResponseDTO();
        dto.code = status.getCode();
        dto.msg = msg;
        return dto;
    }

}
