package com.cdd.eshop.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class UserInfoVo {

    @ApiModelProperty("用户Id")
    Integer userId;

    @ApiModelProperty("用户名")
    String userName;

    @ApiModelProperty("注册时间")
    Date regTime;

    @ApiModelProperty("token")
    String token;

    @ApiModelProperty("头像url")
    String avatarUrl;

}
