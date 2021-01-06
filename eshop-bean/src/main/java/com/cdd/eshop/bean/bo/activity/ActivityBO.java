package com.cdd.eshop.bean.bo.activity;


import com.cdd.eshop.bean.po.activity.ActivityRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 活动业务对象
 *
 * @author quan
 * @date 2021/01/01
 */
@Data
@ApiModel
public class ActivityBO {


    /**
     * 活动Id
     */
    @ApiModelProperty(name = "活动Id",notes = "为空则新增逻辑，否则为更新逻辑")
    private Integer activityId;

    /**
     * 活动名称
     */
    @ApiModelProperty(name = "活动名称")
    private String activityName;


    /**
     * 活动描述
     */
    @ApiModelProperty(name = "活动描述")
    private String activityDesc;


    /**
     * 活动海报url
     */
    @ApiModelProperty(name = "活动海报url")
    private String activityPosterUrl;

    /**
     * 开始时间
     */
    @ApiModelProperty(name = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(name = "结束时间")
    private Date endTime;

    /**
     * 规则列表
     */
    @ApiModelProperty(name = "规则列表")
    private List<ActivityRule> ruleList;

}
