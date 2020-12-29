package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 商品存储库
 *
 * @author quan
 * @date 2020/12/29
 */
public interface GoodsRepository extends JpaRepository<Goods,Integer>, JpaSpecificationExecutor<Goods> {

}
