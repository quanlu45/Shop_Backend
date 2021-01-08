package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.GoodsImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
     * 通过商品Id找到第一个img
     *
     * @param goodsIds 商品id
     * @return {@link List<GoodsImg>}
     */
    @Query("select img.goodsId,img.imgUrl from GoodsImg img " +
            "where img.goodsId in (:goodsIds) and img.isDelete = 0 " +
            "group by img.goodsId")
    List<GoodsImg> findFirstImgByGoodsIdIn(@Param("goodsIds") List<Integer> goodsIds);

    @Modifying
    @Query("update GoodsImg img set img.isDelete =1 where img.goodsId=:goodsId")
    void deleteAllByGoodsId(@Param("goodsId") Integer goodsId);
}
