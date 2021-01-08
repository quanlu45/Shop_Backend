package com.cdd.eshop.controller;

import com.cdd.eshop.bean.bo.activity.ActivityBO;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.activity.ActivityRule;
import com.cdd.eshop.common.BaseController;
import com.cdd.eshop.service.ActivityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动管理控制器
 *
 * @author quan
 * @date 2021/01/08
 */
@RestController
@RequestMapping("/v1/activity")
public class ActivityManagerController extends BaseController {

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

    @PostMapping("/saveOrUpdate")
    @ApiOperation("保存或更新")
    ResponseDTO saveOrUpdateActivity(@RequestBody ActivityBO activityBO) {
        return activityService.saveOrUpdateActivity(activityBO);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    ResponseDTO deleteActivityById(@RequestParam(value = "activityId") Integer activityId) {
        return activityService.deleteActivityById(activityId);
    }


    @PostMapping("/rule/delete")
    @ApiOperation("通过Id删除活动规则")
    ResponseDTO deleteActivityRulesById(List<Integer> ruleIds) {
        return activityService.deleteActivityRulesById(ruleIds);
    }
    @GetMapping("/rule/update")
    @ApiOperation("通过Id更新")
    ResponseDTO updateActivityRuleByRuleId(@RequestBody ActivityRule newRule) {
        return activityService.updateActivityRuleByRuleId(newRule);
    }
}
