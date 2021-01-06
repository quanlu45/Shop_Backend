package com.cdd.eshop.mapper.activity;

import com.cdd.eshop.bean.po.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 活动库
 *
 * @author quan
 * @date 2021/01/01
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity,Integer> {

    @Query("select a from Activity a where a.status<>3 and (:status is null or a.status =: status)")
    List<Activity> listActivity(@Param("status") Short status);
}
