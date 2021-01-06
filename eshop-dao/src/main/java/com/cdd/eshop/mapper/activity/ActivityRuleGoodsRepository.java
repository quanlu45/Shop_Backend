package com.cdd.eshop.mapper.activity;

import com.cdd.eshop.bean.po.activity.RuleGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 活动规则商品关联存储库
 *
 * @author quan
 * @date 2021/01/05
 */
@Repository
public interface ActivityRuleGoodsRepository extends JpaRepository<RuleGoods,Integer> {
}
