package com.cdd.eshop.bean.po.activity;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 规则商品关联表
 *
 * @author quan
 * @date 2021/01/05
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_rule_goods")
public class RuleGoods {


    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ruleGoodsId;

    /**
     * 规则Id
     */
    private Integer ruleId;

    /**
     * 商品Id
     */
    private Integer goodsId;
}
