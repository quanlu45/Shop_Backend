package com.cdd.eshop.bean.po.activity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 活动
 *
 * @author quan
 * @date 2021/01/01
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_activity")
public class Activity {

    /**
     * 活动Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;

    /**
     * 活动名称
     */
    private String activityName;


    /**
     * 活动描述
     */
    private String activityDesc;


    /**
     * 活动海报url
     */
    private String activityPosterUrl;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;


    /**
     * 状态
     */
    private Short status;

}
