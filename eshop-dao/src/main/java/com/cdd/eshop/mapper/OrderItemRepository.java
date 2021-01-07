package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单项存储库
 *
 * @author quan
 * @date 2021/01/06
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {


    @Query("select oi from OrderItem oi " +
            "where oi.orderNumber =:orderNumbers " +
            "order by oi.goodsId")
    List<OrderItem> findAllByOrderNumber(@Param("orderNumber")String orderNumber);

}
