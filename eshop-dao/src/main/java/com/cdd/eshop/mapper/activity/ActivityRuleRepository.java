package com.cdd.eshop.mapper.activity;

import com.cdd.eshop.bean.po.activity.ActivityRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 规则存储库
 *
 * @author quan
 * @date 2021/01/01
 */
@Repository
public interface ActivityRuleRepository extends JpaRepository<ActivityRule,Integer> {

    @Modifying
    @Transactional
    @Query("delete from ActivityRule r where r.ruleId in (?1)")
    void deleteBatchByIds(List<Integer> ids);
}
