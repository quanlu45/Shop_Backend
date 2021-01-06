package com.cdd.eshop.controller;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.common.BaseController;
import com.cdd.eshop.service.ActivityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 活动控制器
 *
 * @author quan
 * @date 2021/01/06
 */
@RestController("/v1/activity")
public class ActivityController extends BaseController {

    @Autowired
    ActivityService activityService;

    @GetMapping("/list")
    @ApiOperation("列出所有活动")
    ResponseDTO listActivities(@RequestParam(value = "status",required = false)Short status) {
        return activityService.listActivities(status);
    }

    @GetMapping("/getById")
    @ApiOperation("列出活动详情信息")
    ResponseDTO getActivityDetailsById(Integer activityId) {
        return activityService.getActivityDetailById(activityId);
    }
}
