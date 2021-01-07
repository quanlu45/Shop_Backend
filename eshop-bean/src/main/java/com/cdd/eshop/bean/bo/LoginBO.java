package com.cdd.eshop.bean.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录业务对象
 *
 * @author quan
 * @date 2021/01/07
 */
@Data
@ApiModel
public class LoginBO {
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty("用户名")
    private String password;
}
