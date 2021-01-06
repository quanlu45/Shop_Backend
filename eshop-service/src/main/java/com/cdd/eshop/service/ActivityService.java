package com.cdd.eshop.service;

import com.cdd.eshop.bean.bo.activity.ActivityBO;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.activity.ActivityRule;

import java.util.List;

/**
 * 活动服务
 *
 * @author quan
 * @date 2021/01/01
 */
public interface ActivityService {

    /**
     * 保存或更新活动
     *
     * @param activityBO 活动业务对象
     * @return {@link ResponseDTO}
     */
    ResponseDTO saveOrUpdateActivity(ActivityBO activityBO);

    /**
     * 通过Id删除活动
     *
     * @param activityId 活动Id
     * @return {@link ResponseDTO}
     */
    ResponseDTO deleteActivityById(Integer activityId);


    /**
     * 活动列表
     *
     * @param status 状态
     * @return {@link ResponseDTO}
     */
    ResponseDTO listActivities(Short status);

    /**
     * 通过Id得到活动细节
     *
     * @param activityId 活动Id
     * @return {@link ResponseDTO}
     */
    ResponseDTO getActivityDetailById(Integer activityId);


    /**
     * 通过Id删除活动规则
     *
     * @param ruleIds    规则的id
     * @return {@link ResponseDTO}
     */
    ResponseDTO deleteActivityRulesById(List<Integer> ruleIds);

    /**
     * 通过规则Id更新活动规则
     *
     * @param newRule 新规则
     * @return {@link ResponseDTO}
     */
    ResponseDTO updateActivityRuleByRuleId(ActivityRule newRule);

}
