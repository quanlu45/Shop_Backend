package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.ShopCart;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商店购物车库
 *
 * @author quan
 * @date 2021/01/06
 */
@Repository
public interface ShopCartRepository extends JpaRepository<ShopCart,Integer> {

    @Modifying
    @Transactional
    @Query("delete from ShopCart  sc where sc.userId =:userId and sc.cartId =:cartId")
    int deleteByUserIdAndCartId(@Param("userId") Integer userId,@Param("cartId") Integer cartId);

    @Query("select s from ShopCart s where s.userId=:userId")
    List<ShopCart> findAllByUserId(@Param("userId")Integer userId);
}
