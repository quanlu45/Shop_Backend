package com.cdd.eshop.bean.po;


import lombok.Data;

import javax.persistence.*;

/**
 * 商品类型
 *
 * @author quan
 * @date 2020/12/22
 */
@Data
@Entity
@Table(name = "tb_goods_type")
public class GoodsType {

    /**
     * 商品类型Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer typeId;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型代码
     */
    private String typeCode;


    /**
     * 类型描述
     */
    private String typeDesc;


    /**
     * 父类型代码
     */
    private String parentTypeCode;


    /**
     * 是否逻辑删除
     */
    private Boolean isDelete;
}
