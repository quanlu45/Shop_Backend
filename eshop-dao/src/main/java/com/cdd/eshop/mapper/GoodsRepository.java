package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品存储库
 *
 * @author quan
 * @date 2020/12/29
 */
@Repository
public interface GoodsRepository extends JpaRepository<Goods,Integer>, JpaSpecificationExecutor<Goods> {

    @Query("select g.goodsId,g.goodsPrice,g.status from Goods g where g.goodsId in (:ids)")
    List<Goods> findAllByGoodsIdIn(@Param("ids")List<Integer> ids);

}
