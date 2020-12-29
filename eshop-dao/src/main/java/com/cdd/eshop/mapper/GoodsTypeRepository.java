package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.GoodsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author quan
 * @date 2020/12/22
 */
@Repository
public interface GoodsTypeRepository extends JpaRepository<GoodsType,Integer> {
}
