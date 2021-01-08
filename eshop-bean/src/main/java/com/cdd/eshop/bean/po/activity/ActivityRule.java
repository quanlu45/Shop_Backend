package com.cdd.eshop.bean.po.activity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 活动规则
 *
 * @author quan
 * @date 2021/01/01
 */

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_activity_rule")
public class ActivityRule{
    /**
     * 规则Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ruleId;

    /**
     * 类型
     */
    private Integer ruleType;

    /**
     * 限制
     */
    private Float ruleLimit;

    /**
     * 值
     */
    private Float ruleVal;

    /**
     * 适用商品类型
     */
    private Integer relationType;

    /**
     * 活动Id
     */
    private Integer activityId;

//    /**
//     * 规则关系商品列表
//     */
//    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
//    private List<RuleGoods> ruleRelationGoodsList;

}
