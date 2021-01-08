package com.cdd.eshop.bean.po;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 地址
 *
 * @author quan
 * @date 2020/12/21
 */

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_address")
public class Address {

    /**
     * 地址Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;


    /**
     * 关联用户Id
     */
    private Integer userId;

    /**
     * 地址
     */
    private String address;


    /**
     * 标签
     */
    private String tag;

    /**
     * 状态
     */
    private Short status;

}
