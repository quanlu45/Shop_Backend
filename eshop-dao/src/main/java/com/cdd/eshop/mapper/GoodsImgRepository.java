package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.GoodsImg;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品img存储库
 *
 * @author quan
 * @date 2021/01/06
 */
@Repository
public interface GoodsImgRepository extends JpaRepository<GoodsImg,Integer> {


    /**
     * 商品Id删除所有通过图片
     *
     * @param goodsId 商品Id
     */
    @Transactional
    @Modifying
    @Query("delete from GoodsImg img where img.goodsId=:goodsId")
    void deleteAllByGoodsId(@Param("goodsId") Integer goodsId);

    /**
     * 通过商品Id列表找到所有图片
     *
     * @param goodsIds 商品id
     * @return {@link List<GoodsImg>}
     */
    @Query("select img from GoodsImg img " +
            "where img.goodsId in (:goodsIds) ")
    List<GoodsImg> findAllByGoodsIdIn(@Param("goodsIds") List<Integer> goodsIds);

    /**
     * 过商品Id找到所有图片
     *
     * @param goodsId 商品Id
     * @return {@link List<GoodsImg>}
     */
    @Query("select img from GoodsImg img " +
            "where img.goodsId =:goodsId ")
    List<GoodsImg> findAllByGoodsId(@Param("goodsId")Integer goodsId);
}
