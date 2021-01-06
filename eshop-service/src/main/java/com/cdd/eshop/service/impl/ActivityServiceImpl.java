package com.cdd.eshop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cdd.eshop.bean.bo.activity.ActivityBO;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.activity.Activity;
import com.cdd.eshop.bean.po.activity.ActivityRule;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.mapper.GoodsRepository;
import com.cdd.eshop.mapper.activity.ActivityRepository;
import com.cdd.eshop.mapper.activity.ActivityRuleRepository;
import com.cdd.eshop.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 活动服务实现类
 *
 * @author quan
 * @date 2021/01/01
 */
@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityRuleRepository ruleRepository;

    @Autowired
    GoodsRepository goodsRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveOrUpdateActivity(ActivityBO activityBO) {

        //参数校验，规则检查
        List<ActivityRule> ruleList = activityBO.getRuleList();
        if (null == ruleList || ruleList.size() <1){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"规则不可为空!");
        }

        Activity activity = new Activity();
        BeanUtils.copyProperties(activityBO,activity);

        try {
            activity = activityRepository.saveAndFlush(activity);
            Integer activityId = activity.getActivityId();

            ruleRepository.saveAll(activityBO.getRuleList());
            ruleRepository.flush();

            //拿到Id
            List<Integer> ids = activityBO.getRuleList()
                    .stream()
                    .map(ActivityRule::getActivityId)
                    .collect(Collectors.toList());

            return ResponseDTO
                    .success()
                    .withKeyValueData("activityId",activityId)
                    .withKeyValueData("ruleIdList",ids);

        }catch (Exception e){
            String errorMsg = e.getCause().getMessage();
            log.error("saveOrUpdateActivity ==> activity = {} ,{}", JSON.toJSONString(activity),errorMsg);
            return ResponseDTO.error().msg(errorMsg);
        }
    }

    @Override
    public ResponseDTO deleteActivityById(Integer activityId) {

        //逻辑删除
        try {
            Activity activity = new Activity();
            activity.setActivityId(activityId);
            activity.setStatus((short) 3);
            activityRepository.saveAndFlush(activity);
            return ResponseDTO.success();
        } catch (Exception e) {
            log.error("deleteActivityById ==> activityId = {},{}", activityId, e.getMessage());
            return ResponseDTO.error().msg(e.getCause().getMessage());
        }
    }


    @Override
    public ResponseDTO listActivities(Short status) {

        List<Activity> activityList = activityRepository.listActivity(status);
        if (activityList.size() != 0){

            HashMap<Integer,List<ActivityRule>> ruleMap = new HashMap<>();
            activityList.forEach(a->{
                ruleMap.put(a.getActivityId(),new LinkedList<>());
            });

            List<ActivityRule> ruleList = ruleRepository.findAll();

            ruleList.forEach(rule ->{
                if (ruleMap.containsKey(rule.getActivityId())){
                    ruleMap.get(rule.getActivityId()).add(rule);
                }
            });

            List<ActivityBO> boList = new LinkedList<>();
            activityList.forEach(a->{
                ActivityBO bo = new ActivityBO();
                BeanUtils.copyProperties(a,bo);
                bo.setRuleList(ruleMap.get(bo.getActivityId()));
                boList.add(bo);
            });
            return ResponseDTO.success().withKeyValueData("activityList",boList);
        }else {
            return ResponseDTO.success().msg("活动项为空!");
        }
    }

    @Override
    public ResponseDTO getActivityDetailById(Integer activityId) {
        Optional<Activity> activityOptional =  activityRepository.findById(activityId);
        if (!activityOptional.isPresent() || activityOptional.get().getStatus().intValue() == 3){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"该活动Id不存在或已经删除！");
        }
        ActivityRule queryCondition = new ActivityRule();
        queryCondition.setActivityId(activityId);
        List<ActivityRule> ruleList =  ruleRepository.findAll(Example.of(queryCondition));

        ActivityBO activityBO = new ActivityBO();
        BeanUtils.copyProperties(activityOptional.get(),activityBO);
        activityBO.setRuleList(ruleList);
        return ResponseDTO.success().withKeyValueData("activity",activityBO);
    }

    @Override
    public ResponseDTO deleteActivityRulesById(List<Integer> ruleIds) {

        if (null == ruleIds || ruleIds.size()<1){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"规则列表不可为空！");
        }
        ruleRepository.deleteBatchByIds(ruleIds);
        return ResponseDTO.success();
    }

    @Override
    public ResponseDTO updateActivityRuleByRuleId(ActivityRule newRule) {
        if (null == newRule.getRuleId()){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"ruleId不可为空！");
        }
        ruleRepository.saveAndFlush(newRule);
        return ResponseDTO.success();
    }
}
