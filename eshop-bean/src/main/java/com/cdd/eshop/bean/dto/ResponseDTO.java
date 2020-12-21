package com.cdd.eshop.bean.dto;

import com.cdd.eshop.common.StatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;


/**
 * 响应数据传输对象
 *
 * @author quan
 * @date 2020/12/21
 */
@Data
public class ResponseDTO implements Serializable {


    /**
     * 数据
     */
    private Object data = null;

    /**
     * 消息
     */
    private String msg = "";

    /**
     * 代码
     */
    private Integer code = null;

    private ResponseDTO(){};


    /**
     * 成功
     *
     * @return {@link ResponseDTO}
     */
    public static ResponseDTO success(){
        ResponseDTO dto = new ResponseDTO();
        dto.code = StatusEnum.SUCCESS.getCode();
        dto.msg = "请求成功!";
        return dto;
    };

    /**
     * 成功
     *
     * @param msg 消息
     * @return {@link ResponseDTO}
     */
    public static ResponseDTO success(String msg){
        ResponseDTO dto = new ResponseDTO();
        dto.code = StatusEnum.SUCCESS.getCode();
        dto.msg = "请求成功!";
        return dto;
    }

    /**
     * 数据
     *
     * @param data 数据
     * @return {@link ResponseDTO}
     */
    public ResponseDTO data(Object data){
        this.data = data;
        return this;
    }


    /**
     * 数据
     *
     * @return {@link HashMap<String, Object>}
     */
    public ResponseDTO data(){
        this.data = new HashMap<>();
        return this;
    }

    /**
     * 消息
     *
     * @param msg 消息
     * @return {@link ResponseDTO}
     */
    public ResponseDTO msg(String msg){
        this.msg = msg;
        return this;
    }

    /**
     * 错误
     *
     * @return {@link ResponseDTO}
     */
    public static ResponseDTO error(){
        ResponseDTO dto = new ResponseDTO();
        dto.code = StatusEnum.SERVER_ERROR.getCode();
        dto.msg = "请求错误!";
        return dto;
    }

    /**
     * 错误
     *
     * @param status 状态
     * @param msg    消息
     * @return {@link ResponseDTO}
     */
    public static ResponseDTO error(StatusEnum status,String msg){
        ResponseDTO dto = new ResponseDTO();
        dto.code = status.getCode();
        dto.msg = msg;
        return dto;
    }


    /**
     * 增加键值数据
     *
     * @param key 关键
     * @param val 值
     * @return {@link ResponseDTO}
     */
    @SuppressWarnings("unchecked")
    public ResponseDTO withKeyValueData(String key,Object val){
        if (this.data == null){
            this.data = new HashMap<>();
        }
        if (this.data instanceof HashMap){
            HashMap<String,Object> map = (HashMap<String,Object>)this.data;
            map.put(key,val);
        }
        return this;
    }

}
